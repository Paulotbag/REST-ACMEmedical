/**
 * @AUTHOR: PAULO GRANJEIRO, Azadeh Sadeghtehrani, Abraham El Kachi, Harpinder Brar
 * DATE: 2024-12-03
 * Objected Oriented Programming
 *
 * NOTES: This class, MedicineResource, is a RESTful resource for managing medicine,
 * including retrieving, adding, and deleting medicine data,
 * with role-based access control (admin and user roles) enforced via annotations.
 * It integrates with a service layer for business logic and handles security and exception scenarios.
 */

package acmemedical.rest.resource;

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

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.Medicine;

import static acmemedical.utility.MyConstants.*;

@Path(MEDICINE_SUBRESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicineResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getAllMedicines() {
        LOG.debug("retrieving all medicines ...");
        List<Medicine> medicines = service.getAllMedicines();
        return Response.ok(medicines).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getMedicineById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("try to retrieve specific medicine " + id);
        Medicine medicine = service.getMedicineById(id);
        return Response.status(medicine == null ? Status.NOT_FOUND : Status.OK)
                .entity(medicine)
                .build();
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addMedicine(Medicine newMedicine) {
        LOG.debug("Adding new medicine");
        Medicine persistedMedicine = service.persistMedicine(newMedicine);
        return Response.ok(persistedMedicine).build();
    }

    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteMedicine(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Deleting medicine with id = " + id);
        service.deleteMedicineById(id);
        return Response.noContent().build();
    }
}

