/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import genesys.project.fxml.ErrorController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author krzysztofg
 */
public final class GenesysProjectBuilder extends Application {

    private static Window primaryStageWindow;

    @Override
    public void start(Stage primaryStage) {
        try {
            DatabaseSwiper.databaseSwiper();
            Parent root = FXMLLoader.load(getClass().getResource("/genesys/project/fxml/BuilderFXML.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Genesys Project Builder");
            primaryStage.show();
            primaryStage.setOnCloseRequest(e -> Platform.exit());
            primaryStageWindow = Window.getWindows().get(0);
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(GenesysProjectBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * hideOtherThanMainStage
     */
    public static void hideOtherThanMainStage() {
        for (int i = 0; i < Window.getWindows().size(); i++) {
            if (!Window.getWindows().get(i).equals(primaryStageWindow)) {
                Window.getWindows().get(i).hide();
            }
        }
    }

}
