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
    * curl -v http://localhost:9000/api/v1/profile/id2
    */
    @Test
    public void sayThatYouUnauthorizedUser() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = routeAndCall(fakeRequest(GET, "/api/v1/profile/id2"));

                assertThat(status(result)).isEqualTo(UNAUTHORIZED);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).isEqualTo(
                    "{\"message\":\"Unauthorized user, please login\"}"
                );
            }
        });
    }

    /**
    * curl -v http://localhost:9000/api/v1/profile/id2
    *      -b "PLAY_SESSION=49e6efba8019d9c8576880ad26f5090f60f128a8-id%3A2%00hash%3A0aa371f7f51bd1312cef02e827f35122c46aa011"
    */
    @Test
    public void getPatricProfile() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                FakeRequest fr = fakeRequest(GET, "/api/v1/profile/id2");
                Http.Cookie cookie = new Http.Cookie("PLAY_SESSION"
                    , "49e6efba8019d9c8576880ad26f5090f60f128a8-id%3A2%00hash%3A0aa371f7f51bd1312cef02e827f35122c46aa011"
                    , null
                    , null
                    , null
                    , true
                    , true
                );
                fr.withCookies(cookie);
                Result result = routeAndCall(fr);

                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("application/json");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).isEqualTo(
                    "{\"id\":2,\"first_name\":\"Patric\",\"last_name\":\"Grey\",\"gender\":\"M\"}"
                );
            }
        });
    }

}
