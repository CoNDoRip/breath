package controllers;

import play.libs.Json;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;
import play.db.jpa.Transactional;

import models.Profile;
import org.codehaus.jackson.JsonNode;
import java.util.List;

@Security.Authenticated(Secured.class)
public class RatingPage extends Controller {

	/**
	* 
	*/
	@Transactional(readOnly=true)
	public static Result getRating(Integer page) {
		if (page > 0) {
			List<Profile> listOfProfiles = Profile.getRating(page);
			return ok(Json.toJson(listOfProfiles));
		} else {
			return Application.errorResponse(
				"Error in page number! Page must be greater than 0"
				);
		}
	}

}
