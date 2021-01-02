import com.edu.nju.huluwa.GameManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent mainMenuRoot = FXMLLoader.load(getClass().getClassLoader().getResource("mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuRoot, 300, 500);
        mainMenuScene.getStylesheets().add(getClass().getClassLoader().getResource("mainMenu.css").toExternalForm());

        primaryStage.setTitle("HuluwaGame");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
        GameManager.getInstance().setPrimaryStage(primaryStage);
    }
}
