/********************************************************************************************************
 * File:  PhysicianResource.java Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * Implemented by: Paulo Granjeiro and Azadeh
 * 
 */
package acmemedical.rest.resource;

import static acmemedical.utility.MyConstants.ADMIN_ROLE;
import static acmemedical.utility.MyConstants.PHYSICIAN_PATIENT_MEDICINE_RESOURCE_PATH;
import static acmemedical.utility.MyConstants.PHYSICIAN_RESOURCE_NAME;
import static acmemedical.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static acmemedical.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static acmemedical.utility.MyConstants.USER_ROLE;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    @Inject
    protected SecurityContext sc;

    /**
     *
     * @return Response
     */
    @GET
    //Only a user with the SecurityRole ‘ADMIN_ROLE’ can get the list of all physicians.
    @RolesAllowed({ADMIN_ROLE})
    public Response getPhysicians() {
        LOG.debug("retrieving all physicians ...");
        List<Physician> physicians = service.getAllPhysicians();
        Response response = Response.ok(physicians).build();
        return response;
    }

    /**
     *
     * @param id
     * @return Response
     */
    //Access Physician by ID Using SecurityUser's Associated Physician
    @GET
    //A user with either the role ‘ADMIN_ROLE’ or ‘USER_ROLE’ can get a specific physician.
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getPhysicianById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("try to retrieve specific physician " + id);
        Response response = null;
        Physician physician = null;

        //SecurityContext - Check roles and access Principal of current user
        //Admin can access any physician by ID.
        if (sc.isCallerInRole(ADMIN_ROLE)) {
        	physician = service.getPhysicianById(id);
            response = Response.status(physician == null ? Status.NOT_FOUND : Status.OK).entity(physician).build();

            //The role User can only access the physician associated with the SecurityUser
            //Unwrapping SecurityUser
        } else if (sc.isCallerInRole(USER_ROLE)) {
            //These 2 lines bellow extracts the SecurityUser instance associated with the currently authenticated principal.
            WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
            SecurityUser sUser = (SecurityUser) wCallerPrincipal.getWrapped();
            physician = sUser.getPhysician();
            if (physician != null && physician.getId() == id) {
                response = Response.status(Status.OK).entity(physician).build();
            } else {
            	//disallows a ‘USER_ROLE’ user from getting a physician that is not linked to the SecurityUser.
                throw new ForbiddenException("User trying to access resource it does not own (wrong userid)");
            }
        } else {
            response = Response.status(Status.BAD_REQUEST).build();
        }
        return response;
    }

    /**
     *
     * @param newPhysician
     * @return Response
     */
    @POST
    //Only a user with the SecurityRole ‘ADMIN_ROLE’ can add a new physician.
    @RolesAllowed({ADMIN_ROLE})
    public Response addPhysician(Physician newPhysician) {
        Response response = null;
        Physician newPhysicianWithIdTimestamps = service.persistPhysician(newPhysician);
        // Build a SecurityUser linked to the new physician
        service.buildUserForNewPhysician(newPhysicianWithIdTimestamps);
        response = Response.ok(newPhysicianWithIdTimestamps).build();
        return response;
    }

    /**
     *
     * @param physicianId
     * @param patientId
     * @param newMedicine
     * @return Response
     */
    @PUT
    //Only an ‘ADMIN_ROLE’ user can associate a Medicine and/or Patient to a Physician.
    @RolesAllowed({ADMIN_ROLE})
    @Path(PHYSICIAN_PATIENT_MEDICINE_RESOURCE_PATH)
    public Response updateMedicineForPhysicianPatient(@PathParam("physicianId") int physicianId, @PathParam("patientId") int patientId, Medicine newMedicine) {
        Response response = null;
        Medicine medicine = service.setMedicineForPhysicianPatient(physicianId, patientId, newMedicine);
        response = Response.ok(medicine).build();
        return response;
    }
    
}