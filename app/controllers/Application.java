package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

public class Application extends Controller {
  
    /**
     * Preview page
     */
    public static Result index() {
        return ok(index.render());
    }

    public static Result about() {
        return ok(about.render());
    }

    public static Result download() {
        return ok(download.render());
    }

    public static Result contact() {
        return ok(contact.render());
    }

    /**
    * Get profile id from cookie 
    */
    public static Long getProfileId() {
        String id = session("id");
        Long profileId = Long.valueOf(id).longValue();
        return profileId;
    }

    /**
    * Generate error response with error message
    */
	public static Result errorResponse(String message) {
	    ObjectNode result = Json.newObject();
	    result.put("message", message);
	    return badRequest(result);
	}

    /**
    * Generate error response with error message
    */
    public static Result goodResponse(String message) {
        ObjectNode result = Json.newObject();
        result.put("message", message);
        return ok(result);
    }
  
}
