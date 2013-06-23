package controllers;

import play.*;
import play.mvc.*;
import play.db.jpa.*;

import views.html.*;
import models.Profile;
import models.Checks;
import models.UserTask;
import models.UserTask.UserTaskWithTitle;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

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

			List<UserTaskWithTitle> listOfChecks = Checks.findChecks(profileId, page);
			
			return ok(Json.toJson(listOfChecks));
		} else {
			return Application.errorResponse("Error in page number! Page must be greater than 0");
		}
	}

}
