package acmemedical.rest.resource;

<<<<<<< HEAD
import static acmemedical.utility.MyConstants.*;

=======
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
<<<<<<< HEAD
=======
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.Patient;

<<<<<<< HEAD
=======
import static acmemedical.utility.MyConstants.*;
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1

@Path(PATIENT_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatientResource {

<<<<<<< HEAD
    private static final Logger LOG = LogManager.getLogger(PatientResource.class);
=======
    private static final Logger LOG = LogManager.getLogger();
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1

    @EJB
    protected ACMEMedicalService service;

<<<<<<< HEAD
    /**
     * Retrieve all patients (ADMIN_ROLE only).
     *
     * @return Response containing a list of patients.
     */
    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getPatients() {
        LOG.debug("Retrieving all patients...");
=======
    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getPatients() {
        LOG.debug("retrieving all patients ...");
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
        List<Patient> patients = service.getAllPatients();
        return Response.ok(patients).build();
    }

<<<<<<< HEAD
    /**
     * Retrieve a specific patient by ID.
     *
     * @param id Patient ID.
     * @return Response containing the patient data or error.
     */
=======
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getPatientById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
<<<<<<< HEAD
        LOG.debug("Retrieving patient with ID: {}", id);
        Patient patient = service.getPatientById(id);
        if (patient == null) {
            return Response.status(Status.NOT_FOUND).entity("Patient not found").build();
        }
        return Response.ok(patient).build();
    }

    /**
     * Add a new patient (ADMIN_ROLE only).
     *
     * @param newPatient New patient to add.
     * @return Response with the added patient.
     */
    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addPatient(Patient newPatient) {
        LOG.debug("Adding new patient: {}", newPatient);
        try {
            Patient createdPatient = service.persistPatient(newPatient);
            return Response.status(Status.CREATED).entity(createdPatient).build();
        } catch (Exception e) {
            LOG.error("Error adding patient", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error adding patient").build();
        }
    }

    /**
     * Update an existing patient (ADMIN_ROLE only).
     *
     * @param id ID of the patient to update.
     * @param updatedPatient Updated patient details.
     * @return Response with the updated patient.
     */
    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updatePatient(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, Patient updatedPatient) {
        LOG.debug("Updating patient with ID: {}", id);
        try {
            Patient existingPatient = service.getPatientById(id);
            if (existingPatient == null) {
                return Response.status(Status.NOT_FOUND).entity("Patient not found").build();
            }
            updatedPatient.setId(id); // Ensure the ID is consistent
            Patient patient = service.updatePatient(updatedPatient);
            return Response.ok(patient).build();
        } catch (Exception e) {
            LOG.error("Error updating patient with ID: {}", id, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error updating patient").build();
        }
    }

    /**
     * Delete a patient (ADMIN_ROLE only).
     *
     * @param id ID of the patient to delete.
     * @return Response indicating the result of the deletion.
     */
=======
        LOG.debug("try to retrieve specific patient " + id);
        Patient patient = service.getPatientById(id);
        return Response.status(patient == null ? Status.NOT_FOUND : Status.OK)
                .entity(patient)
                .build();
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addPatient(Patient newPatient) {
        Patient persistedPatient = service.persistPatient(newPatient);
        return Response.ok(persistedPatient).build();
    }

>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deletePatient(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
<<<<<<< HEAD
        LOG.debug("Deleting patient with ID: {}", id);
        try {
            service.deletePatientById(id);
            return Response.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting patient with ID: {}", id, e);
            return Response.status(Status.NOT_FOUND).entity("Patient not found").build();
        }
=======
        LOG.debug("Deleting patient with id = " + id);
        service.deletePatientById(id);
        return Response.noContent().build();
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
    }
}
