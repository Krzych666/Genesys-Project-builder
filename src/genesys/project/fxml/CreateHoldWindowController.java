/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue;
import genesys.project.builder.DatabaseModifier;
import genesys.project.builder.GenesysProjectBuilder;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class CreateHoldWindowController implements Initializable {

    @FXML
    private ListView skillsList3;
    @FXML
    private ListView classList1;

    @FXML
    private Label rTValue3;
    @FXML
    private Label mTText3;
    @FXML
    private Label commandText3;
    @FXML
    private Label rTText3;
    @FXML
    private Label commandValue3;
    @FXML
    private Label moraleText3;
    @FXML
    private Label mTValue3;
    @FXML
    private Label moraleValue3;
    @FXML
    private Label sizeText3;
    @FXML
    private Label attacksText3;
    @FXML
    private Label woundsText3;
    @FXML
    private Label pointsPerModelValue3;
    @FXML
    private Label woundsValue3;
    @FXML
    private Label sizeValue3;
    @FXML
    private Label attacksValue3;
    @FXML
    private Label characteristicsText3;
    @FXML
    private Label targetNumbersText3;
    @FXML
    private Label pointsPerModelText3;
    @FXML
    private Label strengthText3;
    @FXML
    private Label strengthValue3;
    @FXML
    private Label toughnessText3;
    @FXML
    private Label toughnessValue3;
    @FXML
    private Label movementText3;
    @FXML
    private Label movementValue3;
    @FXML
    private Label martialText3;
    @FXML
    private Label martialValue3;
    @FXML
    private Label rangedText3;
    @FXML
    private Label rangedValue3;
    @FXML
    private Label defenseText3;
    @FXML
    private Label defenseValue3;
    @FXML
    private Label disciplineValue3;
    @FXML
    private Label willpowerValue3;
    @FXML
    private Label disciplineText3;
    @FXML
    private Label willpowerText3;
    @FXML
    private Label lifeDomainText3;
    @FXML
    private Label lifeDomainValue3;
    @FXML
    private Label skillsText3;
    @FXML
    private Label clasesLabel3;
    @FXML
    private Button addClassButton;
    @FXML
    private Button createSpeciesFinalButton;
    @FXML
    private Label speciesNameValue3;
    @FXML
    private Label speciesNameText3;
    @FXML
    private Label classTypeText3;
    @FXML
    private Label classTypeValue3;
    @FXML
    private Label basedOnText3;
    @FXML
    private Label basedOnValue3;
    @FXML
    private Label classesLeft3a;
    @FXML
    private Label classesLeft3b;

    /**
     * classCreatorWindowStage
     */
    public Stage classCreatorWindowStage = new Stage();
    private ClassCreatorWindowController classCreatorWindowController;
    private ListView speciesList;
    private Label[] valuesLabels3;

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void classList1MousePressedActions() throws SQLException {
        if (!classList1.getSelectionModel().isEmpty()) {
            DatabaseModifier.classList1Holder = classList1.getSelectionModel().getSelectedItem().toString();
            int cost;
            if ("<base species>".equals(classList1.getSelectionModel().getSelectedItem().toString())) {
                skillsList3.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.holdSpecies.getSkills()));
                BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
                classTypeValue3.setText("");
                basedOnValue3.setText("");
                cost = DatabaseModifier.baseAddedCost(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdSpecies, null, 0, 0);
            } else {
                for (int i = 0; i < DatabaseModifier.numberOfClases; i++) {
                    if (DatabaseModifier.holdClass[i].getClassName().equals(classList1.getSelectionModel().getSelectedItem().toString())) {
                        DatabaseModifier.b = i;
                    }
                }
                skillsList3.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.getBaseAddedSkills(classList1.getSelectionModel().getSelectedItem().toString())));
                BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
                classTypeValue3.setText(DatabaseModifier.holdClass[DatabaseModifier.b].getType());
                basedOnValue3.setText(DatabaseModifier.holdClass[DatabaseModifier.b].getBasedOn());
                cost = DatabaseModifier.baseAddedCost(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdSpecies, DatabaseModifier.holdClass, DatabaseModifier.b, 0);
            }
            String tex = Integer.toString(cost);
            pointsPerModelValue3.setText(tex);
            populateLabels();
        }
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    public void addClassButtonActions() throws IOException {
        if (classCreatorWindowStage.isShowing()) {
            classCreatorWindowStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/ClassCreatorWindowFXML.fxml"));
            Parent root = loader.load();
            classCreatorWindowController = loader.getController();
            classCreatorWindowController.setClassList1(classList1);
            classCreatorWindowController.setClassesLeft3b(classesLeft3b);
            Scene scene = new Scene(root);
            classCreatorWindowStage.setScene(scene);
            classCreatorWindowStage.show();
        }
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void createSpeciesFinalButtonActions() throws SQLException {
        if (DatabaseModifier.isModyfyinfg) {
            DatabaseModifier.isModyfyinfg = !DatabaseModifier.isModyfyinfg;
            for (int i = 0; i < DatabaseModifier.numberOfClases; i++) {
                if (DatabaseModifier.holdClass[i].getSkills().length() > 2) {
                    DatabaseModifier.holdClass[i].setSkills(DatabaseModifier.holdClass[i].getSkills().substring(0, DatabaseModifier.holdClass[i].getSkills().length() - 1));
                }
                DatabaseModifier.modifyClass(i);
            }
            //DatabaseModifier.modifyHero();
            //DatabaseModifier.modifyProgress();
            //DatabaseModifier.modifyRoster();
            DatabaseModifier.holdSpecies = null;
            DatabaseModifier.modifiedHoldSpecies = null;
        } else {
            DatabaseModifier.writeToDB();
        }

        speciesList.setItems(BuilderCORE.getSpeciesList());
        speciesList.getSelectionModel().clearSelection();
        Stage stage = (Stage) createSpeciesFinalButton.getScene().getWindow();
        stage.hide();
    }

    /**
     *
     * @throws SQLException
     */
    public void createHoldUpdate() throws SQLException {
        lifeDomainValue3.setText(DatabaseModifier.holdSpecies.getLifedomain().toString());
        speciesNameValue3.setText(DatabaseModifier.holdSpecies.getSpeciesName());
        ObservableList tmp = FXCollections.observableArrayList();
        tmp.add("<base species>");
        if (LifedomainValue.Fey.equals(BuilderFXMLController.currentLifeDomain)) {
            tmp.add("<lesser species>");
            tmp.add("<greater species>");
        }
        for (int i = 0; i < DatabaseModifier.numberOfClases; i++) {
            tmp.add(DatabaseModifier.holdClass[i].getClassName());
        }
        classList1.setItems(tmp);
        clearLists3();
        DatabaseModifier.setNumberOfClases();
        classesLeft3a.setText(DatabaseModifier.classCanTake());
        classesLeft3b.setText(DatabaseModifier.classLeftModify());
    }

    /**
     *
     * @throws SQLException
     */
    public void populateLabels() throws SQLException {
        for (int i = 0; i < valuesLabels3.length; i++) {
            valuesLabels3[i].setText(GenesysProjectBuilder.CORE.getCharacteristics(DatabaseModifier.holdSpecies.getLifedomain().toString(),DatabaseModifier.holdSpecies.getCharacteristicGroup().toString())[i]);
        }
    }

    /**
     * clearLists3
     */
    public void clearLists3() {
        strengthValue3.setText("");
        toughnessValue3.setText("");
        movementValue3.setText("");
        martialValue3.setText("");
        rangedValue3.setText("");
        defenseValue3.setText("");
        disciplineValue3.setText("");
        willpowerValue3.setText("");
        commandValue3.setText("");
        mTValue3.setText("");
        rTValue3.setText("");
        moraleValue3.setText("");
        pointsPerModelValue3.setText("");
        woundsValue3.setText("");
        sizeValue3.setText("");
        attacksValue3.setText("");
        skillsList3.getItems().clear();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.valuesLabels3 = new Label[]{strengthValue3, toughnessValue3, movementValue3, martialValue3, rangedValue3, defenseValue3, disciplineValue3, willpowerValue3, commandValue3, woundsValue3, attacksValue3, sizeValue3, mTValue3, rTValue3, moraleValue3};
        try {
            createHoldUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CreateHoldWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

}
