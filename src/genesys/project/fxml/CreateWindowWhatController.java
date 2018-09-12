/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseHolder.mainWindowData;
import genesys.project.builder.GenesysProjectBuilder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private mainWindowData mainWindowDataPropagator;

    private void newSpeciesActions(String What) {
        try {
            GenesysProjectBuilder.hideOtherThanMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateSpeciesFXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            createSpeciesController = loader.getController();
            createSpeciesController.setDataLists(mainWindowDataPropagator);
            createSpeciesStage.setScene(scene);
            createSpeciesStage.setTitle("Choose Lifedomain");
            if (What.equals("Choose")) {
                createSpeciesStage.show();
            } else {
                createSpeciesController.newWhat(What);
            }
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(CreateWindowWhatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void createSpeciesChooseActionPerformed() {
        newSpeciesActions("Choose");
    }

    private void newCultureActions(String Selection) {
        try {
            GenesysProjectBuilder.hideOtherThanMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateCultureFXML.fxml"));
            Parent root = loader.load();
            createCultureController = loader.getController();
            createCultureController.setDataLists(mainWindowDataPropagator);
            Scene scene = new Scene(root);
            createCultureStage.setScene(scene);
            createCultureStage.setTitle("Create Culture");
            if (Selection != null && !Selection.equals("Choose")) {
                createCultureController.setSpeciesSelection(Selection);
            }
            createCultureStage.show();
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(CreateWindowWhatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void createCultureChooseActionPerformed() {
        newCultureActions("Choose");
    }

    private void newRosterActions(String Selection) {
        try {
            GenesysProjectBuilder.hideOtherThanMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateRosterFXML.fxml"));
            Parent root = loader.load();
            createRosterController = loader.getController();
            createRosterController.setDataLists(mainWindowDataPropagator);
            Scene scene = new Scene(root);
            createRosterStage.setScene(scene);
            createRosterStage.setTitle("Create Roster");
            if (Selection != null) {
                createRosterController.setSpeciesAndCultureSelection(Selection);
            }
            createRosterStage.show();
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(CreateWindowWhatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void createRosterChooseActionPerformed() {
        newRosterActions("Choose");
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

    void setDataLists(DatabaseHolder.mainWindowData mainWindowDataPropagator) {
        this.mainWindowDataPropagator = mainWindowDataPropagator;
    }

    void newWhat(String What, String Selection) {
        switch (What) {
            case "Species":
                newSpeciesActions("Choose");
                break;
            case "Culture":
                newCultureActions(Selection);
                break;
            case "Roster":
                newRosterActions(Selection);
                break;
            case "Humanoid":
            case "Fey":
            case "Reptilia":
            case "Biest":
            case "Insecta":
                newSpeciesActions(What);
                break;
            default:
                break;
        }
    }

}
