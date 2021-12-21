package Presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FXMain extends Application {

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

    public static void main(String[] args) {
        launch(args);
    }
}
