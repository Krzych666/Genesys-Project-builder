/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.DatabaseModifier;
import genesys.project.builder.GenesysProjectBuilder;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author krzysztofg
 */
public class RosterCreatorWindowController implements Initializable {

    @FXML
    private ListView skillsList;
    @FXML
    private ListView classList;

    @FXML
    private Label rTValue;
    @FXML
    private Label mTText;
    @FXML
    private Label commandText;
    @FXML
    private Label rTText;
    @FXML
    private Label commandValue;
    @FXML
    private Label moraleText;
    @FXML
    private Label mTValue;
    @FXML
    private Label moraleValue;
    @FXML
    private Label sizeText;
    @FXML
    private Label attacksText;
    @FXML
    private Label woundsText;
    @FXML
    private Label pointsPerModelValue;
    @FXML
    private Label woundsValue;
    @FXML
    private Label sizeValue;
    @FXML
    private Label attacksValue;
    @FXML
    private Label characteristicsText;
    @FXML
    private Label targetNumbersText;
    @FXML
    private Label pointsPerModelText;
    @FXML
    private Label strengthText;
    @FXML
    private Label strengthValue;
    @FXML
    private Label toughnessText;
    @FXML
    private Label toughnessValue;
    @FXML
    private Label movementText;
    @FXML
    private Label movementValue;
    @FXML
    private Label martialText;
    @FXML
    private Label martialValue;
    @FXML
    private Label rangedText;
    @FXML
    private Label rangedValue;
    @FXML
    private Label defenseText;
    @FXML
    private Label defenseValue;
    @FXML
    private Label disciplineValue;
    @FXML
    private Label willpowerValue;
    @FXML
    private Label disciplineText;
    @FXML
    private Label willpowerText;
    @FXML
    private Label lifeDomainText;
    @FXML
    private Label lifeDomainValue;
    @FXML
    private Label skillsText;
    @FXML
    private Label clasesLabel;
    @FXML
    private Button createRosterFinalButton;
    @FXML
    private Label speciesNameValue;
    @FXML
    private Label speciesNameText;
    @FXML
    private Label classTypeText;
    @FXML
    private Label classTypeValue;
    @FXML
    private Label basedOnText;
    @FXML
    private Label basedOnValue;

    private Label[] valuesLabels;

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void classListMousePressedActions() throws SQLException {
        if (!classList.getSelectionModel().isEmpty()) {
            DatabaseModifier.classList1Holder = classList.getSelectionModel().getSelectedItem().toString();
            for (int i = 0; i < DatabaseModifier.holdClass.length; i++) {
                if (DatabaseModifier.holdClass[i].getClassName().equals(classList.getSelectionModel().getSelectedItem().toString())) {
                    DatabaseModifier.b = i;
                }
            }
            skillsList.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.getBaseAddedSkills(classList.getSelectionModel().getSelectedItem().toString())));
            BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
            classTypeValue.setText(DatabaseModifier.holdClass[DatabaseModifier.b].getType());
            basedOnValue.setText(DatabaseModifier.holdClass[DatabaseModifier.b].getBasedOn());
            int cost = DatabaseModifier.baseAddedCost(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdSpecies, DatabaseModifier.holdClass, DatabaseModifier.b, 0);
            String tex = Integer.toString(cost);
            pointsPerModelValue.setText(tex);
            populateLabels();
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void populateLabels() throws SQLException {
        for (int i = 0; i < valuesLabels.length; i++) {
            valuesLabels[i].setText(GenesysProjectBuilder.CORE.getCharacteristics(DatabaseModifier.holdSpecies.getLifedomain().toString(), DatabaseModifier.holdSpecies.getCharacteristicGroup().toString())[i]);
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
        this.valuesLabels = new Label[]{strengthValue, toughnessValue, movementValue, martialValue, rangedValue, defenseValue, disciplineValue, willpowerValue, commandValue, woundsValue, attacksValue, sizeValue, mTValue, rTValue, moraleValue};
        lifeDomainValue.setText(DatabaseModifier.holdSpecies.getLifedomain().toString());
        speciesNameValue.setText(DatabaseModifier.holdSpecies.getSpeciesName());
        ObservableList tmp = FXCollections.observableArrayList();
        for (int i = 0; i < DatabaseModifier.holdClass.length; i++) {
            tmp.add(DatabaseModifier.holdClass[i].getClassName());
        }
        classList.setItems(tmp);
    }

}
