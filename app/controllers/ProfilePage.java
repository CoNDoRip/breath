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
import org.codehaus.jackson.JsonNode;

import java.io.File;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.commons.io.IOUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;

@Security.Authenticated(Secured.class)
public class ProfilePage extends Controller {

	/**
	* Get user's profile page by id
	*/
	@Transactional(readOnly=true)
	public static Result getProfile(Long id) {
		Profile p = Profile.findById(id);
		p.todo_list = Task.countTasks(p);
		p.update();
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

	/**
	* 
	*/
	@Transactional
	public static Result updatePropfile() {
		JsonNode jsonBody = request().body().asJson();
		if (jsonBody != null) {
			Profile profile = Profile.findById(Application.getProfileId());
			if(profile != null) {
        		String email = jsonBody.findPath("email").getTextValue();
        			if (email != null) profile.email = email;
        		String username = jsonBody.findPath("username").getTextValue();
        			if (username != null) profile.username = username;
        		String first_name = jsonBody.findPath("first_name").getTextValue();
        			if (first_name != null) profile.first_name = first_name;
        		String last_name = jsonBody.findPath("last_name").getTextValue();
        			if (last_name != null) profile.last_name = last_name;
        		String stringBirthday = jsonBody.findPath("birthday").getTextValue();
        			if (stringBirthday != null) {
						SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
						Date birthday = null;
						try {
							birthday = format.parse(stringBirthday);
						} catch(ParseException e) {}
						if(birthday != null) {
							profile.birthday = birthday;
						} else {
							return Application.errorResponse("Invalid Birthday date");
						}
        			}
				if (jsonBody.has("gender")) {
					int gender = jsonBody.findPath("gender").getValueAsInt();
        			if(gender == 0 || gender == 1) {
        				profile.gender = gender;
        			} else {
        				return Application.errorResponse("The field [gender] is incorrect");
        			}
        		}
        		String status = jsonBody.findPath("status").getTextValue();
        			if (status != null) profile.status = status;
        		profile.update();
				return Application.goodResponse("Your profile successfully updated");
			} else {
				return Application.errorResponse("Can't find your profile");
			}
        } else {
			return Application.errorResponse("Expecting Json data");
		}
	}

	/**
	* 
	*/
	@Transactional(readOnly=true)
	public static Result getAvatar(Long id) {
		Profile profile = Profile.findById(id);
    	File avatar  = Play.application().getFile(Application.AVATAR_IMAGES
    		+ id.toString() + ".png");
    	return ok(avatar);
	}

    /**
    * 
    */
	@Transactional
	public static Result updateAvatar() {
		MultipartFormData body = request().body().asMultipartFormData();
		if(body != null) {
			FilePart image = body.getFile("image");
			if (image != null) {
				File imageFile = image.getFile();
				Long profileId = Application.getProfileId();
				String imageName = profileId.toString() + ".png";
				Profile profile = null;
				try {
        			profile.avatar = imageName;
        			profile.update();
        		} catch (NoResultException e) {
        			return Application.errorResponse("Can't found your profile");
        		}
        		try (FileInputStream is = new FileInputStream(imageFile)) {
					File imageToSave = Play.application().getFile(Application.AVATAR_IMAGES
						+ imageName);
    				if(!imageToSave.exists()) imageFile.createNewFile();
					IOUtils.copy(is, new FileOutputStream(imageToSave));
        		} catch (IOException e) {}
				return Application.goodResponse("Your avatar successfully updated");  
			} else {
				return Application.errorResponse("Can't find image");    
			}
		} else {
			return Application.errorResponse("Body is null");
		}
	}

}
