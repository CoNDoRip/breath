import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import org.codehaus.jackson.JsonNode;
import play.libs.Json;
import java.util.Map;
import java.util.HashMap;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AuthorizationTest {

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login --header "Content-Type:application/json" --data '{"email": "patric@mail.ru", "password": "password"}'
    */
    //@Test
    //public void redirectToProfile() {
    //    Map<String,String> map = new HashMap<String,String>();
    //    map.put("email", "patric@mail.ru");
    //    map.put("password", "password");
    //    JsonNode node = Json.toJson(map);
    //    Result result = routeAndCall(fakeRequest("POST", "/api/v1/login")
    //                    .withJsonBody(node));
    //
    //    assertThat(result).isNotNull();
    //    assertThat(status(result)).isEqualTo(SEE_OTHER);
    //    assertThat(redirectLocation(result).contains("/api/v1/profile/2"));
    //}

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login
    */
    @Test
    public void postWithoutJsonData() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = routeAndCall(fakeRequest(POST, "/api/v1/login"));

                assertThat(status(result)).isEqualTo(BAD_REQUEST);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).contains("Expecting Json data");
            }
        });
    }

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login --header "Content-Type:application/json" --data '{"no-email": "patric@mail.ru", "password": "password"}'
    */
    @Test
    public void postWithoutEmail() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String,String> map = new HashMap<String,String>();
                map.put("no-email", "patric@mail.ru");
                map.put("password", "password");
                JsonNode node = Json.toJson(map);
                Result result = routeAndCall(fakeRequest("POST", "/api/v1/login")
                                .withJsonBody(node));

                assertThat(status(result)).isEqualTo(BAD_REQUEST);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).contains("Missing parameter [email]");
            }
        });
    }

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login --header "Content-Type:application/json" --data '{"email": "patric@mail.ru", "no-password": "password"}'
    */
    @Test
    public void postWithoutPassword() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String,String> map = new HashMap<String,String>();
                map.put("email", "patric@mail.ru");
                map.put("no-password", "password");
                JsonNode node = Json.toJson(map);
                Result result = routeAndCall(fakeRequest("POST", "/api/v1/login")
                                .withJsonBody(node));

                assertThat(status(result)).isEqualTo(BAD_REQUEST);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).contains("Missing parameter [password]");
            }
        });
    }

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login --header "Content-Type:application/json" --data '{"email": "patric@mail.ru", "password": "password"}'
    */
    @Test
    public void postWithPatricsEmail() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String,String> map = new HashMap<String,String>();
                map.put("email", "patric@mail.ru");
                map.put("password", "password");
                JsonNode node = Json.toJson(map);
                Result result = routeAndCall(fakeRequest("POST", "/api/v1/login")
                                .withJsonBody(node));
                
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).contains("Hello, Patric!");
            }
        });
    }
  
}
