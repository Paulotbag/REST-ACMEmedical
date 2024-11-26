package acmemedical.rest.resource;

import static acmemedical.utility.MyConstants.ADMIN_ROLE;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.MedicalCertificate;



@Path("medicalCertificates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicalCertificateResource {

    private static final Logger LOG = LogManager.getLogger(MedicalCertificateResource.class);

    @EJB
    private ACMEMedicalService service;

    /**
     * Create a new MedicalCertificate (ADMIN_ROLE only).
     *
     * @param medicalCertificate New MedicalCertificate to create.
     * @return Response containing the created MedicalCertificate.
     */
    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response createMedicalCertificate(MedicalCertificate medicalCertificate) {
        LOG.debug("Creating MedicalCertificate: {}", medicalCertificate);
        MedicalCertificate createdCertificate = service.createMedicalCertificate(medicalCertificate);
        return Response.ok(createdCertificate).build();
    }

    /**
     * Retrieve a specific MedicalCertificate by ID (ADMIN_ROLE only).
     *
     * @param id MedicalCertificate ID.
     * @return Response containing the MedicalCertificate or error.
     */
    @GET
    @Path("/{id}")
    @RolesAllowed({ADMIN_ROLE})
    public Response getMedicalCertificateById(@PathParam("id") int id) {
        LOG.debug("Retrieving MedicalCertificate with ID: {}", id);
        MedicalCertificate certificate = service.getMedicalCertificateById(id);
        if (certificate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(certificate).build();
    }

    /**
     * Update an existing MedicalCertificate (ADMIN_ROLE only).
     *
     * @param id MedicalCertificate ID.
     * @param medicalCertificate MedicalCertificate with updated data.
     * @return Response containing the updated MedicalCertificate.
     */
    @PUT
    @Path("/{id}")
    @RolesAllowed({ADMIN_ROLE})
    public Response updateMedicalCertificate(@PathParam("id") int id, MedicalCertificate medicalCertificate) {
        LOG.debug("Updating MedicalCertificate with ID: {}", id);
        MedicalCertificate updatedCertificate = service.updateMedicalCertificate(id, medicalCertificate);
        if (updatedCertificate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedCertificate).build();
    }

    /**
     * Delete a specific MedicalCertificate by ID (ADMIN_ROLE only).
     *
     * @param id MedicalCertificate ID.
     * @return Response confirming the deletion.
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed({ADMIN_ROLE})
    public Response deleteMedicalCertificate(@PathParam("id") int id) {
        LOG.debug("Deleting MedicalCertificate with ID: {}", id);
        boolean deleted = service.deleteMedicalCertificate(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
