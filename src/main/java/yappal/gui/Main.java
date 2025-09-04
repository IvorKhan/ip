package yappal.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import yappal.YapPal;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private YapPal yapPal = new YapPal();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setYapPal(yapPal);  // inject the YapPal instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
