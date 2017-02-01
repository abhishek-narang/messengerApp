package org.abhishek.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.abhishek.messenger.database.DatabaseClass;
import org.abhishek.messenger.model.Profile;

public class ProfileService {
	
	Map<String,Profile> profiles= DatabaseClass.getProfiles();
	
	public ProfileService(){
		profiles.put("abhi", new Profile(1L, "abhi", "Abhishek", "Narang"));
		profiles.put("jyoti", new Profile(2L, "jyoti", "Jyoti", "Narang"));
	}
	
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}	
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile createProfile(Profile profile) {
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if(profile.getProfileName().isEmpty())
			return null;
		
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName){
		return profiles.remove(profileName);
	}
}
