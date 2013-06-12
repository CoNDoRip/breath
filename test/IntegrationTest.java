import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import play.libs.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
    * Starting a real HTTP server
    * Sometimes you want to test the real HTTP stack from with your test. 
    * You can do this by starting a test server:
    */
    @Test
    public void testInServer() {
        running(testServer(3333), new Runnable() {
            public void run() {
               assertThat(
                 WS.url("http://localhost:3333").get().get().getStatus()
               ).isEqualTo(OK);
            }
        });
    }
  
}
