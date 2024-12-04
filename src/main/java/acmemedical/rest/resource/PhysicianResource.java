/**
 * @AUTHOR: PAULO GRANJEIRO, Azadeh Sadeghtehrani, Abraham El Kachi, Harpinder Brar
 * DATE: 2024-12-03
 * Objected Oriented Programming
 *
 * NOTES: This class, PhysicianResource, is a RESTful resource for managing physicians,
 * including retrieving, adding, updating, and deleting physician data,
 * with role-based access control (admin and user roles) enforced via annotations.
 * It integrates with a service layer for business logic and handles security and exception scenarios.
 */

package acmemedical.rest.resource;

import static acmemedical.utility.MyConstants.*;

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
import acmemedical.entity.Medicine;
import acmemedical.entity.SecurityUser;
import acmemedical.entity.Physician;

@Path(PHYSICIAN_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PhysicianResource {

    private static final Logger LOG = LogManager.getLogger(PhysicianResource.class);

    @EJB
    protected ACMEMedicalService service;

    @Inject
    protected SecurityContext sc;

    /**
     * Retrieve all physicians (ADMIN_ROLE only).
     *
     * @return Response containing a list of physicians.
     */
    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getPhysicians() {
        LOG.debug("Retrieving all physicians...");
        List<Physician> physicians = service.getAllPhysicians();
        return Response.ok(physicians).build();
    }

    /**
     * Retrieve a specific physician by ID.
     *
     * ADMIN_ROLE can access any physician.
     * USER_ROLE can only access their associated physician.
     *
     * @param id Physician ID.
     * @return Response containing the physician data or error.
     */
    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getPhysicianById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Retrieving physician with ID: {}", id);
        Physician physician = null;

        if (sc.isCallerInRole(ADMIN_ROLE)) {
            // ADMIN_ROLE can access any physician
            physician = service.getPhysicianById(id);
            if (physician == null) {
                return Response.status(Status.NOT_FOUND).build();
            }
        } else if (sc.isCallerInRole(USER_ROLE)) {
            // USER_ROLE can only access their associated physician
            WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
            SecurityUser sUser = (SecurityUser) wCallerPrincipal.getWrapped();
            physician = sUser.getPhysician(); // Fetch the associated physician
            if (physician == null || physician.getId() != id) {
                // Access denied if the IDs do not match
                throw new ForbiddenException("Access denied to this resource");
            }
        } else {
            // Invalid role or missing permissions
            return Response.status(Status.BAD_REQUEST).build();
        }
        return Response.ok(physician).build();
    }

    /**
     * Add a new physician (ADMIN_ROLE only).
     *
     * @param newPhysician New physician to add.
     * @return Response with the added physician.
     */
    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addPhysician(Physician newPhysician) {
        LOG.debug("Adding new physician: {}", newPhysician);
        Physician createdPhysician = service.persistPhysician(newPhysician);
        service.buildUserForNewPhysician(createdPhysician);
        return Response.ok(createdPhysician).build();
    }

    /**
     * Updates the medicine associated with a physician-patient relationship.
     * Only accessible by users with {@link #//ADMIN_ROLE}.
     * 
     * @param physicianId The ID of the physician.
     * @param patientId The ID of the patient.
     * @param newMedicine The new medicine to associate.
     * @return Response containing the updated medicine.
     */
    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(PHYSICIAN_PATIENT_MEDICINE_RESOURCE_PATH)
    public Response updateMedicineForPhysicianPatient(@PathParam("physicianId") int physicianId,
                                                      @PathParam("patientId") int patientId,
                                                      Medicine newMedicine) {
        LOG.debug("Updating medicine for physicianId: {}, patientId: {}", physicianId, patientId);
        Medicine updatedMedicine = service.setMedicineForPhysicianPatient(physicianId, patientId, newMedicine);
        return Response.ok(updatedMedicine).build();
    }

    /**
     * Deletes a physician (ADMIN_ROLE only).
     *
     * @param id The ID of the physician to delete.
     * @return Response indicating the result of the deletion.
     */
    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path("{id}")
    public Response deletePhysician(@PathParam("id") int id) {
        LOG.debug("Deleting physician with ID: {}", id);
        
        try {
            // Call the service method to delete the physician
            service.deletePhysicianById(id);
            
            // Return success response
            return Response.noContent().build(); // 204 No Content for successful deletion
        } catch (Exception e) {
            // Log and handle any exceptions that might occur (e.g., physician not found)
            LOG.error("Error deleting physician with ID: {}", id, e);
            return Response.status(Status.NOT_FOUND)
                           .entity("Physician not found or an error occurred.")
                           .build(); // 404 if physician not found or error occurs
        }
    }

}
