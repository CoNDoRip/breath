import java.util.Map;
import java.util.HashMap;

import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    /**
    * Test redirect to login page for unauthorized users
    * curl -v http://localhost:9000/
    */
    @Test
    public void sayThatYouUnauthorizedUser() {
        Result result = routeAndCall(fakeRequest());

        assertThat(status(result)).isEqualTo(UNAUTHORIZED);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).isEqualTo(
            "{\"message\":\"Unauthorized user, please login\"}"
        );
    }

    /**
    * Test redirect to profile page for authorized users
    * curl -v http://localhost:9000/
    *      -b "PLAY_SESSION=49e6efba8019d9c8576880ad26f5090f60f128a8-id%3A2%00hash%3A0aa371f7f51bd1312cef02e827f35122c46aa011"
    */
    @Test
    public void redirectAuthorizedUserToProfilePage() {
        FakeRequest fr = fakeRequest();
        Http.Cookie cookie = new Http.Cookie(
              "PLAY_SESSION"
            , "49e6efba8019d9c8576880ad26f5090f60f128a8-id%3A2%00hash%3A0aa371f7f51bd1312cef02e827f35122c46aa011"
            , null
            , null
            , null
            , true
            , true
        );
        fr.withCookies(cookie);
        Result result = routeAndCall(fr);

        assertThat(result).isNotNull();
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result).equals("/api/v1/profile/id2"));
    }
    
    /**
    * Test render of template
    */
    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }  
   
}
