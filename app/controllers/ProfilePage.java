package controllers;

import play.libs.Json;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;
import play.db.jpa.Transactional;

import models.Profile;

@Security.Authenticated(Secured.class)
public class ProfilePage extends Controller {

	/**
	* Get user's profile page by id
	*/
	@Transactional(readOnly=true)
	public static Result getProfile(Long id) {
		Profile p = Profile.findById(id);
		Profile.ProfileSafe ps = new Profile.ProfileSafe(p);
		return ok(Json.toJson(ps));
	}

	/**
	* Get current user's profile page
	*/
	@Transactional(readOnly=true)
	public static Result getCurrentProfile() {		
		return getProfile(Application.getProfileId());
	}

}
