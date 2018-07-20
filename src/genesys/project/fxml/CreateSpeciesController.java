/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.Enums.Enmuerations.Creators;
import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
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
     * @
     */
    @FXML
    public void createHumanoidActions()  {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseHolder.creator(LifedomainValue.Humanoid, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @
     */
    @FXML
    public void createFeyActions()  {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseHolder.creator(LifedomainValue.Fey, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @
     */
    @FXML
    public void createReptiliaActions()  {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseHolder.creator(LifedomainValue.Reptilia, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @
     */
    @FXML
    public void createBiestActions()  {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseHolder.creator(LifedomainValue.Biest, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @
     */
    @FXML
    public void createInsectaActions()  {
        GenesysProjectBuilder.hideOtherThanMainStage();
        DatabaseHolder.creator(LifedomainValue.Insecta, Creators.CreateSpecies);
        showNextStage();
    }

    /**
     *
     * @
     */
    public void showNextStage()  {
        try {
            GenesysProjectBuilder.hideOtherThanMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/SpeciesCreatorWindowFXML.fxml"));
            Parent root = loader.load();
            speciesCreatorWindowController = loader.getController();
            speciesCreatorWindowController.setSpeciesList(speciesList);
            Scene scene = new Scene(root);
            speciesCreatorWindowStage.setScene(scene);
            speciesCreatorWindowStage.setTitle("Create Species");
            speciesCreatorWindowStage.show();
        } catch (IOException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(CreateSpeciesController.class.getName()).log(Level.SEVERE, null, ex);
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

    void newWhat(String What)  {
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
            default:
                break;
        }
    }

}
