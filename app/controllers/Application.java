package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

public class Application extends Controller {
  
    /**
     * Handle default path requests, redirect to API v1
     */
    public static Result index() {
    	String id = session("id");
  		if(id != null) {
  			Long profileId = Long.valueOf(id).longValue();
  			return redirect(routes.ProfilePage.getProfile(profileId));
  		} else {
			ObjectNode result = Json.newObject();
			result.put("message", "Unauthorized user, please login");
			return unauthorized(result);
  		}
    }

    /**
    * Generate error response with error message
    */
	public static Result errorResponse(String message) {
		ObjectNode result = Json.newObject();
		result.put("message", message);
		return badRequest(result);
	}
  
}
