package controllers;

import play.libs.Json;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;
import play.db.jpa.Transactional;

import models.Profile;
import models.Task;
import models.UserTask;

import java.util.List;
import java.io.File;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

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

	/**
	* 
	*/
	@Transactional
	public static Result doTask(Long id) {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart image = body.getFile("image");
		if (image != null) {
			String imageName = image.getFilename();
			File file = image.getFile();
			UserTask ut = new UserTask(Application.getProfileId(), id, imageName);
			ut.save();
			return Application.goodResponse("Task " + id + "successfully has done");  
		} else {
			return Application.errorResponse("Can't found image");    
		}
	}

}
