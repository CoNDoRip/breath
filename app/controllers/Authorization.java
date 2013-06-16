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
		result.put("message", "Unauthorized user, please login");
		return unauthorized(result);
	}

	@Transactional(readOnly=true)
	public static Result login() {
		JsonNode jsonBody = request().body().asJson();
		if (jsonBody == null) {
			// Return HTML!!! Bug
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
        		ObjectNode result = Json.newObject();
				result.put("message", "Invalid email or password");
				return unauthorized(result);
        	}
        }
	}

	private static String hashPassword(String password) {
		return Crypto.sign(password);
	}

}
