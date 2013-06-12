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

public class ApiTest {

    @Test
    public void redirectToApi() {
        Result result = routeAndCall(fakeRequest());
        assertThat(result).isNotNull();
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result).equals("/api/v1/hello"));
    }

    @Test
    public void helloToAnybody() {

        Result result = routeAndCall(fakeRequest(GET, "/api/v1/hello"));

        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Hello my friend!");
    }

    @Test
    public void postWithoutJsonData() {
        //FakeRequest fr = new FakeRequest(POST, "api/v1");
        Result result = routeAndCall(fakeRequest(POST, "/api/v1"));

        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Expecting Json data");
    }

    @Test
    public void postWithoutName() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("namea", "Patric");
        JsonNode node = Json.toJson(map);
        Result result = routeAndCall(fakeRequest("POST", "/api/v1")
                        .withJsonBody(node));

        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Missing parameter [name]");
    }

    @Test
    public void postWithNamePatric() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("name", "Patric");
        JsonNode node = Json.toJson(map);
        Result result = routeAndCall(fakeRequest("POST", "/api/v1")
                        .withJsonBody(node));

        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Hello, Patric!");
    }

    @Test
    public void badRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/xx/Kiki"));
        assertThat(result).isNull();
    }

    @Test
    public void goodRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();
    }
  
}
