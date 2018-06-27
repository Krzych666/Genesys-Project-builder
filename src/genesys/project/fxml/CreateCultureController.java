/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.Enums.Enmuerations.Creators;
import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
import genesys.project.builder.Enums.Enmuerations.MainDomainValue;
import genesys.project.builder.Enums.Enmuerations.MainLineageValue;
import genesys.project.builder.Enums.Enmuerations.UseCases;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import genesys.project.builder.DatabaseModifier;
import genesys.project.builder.GenesysProjectBuilder;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @throws SQLException
     */
    public void createCulture() throws SQLException {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomain FROM CreatedSpecies WHERE SpeciesName = ?");
            stmt.setString(1, speciesChooseDropdown4.getSelectionModel().getSelectedItem().toString());
            DatabaseModifier.creator(LifedomainValue.valueOf(BuilderCORE.getValue(stmt, "LifeDomain")), Creators.CreateCulture);
        } catch (IOException ex) {
            Logger.getLogger(CreateCultureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DatabaseModifier.holdCulture.setSpeciesName(speciesChooseDropdown4.getSelectionModel().getSelectedItem().toString());
        DatabaseModifier.holdSpecies.setSpeciesName(speciesChooseDropdown4.getSelectionModel().getSelectedItem().toString());
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedSpecies WHERE SpeciesName = ?");
        stmt1.setString(1, speciesChooseDropdown4.getSelectionModel().getSelectedItem().toString());
        DatabaseModifier.holdSpecies.setSkills(BuilderCORE.getValue(stmt1, "Skills") + ",");
        clearLists4();
        //skillsList1 = DatabaseModifier.getAddedSkills(DatabaseModifier.holdSpecies.getSkills());
        //BuilderFXMLController.getSkillModifiers(ruledskills);
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT SpeciesModifiers FROM CreatedSpecies WHERE SpeciesName = ?");
        stmt2.setString(1, speciesChooseDropdown4.getSelectionModel().getSelectedItem().toString());
        DatabaseModifier.holdSpecies.setSpeciesModifiers(BuilderCORE.getValue(stmt2, "SpeciesModifiers"));
        switch (DatabaseModifier.holdSpecies.getLifedomain()) {
            case Humanoid:
                DatabaseModifier.arcana = Boolean.valueOf(DatabaseModifier.holdSpecies.getSpeciesModifiers().split("=")[1]);
                break;
            case Fey:
                ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setMainDomain(MainDomainValue.getEnum((DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[0].split("=")[1])));
                DatabaseModifier.outcasts = Boolean.valueOf(DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[1].split("=")[1]);
                ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setSecondaryDomain(MainDomainValue.getEnum(DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[2].split("=")[1]));
                break;
            case Reptilia:
                ((DatabaseModifier.AReptilia) DatabaseModifier.holdSpecies).setMainLineage(MainLineageValue.getEnum(DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[0].split("=")[1]));
                break;
            case Biest:
                break;
            case Insecta:
                break;
        }
        //skillSetChooser.setItems(DatabaseModifier.getSkillSet());
        lifeDomainValue4.setText(DatabaseModifier.holdSpecies.getLifedomain().toString());
        skillsList5.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.holdSpecies.getSkills()));
        DatabaseModifier.excludeSkills = skillsList5;
        BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
        int cost = DatabaseModifier.baseAddedCost(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdSpecies, null, DatabaseModifier.b, 0);
        String tex = Integer.toString(cost);
        pointsPerModelValue4.setText(tex);
        populateLabels();
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void speciesChooseDropdown4ItemStateChangedActions() throws IOException, SQLException {
        if (speciesChooseDropdown4.getSelectionModel().getSelectedItem().equals("--CHOOSE--")) {
            clearLists4();
        }
        createCulture();
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    public void selectSpecies4Actions() throws IOException {
        Stage stage = (Stage) selectSpecies4.getScene().getWindow();
        stage.hide();
        DatabaseModifier.holdCulture.setCultureName(cultureNameValue4.getText());
        if (createHoldWindowStage.isShowing()) {
            createHoldWindowStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateHoldWindowFXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            createHoldWindowController = loader.getController();
            createHoldWindowController.setSpeciesList(speciesList);
            createHoldWindowStage.setScene(scene);
            createHoldWindowStage.show();
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
     * @throws SQLException
     */
    public void populateLabels() throws SQLException {
        for (int i = 0; i < valuesLabels4.length; i++) {
            valuesLabels4[i].setText(GenesysProjectBuilder.CORE.getCharacteristics(DatabaseModifier.holdSpecies.getLifedomain().toString(), DatabaseModifier.holdSpecies.getCharacteristicGroup().toString())[i]);
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
        try {
            speciesChooseDropdown4.setItems(DatabaseModifier.populateDropdownsSpecies());
        } catch (SQLException ex) {
            Logger.getLogger(CreateCultureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        speciesChooseDropdown4.getSelectionModel().select(0);
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

    void setSpeciesSelection(String Selection) throws SQLException, IOException {
        speciesChooseDropdown4.getSelectionModel().select(Selection);
        speciesChooseDropdown4ItemStateChangedActions();
    }

}
