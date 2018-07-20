/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.AvailableSkillsLister;
import genesys.project.builder.BuilderCORE;
import genesys.project.builder.Enums.Enmuerations.Creators;
import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
import genesys.project.builder.Enums.Enmuerations.MainDomainValue;
import genesys.project.builder.Enums.Enmuerations.MainLineageValue;
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class CreateCultureController implements Initializable {

    //@FXML private CreateCulture4;
    @FXML
    private ListView skillsList5;
    @FXML
    private Label speciesChooseLabel4;
    @FXML
    private ComboBox speciesChooseDropdown4;
    @FXML
    private Label willpowerText4;
    @FXML
    private Label commandText4;
    @FXML
    private Label commandValue4;
    @FXML
    private Label rTValue4;
    @FXML
    private Label mTText4;
    @FXML
    private Label rTText4;
    @FXML
    private Label moraleText4;
    @FXML
    private Label mTValue4;
    @FXML
    private Label moraleValue4;
    @FXML
    private Label sizeText4;
    @FXML
    private Label attacksText4;
    @FXML
    private Label woundsText4;
    @FXML
    private Label pointsPerModelValue4;
    @FXML
    private Label woundsValue4;
    @FXML
    private Label sizeValue4;
    @FXML
    private Label characteristicsText4;
    @FXML
    private Label targetNumbersText4;
    @FXML
    private Label skillsText4;
    @FXML
    private Label pointsPerModelText4;
    @FXML
    private Label strengthText4;
    @FXML
    private Label strengthValue4;
    @FXML
    private Label toughnessText4;
    @FXML
    private Label toughnessValue4;
    @FXML
    private Label movementText4;
    @FXML
    private Label movementValue4;
    @FXML
    private Label martialText4;
    @FXML
    private Label martialValue4;
    @FXML
    private Label rangedText4;
    @FXML
    private Label rangedValue4;
    @FXML
    private Label defenseText4;
    @FXML
    private Label defenseValue4;
    @FXML
    private Label disciplineValue4;
    @FXML
    private Label willpowerValue4;
    @FXML
    private Label disciplineText4;
    @FXML
    private Label attacksValue4;
    @FXML
    private Button selectSpecies4;
    @FXML
    private Button cancelSelectSpecies4;
    @FXML
    private TextField cultureNameValue4;
    @FXML
    private Label cultureNameText4;
    @FXML
    private Label lifeDomainText4;
    @FXML
    private Label lifeDomainValue4;

    /**
     *
     */
    public Stage createHoldWindowStage = new Stage();
    private CreateHoldWindowController createHoldWindowController;

    private ListView speciesList;
    private Label[] valuesLabels4;

    /**
     *
     */
    public void createCulture()  {
        ObservableList data = DatabaseReader.getSpeciesData(speciesChooseDropdown4.getSelectionModel().getSelectedItem().toString());
        DatabaseHolder.creator(LifedomainValue.valueOf(data.get(0).toString().split("\\|")[0]), Creators.CreateCulture);
        DatabaseHolder.holdCulture.setSpeciesName(speciesChooseDropdown4.getSelectionModel().getSelectedItem().toString());
        DatabaseHolder.holdSpecies.setSpeciesName(speciesChooseDropdown4.getSelectionModel().getSelectedItem().toString());
        DatabaseHolder.holdSpecies.setSkills(data.get(0).toString().split("\\|")[3] + ",");
        clearLists4();
        //skillsList1 = DatabaseReader.getAddedSkills(DatabaseReader.holdSpecies.getFullSkills());
        //BuilderFXMLController.getSkillModifiers(ruledskills);
        DatabaseHolder.holdSpecies.setSpeciesModifiers(data.get(0).toString().split("\\|")[4]);
        
        switch (DatabaseHolder.holdSpecies.getLifedomain()) {
            case Humanoid:
                DatabaseHolder.arcana = Boolean.valueOf(DatabaseHolder.holdSpecies.getSpeciesModifiers().split("=")[1]);
                break;
            case Fey:
                ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).setMainDomain(MainDomainValue.getEnum((DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[0].split("=")[1])));
                DatabaseHolder.outcasts = Boolean.valueOf(DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[1].split("=")[1]);
                ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).setSecondaryDomain(MainDomainValue.getEnum(DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[2].split("=")[1]));
                break;
            case Reptilia:
                ((DatabaseHolder.AReptilia) DatabaseHolder.holdSpecies).setMainLineage(MainLineageValue.getEnum(DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[0].split("=")[1]));
                break;
            case Biest:
                break;
            case Insecta:
                break;
        }
        //skillSetChooser.setItems(DatabaseReader.getSkillSet());
        lifeDomainValue4.setText(DatabaseHolder.holdSpecies.getLifedomain().toString());
        skillsList5.setItems(AvailableSkillsLister.getAddedSkills(DatabaseHolder.holdSpecies.getSkills()));
        BuilderFXMLController.getSkillModifiers(DatabaseHolder.ruledskills);
        int cost = BuilderCORE.baseAddedCost(DatabaseHolder.holdSpecies.getLifedomain(), DatabaseHolder.holdSpecies, null, DatabaseHolder.b, 0);
        String tex = Integer.toString(cost);
        pointsPerModelValue4.setText(tex);
        populateLabels();
    }

    /**
     *
     */
    @FXML
    public void speciesChooseDropdown4ItemStateChangedActions()  {
        if (speciesChooseDropdown4.getSelectionModel().getSelectedItem().equals("--CHOOSE--")) {
            clearLists4();
        }
        createCulture();
    }

    /**
     *
     * @
     */
    @FXML
    public void selectSpecies4Actions()  {
        Stage stage = (Stage) selectSpecies4.getScene().getWindow();
        stage.hide();
        DatabaseHolder.holdCulture.setCultureName(cultureNameValue4.getText());
        if (createHoldWindowStage.isShowing()) {
            createHoldWindowStage.requestFocus();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateHoldWindowFXML.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                createHoldWindowController = loader.getController();
                createHoldWindowController.setSpeciesList(speciesList);
                createHoldWindowStage.setScene(scene);
                createHoldWindowStage.setTitle("Create Classes for " + DatabaseHolder.holdCulture.getSpeciesName() + " - " + DatabaseHolder.holdCulture.getCultureName());
                createHoldWindowStage.show();
            } catch (IOException ex) {
                ErrorController.ErrorController(ex);
                Logger.getLogger(CreateCultureController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * cancelSelectSpecies4Actions
     */
    @FXML
    public void cancelSelectSpecies4Actions() {
        Stage stage = (Stage) cancelSelectSpecies4.getScene().getWindow();
        stage.hide();
    }

    /**
     *
     * @
     */
    public void populateLabels()  {
        for (int i = 0; i < valuesLabels4.length; i++) {
            valuesLabels4[i].setText(DatabaseReader.getCharacteristics(DatabaseHolder.holdSpecies.getLifedomain().toString(), DatabaseHolder.holdSpecies.getCharacteristicGroup().toString())[i]);
        }
    }

    /**
     * clearLists4
     */
    public void clearLists4() {
        strengthValue4.setText("");
        toughnessValue4.setText("");
        movementValue4.setText("");
        martialValue4.setText("");
        rangedValue4.setText("");
        defenseValue4.setText("");
        disciplineValue4.setText("");
        willpowerValue4.setText("");
        commandValue4.setText("");
        mTValue4.setText("");
        rTValue4.setText("");
        moraleValue4.setText("");
        pointsPerModelValue4.setText("");
        woundsValue4.setText("");
        sizeValue4.setText("");
        attacksValue4.setText("");
        skillsList5.getItems().clear();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.valuesLabels4 = new Label[]{strengthValue4, toughnessValue4, movementValue4, martialValue4, rangedValue4, defenseValue4, disciplineValue4, willpowerValue4, commandValue4, woundsValue4, attacksValue4, sizeValue4, mTValue4, rTValue4, moraleValue4};
        speciesChooseDropdown4.setItems(DatabaseReader.populateDropdownsSpecies());
        speciesChooseDropdown4.getSelectionModel().select(0);
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

    void setSpeciesSelection(String Selection) {
        speciesChooseDropdown4.getSelectionModel().select(Selection);
        speciesChooseDropdown4ItemStateChangedActions();
    }

}
