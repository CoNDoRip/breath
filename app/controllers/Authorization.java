package controllers;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import javax.persistence.NoResultException;

import views.html.*;
import models.Profile;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

import play.libs.Crypto;

public class Authorization extends Controller {

	/**
	* Authenticate existing user or create a new user if this email not exists in DB
	*/
	@Transactional(readOnly=true)
	public static Result login() {
		JsonNode jsonBody = request().body().asJson();
		if (jsonBody == null) {
			// BUG!!! Return HTML with status 400 if Content-type: application/json
			return Application.errorResponse("Expecting Json data");
        } else {
        	String email = jsonBody.findPath("email").getTextValue();
        	if (email == null) {
				return Application.errorResponse("Missing parameter [email]");
        	}
        	String password = jsonBody.findPath("password").getTextValue();
        	if (password == null) {
				return Application.errorResponse("Missing parameter [password]");
        	}
        	Profile profile;
        	try {
        		profile = Profile.findByEmail(email);
        		return authenticate(profile, password);
        	} catch (NoResultException e) {
        		ObjectNode result = Json.newObject();
				result.put("message", "Unknown user");
				return unauthorized(result);
        	}
        }
	}

	/**
	* Authenticate existing user
	*/
	private static Result authenticate(Profile profile, String password) {
        ObjectNode result = Json.newObject();

        String hash = hashPassword(password);
        if (hash.equals(profile.password)) {
        	session("id", profile.id.toString());
        	session("hash", hash);
        	result.put("message", "Successful login! Welcome, "
        		                      + profile.first_name + "!");
			return created(result);
        } else {
			result.put("message", "Invalid password");
			return unauthorized(result);
        }
	}

	/**
	* Calculate hash of password using application.secret as a salt
	*/
	private static String hashPassword(String password) {
		return Crypto.sign(password);
	}

}
