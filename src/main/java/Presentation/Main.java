package Presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/Start.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Beermachine");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX() + (bounds.getWidth() - root.getScene().getWidth()) * 0.5;
        double y = bounds.getMinY() + (bounds.getHeight() - root.getScene().getHeight()) * 0.5;
        primaryStage.setX(x);
        primaryStage.setY(y);
    }

    public static void main(String[] args) throws Exception {
        // launch(args);
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