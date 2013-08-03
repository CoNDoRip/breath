package controllers;

import play.Play;
import play.libs.Json;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;
import play.db.jpa.Transactional;
import javax.persistence.NoResultException;

import models.Profile;
import models.Task;
import models.UserTask;
import models.UserTask.UserTaskStatus;

import java.util.Date;
import java.util.List;
import java.io.File;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.commons.io.IOUtils;

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
	public static Result doTask(Long taskId) {
		MultipartFormData body = request().body().asMultipartFormData();
		if(body != null) {
			FilePart image = body.getFile("image");
			if (image != null) {
				File imageFile = image.getFile();
				String imageName = "task" + taskId.toString() + ".jpg";
				Long profileId = Application.getProfileId();
				UserTask ut = null;
				try {
        			ut = UserTask.findByProfileIdAndTaskId(profileId, taskId);
        			if(ut.status == UserTaskStatus.REJECTED.getStatus()) {
        				ut.datetime = new Date();
        				ut.status = UserTaskStatus.PENDING.getStatus();
        				ut.approved = 0;
        				ut.rejected = 0;
        				ut.liked = 0;
						ut.update();
        			} else {
        				return Application.errorResponse("You have performed this task");
        			}
        		} catch (NoResultException e) {
					ut = new UserTask(profileId, taskId, imageName);
					ut.save();
        		}
        		try (FileInputStream is = new FileInputStream(imageFile)) {
        			String profileDirName = Application.imgPath + profileId;
					File profileDir = Play.application().getFile(profileDirName);
					if(!profileDir.exists()) profileDir.mkdir();
					File imageToSave = Play.application().getFile(profileDirName 
						+ File.separator + imageName);
    				if(!imageToSave.exists()) imageFile.createNewFile();
					IOUtils.copy(is, new FileOutputStream(imageToSave));
        		} catch (IOException e) {}
				return Application.goodResponse("Task " + taskId.toString() + " successfully has done");  
			} else {
				return Application.errorResponse("Can't found image");    
			}
		} else {
			return Application.errorResponse("body is null");
		}
	}

}
