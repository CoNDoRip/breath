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
                assertThat(contentAsString(result)).isEqualTo(
                    "{\"message\":\"Expecting Json data\"}"
                );
            }
        });
    }

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login 
    *      --header "Content-Type:application/json" 
    *      --data '{"no-email": "patric@mail.ru", "password": "password"}'
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
                assertThat(contentAsString(result)).isEqualTo(
                    "{\"message\":\"Missing parameter [email]\"}"
                    );
            }
        });
    }

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login 
    *      --header "Content-Type:application/json" 
    *      --data '{"email": "patric@mail.ru", "no-password": "password"}'
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
                assertThat(contentAsString(result)).isEqualTo(
                    "{\"message\":\"Missing parameter [password]\"}"
                    );
            }
        });
    }

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login 
    *      --header "Content-Type:application/json" 
    *      --data '{"email": "patric@mail.ru", "password": "1234"}'
    */
    @Test
    public void postWithInvalidPassword() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String,String> map = new HashMap<String,String>();
                map.put("email", "patric@mail.ru");
                map.put("password", "1234");
                JsonNode node = Json.toJson(map);
                Result result = routeAndCall(fakeRequest("POST", "/api/v1/login")
                                .withJsonBody(node));

                assertThat(status(result)).isEqualTo(UNAUTHORIZED);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).isEqualTo(
                    "{\"message\":\"Invalid email or password\"}"
                    );
            }
        });
    }

    /**
    * curl -v -X POST http://localhost:9000/api/v1/login 
    *      --header "Content-Type:application/json" 
    *      --data '{"email": "patric@mail.ru", "password": "password"}'
    */
    @Test
    public void redirectToPatricProfile() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String,String> map = new HashMap<String,String>();
                map.put("email", "patric@mail.ru");
                map.put("password", "password");
                JsonNode node = Json.toJson(map);
                Result result = routeAndCall(fakeRequest("POST", "/api/v1/login")
                                .withJsonBody(node));

                assertThat(result).isNotNull();
                assertThat(status(result)).isEqualTo(SEE_OTHER);
                assertThat(redirectLocation(result).equals("/api/v1/profile/id2"));
                //assertThat(cookie("id", result)).isEqualTo(2);
                //assertThat(cookie("hash", result)).isEqualTo("0aa371f7f51bd1312cef02e827f35122c46aa011");
            }
        });
    }
  
}
