package controllers;

import play.libs.Json;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;
import play.db.jpa.Transactional;

import models.Profile;
import models.Task;

import java.util.List;

@Security.Authenticated(Secured.class)
public class TaskPage extends Controller {

	/**
	* Get user's avaliable tasks
	*/
	@Transactional(readOnly=true)
	public static Result getTasks(Integer page) {
		if (page > 0) {
			Long profileId = Application.getProfileId();
			Profile profile = Profile.findById(profileId);
			List<Task> listOfTasks = Task.findByLevel(
				profileId, profile.level, page
			);
			return ok(Json.toJson(listOfTasks));
		} else {
			return Application.errorResponse(
				"Error in page number! Page must be greater than 0"
				);
		}
	}

}
