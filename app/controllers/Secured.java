package controllers;

import play.mvc.Security;
import play.mvc.Result;
import static play.mvc.Results.*;

import play.mvc.Http.Context;

import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

public class Secured extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("id");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        ObjectNode result = Json.newObject();
		result.put("message", "Unauthorized user, please login");
		return unauthorized(result);
    }

}
