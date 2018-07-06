/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    private ListView Roster;

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
    @FXML
    private Button createRosterFinalButton;
    @FXML
    private Button cancelCreateRosterButton;
    @FXML
    private Button addUnitButton;
    @FXML
    private Button removeSelectedButton;
    @FXML
    private Label maxPointsText;
    @FXML
    private Label maxPointsValue;
    @FXML
    private Label currentPointsText;
    @FXML
    private Label currentPointsValue;

    private Label[] valuesLabels;
    /**
     *
     */
    public Stage rosterAddUnitStage = new Stage();
    private RosterAddUnitController rosterAddUnitController;

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void classListMousePressedActions() throws SQLException {
        if (!classList.getSelectionModel().isEmpty()) {
            DatabaseHolder.classList1Holder = classList.getSelectionModel().getSelectedItem().toString();
            for (int i = 0; i < DatabaseHolder.holdClass.length; i++) {
                if (DatabaseHolder.holdClass[i].getClassName().equals(classList.getSelectionModel().getSelectedItem().toString())) {
                    DatabaseHolder.b = i;
                }
            }
            DatabaseHolder.fullSkillList1 = "";
            skillsList.setItems(DatabaseReader.getAddedSkills(BuilderCORE.getBaseAddedSkills(classList.getSelectionModel().getSelectedItem().toString())));
            BuilderFXMLController.getSkillModifiers(DatabaseHolder.ruledskills);
            classTypeValue.setText(DatabaseHolder.holdClass[DatabaseHolder.b].getType());
            basedOnValue.setText(DatabaseHolder.holdClass[DatabaseHolder.b].getBasedOn());
            int cost = BuilderCORE.baseAddedCost(DatabaseHolder.holdSpecies.getLifedomain(), DatabaseHolder.holdSpecies, DatabaseHolder.holdClass, DatabaseHolder.b, 0);
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
            valuesLabels[i].setText(DatabaseReader.getCharacteristics(DatabaseHolder.holdSpecies.getLifedomain().toString(), DatabaseHolder.holdSpecies.getCharacteristicGroup().toString())[i]);
        }
    }

    /**
     *
     * @throws IOException
     * @throws java.sql.SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    @FXML
    public void addUnitActions() throws IOException, SQLException, CloneNotSupportedException {
        if (!classList.getSelectionModel().isEmpty()) {
            if (rosterAddUnitStage.isShowing()) {
                rosterAddUnitStage.requestFocus();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/RosterAddUnitFXML.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                rosterAddUnitController = loader.getController();
                rosterAddUnitController.setNameBasePointsRoster(classList.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(pointsPerModelValue.getText()), Roster, currentPointsValue, maxPointsValue);
                rosterAddUnitStage.setScene(scene);
                rosterAddUnitStage.setTitle("Add Unit to " + DatabaseHolder.holdRoster.getRosterName() + " for " + DatabaseHolder.holdRoster.getSpeciesName() + " - " + DatabaseHolder.holdRoster.getCultureName());
                rosterAddUnitStage.show();
            }
        }
    }

    /**
     * removeSelectedButtonActions
     */
    @FXML
    public void removeSelectedButtonActions() {
        if (!Roster.getSelectionModel().isEmpty()) {
            currentPointsValue.setText(Integer.toString(Integer.parseInt(currentPointsValue.getText()) - Integer.parseInt(Roster.getSelectionModel().getSelectedItem().toString().split("total points:")[1])));
            checkCurrentToMaxPoints();
            Roster.getItems().remove(Roster.getSelectionModel().getSelectedIndex());
        }
    }

    /**
     * checkCurrentToMaxPoints
     */
    public void checkCurrentToMaxPoints() {
        if (Integer.parseInt(maxPointsValue.getText()) != 0) {
            currentPointsValue.setTextFill(Color.web(Integer.parseInt(currentPointsValue.getText()) > Integer.parseInt(maxPointsValue.getText()) ? "#eb1112" : "#000"));
        }
    }

    /**
     * createRosterFinalButtonActions
     */
    @FXML
    public void createRosterFinalButtonActions() {
        StringBuilder rosterPrint = new StringBuilder();
        for (int i = 0; i < Roster.getItems().size(); i++) {
            rosterPrint.append(Roster.getItems().get(i).toString());
            if (i < Roster.getItems().size() - 1) {
                rosterPrint.append(";");
            }
        }
        DatabaseHolder.holdRoster.setRoster(rosterPrint.toString());
        DatabaseWriter.writeRosterToDB();
        Stage stage = (Stage) createRosterFinalButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * cancelCreateRosterButton
     */
    @FXML
    public void cancelCreateRosterButtonActions() {
        Stage stage = (Stage) cancelCreateRosterButton.getScene().getWindow();
        stage.hide();
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
        lifeDomainValue.setText(DatabaseHolder.holdSpecies.getLifedomain().toString());
        speciesNameValue.setText(DatabaseHolder.holdSpecies.getSpeciesName());
        ObservableList tmp = FXCollections.observableArrayList();
        for (DatabaseHolder.AClass holdClas : DatabaseHolder.holdClass) {
            tmp.add(holdClas.getClassName());
        }
        classList.setItems(tmp);
        Roster.setCellFactory(lst
                -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setPrefHeight(45.0);
                    setText(null);
                } else {
                    setPrefHeight(Region.USE_COMPUTED_SIZE);
                    setText(item);
                }
            }
        });
    }

    void setMaxPoints(String maxPoints) {
        maxPointsValue.setText(maxPoints);
    }
}
