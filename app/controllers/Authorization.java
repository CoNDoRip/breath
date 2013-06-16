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

	public static Result needLogin() {
		ObjectNode result = Json.newObject();
		result.put("status", "Unauthorized");
		result.put("message", "Unauthorized user, please login");
		result.put("url", "/api/v1/login");
		return unauthorized(result);
	}

	@Transactional(readOnly=true)
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
        	// All data exists, start authenticate the user
        	String hash = hashPassword(password);
        	Long id;
        	try {
        		id = Profile.login(email, hash);
        		session("id", id.toString());
        		session("hash", hash);
				return redirect(routes.ProfilePage.getProfile(id));
        	} catch (NoResultException e) {
				return Application.errorResponse("Invalid email or password");
        	}
        }
	}

	private static String hashPassword(String password) {
		return Crypto.sign(password);
	}

}
