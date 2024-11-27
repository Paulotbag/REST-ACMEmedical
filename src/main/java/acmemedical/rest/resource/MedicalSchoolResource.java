package acmemedical.rest.resource;

import java.util.List;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.MedicalSchool;

@Path("/medical-school")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicalSchoolResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    /**
     * Retrieves all medical schools (accessible to all users).
     *
     * @return Response containing the list of medical schools.
     */
    @GET
    @PermitAll
    public Response getMedicalSchools() {
        LOG.debug("Retrieving all medical schools...");
        List<MedicalSchool> medicalSchools = service.getAllMedicalSchools();
        return Response.ok(medicalSchools).build();
    }

    /**
     * Retrieves a specific medical school by ID.
     *
     * @param medicalSchoolId ID of the medical school to retrieve.
     * @return Response containing the medical school.
     */
    @GET
    @Path("/{medicalSchoolId}")
    @PermitAll
    public Response getMedicalSchoolById(@PathParam("medicalSchoolId") int medicalSchoolId) {
        LOG.debug("Retrieving medical school with ID = {}", medicalSchoolId);
        MedicalSchool medicalSchool = service.getMedicalSchoolById(medicalSchoolId);
        return Response.ok(medicalSchool).build();
    }
}
