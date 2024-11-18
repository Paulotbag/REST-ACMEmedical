/********************************************************************************************************
 * File:  MedicalSchoolResource.java Course Materials CST 8277
 *
 * @author
 * @modified Resolved warnings and errors for unused fields, methods, and redundant variables.
 *
 */
package acmemedical.rest.resource;

import java.util.List;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static acmemedical.utility.MyConstants.ADMIN_ROLE;
import static acmemedical.utility.MyConstants.USER_ROLE;
import static acmemedical.utility.MyConstants.MEDICAL_SCHOOL_RESOURCE_NAME;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.MedicalTraining;
import acmemedical.entity.MedicalSchool;

@Path(MEDICAL_SCHOOL_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicalSchoolResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    /**
     * Retrieve all medical schools (accessible to all users).
     *
     * @return List of all medical schools.
     */
    @GET
    @PermitAll
    public Response getMedicalSchools() {
        LOG.debug("Retrieving all medical schools...");
        List<MedicalSchool> medicalSchools = service.getAllMedicalSchools();
        LOG.debug("Medical schools found = {}", medicalSchools);
        return Response.ok(medicalSchools).build();
    }

    /**
     * Retrieve a medical school by its ID (accessible to ADMIN_ROLE and USER_ROLE).
     *
     * @param medicalSchoolId ID of the medical school.
     * @return Medical school with the specified ID.
     */
    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path("/{medicalSchoolId}")
    public Response getMedicalSchoolById(@PathParam("medicalSchoolId") int medicalSchoolId) {
        LOG.debug("Retrieving medical school with id = {}", medicalSchoolId);
        MedicalSchool medicalSchool = service.getMedicalSchoolById(medicalSchoolId);
        return Response.ok(medicalSchool).build();
    }

    /**
     * Delete a medical school by its ID (accessible only to ADMIN_ROLE).
     *
     * @param medicalSchoolId ID of the medical school.
     * @return Deleted medical school.
     */
    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{medicalSchoolId}")
    public Response deleteMedicalSchool(@PathParam("medicalSchoolId") int medicalSchoolId) {
        LOG.debug("Deleting medical school with id = {}", medicalSchoolId);
        MedicalSchool deletedSchool = service.deleteMedicalSchool(medicalSchoolId);
        return Response.ok(deletedSchool).build();
    }

    /**
     * Add a new medical school (accessible only to ADMIN_ROLE).
     *
     * @param newMedicalSchool New medical school to add.
     * @return Newly added medical school.
     */
    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addMedicalSchool(MedicalSchool newMedicalSchool) {
        LOG.debug("Adding a new medical school = {}", newMedicalSchool);
        if (service.isDuplicated(newMedicalSchool)) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Medical school already exists.")
                    .build();
        }
        MedicalSchool addedSchool = service.persistMedicalSchool(newMedicalSchool);
        return Response.ok(addedSchool).build();
    }

    /**
     * Add a medical training to an existing medical school (accessible only to ADMIN_ROLE).
     *
     * @param medicalSchoolId ID of the medical school.
     * @param newMedicalTraining New medical training to add.
     * @return Updated medical school.
     */
    @POST
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{medicalSchoolId}/medical-training")
    public Response addMedicalTrainingToMedicalSchool(
            @PathParam("medicalSchoolId") int medicalSchoolId, MedicalTraining newMedicalTraining) {
        LOG.debug("Adding a new MedicalTraining to medical school with id = {}", medicalSchoolId);

        MedicalSchool medicalSchool = service.getMedicalSchoolById(medicalSchoolId);
        newMedicalTraining.setMedicalSchool(medicalSchool);
        medicalSchool.getMedicalTrainings().add(newMedicalTraining);
        MedicalSchool updatedSchool = service.updateMedicalSchool(medicalSchoolId, medicalSchool);

        return Response.ok(updatedSchool).build();
    }

    /**
     * Update a medical school (accessible to ADMIN_ROLE and USER_ROLE).
     *
     * @param medicalSchoolId ID of the medical school.
     * @param updatingMedicalSchool Updated medical school data.
     * @return Updated medical school.
     */
    @PUT
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path("/{medicalSchoolId}")
    public Response updateMedicalSchool(
            @PathParam("medicalSchoolId") int medicalSchoolId, MedicalSchool updatingMedicalSchool) {
        LOG.debug("Updating a specific medical school with id = {}", medicalSchoolId);
        MedicalSchool updatedSchool = service.updateMedicalSchool(medicalSchoolId, updatingMedicalSchool);
        return Response.ok(updatedSchool).build();
    }
}
