/********************************************************************************************************
 * File:  ACMEMedicalService.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @implemented by Azadeh Sadeghtehrani
 * 
 */
package acmemedical.ejb;

import static acmemedical.utility.MyConstants.DEFAULT_KEY_SIZE;
import static acmemedical.utility.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static acmemedical.utility.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static acmemedical.utility.MyConstants.DEFAULT_SALT_SIZE;
import static acmemedical.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static acmemedical.utility.MyConstants.DEFAULT_USER_PREFIX;
import static acmemedical.utility.MyConstants.PARAM1;
import static acmemedical.utility.MyConstants.PROPERTY_ALGORITHM;
import static acmemedical.utility.MyConstants.PROPERTY_ITERATIONS;
import static acmemedical.utility.MyConstants.PROPERTY_KEY_SIZE;
import static acmemedical.utility.MyConstants.PROPERTY_SALT_SIZE;
import static acmemedical.utility.MyConstants.PU_NAME;
import static acmemedical.utility.MyConstants.USER_ROLE;
import static acmemedical.entity.Physician.ALL_PHYSICIANS_QUERY_NAME;
import static acmemedical.entity.MedicalSchool.ALL_MEDICAL_SCHOOLS_QUERY_NAME;
import static acmemedical.entity.MedicalSchool.IS_DUPLICATE_QUERY_NAME;
import static acmemedical.entity.MedicalSchool.SPECIFIC_MEDICAL_SCHOOL_QUERY_NAME;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmemedical.entity.MedicalTraining;
import acmemedical.entity.Patient;
import acmemedical.entity.MedicalCertificate;
import acmemedical.entity.Medicine;
import acmemedical.entity.Prescription;
import acmemedical.entity.PrescriptionPK;
import acmemedical.entity.SecurityRole;
import acmemedical.entity.SecurityUser;
import acmemedical.entity.Physician;
import acmemedical.entity.MedicalSchool;

@SuppressWarnings("unused")

/**
 * Stateless Singleton EJB Bean - ACMEMedicalService
 */
@Singleton
public class ACMEMedicalService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = LogManager.getLogger();
    
    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;
    
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    public List<Physician> getAllPhysicians() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Physician> cq = cb.createQuery(Physician.class);
        cq.select(cq.from(Physician.class));
        return em.createQuery(cq).getResultList();
    }

    public Physician getPhysicianById(int id) {
        return em.find(Physician.class, id);
    }

    @Transactional
    public Physician persistPhysician(Physician newPhysician) {
        em.persist(newPhysician);
        return newPhysician;
    }

    @Transactional
    public void buildUserForNewPhysician(Physician newPhysician) {
        SecurityUser userForNewPhysician = new SecurityUser();
        userForNewPhysician.setUsername(
            DEFAULT_USER_PREFIX + "_" + newPhysician.getFirstName() + "." + newPhysician.getLastName());
        
        Map<String, String> pbAndjProperties = new HashMap<>();
        pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
        pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
        pbAndjProperties.put(PROPERTY_SALT_SIZE, DEFAULT_SALT_SIZE);
        pbAndjProperties.put(PROPERTY_KEY_SIZE, DEFAULT_KEY_SIZE);
        pbAndjPasswordHash.initialize(pbAndjProperties);
        String pwHash = pbAndjPasswordHash.generate(DEFAULT_USER_PASSWORD.toCharArray());
        userForNewPhysician.setPwHash(pwHash);
        userForNewPhysician.setPhysician(newPhysician);
        
     // TODO ACMECS01 - Use NamedQuery on SecurityRole to find USER_ROLE
        SecurityRole userRole = em.createQuery(
                "SELECT r FROM SecurityRole r WHERE r.roleName = :roleName", SecurityRole.class)
                .setParameter("roleName", USER_ROLE)
                .getSingleResult();
        
        
        userForNewPhysician.getRoles().add(userRole);
        userRole.getUsers().add(userForNewPhysician);
        em.persist(userForNewPhysician);
    }

    @Transactional
    public Medicine setMedicineForPhysicianPatient(int physicianId, int patientId, Medicine newMedicine) {
        Physician physicianToBeUpdated = em.find(Physician.class, physicianId);
        if (physicianToBeUpdated != null) { // Physician exists
            Set<Prescription> prescriptions = physicianToBeUpdated.getPrescriptions();
            prescriptions.forEach(p -> {
                if (p.getPatient().getId() == patientId) {
                    if (p.getMedicine() != null) { // Medicine exists
                        Medicine medicine = em.find(Medicine.class, p.getMedicine().getId());
                        medicine.setMedicine(newMedicine.getDrugName(),
                        				  newMedicine.getManufacturerName(),
                        				  newMedicine.getDosageInformation());
                        em.merge(medicine);
                    }
                    else { // Medicine does not exist
                        p.setMedicine(newMedicine);
                        em.merge(physicianToBeUpdated);
                    }
                }
            });
            return newMedicine;
        }
        else return null;  // Physician doesn't exists
    }

    /**
     * To update a physician
     * 
     * @param id - id of entity to update
     * @param physicianWithUpdates - entity with updated information
     * @return Entity with updated information
     */
    @Transactional
    public Physician updatePhysicianById(int id, Physician physicianWithUpdates) {
    	Physician physicianToBeUpdated = getPhysicianById(id);
        if (physicianToBeUpdated != null) {
            em.refresh(physicianToBeUpdated);
            em.merge(physicianWithUpdates);
            em.flush();
        }
        return physicianToBeUpdated;
    }

    /**
     * To delete a physician by id
     * 
     * @param id - physician id to delete
     */
    @Transactional
    public void deletePhysicianById(int id) {
        Physician physician = getPhysicianById(id);
        if (physician != null) {
            em.refresh(physician);
            /* TODO ACMECS02 - Use NamedQuery on SecurityRole to find this related Student
               so that when we remove it, the relationship from SECURITY_USER table
               is not dangling
            */
            TypedQuery<SecurityUser> findUser = em.createQuery(
                    "SELECT u FROM SecurityUser u WHERE u.physician.id = :physicianId", SecurityUser.class)
                    .setParameter("physicianId", physician.getId());
            
            SecurityUser sUser = findUser.getSingleResult();
            em.remove(sUser);
            em.remove(physician);
        }
    }
    
    public List<MedicalSchool> getAllMedicalSchools() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MedicalSchool> cq = cb.createQuery(MedicalSchool.class);
        cq.select(cq.from(MedicalSchool.class));
        return em.createQuery(cq).getResultList();
    }

    // Why not use the build-in em.find?  The named query SPECIFIC_MEDICAL_SCHOOL_QUERY_NAME
    // includes JOIN FETCH that we cannot add to the above API
    public MedicalSchool getMedicalSchoolById(int id) {
        TypedQuery<MedicalSchool> specificMedicalSchoolQuery = em.createNamedQuery(SPECIFIC_MEDICAL_SCHOOL_QUERY_NAME, MedicalSchool.class);
        specificMedicalSchoolQuery.setParameter(PARAM1, id);
        return specificMedicalSchoolQuery.getSingleResult();
    }
    
    // These methods are more generic.

    public <T> List<T> getAll(Class<T> entity, String namedQuery) {
        TypedQuery<T> allQuery = em.createNamedQuery(namedQuery, entity);
        return allQuery.getResultList();
    }
    
    public <T> T getById(Class<T> entity, String namedQuery, int id) {
        TypedQuery<T> allQuery = em.createNamedQuery(namedQuery, entity);
        allQuery.setParameter(PARAM1, id);
        return allQuery.getSingleResult();
    }

    @Transactional
    public MedicalSchool deleteMedicalSchool(int id) {
        //MedicalSchool ms = getMedicalSchoolById(id);
    	MedicalSchool ms = getById(MedicalSchool.class, MedicalSchool.SPECIFIC_MEDICAL_SCHOOL_QUERY_NAME, id);
        if (ms != null) {
            Set<MedicalTraining> medicalTrainings = ms.getMedicalTrainings();
            List<MedicalTraining> list = new LinkedList<>();
            medicalTrainings.forEach(list::add);
            list.forEach(mt -> {
                if (mt.getCertificate() != null) {
                    MedicalCertificate mc = getById(MedicalCertificate.class, MedicalCertificate.ID_CARD_QUERY_NAME, mt.getCertificate().getId());
                    mc.setMedicalTraining(null);
                }
                mt.setCertificate(null);
                em.merge(mt);
            });
            em.remove(ms);
            return ms;
        }
        return null;
    }
    
    // Please study & use the methods below in your test suites
    
    public boolean isDuplicated(MedicalSchool newMedicalSchool) {
        TypedQuery<Long> allMedicalSchoolsQuery = em.createNamedQuery(IS_DUPLICATE_QUERY_NAME, Long.class);
        allMedicalSchoolsQuery.setParameter(PARAM1, newMedicalSchool.getName());
        return (allMedicalSchoolsQuery.getSingleResult() >= 1);
    }

    @Transactional
    public MedicalSchool persistMedicalSchool(MedicalSchool newMedicalSchool) {
        em.persist(newMedicalSchool);
        return newMedicalSchool;
    }

    @Transactional
    public MedicalSchool updateMedicalSchool(int id, MedicalSchool updatingMedicalSchool) {
    	MedicalSchool medicalSchoolToBeUpdated = getMedicalSchoolById(id);
        if (medicalSchoolToBeUpdated != null) {
            em.refresh(medicalSchoolToBeUpdated);
            medicalSchoolToBeUpdated.setName(updatingMedicalSchool.getName());
            em.merge(medicalSchoolToBeUpdated);
            em.flush();
        }
        return medicalSchoolToBeUpdated;
    }
    
    @Transactional
    public MedicalTraining persistMedicalTraining(MedicalTraining newMedicalTraining) {
        em.persist(newMedicalTraining);
        return newMedicalTraining;
    }
    
    public MedicalTraining getMedicalTrainingById(int mtId) {
        TypedQuery<MedicalTraining> allMedicalTrainingQuery = em.createNamedQuery(MedicalTraining.FIND_BY_ID, MedicalTraining.class);
        allMedicalTrainingQuery.setParameter(PARAM1, mtId);
        return allMedicalTrainingQuery.getSingleResult();
    }

    @Transactional
    public MedicalTraining updateMedicalTraining(int id, MedicalTraining medicalTrainingWithUpdates) {
    	MedicalTraining medicalTrainingToBeUpdated = getMedicalTrainingById(id);
        if (medicalTrainingToBeUpdated != null) {
            em.refresh(medicalTrainingToBeUpdated);
            em.merge(medicalTrainingWithUpdates);
            em.flush();
        }
        return medicalTrainingToBeUpdated;
    }

    /**
     * Create a new MedicalCertificate and persist it in the database.
     *
     * @param medicalCertificate New MedicalCertificate to persist.
     * @return The persisted MedicalCertificate.
     */
    @Transactional
    public MedicalCertificate createMedicalCertificate(MedicalCertificate medicalCertificate) {
        if (medicalCertificate == null) {
            throw new IllegalArgumentException("MedicalCertificate cannot be null");
        }
        em.persist(medicalCertificate);
        return medicalCertificate;
    }

    /**
     * Retrieve a MedicalCertificate by its ID.
     *
     * @param id ID of the MedicalCertificate to retrieve.
     * @return The MedicalCertificate if found, or null otherwise.
     */
    public MedicalCertificate getMedicalCertificateById(int id) {
        return em.find(MedicalCertificate.class, id);
    }

    /**
     * Update an existing MedicalCertificate by its ID.
     *
     * @param id ID of the MedicalCertificate to update.
     * @param medicalCertificate Updated data for the MedicalCertificate.
     * @return The updated MedicalCertificate, or null if the ID does not exist.
     */
    @Transactional
    public MedicalCertificate updateMedicalCertificate(int id, MedicalCertificate medicalCertificate) {
        LOG.debug("Updating MedicalCertificate with ID: {}", id);

        // Fetch the existing MedicalCertificate
        MedicalCertificate existingCertificate = em.find(MedicalCertificate.class, id);

        if (existingCertificate != null) {
            // Update the existing certificate's fields with values from the input object (medicalCertificate)
            if (medicalCertificate.getMedicalTraining() != null) {
                existingCertificate.setMedicalTraining(medicalCertificate.getMedicalTraining());
            }
            if (medicalCertificate.getOwner() != null) {
                existingCertificate.setOwner(medicalCertificate.getOwner());
            }
            // Use the existing `setSigned` method for the `signed` field
            existingCertificate.setSigned(medicalCertificate.getSigned());

            // Merge and return the updated entity
            em.merge(existingCertificate);
            return existingCertificate;
        }

        LOG.debug("MedicalCertificate with ID: {} not found.", id);
        return null;
    }

    /**
     * Delete a MedicalCertificate by its ID.
     *
     * @param id ID of the MedicalCertificate to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    @Transactional
    public boolean deleteMedicalCertificate(int id) {
        MedicalCertificate certificate = em.find(MedicalCertificate.class, id);
        if (certificate == null) {
            return false; // Certificate not found
        }
        em.remove(certificate);
        return true;
    }

    ///////
    public List<Medicine> getAllMedicines() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Medicine> cq = cb.createQuery(Medicine.class);
        cq.select(cq.from(Medicine.class));
        return em.createQuery(cq).getResultList();
    }

    public Medicine getMedicineById(int id) {
        return em.find(Medicine.class, id);
    }

    @Transactional
    public Medicine persistMedicine(Medicine newMedicine) {
        em.persist(newMedicine);
        return newMedicine;
    }

    @Transactional
    public void deleteMedicineById(int id) {
        Medicine medicine = getMedicineById(id);
        if (medicine != null) {
            em.refresh(medicine);
            // Remove associated prescriptions first
            Set<Prescription> prescriptions = medicine.getPrescriptions();
            prescriptions.forEach(prescription -> {
                em.remove(prescription);
            });
            em.remove(medicine);
        }
    }

    ///////
    public List<Patient> getAllPatients() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
        cq.select(cq.from(Patient.class));
        return em.createQuery(cq).getResultList();
    }

    public Patient getPatientById(int id) {
        return em.find(Patient.class, id);
    }

    @Transactional
    public Patient persistPatient(Patient newPatient) {
        em.persist(newPatient);
        return newPatient;
    }

    @Transactional
    public void deletePatientById(int id) {
        Patient patient = getPatientById(id);
        if (patient != null) {
            em.refresh(patient);

            // Get all prescriptions for this patient
            Set<Prescription> prescriptions = patient.getPrescriptions();

            // For each prescription
            prescriptions.forEach(prescription -> {
                // If there's a medical certificate linked to the physician who wrote the prescription
                Physician physician = prescription.getPhysician();
                if (physician != null) {
                    TypedQuery<MedicalCertificate> certQuery = em.createQuery(
                            "SELECT mc FROM MedicalCertificate mc WHERE mc.physician.id = :physicianId",
                            MedicalCertificate.class
                    );
                    certQuery.setParameter("physicianId", physician.getId());
                    List<MedicalCertificate> certificates = certQuery.getResultList();

                }

                // Remove the prescription
                em.remove(prescription);
            });

            // Finally remove the patient
            em.remove(patient);
        }
    }

    public List<Prescription> getAllPrescriptions() {
        return em.createQuery("SELECT p FROM Prescription p", Prescription.class).getResultList();
    }

    public Prescription getPrescriptionById(int physicianId, int patientId) {
        try {
            return em.createQuery("SELECT p FROM Prescription p WHERE p.physicianId = :physicianId AND p.patientId = :patientId", Prescription.class)
                    .setParameter("physicianId", physicianId)
                    .setParameter("patientId", patientId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Or handle as needed
        }
    }

    @Transactional
    public Prescription persistPrescription(Prescription prescription) {
        em.persist(prescription);
        return prescription;
    }

    @Transactional
    public Prescription updatePrescription(int physicianId, int patientId, Prescription updatedPrescription) {
        Prescription existing = getPrescriptionById(physicianId, patientId);
        if (existing != null) {
            existing.setMedicine(updatedPrescription.getMedicine());
            existing.setNumberOfRefills(updatedPrescription.getNumberOfRefills());
            existing.setPrescriptionInformation(updatedPrescription.getPrescriptionInformation());
            em.merge(existing);
            return existing;
        }
        return null;
    }



    @Transactional
    public boolean deletePrescriptionById(int physicianId, int patientId) {
        Prescription prescription = getPrescriptionById(physicianId, patientId);
        if (prescription != null) {
            em.remove(prescription);
            return true;
        }
        return false;
    }
}