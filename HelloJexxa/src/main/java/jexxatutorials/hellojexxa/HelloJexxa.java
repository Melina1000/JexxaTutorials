package jexxatutorials.hellojexxa;

import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivingadapter.jmx.JMXAdapter;
import io.jexxa.infrastructure.drivingadapter.rest.RESTfulRPCAdapter;

public final class HelloJexxa
{
    public static void main(String[] args)
    {
        //Create your jexxaMain for this application
        JexxaMain jexxaMain = new JexxaMain("HelloJexxa");

        jexxaMain
                //Bind a JMX adapter to a business object.
                //It allows to access the public methods of the business object via 'jconsole'
                .bind(JMXAdapter.class).to(jexxaMain.getBoundedContext())

                //Bind a REST adapter to the same business object.
                //It allows to access the public mehtods of the business object via RMI over REST
                .bind(RESTfulRPCAdapter.class).to(jexxaMain.getBoundedContext())

                //Start Jexxa and all bindings
                .start()

                //Wait until shutdown is called by one of the following options:
                // - Press CRTL-C
                // Use 'jconsole' to connect to this application and invoke method shutdown
                // - Use HTTP-post to URL: 'http://localhost:7001/BoundedContext/shutdown'
                //   (using curl: 'curl -X POST http://localhost:7001/BoundedContext/shutdown'
                .waitForShutdown()

                //Finally invoke stop() for proper cleanup
                .stop();
    }
}
