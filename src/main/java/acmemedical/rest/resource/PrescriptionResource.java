/**
 * @AUTHOR: PAULO GRANJEIRO, Azadeh Sadeghtehrani, Abraham El Kachi, Harpinder Brar
 * DATE: 2024-12-03
 * Objected Oriented Programming
 *
 * NOTES: This class, PrescriptionResource, is a RESTful resource for managing prescription,
 * including retrieving, adding, updating, and deleting prescription data,
 * with role-based access control (admin and user roles) enforced via annotations.
 * It integrates with a service layer for business logic and handles security and exception scenarios.
 */

package acmemedical.rest.resource;

import static acmemedical.utility.MyConstants.ADMIN_ROLE;
import static acmemedical.utility.MyConstants.USER_ROLE;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.soteria.WrappingCallerPrincipal;


import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.Prescription;

/**
 * REST resource for managing Prescription entities.
 */
@Path("prescriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrescriptionResource {

    private static final Logger LOG = LogManager.getLogger(PrescriptionResource.class);

    @Inject
    private ACMEMedicalService medicalService;

    /**
     * Retrieve all prescriptions.
     *
     * @return List of prescriptions.
     */
    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getAllPrescriptions() {
        LOG.debug("Request to retrieve all prescriptions");
        try {
            List<Prescription> prescriptions = medicalService.getAllPrescriptions();
            return Response.ok(prescriptions).build();
        } catch (Exception e) {
            LOG.error("Error retrieving all prescriptions", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to retrieve prescriptions").build();
        }
    }

    /**
     * Retrieve a prescription by its composite ID.
     *
     * @param physicianId Physician's ID.
     * @param patientId Patient's ID.
     * @return Prescription if found.
     */
    @GET
    @Path("{physicianId}/{patientId}")
    public Response getPrescription(
            @PathParam("physicianId") int physicianId,
            @PathParam("patientId") int patientId
    ) {
        LOG.debug("Request to retrieve prescription with physicianId: {}, patientId: {}", physicianId, patientId);
        try {
            Prescription prescription = medicalService.getPrescriptionById(physicianId, patientId);
            if (prescription != null) {
                return Response.ok(prescription).build();
            }
            return Response.status(Status.NOT_FOUND).entity("Prescription not found").build();
        } catch (Exception e) {
            LOG.error("Error retrieving prescription", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to retrieve prescription").build();
        }
    }

    /**
     * Create a new prescription.
     *
     * @param prescription The prescription to create.
     * @return The created prescription.
     */
    @POST
    public Response createPrescription(Prescription prescription) {
        LOG.debug("Request to create prescription: {}", prescription);
        try {
            Prescription createdPrescription = medicalService.persistPrescription(prescription);
            return Response.status(Status.CREATED).entity(createdPrescription).build();
        } catch (Exception e) {
            LOG.error("Error creating prescription", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to create prescription").build();
        }
    }

    /**
     * Update an existing prescription.
     *
     * @param physicianId Physician's ID.
     * @param patientId Patient's ID.
     * @param updatedPrescription The updated prescription data.
     * @return The updated prescription if successful.
     */
    @PUT
    @Path("{physicianId}/{patientId}")
    public Response updatePrescription(
            @PathParam("physicianId") int physicianId,
            @PathParam("patientId") int patientId,
            Prescription updatedPrescription
    ) {
        LOG.debug("Request to update prescription with physicianId: {}, patientId: {}", physicianId, patientId);
        try {
            Prescription prescription = medicalService.updatePrescription(physicianId, patientId, updatedPrescription);
            if (prescription != null) {
                return Response.ok(prescription).build();
            }
            return Response.status(Status.NOT_FOUND).entity("Prescription not found for update").build();
        } catch (Exception e) {
            LOG.error("Error updating prescription", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to update prescription").build();
        }
    }

    /**
     * Delete a prescription by its composite ID.
     *
     * @param physicianId Physician's ID.
     * @param patientId Patient's ID.
     * @return Status of the deletion operation.
     */
    @DELETE
    @Path("{physicianId}/{patientId}")
    public Response deletePrescription(
            @PathParam("physicianId") int physicianId,
            @PathParam("patientId") int patientId
    ) {
        LOG.debug("Request to delete prescription with physicianId: {}, patientId: {}", physicianId, patientId);
        try {
            boolean deleted = medicalService.deletePrescriptionById(physicianId, patientId);
            if (deleted) {
                return Response.noContent().build();
            }
            return Response.status(Status.NOT_FOUND).entity("Prescription not found for deletion").build();
        } catch (Exception e) {
            LOG.error("Error deleting prescription", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to delete prescription").build();
        }
    }
}
