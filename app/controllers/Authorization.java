package controllers;

import play.*;
import play.mvc.*;
import play.db.jpa.*;

import views.html.*;
import models.Profile;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

public class Authorization extends Controller {

	@Transactional(readOnly=true)
	public static Result login() {
		ObjectNode result = Json.newObject();
		JsonNode jsonBody = request().body().asJson();
		if (jsonBody == null) {
			result.put("status", "Bad request");
			result.put("message", "Expecting Json data");
			return badRequest(result);
        } else {
        	String email = jsonBody.findPath("email").getTextValue();
        	if (email == null) {
				result.put("status", "Bad request");
				result.put("message", "Missing parameter [email]");
				return badRequest(result);
        	}
        	String password = jsonBody.findPath("password").getTextValue();
        	if (password == null) {
				result.put("status", "Bad request");
				result.put("message", "Missing parameter [password]");
				return badRequest(result);
        	}
        	
        	Long id = Profile.login(email, password);
			return redirect(routes.ProfilePage.getProfile(id));
        }
	}

}
