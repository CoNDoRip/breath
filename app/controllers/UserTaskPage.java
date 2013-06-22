package controllers;

import play.*;
import play.mvc.*;
import play.db.jpa.*;

import views.html.*;
import models.Profile;
import models.UserTask;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

import java.util.List;

@Security.Authenticated(Secured.class)
public class UserTaskPage extends Controller {

	/**
	* Get user's completed tasks
	*/
	@Transactional(readOnly=true)
	public static Result getUserTasks(Integer page) {
		if (page > 0) {
			String id = session("id");
			Long profileId = Long.valueOf(id).longValue();

			List<UserTask> listOfUserTasks = UserTask.findByProfileId(profileId, page);

			return ok(Json.toJson(listOfUserTasks));
		} else {
			return Application.errorResponse("Error in page number! Page must be greater than 0");
		}
	}

}
