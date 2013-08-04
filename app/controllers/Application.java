package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;

import views.html.cap.*;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import play.libs.Crypto;

import java.io.File;

public class Application extends Controller {

    public static final String PATH_TO_ALL_IMAGES = File.separator + "public" 
                                                  + File.separator + "images"
                                                  + File.separator;

    public static final String AVATAR_IMAGES    = PATH_TO_ALL_IMAGES + "avatars"
                                                + File.separator;

    public static final String  USERTASK_IMAGES = PATH_TO_ALL_IMAGES + "usertasks"
                                                + File.separator;
  
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
        String idHash = session("id");
        String id = Crypto.decryptAES(idHash);
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
