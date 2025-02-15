package fr.ul.miage.weiss.s8_projet_poo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale de l'application.
 * @author Lucas WEISS
 */
public class MyApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("hello-view.fxml"));
        Modele modele = new Modele();
        fxmlLoader.setControllerFactory(c -> new HomeController(modele));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.getIcons().add(new javafx.scene.image.Image(MyApplication.class.getResourceAsStream("logo.png")));
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setTitle("Projet COO S8 - WEISS Lucas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
