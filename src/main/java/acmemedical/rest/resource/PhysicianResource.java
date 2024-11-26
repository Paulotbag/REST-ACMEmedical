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
     * Update medicine for a physician-patient relationship (ADMIN_ROLE only).
     *
     * @param physicianId ID of the physician.
     * @param patientId ID of the patient.
     * @param newMedicine New medicine to associate.
     * @return Response with the updated medicine.
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
}
