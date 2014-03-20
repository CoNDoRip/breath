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
import org.codehaus.jackson.JsonNode;

@Security.Authenticated(Secured.class)
public class ChecksPage extends Controller {

	private static final int APPROVE = 1;
	private static final int REJECT = -1;

	/**
	* Get user's avaliable tasks
	*/
	@Transactional(readOnly=true)
	public static Result getChecks(Integer page) {
		if (page > 0) {
			Long profileId = Application.getProfileId();
			Profile profile = Profile.findById(profileId);
			
			List<UserTask.UserTaskWithTitle> listOfChecks = Checks.findChecks(profileId, page);
			
			return ok(Json.toJson(listOfChecks));
		} else {
			return Application.errorResponse(
				"Error in page number! Page must be greater than 0"
				);
		}
	}

	@Transactional
	public static Result check(Long userTaskId) {
		JsonNode jsonBody = request().body().asJson();
		if (jsonBody != null) {
			Long profileId = Application.getProfileId();
			UserTask ut = UserTask.findById(userTaskId);
			if(profileId != ut.profileId) {
        		if (jsonBody.has("status")) {
					int status = jsonBody.findPath("status").getValueAsInt();
        			if(status == APPROVE || status == REJECT) {
        				if(status == APPROVE) {
        					ut.approved++;
        				} else {
        					ut.rejected++;
        				}
        				ut.checkStatus();
        				ut.update();
        				Checks check = new Checks(profileId, ut.id, status);
        				check.save();
        				return Application.goodResponse("UserTask " + userTaskId.toString() 
        					+ " successfully checked");
        			} else {
        				return Application.errorResponse("The field [status] is not intincorrect");
        			}
        		} else {        			
					return Application.errorResponse("Missing parameter [status]");
        		}
			} else {
				return Application.errorResponse("You can't check your usertask");
			}
        } else {
			return Application.errorResponse("Expecting Json data");
        }
	}

}
