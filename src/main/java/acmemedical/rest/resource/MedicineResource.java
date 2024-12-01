package acmemedical.rest.resource;

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
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicineResource {

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
    }
}

