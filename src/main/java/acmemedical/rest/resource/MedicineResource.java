package acmemedical.rest.resource;

<<<<<<< HEAD
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.Medicine;

import java.util.List;

/**
 * REST resource for managing Medicine entities.
 */
@Path("medicines")
=======
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
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicineResource {

<<<<<<< HEAD
    private static final Logger LOG = LogManager.getLogger(MedicineResource.class);

    @EJB
    private ACMEMedicalService medicalService;

    /**
     * Retrieve all medicines.
     *
     * @return Response containing the list of all medicines.
     */
    @GET
    public Response getAllMedicines() {
        LOG.debug("Retrieving all medicines...");
        List<Medicine> medicines = medicalService.getAllMedicines();
        return Response.ok(medicines).build();
    }

    /**
     * Retrieve a specific medicine by its ID.
     *
     * @param id The ID of the medicine to retrieve.
     * @return Response containing the medicine data or error.
     */
    @GET
    @Path("{id}")
    public Response getMedicineById(@PathParam("id") int id) {
        LOG.debug("Retrieving medicine with ID: {}", id);
        Medicine medicine = medicalService.getMedicineById(id);
        if (medicine != null) {
            return Response.ok(medicine).build();
        }
        LOG.warn("Medicine with ID {} not found", id);
        return Response.status(Response.Status.NOT_FOUND).entity("Medicine not found").build();
    }

    /**
     * Create a new medicine.
     *
     * @param newMedicine The new medicine to create.
     * @return Response with the created medicine.
     */
    @POST
    public Response createMedicine(Medicine newMedicine) {
        LOG.debug("Creating new medicine: {}", newMedicine);
        Medicine createdMedicine = medicalService.persistMedicine(newMedicine);
        LOG.info("Medicine created with ID: {}", createdMedicine.getId());
        return Response.status(Response.Status.CREATED).entity(createdMedicine).build();
    }

    /**
     * Update an existing medicine.
     *
     * @param id The ID of the medicine to update.
     * @param updatedMedicine The updated medicine data.
     * @return Response containing the updated medicine or error.
     */
    @PUT
    @Path("{id}")
    public Response updateMedicine(@PathParam("id") int id, Medicine updatedMedicine) {
        LOG.debug("Updating medicine with ID: {}", id);
        Medicine existingMedicine = medicalService.getMedicineById(id);
        if (existingMedicine == null) {
            LOG.warn("Medicine with ID {} not found for update", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Medicine not found").build();
        }
        updatedMedicine.setId(id); // Ensure the ID remains consistent
        Medicine updated = medicalService.updateMedicine(updatedMedicine);
        LOG.info("Medicine with ID {} successfully updated", id);
        return Response.ok(updated).build();
    }

    /**
     * Delete a medicine by its ID.
     *
     * @param id The ID of the medicine to delete.
     * @return Response indicating the result of the deletion.
     */
    @DELETE
    @Path("{id}")
    public Response deleteMedicine(@PathParam("id") int id) {
        LOG.debug("Deleting medicine with ID: {}", id);
        boolean deleted = medicalService.deleteMedicineById(id);
        if (deleted) {
            LOG.info("Medicine with ID {} successfully deleted", id);
            return Response.noContent().build();
        }
        LOG.warn("Medicine with ID {} not found for deletion", id);
        return Response.status(Response.Status.NOT_FOUND).entity("Medicine not found").build();
=======
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
>>>>>>> 1584fb15b7fd8c0cd178d421776bda56d7f3e7b1
    }
}

