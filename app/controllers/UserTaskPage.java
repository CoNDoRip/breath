package controllers;

import play.Play;
import play.libs.Json;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;
import play.db.jpa.Transactional;

import models.Profile;
import models.UserTask;

import java.util.List;
import java.io.File;

@Security.Authenticated(Secured.class)
public class UserTaskPage extends Controller {

	/**
	* Get user's completed tasks
	*/
	@Transactional(readOnly=true)
	public static Result getUserTasks(Integer page) {
		if (page > 0) {
			Long profileId = Application.getProfileId();
			List<UserTask.UserTaskWithTitle> listOfUserTasks 
				= UserTask.findByProfileId(profileId, page);
			return ok(Json.toJson(listOfUserTasks));
		} else {
			return Application.errorResponse(
				"Error in page number! Page must be greater than 0"
				);
		}
	}

	@Transactional(readOnly=true)
	public static Result getImage(Long id) {
		UserTask ut = UserTask.findById(id);
    	File file  = Play.application().getFile(Application.USERTASK_IMAGES 
    		+ ut.profileId + File.separator + ut.image);
    	return ok(file);
	}

}
