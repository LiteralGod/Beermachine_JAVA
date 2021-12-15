package Presentation;

import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebMain {

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8080;
        Server server = new Server();

        WebAppContext context = new WebAppContext();

        String resourceBasePath = "./src/main/resources";
        context.setDescriptor(resourceBasePath + "WEB-INF/web.xml");
        context.setResourceBase(resourceBasePath);
        context.setExtractWAR(true);

        // JSP support
        context.addServlet(new ServletHolder("jsp", JettyJspServlet.class), "*.jsp");



        ServerConnector connector = new ServerConnector(server);
        connector.setHost(host);
        connector.setPort(port);
        server.setHandler(context);

        server.setConnectors(new Connector[] { connector });

        server.start();
        server.join();
    }
}