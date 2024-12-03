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
<<<<<<< HEAD
	
=======

>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
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
<<<<<<< HEAD
        @PathParam("physicianId") int physicianId,
        @PathParam("patientId") int patientId
    ) {
    	LOG.debug("Request to retrieve prescription with physicianId: {}, patientId: {}", physicianId, patientId);
=======
            @PathParam("physicianId") int physicianId,
            @PathParam("patientId") int patientId
    ) {
        LOG.debug("Request to retrieve prescription with physicianId: {}, patientId: {}", physicianId, patientId);
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
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
<<<<<<< HEAD
    
=======

>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
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
<<<<<<< HEAD
    
=======

>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
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
<<<<<<< HEAD
        @PathParam("physicianId") int physicianId,
        @PathParam("patientId") int patientId,
        Prescription updatedPrescription
    ) {
    	LOG.debug("Request to update prescription with physicianId: {}, patientId: {}", physicianId, patientId);
=======
            @PathParam("physicianId") int physicianId,
            @PathParam("patientId") int patientId,
            Prescription updatedPrescription
    ) {
        LOG.debug("Request to update prescription with physicianId: {}, patientId: {}", physicianId, patientId);
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
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
<<<<<<< HEAD
    
=======

>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
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
<<<<<<< HEAD
        @PathParam("physicianId") int physicianId,
        @PathParam("patientId") int patientId
    ) {
    	LOG.debug("Request to delete prescription with physicianId: {}, patientId: {}", physicianId, patientId);
=======
            @PathParam("physicianId") int physicianId,
            @PathParam("patientId") int patientId
    ) {
        LOG.debug("Request to delete prescription with physicianId: {}, patientId: {}", physicianId, patientId);
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
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
<<<<<<< HEAD
}
=======
}
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
