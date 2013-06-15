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

public class ProfilePageTest {

    /**
    * curl -v http://localhost:9000/api/v1/profile/5
    */
    @Test
    public void getProfile5() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = routeAndCall(fakeRequest(GET, "/api/v1/profile/5"));

                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result).length()).isEqualTo(62);
                assertThat(contentAsString(result)).contains(
                    "{\"id\":5,\"first_name\":\"Patric\",\"last_name\":\"Grey\",\"gender\":\"M\"}"
                );
            }
        });
    }
  
}
