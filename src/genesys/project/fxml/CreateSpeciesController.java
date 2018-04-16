/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE.Enmuerations.Creators;
import genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue;
import genesys.project.builder.DatabaseModifier;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class CreateSpeciesController implements Initializable {

    /**
     * @FXML private CreateSpecies = new javax.swing.JDialog;
     *
     */
    @FXML
    private Button createHumanoid;
    @FXML
    private Button createFey;
    @FXML
    private Button createReptilia;
    @FXML
    private Button createBiest;
    @FXML
    private Button createInsecta;

    /**
     *speciesCreatorWindowStage
     */
    public Stage speciesCreatorWindowStage = new Stage();
    private SpeciesCreatorWindowController speciesCreatorWindowController;

    private ListView speciesList;

    /**
     *
     * @throws IOException
     */
    public void createHumanoidActions() throws IOException {
        Stage stage = (Stage) createHumanoid.getScene().getWindow();
        stage.hide();
        DatabaseModifier.creator(LifedomainValue.Humanoid, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    public void createFeyActions() throws IOException {
        Stage stage = (Stage) createFey.getScene().getWindow();
        stage.hide();
        DatabaseModifier.creator(LifedomainValue.Fey, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    public void createReptiliaActions() throws IOException {
        Stage stage = (Stage) createReptilia.getScene().getWindow();
        stage.hide();
        DatabaseModifier.creator(LifedomainValue.Reptilia, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    public void createBiestActions() throws IOException {
        Stage stage = (Stage) createBiest.getScene().getWindow();
        stage.hide();
        DatabaseModifier.creator(LifedomainValue.Biest, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    public void createInsectaActions() throws IOException {
        Stage stage = (Stage) createInsecta.getScene().getWindow();
        stage.hide();
        DatabaseModifier.creator(LifedomainValue.Insecta, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    public void showNextStage() throws IOException {
        if (speciesCreatorWindowStage.isShowing()) {
            speciesCreatorWindowStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/SpeciesCreatorWindowFXML.fxml"));
            Parent root = loader.load();
            speciesCreatorWindowController = loader.getController();
            speciesCreatorWindowController.setSpeciesList(speciesList);
            Scene scene = new Scene(root);
            speciesCreatorWindowStage.setScene(scene);
            speciesCreatorWindowStage.show();
        }

    }

    /**
     * Initializes the controller class.
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
