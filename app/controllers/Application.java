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
        return APIv1;
    }

    /**
     * This result directly redirect to API v1
     */
    public static Result APIv1 = redirect(
        routes.Application.hello()
    );

    /**
    * Simple API response that say Hello to anybody
    */
	public static Result hello() {
		ObjectNode result = Json.newObject();
		result.put("status", "OK");
		result.put("message", "Hello my friend!");
		return ok(result);
	}


    /**
    * JSON response to your request that say Hello to you
    */
	public static Result sayHello() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
        } else {
			ObjectNode result = Json.newObject();
			String name = json.findPath("name").getTextValue();
			if(name == null) {
				result.put("status", "KO");
				result.put("message", "Missing parameter [name]");
				return badRequest(result);
			} else {
				result.put("status", "OK");
				result.put("message", "Hello, " + name + "!");
				return ok(result);
			}
		}
	}
  
}
