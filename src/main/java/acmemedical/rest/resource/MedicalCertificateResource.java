package acmemedical.rest.resource;

import static acmemedical.utility.MyConstants.ADMIN_ROLE;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.ws.rs.core.SecurityContext;


import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.MedicalCertificate;



@Path("medicalCertificates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicalCertificateResource {

    private static final Logger LOG = LogManager.getLogger(MedicalCertificateResource.class);

    @EJB
    private ACMEMedicalService service;
    
    @Context
    private SecurityContext securityContext;


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
     * Retrieve a specific MedicalCertificate by ID (USER_ROLE can only read their own certificate).
     *
     * @param id MedicalCertificate ID.
     * @return Response containing the MedicalCertificate or an error if not found or unauthorized.
     */
    @GET
    @Path("/{id}")
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    public Response getMedicalCertificateById(@PathParam("id") int id) {
        LOG.debug("Retrieving MedicalCertificate with ID: {}", id);
        MedicalCertificate certificate = service.getMedicalCertificateById(id);

        // Check if the certificate exists
        if (certificate == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Medical Certificate with ID " + id + " not found.")
                           .build();
        }
        

        // Ensure that the certificate belongs to the currently authenticated user (USER_ROLE only)
        if (securityContext.isCallerInRole(USER_ROLE)) {
            // Assuming that MedicalCertificate has a method getUser() returning the user who owns it
            if (!certificate.getOwner().getFirstName().equals(securityContext.getUserPrincipal().getName())) {
                return Response.status(Response.Status.FORBIDDEN)
                               .entity("You are not authorized to access this certificate.")
                               .build(); // Not the user's certificate
            }
        }

        return Response.ok(certificate).build(); // Return the certificate if valid
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
