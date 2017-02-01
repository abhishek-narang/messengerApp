package org.abhishek.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.abhishek.messenger.model.Profile;
import org.abhishek.messenger.service.ProfileService;

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {

	private ProfileService profileService= new ProfileService();
	
	@GET
	public List<Profile> getAllProfiles() {
		return profileService.getAllProfiles();
	}
	
	@GET
	@Path("/{profileKey}")
	public Profile getProfile(@PathParam("profileKey") String profileKey) {
		return profileService.getProfile(profileKey);
	}
	
	@POST
	public Profile addProfile(Profile profile) {
		return profileService.createProfile(profile);
	}

	@PUT
	@Path("/{profileKey}")
	public Profile updateMessage(@PathParam("profileKey") String profileKey,
			Profile profile) {
		profile.setProfileName(profileKey);
		return profileService.updateProfile(profile);
	}

	@DELETE
	@Path("/{profileKey}")
	public void deleteProfile(@PathParam("profileKey") String profileKey) {
		profileService.removeProfile(profileKey);
	}
}
