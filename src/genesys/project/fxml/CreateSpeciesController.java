/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.Enums.Enmuerations.Creators;
import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
import genesys.project.builder.DatabaseModifier;
import genesys.project.builder.GenesysProjectBuilder;
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
     * speciesCreatorWindowStage
     */
    public Stage speciesCreatorWindowStage = new Stage();
    private SpeciesCreatorWindowController speciesCreatorWindowController;

    private ListView speciesList;

    /**
     *
     * @throws IOException
     */
    @FXML
    public void createHumanoidActions() throws IOException {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseModifier.creator(LifedomainValue.Humanoid, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    public void createFeyActions() throws IOException {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseModifier.creator(LifedomainValue.Fey, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    public void createReptiliaActions() throws IOException {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseModifier.creator(LifedomainValue.Reptilia, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    public void createBiestActions() throws IOException {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseModifier.creator(LifedomainValue.Biest, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    public void createInsectaActions() throws IOException {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseModifier.creator(LifedomainValue.Insecta, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @throws IOException
     */
    public void showNextStage() throws IOException {
        GenesysProjectBuilder.hideOtherThanMainStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/SpeciesCreatorWindowFXML.fxml"));
        Parent root = loader.load();
        speciesCreatorWindowController = loader.getController();
        speciesCreatorWindowController.setSpeciesList(speciesList);
        Scene scene = new Scene(root);
        speciesCreatorWindowStage.setScene(scene);
        speciesCreatorWindowStage.show();
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

    void newWhat(String What) throws IOException {
        switch (What) {
            case "Humanoid":
                createHumanoidActions();
                break;
            case "Fey":
                createFeyActions();
                break;
            case "Reptilia":
                createReptiliaActions();
                break;
            case "Biest":
                createBiestActions();
                break;
            case "Insecta":
                createInsectaActions();
                break;
        }
    }

}
