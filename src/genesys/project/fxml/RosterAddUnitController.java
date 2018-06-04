/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.DatabaseModifier;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private ListView Roster;
    private Label currentPointsValue;
    private static final HashMap<String, String> IMPROVEMENTS = new HashMap<String, String>();

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
    }
    
    private int itemsCost() throws SQLException {
        int cost = 0;
        for (int i = 0; i < currentEquipmentList.getItems().size(); i++) {
            cost += DatabaseModifier.getItemCost(currentEquipmentList.getItems().get(i).toString().split(" X")[0]) * Integer.parseInt(currentEquipmentList.getItems().get(i).toString().split(" X")[1]);
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
        availableEquipmentList.setItems(DatabaseModifier.getItemsNames(equipmentTypeChooser.getSelectionModel().getSelectedItem().toString()));
    }
    
    @FXML
    public void availableEquipmentListMousePressed() throws SQLException {
        generateImprovementsList();
    }
    
    private void generateImprovementsList() throws SQLException {
        ObservableList<String> improvementsObservableList = DatabaseModifier.getImprovements(availableEquipmentList.getSelectionModel().getSelectedItem().toString());
        improvementsList.setItems(improvementsObservableList);
        improvementsList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ImprovementsCell();
            }
        });
    }
    
    static class ImprovementsCell extends ListCell<String> {
        
        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        Button buttonAdd = new Button("+");
        private TextField numberbox;
        Button buttonRemove = new Button("-");
        String lastItem;
        
        public ImprovementsCell() {
            super();
            numberbox = new TextField("0");
            numberbox.addEventFilter(KeyEvent.KEY_TYPED, BuilderCORE.numeric_Validation(10));
            numberbox.setMaxWidth(50);
            hbox.getChildren().addAll(label, pane, buttonAdd, numberbox, buttonRemove);
            numberbox.setEditable(false);
            HBox.setHgrow(pane, Priority.ALWAYS);
            buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent event) {
                    if (numberbox.getText().isEmpty() || Integer.parseInt(numberbox.getText()) < 0) {
                        numberbox.setText("0");
                    }
                    numberbox.setText(Integer.toString(Integer.parseInt(numberbox.getText()) + 1));
                    IMPROVEMENTS.put(label.getText(), numberbox.getText());
                }
            });
            buttonRemove.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (numberbox.getText().isEmpty() || Integer.parseInt(numberbox.getText()) < 0) {
                        numberbox.setText("0");
                    }
                    if (Integer.parseInt(numberbox.getText()) > 0) {
                        numberbox.setText(Integer.toString(Integer.parseInt(numberbox.getText()) - 1));
                    }
                    if (numberbox.getText().equals("0")) {
                        IMPROVEMENTS.remove(label.getText());
                    } else {
                        IMPROVEMENTS.put(label.getText(), numberbox.getText());
                    }
                    
                }
            });
        }
        
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                label.setText(item != null ? item : "<null>");
                setGraphic(hbox);
            }
        }

        /**
         * @return the numberbox
         */
        public TextField getNumberbox() {
            return numberbox;
        }

        /**
         * @param numberbox the numberbox to set
         */
        public void setNumberbox(TextField numberbox) {
            this.numberbox = numberbox;
        }
    }
    
    private String getImprovements() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        StringBuilder tempList = new StringBuilder();
        if (IMPROVEMENTS.size() > 0) {
            tempList.append(" ").append(IMPROVEMENTS.toString());
        }
        return tempList.toString();
    }

    /**
     * addaquadButtonActions
     */
    @FXML
    public void addSquadButtonActions() {
        Roster.getItems().add(className
                + " x" + squadSizeValue.getText()
                + "   \n" + currentEquipmentList.getItems().toString()
                + "   \n total points:" + totalPointsValue.getText());
        currentPointsValue.setText(Integer.toString(Integer.parseInt(currentPointsValue.getText()) + Integer.parseInt(totalPointsValue.getText())));
        Stage stage = (Stage) addSquadButton.getScene().getWindow();
        stage.hide();
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
     * @param Roster
     * @param currentPointsValue
     */
    public void setNameBasePointsRoster(String className, int basePoints, ListView Roster, Label currentPointsValue) {
        this.className = className;
        this.basePoints = basePoints;
        this.Roster = Roster;
        this.currentPointsValue = currentPointsValue;
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
    
}
