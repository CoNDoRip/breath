package controllers;

import play.*;
import play.mvc.*;
import play.db.jpa.*;

import views.html.*;
import models.Profile;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

@Security.Authenticated(Secured.class)
public class ProfilePage extends Controller {

	/**
	* Get user's profile page by id
	*/
	@Transactional(readOnly=true)
	public static Result getProfile(Long id) {
		Profile profile = Profile.findById(id);

		ObjectNode result = Json.newObject();
		result.put("id",         profile.id);
		result.put("first_name", profile.first_name);
		result.put("last_name",  profile.last_name);
		if (profile.gender != null)
			result.put("gender", profile.gender.toString());
		
		return ok(result);
	}

	/**
	* Get current user's profile page
	*/
	@Transactional(readOnly=true)
	public static Result getCurrentProfile() {
		String id = session("id");
		Long profileId = Long.valueOf(id).longValue();
		
		return getProfile(profileId);
	}

}
