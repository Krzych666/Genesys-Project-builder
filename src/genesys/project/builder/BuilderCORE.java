/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
import static genesys.project.builder.Enums.Enmuerations.LifedomainValue.Biest;
import static genesys.project.builder.Enums.Enmuerations.LifedomainValue.Fey;
import static genesys.project.builder.Enums.Enmuerations.LifedomainValue.Humanoid;
import static genesys.project.builder.Enums.Enmuerations.LifedomainValue.Insecta;
import static genesys.project.builder.Enums.Enmuerations.LifedomainValue.Reptilia;
import genesys.project.builder.Enums.Enmuerations.UseCases;
import genesys.project.fxml.BuilderFXMLController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author krzysztofg
 */
public class BuilderCORE {

    /**
     * conn
     */
    private static Connection conn;

    /**
     * CLASS_TABLES
     */
    public static final String[] CLASS_TABLES = {"Skills", "Advancements", "Type", "BasedOn", "AdditionalCost"};

    /**
     * HERO_TABLES
     */
    public static final String[] HERO_TABLES = {"Advancements", "BasedOn", "AdditionalCost"};

    private static final String[] CHARACTERISTICS = {"Strength", "Toughness", "Movement", "Martial", "Ranged", "Defense", "Discipline", "Willpower", "Command", "Wounds", "Attacks", "Size", "MT", "RT", "Morale"};

    private static final String[] SIZES = {"T", "S", "M", "L", "XL", "XXL"};

    private static final LifedomainValue[] DOMAINS = {Humanoid, Fey, Reptilia, Biest, Insecta};
    /**
     * LIGHT = "Path of Light"
     */
    public static final String LIGHT = "Path of Light";

    /**
     * DARKNESS = "Path of Darkness"
     */
    public static final String DARKNESS = "Path of Darkness";

    /**
     * TWILIGHT = "Path of Twilight"
     */
    public static final String TWILIGHT = "Shadows of Twilight";

    /**
     * BASE = base species
     */
    public static final String BASE = "<base species>";

    /**
     * LESSER = lesser species
     */
    public static final String LESSER = "<lesser species>";

    /**
     * GREATER = greater species
     */
    public static final String GREATER = "<greater species>";

    /**
     * HUMANOIDSKILS = "Traits"
     */
    public static String HUMANOIDSKILS = "Traits";

    /**
     * HUMANOIDSUBSKILS = "Evolutionary Branch"
     */
    public static final String HUMANOIDSUBSKILS = "Evolutionary Branch";

    /**
     * FEYSKILS = "Paths"
     */
    public static final String FEYSKILS = "Paths";

    /**
     * FEYSUBSKILS = "Spheres of Influence"
     */
    public static final String FEYSUBSKILS = "Spheres of Influence";

    /**
     * "REPTILIASKILS = "Lineage"
     */
    public static final String REPTILIASKILS = "Traits";

    /**
     * REPTILIASUBSKILS = "Ancestry"
     */
    public static final String REPTILIASUBSKILS = "Reptilia Lineages";

    /**
     * @param args the command line arguments
     */
    /**
     * BIESTSKILS = "Traits"
     */
    public static final String BIESTSKILS = "Traits";

    /**
     * BIESTSUBSKILS = "Evolutionary Branch"
     */
    public static final String BIESTSUBSKILS = "Evolutionary Branch";

    /**
     * INSECTASKILS = "Classification"
     */
    public static final String INSECTASKILS = "Classification";

    /**
     * INSECTASUBSKILS = "Order"
     */
    public static final String INSECTASUBSKILS = "Order";

    public static final String[] GAME_TYPES = {"Standard Play", "Hero Play"};
    
    public static final String[] EQUIPMENT_TYPES = {"Weapon", "Armor", "Other"};

    public static void main(String[] args) {

    }

    /**
     *
     * @return
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     *
     * @param uc
     * @throws SQLException
     */
    public static void chooseConnection(UseCases uc) throws SQLException {
        if (UseCases.COREdb.equals(uc)) {
            conn = DBCOREConnect();
        }
        if (UseCases.Userdb.equals(uc)) {
            conn = DBUserDataConnect();
        }
        conn.setAutoCommit(false);
    }

    /**
     *
     * @return
     */
    public static Connection DBCOREConnect() {
        Connection cCORE = null;
        try {
            Class.forName("org.sqlite.JDBC");
            cCORE = DriverManager.getConnection("jdbc:sqlite:src\\genesys\\project\\database\\GenPr_CORE.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return cCORE;
    }

    /**
     *
     * @return
     */
    public static Connection DBUserDataConnect() {
        Connection cUserData = null;
        try {
            Class.forName("org.sqlite.JDBC");
            cUserData = DriverManager.getConnection("jdbc:sqlite:src\\genesys\\project\\database\\GenPr_UserData.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return cUserData;
    }

    /**
     *
     * @param stmt
     * @param columns
     * @param exclude
     * @return
     * @throws java.sql.SQLException
     */
    public static ObservableList getData(PreparedStatement stmt, String[] columns, ObservableList exclude) throws SQLException {
        ObservableList temp = FXCollections.observableArrayList();
        ResultSet rs = stmt.executeQuery();
        try //(            
        //PreparedStatement stmt  = conn.prepareStatement(sql);             
        //)
        {
            for (int j = 0; j < columns.length; j++) {
                // loop through the result set
                while (rs.next()) {
                    //System.out.println(rs.getString(column) +  "\t");
                    if (exclude == null) {
                        temp.add(rs.getString(columns[j]));
                    } else {
                        boolean isPresent = false;
                        for (int i = 0; i < exclude.size(); i++) {
                            if ((exclude.get(i).toString().split(" \\(p")[0]).equals(rs.getString(columns[j]))) {
                                isPresent = true;
                            }
                        }
                        if (!isPresent) {
                            temp.add(rs.getString(columns[j]));
                        }
                    }
                }
                if (j != columns.length - 1) {
                    temp.add("");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (SQLException e) {
                /* ignored */ }
            try {
                conn.close();
            } catch (SQLException e) {
                /* ignored */ }
        }
        return temp;
    }

    /**
     *
     * @param stmt
     * @param column
     * @return
     * @throws java.sql.SQLException
     */
    public static String getValue(PreparedStatement stmt, String column) throws SQLException {
        String Tmp = "";
        ResultSet rs = stmt.executeQuery();
        try// (
        //Statement stmt = conn.createStatement();
        //    )
        {
            Tmp = rs.getString(column);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                /* ignored */ }
            try {
                stmt.close();
            } catch (SQLException e) {
                /* ignored */ }
            try {
                conn.close();
            } catch (SQLException e) {
                /* ignored */ }
        }
        return Tmp;
    }

    /**
     *
     * @return @throws SQLException
     */
    public static ObservableList<String> getSpeciesList() throws SQLException {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        for (LifedomainValue domain : DOMAINS) {
            tmp.add("--" + domain.toString() + "--");
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = conn.prepareStatement("SELECT SpeciesName FROM CreatedSpecies WHERE LifeDomain = ?");
            stmt.setString(1, domain.toString());
            String[] columns = {"SpeciesName"};
            ObservableList<String> tmpget = getData(stmt, columns, null);
            for (int i = 0; i < tmpget.size(); i++) {
                tmp.add(tmpget.get(i));
            }
            tmp.add(" ");
        }
        return tmp;
    }

    /**
     *
     * @param lifedomain
     * @param characteristicGroup
     * @return
     * @throws java.sql.SQLException
     */
    public String[] getCharacteristics(String lifedomain, String characteristicGroup) throws SQLException {
        String[] outputValues = new String[16];
        int[] CharacteristicModifiers = {BuilderFXMLController.HOLD_MODIFIERS.getStrengthModifier(), BuilderFXMLController.HOLD_MODIFIERS.getToughnessModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMovementModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMartialModifier(), BuilderFXMLController.HOLD_MODIFIERS.getRangedModifier(), BuilderFXMLController.HOLD_MODIFIERS.getDefenseModifier(), BuilderFXMLController.HOLD_MODIFIERS.getDisciplineModifier(), BuilderFXMLController.HOLD_MODIFIERS.getWillpowerModifier(), BuilderFXMLController.HOLD_MODIFIERS.getCommandModifier(), BuilderFXMLController.HOLD_MODIFIERS.getWoundsModifier(), BuilderFXMLController.HOLD_MODIFIERS.getAttacksModifier(), BuilderFXMLController.HOLD_MODIFIERS.getSizeModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMTModifier(), BuilderFXMLController.HOLD_MODIFIERS.getRTModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMoraleModifier()};
        for (int j = 0; j < CHARACTERISTICS.length; j++) {
            if ("Size".equals(CHARACTERISTICS[j]) && j < 12) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = conn.prepareStatement("SELECT " + CHARACTERISTICS[j] + " FROM StartingCharacteristics WHERE LifeDomain = ? AND CharacteristicGroup = ?");
                stmt.setString(1, lifedomain);
                stmt.setString(2, characteristicGroup);
                outputValues[j] = SIZES[(Integer.parseInt(getValue(stmt, CHARACTERISTICS[j])) + CharacteristicModifiers[j])];
            } else if (j < 12) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt1 = conn.prepareStatement("SELECT " + CHARACTERISTICS[j] + " FROM StartingCharacteristics WHERE LifeDomain = ? AND CharacteristicGroup = ?");
                stmt1.setString(1, lifedomain);
                stmt1.setString(2, characteristicGroup);
                outputValues[j] = Integer.toString(Integer.parseInt(getValue(stmt1, CHARACTERISTICS[j])) + CharacteristicModifiers[j]);
            }
            if (j == 12) {
                outputValues[j] = Integer.toString(Integer.parseInt(outputValues[3]) + Integer.parseInt(outputValues[5]) + CharacteristicModifiers[12]);
            }
            if (j == 13) {
                outputValues[j] = Integer.toString(Integer.parseInt(outputValues[2]) + Integer.parseInt(outputValues[5]) + CharacteristicModifiers[13]);
            }
            if (j == 14) {
                outputValues[j] = Integer.toString(Integer.parseInt(outputValues[6]) + Integer.parseInt(outputValues[7]) + CharacteristicModifiers[14]);
            }
        }
        return outputValues;
    }

    /**
     *
     * @param first
     * @param second
     * @return
     */
    public static ObservableList mergeListViews(ListView first, ListView second) {
        ObservableList lst = FXCollections.observableArrayList();
        if (first.getItems() != null) {
            for (int i = 0; i < first.getItems().size(); i++) {
                lst.add(first.getItems().get(i));
            }
        }
        if (second.getItems() != null) {
            for (int i = 0; i < second.getItems().size(); i++) {
                lst.add(second.getItems().get(i));
            }
        }
        return lst;
    }

    public static ObservableList generateSubSkills(String skill, Boolean simplifyToCoreSkills) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = getConnection().prepareStatement("SELECT SkillRules FROM Skills WHERE SkillName = ?");
        stmt.setString(1, skill.split(" \\(p")[0]);
        String skl = (getValue(stmt, "SkillRules"));
        if (!simplifyToCoreSkills) {
            skl = skl.replace("_", " ");
        } else {
            skl = skl.replace("_", " ").replace("-", "").replace("+", "").replaceAll("[0-9]", "X").replace(" Xpts", "");
        }
        String[] sklSplit = null;
        if (skl.split(";").length == 0) {
            sklSplit[0] = skl;
        } else {
            sklSplit = skl.split(";");
        }
        ObservableList<String> tmp = FXCollections.observableArrayList();
        tmp.addAll(sklSplit);
        return tmp;
    }

    public static String generateSubSkillText(String subSkill, Boolean simplifyToCoreSkills) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = getConnection().prepareStatement("SELECT SkillRuleExplanation FROM SkillRules WHERE SkillRuleName = ?");
        if (simplifyToCoreSkills) {
            subSkill = subSkill.replace("-", "").replace("+", "").replaceAll("[0-9]", "X").replace(" Xpts", "");
        }
        if (subSkill.contains("(")) {
            subSkill = subSkill.split(" \\(")[0];
        }
        if (subSkill.contains("Increase") || subSkill.contains("Decrease") || subSkill.contains("Enhancement") || subSkill.contains("Reduction") || subSkill.contains("Restriction") || subSkill.contains("Resistance") || subSkill.contains("Weakness")) {
            if (subSkill.split(" ")[0].equals("Increase") || subSkill.split(" ")[0].equals("Decrease") || subSkill.split(" ")[0].equals("Enhancement") || subSkill.split(" ")[0].equals("Reduction") || subSkill.split(" ")[0].equals("Resistance") || subSkill.split(" ")[0].equals("Weakness")) {
                subSkill = subSkill.split(" ")[0] + " X";
            }
            if (subSkill.split(" ")[0].equals("Restriction")) {
                subSkill = "Restriction";
            }
        }
        switch (subSkill) {
            case "Ethereal Sight":
                subSkill = "Ethereal Sight X";
                break;
            default:
                break;
        }
        if (subSkill.contains("Remove Trait")) {
            subSkill = "Remove Trait";
        }
        stmt.setString(1, subSkill);
        return getValue(stmt, "SkillRuleExplanation");
    }

    /**
     * Numeric Validation Limit the characters to maxLengh AND to ONLY DigitS
     *
     * @param max_Lengh
     * @return
     */
    public static EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();
                if (txt_TextField.getText().length() >= max_Lengh) {
                    e.consume();
                }
                if (!e.getCharacter().matches("[0-9]")) {
                    e.consume();
                }
            }
        };
    }

}
