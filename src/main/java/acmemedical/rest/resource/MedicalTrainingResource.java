/**
 * @AUTHOR: PAULO GRANJEIRO, Azadeh Sadeghtehrani, Abraham El Kachi, Harpinder Brar
 * DATE: 2024-12-03
 * Objected Oriented Programming
 *
 * NOTES: This class, MedicalTrainingResource, is a RESTful resource for managing Medical Training,
 *  retrieving medicine data,
 * with role-based access control (admin and user roles) enforced via annotations.
 * It integrates with a service layer for business logic and handles security and exception scenarios.
 */

package acmemedical.rest.resource;

import java.util.List;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.MedicalTraining;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static acmemedical.utility.MyConstants.MEDICAL_TRAINING_RESOURCE_NAME;

@Path(MEDICAL_TRAINING_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicalTrainingResource {

    private static final Logger LOG = LogManager.getLogger(MedicalTrainingResource.class);

    @EJB
    protected ACMEMedicalService service;

    /**
     * Retrieves all medical training records.
     *
     * @return Response containing the list of medical training records.
     */
    @GET
    @PermitAll
    public Response getAllMedicalTrainings() {
        LOG.debug("Retrieving all MedicalTrainings...");
        List<MedicalTraining> medicalTrainings = service.getAll(
                MedicalTraining.class,
                "MedicalTraining.ALL_MEDICAL_TRAININGS_QUERY"
        );
        return Response.ok(medicalTrainings).build();
    }

}
