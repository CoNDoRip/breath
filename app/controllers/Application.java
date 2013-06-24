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
    * Generate error response with error message
    */
	  public static Result errorResponse(String message) {
	      ObjectNode result = Json.newObject();
	      result.put("message", message);
	      return badRequest(result);
	  }
  
}
