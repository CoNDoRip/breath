package controllers;

import play.libs.Json;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;
import play.db.jpa.Transactional;

import models.Profile;
import models.Checks;
import models.UserTask;

import java.util.List;

@Security.Authenticated(Secured.class)
public class ChecksPage extends Controller {

	/**
	* Get user's avaliable tasks
	*/
	@Transactional(readOnly=true)
	public static Result getChecks(Integer page) {
		if (page > 0) {
			String id = session("id");
			Long profileId = Long.valueOf(id).longValue();
			Profile profile = Profile.findById(profileId);
			
			List<UserTask.UserTaskWithTitle> listOfChecks = Checks.findChecks(profileId, page);
			
			return ok(Json.toJson(listOfChecks));
		} else {
			return Application.errorResponse(
				"Error in page number! Page must be greater than 0"
				);
		}
	}

}
