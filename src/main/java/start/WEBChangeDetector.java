package start; /**
 * Created by snels on 15.12.2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WEBChangeDetector extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("WEB ChangeDetector");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/WCD.png")));
        primaryStage.show();
    }
}
