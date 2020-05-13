package sample;

import Data.ClientData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        String title = ClientData.getInstance().getCompanyList().get(0).getName();
        primaryStage.setTitle(title + " v1.2");
        primaryStage.setScene(new Scene(root, 800, 600));
        Image image = new Image("file:tax.png");
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }

    @Override
    public void init() {
        if (!ClientData.getInstance().open()) {
            System.out.println("Not able to successfuly open ClientConnection");
        }
    }

    @Override
    public void stop() {
        ClientData.getInstance().close();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
