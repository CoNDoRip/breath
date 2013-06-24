package controllers;

import play.mvc.Controller;
import play.mvc.Security;
import play.mvc.Result;
import static play.mvc.Results.*;

import play.db.jpa.Transactional;
import javax.persistence.NoResultException;

import models.Profile;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

import play.libs.Crypto;

public class Authorization extends Controller {

	/**
	* Authenticate existing user or create a new user if this email not exists in DB
	*/
	@Transactional
	public static Result login() {
		JsonNode jsonBody = request().body().asJson();
		if (jsonBody == null) {
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

        	String hash = hashPassword(password);
        	Profile profile;
        	try {
        		profile = Profile.findByEmail(email);
        		return authenticate(profile, hash);
        	} catch (NoResultException e) {
				return registration(email, hash);
        	}
        }
	}

	/**
	* Authenticate existing user
	*/
	private static Result authenticate(Profile profile, String hash) {
        ObjectNode result = Json.newObject();

        if (hash.equals(profile.password)) {
        	session("id", profile.id.toString());
        	result.put("message", "Successful login! Welcome, "
        		                      + profile.first_name + "!");
			return created(result);
        } else {
			result.put("message", "Invalid password");
			return unauthorized(result);
        }
	}

	/**
	* Registration a new user
	*/
	@Transactional
	private static Result registration(String email, String hash) {
		Profile profile = new Profile(email, hash);
		profile.save();
		return authenticate(profile, hash);
	}

	/**
	* Calculate HMAC-SHA1 of password using the application secret key as a salt
	*/
	private static String hashPassword(String password) {
		return Crypto.sign(password);
	}

	/**
	* Logout current user and delete its cookie
	*/
	@Security.Authenticated(Secured.class)
	public static Result logout() {
		session().clear();
		return Application.goodResponse("Goodbye!");
	}

	/**
	* Change password of current user
	*/
	@Security.Authenticated(Secured.class)
	@Transactional
	public static Result changePassword() {
		JsonNode jsonBody = request().body().asJson();
		if (jsonBody == null) {
			return Application.errorResponse("Expecting Json data");
        } else {
        	String oldPassword = jsonBody.findPath("oldPassword").getTextValue();
        	if (oldPassword == null) {
				return Application.errorResponse("Missing parameter [oldPassword]");
        	}
        	String newPassword = jsonBody.findPath("newPassword").getTextValue();
        	if (newPassword == null) {
				return Application.errorResponse("Missing parameter [newPassword]");
        	}

        	oldPassword = hashPassword(oldPassword);
        	newPassword = hashPassword(newPassword);

        	Profile profile = Profile.findById(Application.getProfileId());

        	if (profile.password.equals(oldPassword)) {
        		profile.password = newPassword;
        		profile.update();
        		return Application.goodResponse("Password successfully changed");
        	} else {
        		return Application.errorResponse("Old password is not valid");
        	}
        }
	}

}
