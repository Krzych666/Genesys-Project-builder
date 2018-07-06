/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.DatabaseReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author krzysztofg
 */
public class RosterAddUnitController implements Initializable {

    @FXML
    private ListView currentEquipmentList;
    @FXML
    private ListView availableEquipmentList;
    @FXML
    private ListView improvementsList;
    @FXML
    private ListView improvementsDetailsList;
    @FXML
    private Label squadSizeText;
    @FXML
    private TextField squadSizeValue;
    @FXML
    private Label equipmentTypeText;
    @FXML
    private ComboBox equipmentTypeChooser;
    @FXML
    private Button addEquipmentButton;
    @FXML
    private TextField addEquipmentValue;
    @FXML
    private Label totalPointsText;
    @FXML
    private TextField totalPointsValue;
    @FXML
    private Button addSquadButton;
    @FXML
    private Button cancelAddSaquadButton;

    private String className;
    private int basePoints;
    private ListView roster;
    private Label currentPointsValue;
    private Label maxPointsValue;
    private static final HashMap<String, ImprovementsItem> IMPROVEMENTS = new HashMap<String, ImprovementsItem>();

    /**
     * squadSizeChangeActions
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void squadSizeChangeActions() throws SQLException {
        if (!squadSizeValue.getText().isEmpty()) {
            totalPointsValue.setText(Integer.toString(basePoints * Integer.parseInt(squadSizeValue.getText()) + itemsCost()));
        }
    }

    /**
     * addEquipmentButtonActions
     *
     * @throws java.sql.SQLException
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @FXML
    public void addEquipmentButtonActions() throws SQLException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        if (addEquipmentValue.getText().isEmpty() || Integer.parseInt(addEquipmentValue.getText()) == 0) {
            addEquipmentValue.setText("1");
        }
        moveItem();
        availableEquipmentList.getSelectionModel().clearSelection();
        currentEquipmentList.getSelectionModel().clearSelection();
    }

    private void moveItem() throws SQLException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        if (!availableEquipmentList.getSelectionModel().isEmpty()) {
            currentEquipmentList.getItems().add(availableEquipmentList.getSelectionModel().getSelectedItem() + getImprovements() + " X" + addEquipmentValue.getText());
        }
        if (!currentEquipmentList.getSelectionModel().isEmpty()) {
            currentEquipmentList.getItems().remove(currentEquipmentList.getSelectionModel().getSelectedIndex());
        }
        squadSizeChangeActions();
        IMPROVEMENTS.clear();
        improvementsList.getItems().clear();
        improvementsDetailsList.getItems().clear();
    }

    private int itemsCost() throws SQLException {
        int cost = 0;
        for (int i = 0; i < currentEquipmentList.getItems().size(); i++) {
            if (currentEquipmentList.getItems().get(i).toString().contains("} X")) {
                cost += DatabaseReader.getItemCost(currentEquipmentList.getItems().get(i).toString().split("} X")[0], equipmentTypeChooser.getSelectionModel().getSelectedItem().toString()) * Integer.parseInt(currentEquipmentList.getItems().get(i).toString().split("} X")[1]);
            } else {
                cost += DatabaseReader.getItemCost(currentEquipmentList.getItems().get(i).toString().split(" X")[0], equipmentTypeChooser.getSelectionModel().getSelectedItem().toString()) * Integer.parseInt(currentEquipmentList.getItems().get(i).toString().split(" X")[1]);
            }
        }
        return cost;
    }

    /**
     * equipmentTypeChooserStateChangedActions
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void equipmentTypeChooserStateChangedActions() throws SQLException {
        IMPROVEMENTS.clear();        
        availableEquipmentList.setItems(DatabaseReader.getItemsNames(equipmentTypeChooser.getSelectionModel().getSelectedItem().toString()));
        improvementsList.getItems().clear();
        improvementsDetailsList.getItems().clear();
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void availableEquipmentListMousePressed() throws SQLException {
        IMPROVEMENTS.clear();
        generateImprovementsList();
        improvementsDetailsList.getItems().clear();
    }

    private void generateImprovementsList() throws SQLException {
        ObservableList<String> improvementsObservableList = DatabaseReader.getImprovements(availableEquipmentList.getSelectionModel().getSelectedItem().toString(), equipmentTypeChooser.getSelectionModel().getSelectedItem().toString());
        improvementsList.setItems(improvementsObservableList);
        improvementsList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ImprovementsCell();
            }
        });
    }

    static class ImprovementsCell extends ListCell<String> {

        ImprovementsItem improvementsItem;

        public ImprovementsCell() {
            super();
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            if (!empty && improvementsItem != null && item != null) {
                improvementsItem = IMPROVEMENTS.get(item);
            }
            if (improvementsItem == null) {
                improvementsItem = new ImprovementsItem();
            }

            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                improvementsItem.setLastItem(null);
                setGraphic(null);
            } else {
                improvementsItem.setLastItem(item);
                improvementsItem.getLabel().setText(item != null ? item : "<null>");
                IMPROVEMENTS.put(improvementsItem.getLabel().getText(), improvementsItem);
                IMPROVEMENTS.get(improvementsItem.getLabel().getText()).setPosition(getIndex());
                improvementsItem.getCheckBox().setSelected(IMPROVEMENTS.get(improvementsItem.getLabel().getText()).getCheckBoxSelectionState());
                improvementsItem.getNumberbox().setText(IMPROVEMENTS.get(improvementsItem.getLabel().getText()).getLabelValue());
                if (super.getItem().contains("X")) {
                    improvementsItem.getCheckBox().setVisible(false);
                    improvementsItem.getButtonAdd().setVisible(true);
                    improvementsItem.getNumberbox().setVisible(true);
                    improvementsItem.getButtonRemove().setVisible(true);
                } else {
                    improvementsItem.getCheckBox().setVisible(true);
                    improvementsItem.getButtonAdd().setVisible(false);
                    improvementsItem.getNumberbox().setVisible(false);
                    improvementsItem.getButtonRemove().setVisible(false);
                }
                setGraphic(improvementsItem.getHbox());
            }
        }
    }

    private String getImprovements() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        StringBuilder tempList = new StringBuilder();
        tempList.append(" {");
        IMPROVEMENTS.entrySet().forEach((entry) -> {
            if (entry.getValue().getCheckBoxSelectionState()) {
                tempList.append(entry.getKey()).append(", ");
            }
            if (Integer.parseInt(entry.getValue().getLabelValue()) > 0) {
                tempList.append(entry.getKey()).append(entry.getValue().getLabelValue()).append(", ");
            }
        });
        if (tempList.substring(tempList.length() - 2).equals(", ")) {
            tempList.delete(tempList.length() - 2, tempList.length());
            tempList.append("}");
        } else {
            return "";
        }
        return tempList.toString();
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void improvementsListMousePressed() throws SQLException {
        String availableEquipmentName = availableEquipmentList.getSelectionModel().getSelectedItem().toString();
        String improvementsName = improvementsList.getSelectionModel().getSelectedItem().toString();
        String equipmentTypeNamme = equipmentTypeChooser.getSelectionModel().getSelectedItem().toString();
        improvementsDetailsList.setItems(DatabaseReader.getImprovementDetails(availableEquipmentName, improvementsName, equipmentTypeNamme));
    }

    /**
     * addaquadButtonActions
     *
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @FXML
    public void addSquadButtonActions() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        roster.getItems().add(className
                + " x" + squadSizeValue.getText()
                + "   \n" + currentEquipmentList.getItems().toString()
                + "   \n total points:" + totalPointsValue.getText());
        currentPointsValue.setText(Integer.toString(Integer.parseInt(currentPointsValue.getText()) + Integer.parseInt(totalPointsValue.getText())));
        checkCurrentToMaxPoints();
        Stage stage = (Stage) addSquadButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * checkCurrentToMaxPoints
     */
    public void checkCurrentToMaxPoints() { // Duplicate is in RosterCreatorWindowController, fina a good way to get rid of it here.
        if (Integer.parseInt(maxPointsValue.getText()) != 0) {
            currentPointsValue.setTextFill(Color.web(Integer.parseInt(currentPointsValue.getText()) > Integer.parseInt(maxPointsValue.getText()) ? "#eb1112" : "#000"));
        }
    }

    /**
     * cancelAddSaquadButtonActions
     */
    @FXML
    public void cancelAddSaquadButtonActions() {
        Stage stage = (Stage) cancelAddSaquadButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * @param className
     * @param basePoints the basePoints to set
     * @param roster
     * @param currentPointsValue
     * @param maxPointsValue
     */
    public void setNameBasePointsRoster(String className, int basePoints, ListView roster, Label currentPointsValue, Label maxPointsValue) {
        this.className = className;
        this.basePoints = basePoints;
        this.roster = roster;
        this.currentPointsValue = currentPointsValue;
        this.maxPointsValue = maxPointsValue;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        squadSizeValue.addEventFilter(KeyEvent.KEY_TYPED, BuilderCORE.numeric_Validation(6));
        addEquipmentValue.addEventFilter(KeyEvent.KEY_TYPED, BuilderCORE.numeric_Validation(6));
        equipmentTypeChooser.setItems(FXCollections.observableArrayList(BuilderCORE.EQUIPMENT_TYPES));
        equipmentTypeChooser.getSelectionModel().select(0);
        squadSizeValue.setText("1");
        addEquipmentValue.setText("1");
        try {
            equipmentTypeChooserStateChangedActions();
            squadSizeChangeActions();
        } catch (SQLException ex) {
            Logger.getLogger(RosterAddUnitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class ImprovementsItem {

        @Getter
        @Setter
        int position;

        @Getter
        @Setter
        String name = "";

        @Getter
        @Setter
        Boolean checkBoxSelectionState = false;

        @Getter
        @Setter
        String labelValue = "0";

        @Getter
        @Setter
        HBox hbox = new HBox();

        @Getter
        @Setter
        Label label = new Label("(empty)");

        @Getter
        @Setter
        Pane pane = new Pane();

        @Getter
        @Setter
        Button buttonAdd = new Button("+");

        @Getter
        @Setter
        private TextField numberbox = new TextField("0");

        @Getter
        @Setter
        Button buttonRemove = new Button("-");

        @Getter
        @Setter
        String lastItem;

        @Getter
        @Setter
        CheckBox checkBox = new CheckBox();

        public ImprovementsItem() {
            getNumberbox().addEventFilter(KeyEvent.KEY_TYPED, BuilderCORE.numeric_Validation(10));
            getNumberbox().setMaxWidth(50);
            getNumberbox().setEditable(false);
            getHbox().getChildren().addAll(getLabel(), getPane(), getButtonAdd(), getNumberbox(), getButtonRemove(), getCheckBox());
            HBox.setHgrow(getPane(), Priority.ALWAYS);
            getButtonAdd().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (getNumberbox().getText().isEmpty() || Integer.parseInt(getNumberbox().getText()) < 0) {
                        getNumberbox().setText("0");
                    }
                    getNumberbox().setText(Integer.toString(Integer.parseInt(getNumberbox().getText()) + 1));
                    IMPROVEMENTS.get(getLabel().getText()).setName(getLabel().getText());
                    IMPROVEMENTS.get(getLabel().getText()).setLabelValue(getNumberbox().getText());
                }
            });
            getButtonRemove().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (getNumberbox().getText().isEmpty() || Integer.parseInt(getNumberbox().getText()) < 0) {
                        getNumberbox().setText("0");
                    }
                    if (Integer.parseInt(getNumberbox().getText()) > 0) {
                        getNumberbox().setText(Integer.toString(Integer.parseInt(getNumberbox().getText()) - 1));
                    }
                    if (getNumberbox().getText().equals("0")) {
                        IMPROVEMENTS.remove(getLabel().getText());
                    } else {
                        IMPROVEMENTS.get(getLabel().getText()).setLabelValue(getNumberbox().getText());
                    }

                }
            });
            getCheckBox().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (getCheckBox().isSelected()) {
                        IMPROVEMENTS.get(getLabel().getText()).setCheckBoxSelectionState(true);
                    } else {
                        IMPROVEMENTS.remove(getLabel().getText());
                    }
                }
            }
            );
        }
    }
}
