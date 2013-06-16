import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void redirectUnauthorizedUserToLoginPage() {
        Result result = routeAndCall(fakeRequest());

        assertThat(result).isNotNull();
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result).equals("/api/v1/login"));
    }
    
    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }  
   
}
