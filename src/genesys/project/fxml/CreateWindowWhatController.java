/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class CreateWindowWhatController implements Initializable {

    /**
     * @FXML private CreateWindowWhat = new javax.swing.JDialog;
     *
     */
    @FXML
    private Button newSpecies;
    @FXML
    private Button newCulture;
    @FXML
    private Button newRoster;

    /**
     * createSpeciesStage
     */
    public Stage createSpeciesStage = new Stage();
    private CreateSpeciesController createSpeciesController;

    /**
     * createCultureStage
     */
    public Stage createCultureStage = new Stage();
    private CreateCultureController createCultureController;

    /**
     * createRosterStage
     */
    public Stage createRosterStage = new Stage();
    private CreateRosterController createRosterController;

    private ListView speciesList;

    @FXML
    private void newSpeciesActions() throws IOException {
        Stage stage = (Stage) newSpecies.getScene().getWindow();
        stage.hide();
        if (createSpeciesStage.isShowing()) {
            createSpeciesStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateSpeciesFXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            createSpeciesController = loader.getController();
            createSpeciesController.setSpeciesList(speciesList);
            createSpeciesStage.setScene(scene);
            createSpeciesStage.show();
        }
    }

    @FXML
    private void newCultureActions() throws IOException {
        Stage stage = (Stage) newCulture.getScene().getWindow();
        stage.hide();
        if (createCultureStage.isShowing()) {
            createCultureStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateCultureFXML.fxml"));
            Parent root = loader.load();
            createCultureController = loader.getController();
            createCultureController.setSpeciesList(speciesList);
            Scene scene = new Scene(root);
            createCultureStage.setScene(scene);
            createCultureStage.show();
        }
    }

    @FXML
    private void newRosterAcrions() throws IOException {
        Stage stage = (Stage) newRoster.getScene().getWindow();
        stage.hide();
        if (createRosterStage.isShowing()) {
            createRosterStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateRosterFXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            createRosterController = loader.getController();
            createRosterStage.setScene(scene);
            createRosterStage.show();
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

}
