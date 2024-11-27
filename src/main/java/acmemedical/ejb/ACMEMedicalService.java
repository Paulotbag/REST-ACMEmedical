package acmemedical.ejb;

import static acmemedical.utility.MyConstants.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmemedical.entity.MedicalCertificate;
import acmemedical.entity.MedicalSchool;
import acmemedical.entity.MedicalTraining;
import acmemedical.entity.Physician;
import acmemedical.entity.SecurityUser;
import acmemedical.entity.SecurityRole;

@Singleton
public class ACMEMedicalService implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    // ===================== Existing Methods =====================

    public List<Physician> getAllPhysicians() {
        LOG.debug("Fetching all physicians...");
        return em.createQuery("SELECT p FROM Physician p", Physician.class).getResultList();
    }

    public Physician getPhysicianById(int id) {
        LOG.debug("Fetching physician with ID: {}", id);
        return em.find(Physician.class, id);
    }

    @Transactional
    public Physician persistPhysician(Physician newPhysician) {
        LOG.debug("Persisting new physician: {}", newPhysician);
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

        SecurityRole userRole = em.createQuery(
                        "SELECT r FROM SecurityRole r WHERE r.roleName = :roleName", SecurityRole.class)
                .setParameter("roleName", USER_ROLE)
                .getSingleResult();

        userForNewPhysician.getRoles().add(userRole);
        userRole.getUsers().add(userForNewPhysician);
        em.persist(userForNewPhysician);
    }

    // ===================== Newly Added Methods =====================

    /**
     * Create a new MedicalCertificate.
     *
     * @param medicalCertificate MedicalCertificate to create.
     * @return The created MedicalCertificate.
     */
    @Transactional
    public MedicalCertificate createMedicalCertificate(MedicalCertificate medicalCertificate) {
        LOG.debug("Persisting new MedicalCertificate: {}", medicalCertificate);
        em.persist(medicalCertificate);
        return medicalCertificate;
    }

    /**
     * Retrieve a MedicalCertificate by its ID.
     *
     * @param id MedicalCertificate ID.
     * @return The MedicalCertificate if found, otherwise null.
     */
    public MedicalCertificate getMedicalCertificateById(int id) {
        LOG.debug("Fetching MedicalCertificate with ID: {}", id);
        return em.find(MedicalCertificate.class, id);
    }

    /**
     * Update a MedicalCertificate by its ID.
     *
     * @param id MedicalCertificate ID.
     * @param updatedCertificate Updated MedicalCertificate data.
     * @return The updated MedicalCertificate, or null if not found.
     */
    @Transactional
    public MedicalCertificate updateMedicalCertificate(int id, MedicalCertificate updatedCertificate) {
        LOG.debug("Updating MedicalCertificate with ID: {}", id);
        MedicalCertificate existingCertificate = getMedicalCertificateById(id);
        if (existingCertificate != null) {
            em.merge(updatedCertificate);
            return updatedCertificate;
        }
        return null;
    }

    /**
     * Delete a MedicalCertificate by its ID.
     *
     * @param id MedicalCertificate ID.
     * @return True if the MedicalCertificate was deleted, otherwise false.
     */
    @Transactional
    public boolean deleteMedicalCertificate(int id) {
        LOG.debug("Deleting MedicalCertificate with ID: {}", id);
        MedicalCertificate certificate = getMedicalCertificateById(id);
        if (certificate != null) {
            em.remove(certificate);
            return true;
        }
        return false;
    }

    // ===================== Other Helper Methods =====================

    public List<MedicalSchool> getAllMedicalSchools() {
        LOG.debug("Fetching all medical schools...");
        return em.createQuery("SELECT ms FROM MedicalSchool ms", MedicalSchool.class).getResultList();
    }

    public MedicalSchool getMedicalSchoolById(int id) {
        LOG.debug("Fetching MedicalSchool with ID: {}", id);
        return em.find(MedicalSchool.class, id);
    }

    @Transactional
    public MedicalSchool persistMedicalSchool(MedicalSchool newMedicalSchool) {
        LOG.debug("Persisting new MedicalSchool: {}", newMedicalSchool);
        em.persist(newMedicalSchool);
        return newMedicalSchool;
    }

    @Transactional
    public MedicalSchool updateMedicalSchool(int id, MedicalSchool updatingMedicalSchool) {
        LOG.debug("Updating MedicalSchool with ID: {}", id);
        MedicalSchool medicalSchoolToBeUpdated = getMedicalSchoolById(id);
        if (medicalSchoolToBeUpdated != null) {
            em.merge(updatingMedicalSchool);
            return updatingMedicalSchool;
        }
        return null;
    }

    public MedicalTraining getMedicalTrainingById(int mtId) {
        LOG.debug("Fetching MedicalTraining with ID: {}", mtId);
        TypedQuery<MedicalTraining> allMedicalTrainingQuery = em.createNamedQuery("MedicalTraining.FIND_BY_ID", MedicalTraining.class);
        allMedicalTrainingQuery.setParameter(PARAM1, mtId);
        return allMedicalTrainingQuery.getSingleResult();
    }

    @Transactional
    public MedicalTraining persistMedicalTraining(MedicalTraining newMedicalTraining) {
        LOG.debug("Persisting new MedicalTraining: {}", newMedicalTraining);
        em.persist(newMedicalTraining);
        return newMedicalTraining;
    }

    @Transactional
    public MedicalTraining updateMedicalTraining(int id, MedicalTraining medicalTrainingWithUpdates) {
        LOG.debug("Updating MedicalTraining with ID: {}", id);
        MedicalTraining medicalTrainingToBeUpdated = getMedicalTrainingById(id);
        if (medicalTrainingToBeUpdated != null) {
            em.merge(medicalTrainingWithUpdates);
            return medicalTrainingWithUpdates;
        }
        return null;
    }
}
