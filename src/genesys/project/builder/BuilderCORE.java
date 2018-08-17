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
import genesys.project.fxml.ErrorController;
import static java.lang.Integer.max;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
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
    public static Connection conn;

    /**
     * CHARACTERISTICS
     */
    public static final String[] CHARACTERISTICS = {"Strength", "Toughness", "Movement", "Martial", "Ranged", "Defense", "Discipline", "Willpower", "Command", "Wounds", "Attacks", "Size", "MT", "RT", "Morale"};

    /**
     * SIZES
     */
    public static final String[] SIZES = {"T", "S", "M", "L", "XL", "XXL"};

    /**
     * DOMAINS
     */
    public static final LifedomainValue[] DOMAINS = {Humanoid, Fey, Reptilia, Biest, Insecta};
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
    public static final String HUMANOIDSKILS = "Traits";

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

    /**
     * GAME_TYPES
     */
    public static final String[] GAME_TYPES = {"Standard Play", "Hero Play"};

    /**
     * EQUIPMENT_TYPES
     */
    public static final String[] EQUIPMENT_TYPES = {"Weapon", "Armor", "Heavy Military Weapon", "Vehicle", "Companion"};

    /**
     * category number
     */
    private static int catnumber;

    /**
     * where
     */
    private static int where;

    /**
     * main
     *
     * @param args
     */
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
     */
    public static void chooseConnection(UseCases uc) {
        try {
            if (UseCases.COREdb.equals(uc)) {
                conn = DBCOREConnect();
            }
            if (UseCases.Userdb.equals(uc)) {
                conn = DBUserDataConnect();
            }
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(BuilderCORE.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            ErrorController.ErrorControllerMethod(e);
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
            ErrorController.ErrorControllerMethod(e);
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return cUserData;
    }

    /**
     *
     * @param stmt
     * @param columns
     * @param exclude
     * @param excludeCompareColumn
     * @return
     */
    public static ObservableList getData(PreparedStatement stmt, String[] columns, ObservableList exclude, int excludeCompareColumn) {
        ObservableList temp = FXCollections.observableArrayList();
        try {
            int exCompColu = (excludeCompareColumn > columns.length || excludeCompareColumn < 0) ? 0 : excludeCompareColumn - 1;
            ResultSet rs = stmt.executeQuery();
            try {
                // loop through the result set
                while (rs.next()) {
                    //System.out.println(rs.getString(columns[j]) +  "\t");
                    boolean isPresent = false;
                    if (exclude != null) {
                        for (int i = 0; i < exclude.size(); i++) {
                            if ((exclude.get(i).toString().split(" \\(p")[0]).equals(rs.getString(columns[exCompColu]))) {
                                isPresent = true;
                            }
                        }
                    }
                    if (!isPresent) {
                        StringBuilder row = new StringBuilder();
                        for (int j = 0; j < columns.length; j++) {
                            row.append(rs.getString(columns[j]));
                            if (columns.length > 1 && j != columns.length - 1) {
                                row.append("|");
                            }
                        }
                        temp.add(row.toString());
                    }
                    //if (j != columns.length - 1) {temp.add("");} //Why?
                }
            } catch (SQLException e) {
                ErrorController.ErrorControllerMethod(e);
                System.out.println(e.getMessage());
            } finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    ErrorController.ErrorControllerMethod(e);
                }
                try {
                    stmt.close();
                } catch (SQLException e) {
                    ErrorController.ErrorControllerMethod(e);
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    ErrorController.ErrorControllerMethod(e);
                }
            }
            return temp;
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(BuilderCORE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    /**
     *
     * @param stmt
     * @param column
     * @return
     */
    public static String getValue(PreparedStatement stmt, String column) {
        String Tmp = "";
        try {
            ResultSet rs = stmt.executeQuery();
            try {
                Tmp = rs.getString(column);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    ErrorController.ErrorControllerMethod(e);
                }
                try {
                    stmt.close();
                } catch (SQLException e) {
                    ErrorController.ErrorControllerMethod(e);
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    ErrorController.ErrorControllerMethod(e);
                }
            }
            return Tmp;
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(BuilderCORE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Tmp;
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

    /**
     *
     * @param skill
     * @param simplifyToCoreSkills
     * @return
     */
    public static ObservableList generateSubSkills(String skill, Boolean simplifyToCoreSkills) {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        String skl = DatabaseReader.loadSkillFromDB(skill).get(0).toString().split("\\|")[2];
        if (!simplifyToCoreSkills) {
            skl = skl.replace("_", " ");
        } else {
            skl = skl.replace("_", " ").replace("-", "").replace("+", "").replaceAll("[0-9]", "X").replace(" Xpts", "");
        }
        tmp.addAll(skl.split(";"));
        return tmp;
    }

    /**
     *
     * @param subSkill
     * @param simplifyToCoreSkills
     * @return
     */
    public static String generateSubSkillText(String subSkill, Boolean simplifyToCoreSkills) {
        if (simplifyToCoreSkills) {
            subSkill = subSkill.replace("-", "").replace("+", "").replaceAll("[0-9]", "X").replace(" Xpts", "");
        }
        if (subSkill.contains("(")) {
            subSkill = subSkill.split(" \\(")[0];
        }
        if (subSkill.contains("Increase")
                || subSkill.contains("Decrease")
                || subSkill.contains("Enhancement")
                || subSkill.contains("Reduction")
                || subSkill.contains("Restriction")
                || subSkill.contains("Resistance")
                || subSkill.contains("Weakness")) {
            if (subSkill.split(" ")[0].equals("Increase")
                    || subSkill.split(" ")[0].equals("Decrease")
                    || subSkill.split(" ")[0].equals("Enhancement")
                    || subSkill.split(" ")[0].equals("Reduction")
                    || subSkill.split(" ")[0].equals("Resistance")
                    || subSkill.split(" ")[0].equals("Weakness")) {
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
        return DatabaseReader.getSkillRuleExplanation(subSkill);
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

    /**
     *
     * @param allSkills
     * @param skillName
     * @return
     */
    public static int getSkillIndex(ObservableList allSkills, String skillName) {
        int index = -1;
        for (int j = 0; j < allSkills.size(); j++) {
            if (allSkills.get(j).toString().split("\\|")[0].equals(skillName)) {
                index = j;
                j = allSkills.size();
            }
        }
        return index;
    }

    /**
     *
     * @param itemSubType
     * @return
     */
    public static String getImprovementTypeBasedOnItemSubtype(String itemSubType) {
        switch (itemSubType) {
            case "Armor":
            case "Shield":
                return "Armor";
            case "Melee":
            case "Spear":
                return "Melee Weapon";
            case "Throwing":
            case "Bow":
            case "Crossbow":
            case "Alchemy":
                return "Ranged Weapon";
            case "Flintlock":
                return "Ranged Weapon";
            case "Companion":
                return "Exotic Animal Attributes";
            case "HeavyMilitary":
                return "Military Weapon";
            case "Vehicle":
                return "Chariot and Wagon Upgrades";
            default:
                return "";
        }
    }

    static Boolean decider(String requirement, String classSkillsRules) {
        String req = DatabaseHolder.skillsSeparatorRepalcer(requirement);
        if (req.contains(",")) {
            req = req.replaceAll(",", " and ");
            req = req.replaceAll("   ", " ").replaceAll("  ", " ");
        }
        if (req.contains(" any ")) {
            if (req.contains("any Fire Arm Trait")) {
                req = req.replace("any Fire Arm Trait", "Flintlocks or Blunderbuss"); //dirty code
            }
        }
        if (req.contains(" or ") || req.contains(" and ")) {
            String tmp = req.replaceAll(" or ", " # ").replaceAll(" and ", " # ");
            String tmp2 = req.replaceAll(" or ", " # ").replaceAll(" and ", " # ");
            String tmp3 = req.replaceAll(" or ", " o ").replaceAll(" and ", " a ");
            StringBuilder logicString = new StringBuilder();
            for (String split : tmp.split(" \\# ")) {
                Boolean present = false;
                for (String split1 : classSkillsRules.split(";")) {
                    if (convertSkillToRequirement(split1).equals(split)) {
                        present = true;
                    }
                }
                if (present) {
                    logicString.append("1");
                } else {
                    logicString.append("0");
                }
                if (tmp2.contains("#")) {
                    logicString.append(tmp3.charAt(tmp2.indexOf("#")));
                    tmp2 = tmp2.replaceFirst("\\#", "*");
                }
            }
            String gate = logicString.toString().replaceAll(" ", "");
            while (gate.contains("o") || gate.contains("a")) {
                gate = gate.replaceFirst("1o1", "1");
                gate = gate.replaceFirst("1o0", "1");
                gate = gate.replaceFirst("0o1", "1");
                gate = gate.replaceFirst("0o0", "0");
                gate = gate.replaceFirst("1a1", "1");
                gate = gate.replaceFirst("1a0", "0");
                gate = gate.replaceFirst("0a1", "0");
                gate = gate.replaceFirst("0a0", "0");
            }
            if (gate.equals("1")) {
                return true;
            } else if (gate.equals("0")) {
                return false;
            }
        } else {
            for (String split : classSkillsRules.split(";")) {
                if (convertSkillToRequirement(split).equals(req)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param skill
     * @return
     */
    public static String convertSkillToRequirement(String skill) {
        if (skill.contains("Special Weapon:_")) {
            return skill.replaceAll("Special Weapon:_", "");
        }
        if (skill.contains("Heavy Military Weapons")) {
            return "Military Weapons";
        }
        return skill;
    }

    /**
     * setNumberOfClases
     */
    public static void setNumberOfClases() {
        DatabaseHolder.AClass[] tmp = null;
        if (DatabaseHolder.holdClass != null && DatabaseHolder.holdClass[0] != null) {
            tmp = DatabaseHolder.holdClass.clone();
        }
        DatabaseHolder.holdClass = new DatabaseHolder.AClass[DatabaseHolder.numberOfClases + 1];
        for (int i = 0; i < DatabaseHolder.numberOfClases + 1; i++) {
            DatabaseHolder.holdClass[i] = new DatabaseHolder.AClass();
            DatabaseHolder.holdClass[i].clearAClass();
        }
        if (tmp != null) {
            System.arraycopy(tmp, 0, DatabaseHolder.holdClass, 0, DatabaseHolder.numberOfClases);
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public static String getBaseAddedSkills(String name) {
        if (DatabaseHolder.fullSkillList1 == null) {
            DatabaseHolder.fullSkillList1 = "";
        }
        if (name.equals(BASE)) {
            DatabaseHolder.fullSkillList1 += DatabaseHolder.holdSpecies.getSkills();
        } else {
            for (DatabaseHolder.AClass holdClas : DatabaseHolder.holdClass) {
                if (holdClas.getClassName().equals(name)) {
                    if (holdClas.getSkills() != null
                            && !holdClas.getSkills().equals("null,")
                            && !holdClas.getSkills().equals("")
                            && !holdClas.getSkills().equals(";")) {
                        DatabaseHolder.fullSkillList1 += holdClas.getSkills() + getBaseAddedSkills(holdClas.getBasedOn());
                    } else {
                        DatabaseHolder.fullSkillList1 += getBaseAddedSkills(holdClas.getBasedOn());
                    }
                }
            }
        }
        return DatabaseHolder.fullSkillList1;
    }

    /**
     *
     * @param lifeDomain
     * @param species
     * @param classname
     * @param bb
     * @param points
     * @return
     */
    public static int baseAddedCost(LifedomainValue lifeDomain, DatabaseHolder.ASpecies species, DatabaseHolder.AClass[] classname, int bb, int points) {
        int a = 1;
        int source = 0;
        if (classname != null) {
            switch (classname[bb].getType()) {
                case "Standard Class":
                    a = 1;
                    break;
                case "Elite Class":
                    a = 2;
                    break;
                case "Leader Class":
                    a = 3;
                    break;
                case "Unique Class":
                    a = 2;
                    break;
                default:
                    break;
            }
            if (!"".equals(classname[bb].getType()) && !"null".equals(classname[bb].getType()) && !(BASE.equals(DatabaseHolder.classList1Holder))) {
                if (classname[bb].getBasedOn() != null && !classname[bb].getBasedOn().equals(BASE) && !BASE.equals(DatabaseHolder.classList1Holder)) {
                    for (int s = 0; s < classname.length; s++) {
                        if (classname[bb].getBasedOn().equals(classname[s].getClassName())) {
                            source = s;
                        }
                    }
                    points += a * baseAddedCost(lifeDomain, species, classname, source, points);
                    String[] lst = DatabaseHolder.skillsSeparatorRepalcer(classname[bb].getSkills()).split(",");
                    points += pointGetter(lst);
                } else if (classname[bb].getBasedOn() != null
                        && classname[bb].getBasedOn().equals(BASE)
                        && !BASE.equals(DatabaseHolder.classList1Holder)
                        && !"".equals(classname[bb].getSkills())
                        && !",".equals(classname[bb].getSkills())) {
                    String[] lst = DatabaseHolder.skillsSeparatorRepalcer(classname[bb].getSkills()).split(",");
                    points += pointGetter(lst);
                }
                if (!"".equals(species.getSkills()) && classname[bb].getBasedOn().equals(BASE)) {
                    String[] lst = DatabaseHolder.skillsSeparatorRepalcer(species.getSkills()).split(",");
                    points += pointGetter(lst);
                }
            } else {
                String[] lst = DatabaseHolder.skillsSeparatorRepalcer(species.getSkills()).split(",");
                points += pointGetter(lst);
            }
        } else if (!"".equals(species.getSkills())) {
            String[] lst = DatabaseHolder.skillsSeparatorRepalcer(species.getSkills()).split(",");
            points += pointGetter(lst);
        }
        return points;
    }

    private static int pointGetter(String[] lst) {
        int out = 0;
        for (String lst1 : lst) {
            ObservableList readed = DatabaseReader.loadSkillFromDB(lst1);
            if (!readed.isEmpty()) {
                String add = readed.get(0).toString();
                add = add.contains("|") ? add.split("\\|")[1] : add;
                if (add.contains("/")) {
                    add = add.split("/")[0];
                }
                if (!"".equals(add)) {
                    out += Integer.parseInt(add);
                }
            }
        }
        return out;
    }

    /**
     *
     * @return
     */
    public static String skillsCanTake() {
        String rules = DatabaseReader.getStartingNumberOfSkills(DatabaseHolder.holdSpecies.getLifedomain().toString());
        switch (DatabaseHolder.holdSpecies.getLifedomain()) {
            case Humanoid:
                return rules.replace(",", "\n").replace(":", " : ");
            case Fey:
                switch (((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).getMainDomain()) {
                    case Light:
                        if (!DatabaseHolder.outcasts) {
                            return rules.split(";")[0].replace(",", "\n").replace(":", " : ");
                        } else {
                            return rules.split(";")[3].replace(",", "\n").replace(":", " : ");
                        }
                    case Darkness:
                        if (!DatabaseHolder.outcasts) {
                            return rules.split(";")[1].replace(",", "\n").replace(":", " : ");
                        } else {
                            return rules.split(";")[4].replace(",", "\n").replace(":", " : ");
                        }
                    case Twilight:
                        if (!DatabaseHolder.outcasts) {
                            return rules.split(";")[2].replace(",", "\n").replace(":", " : ");
                        } else {
                            return rules.split(";")[5].replace(",", "\n").replace(":", " : ");
                        }
                }
                break;
            case Reptilia:
                return rules.replace(":", " : ");
            case Biest:
                return rules.replace(",", "\n").replace(":", " : ");
            case Insecta:
                return rules.replace(",", "\n").replace(":", " : ");
        }
        return null;
    }

    static int addRemove(int Skl, Boolean Add) {
        int tmp = Skl;
        tmp = Add ? tmp + 1 : tmp - 1;
        return tmp;
    }

    /**
     *
     * @param Skill
     * @param Add
     * @return
     */
    public static String skillsLeftModify(String Skill, Boolean Add) {
        String[] LdTrees = DatabaseReader.getLdTrees(Skill);
        String SkillType1 = LdTrees[0];
        String SkillType3 = LdTrees[1];
        switch (SkillType1) {
            case "Genetic Mutation":
                DatabaseHolder.holdSpecies.setGeneticMutation(addRemove(DatabaseHolder.holdSpecies.getGeneticMutation(), Add));
                break;
            case "Environmental Adaptation":
                DatabaseHolder.holdSpecies.setEnvironmentalAdaptation(addRemove(DatabaseHolder.holdSpecies.getEnvironmentalAdaptation(), Add));
                break;
            case "Knowledge and Science":
                DatabaseHolder.holdSpecies.setKnowledgeAndScience(addRemove(DatabaseHolder.holdSpecies.getKnowledgeAndScience(), Add));
                break;
            case LIGHT:
                if ("Lesser Traits".equals(SkillType3)) {
                    DatabaseHolder.holdSpecies.setLesserTraitsAndPowersOfLight(addRemove(DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfLight(), Add));
                }
                if ("Greater Traits".equals(SkillType3)) {
                    DatabaseHolder.holdSpecies.setGreaterTraitsAndPowersOfLight(addRemove(DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfLight(), Add));
                }
                break;
            case DARKNESS:
                if ("Lesser Traits".equals(SkillType3)) {
                    DatabaseHolder.holdSpecies.setLesserTraitsAndPowersOfDarkness(addRemove(DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfDarkness(), Add));
                }
                if ("Greater Traits".equals(SkillType3)) {
                    DatabaseHolder.holdSpecies.setGreaterTraitsAndPowersOfDarkness(addRemove(DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfDarkness(), Add));
                }
                break;
            case TWILIGHT:
                if ("Lesser Traits".equals(SkillType3)) {
                    DatabaseHolder.holdSpecies.setLesserTraitsAndPowersOfTwilight(addRemove(DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfTwilight(), Add));
                }
                if ("Greater Traits".equals(SkillType3)) {
                    DatabaseHolder.holdSpecies.setGreaterTraitsAndPowersOfTwilight(addRemove(DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfTwilight(), Add));
                }
                break;
            case "Reptilia Lineages":
                DatabaseHolder.holdSpecies.setReptiliaLineage(addRemove(DatabaseHolder.holdSpecies.getReptiliaLineage(), Add));
                break;
            case "EnvironmentalAdaptability":
                DatabaseHolder.holdSpecies.setEnvironmentalAdaptability(addRemove(DatabaseHolder.holdSpecies.getEnvironmentalAdaptability(), Add));
                break;
            case "ExtremisAffinity":
                DatabaseHolder.holdSpecies.setExtremisAffinity(addRemove(DatabaseHolder.holdSpecies.getExtremisAffinity(), Add));
                break;
            case "BiestialKingdoms":
                DatabaseHolder.holdSpecies.setBiestialKingdoms(addRemove(DatabaseHolder.holdSpecies.getBiestialKingdoms(), Add));
                break;
            case "RegionalTraits":
                DatabaseHolder.holdSpecies.setRegionalTraits(addRemove(DatabaseHolder.holdSpecies.getRegionalTraits(), Add));
                break;
            case "SpiritualAndScientificKnowledge":
                DatabaseHolder.holdSpecies.setSpiritualAndScientificKnowledge(addRemove(DatabaseHolder.holdSpecies.getSpiritualAndScientificKnowledge(), Add));
                break;
            case "Clasification":
                DatabaseHolder.holdSpecies.setClasification(addRemove(DatabaseHolder.holdSpecies.getClasification(), Add));
                break;
            case "Order":
                DatabaseHolder.holdSpecies.setOrder(addRemove(DatabaseHolder.holdSpecies.getOrder(), Add));
                break;
            case "GeneticMorphology":
                DatabaseHolder.holdSpecies.setGeneticMorphology(addRemove(DatabaseHolder.holdSpecies.getGeneticMorphology(), Add));
                break;
            case "Knowledge":
                DatabaseHolder.holdSpecies.setKnowledge(addRemove(DatabaseHolder.holdSpecies.getKnowledge(), Add));
                break;
            default:
                break;
        }
        switch (DatabaseHolder.holdSpecies.getLifedomain()) {
            case Humanoid:
                if (!DatabaseHolder.arcana) {
                    return DatabaseHolder.holdSpecies.getGeneticMutation() + "\n"
                            + DatabaseHolder.holdSpecies.getEnvironmentalAdaptation() + "\n"
                            + DatabaseHolder.holdSpecies.getKnowledgeAndScience() + "\n";
                } else {
                    return DatabaseHolder.holdSpecies.getGeneticMutation() + "\n"
                            + DatabaseHolder.holdSpecies.getEnvironmentalAdaptation() + "\n"
                            + DatabaseHolder.holdSpecies.getKnowledgeAndScience() + "\n"; //TODO add fey skills
                }
            case Fey:
                switch (((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).getMainDomain()) {
                    case Light:
                        if (!DatabaseHolder.outcasts) {
                            return DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfLight() + "\n"
                                    + DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfLight() + "\n"
                                    + DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfTwilight() + "\n";
                        } else {
                            return DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfLight() + "\n"
                                    + DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfLight() + "\n"
                                    + DatabaseHolder.holdSpecies.getKnowledgeAndScience() + "\n"
                                    + DatabaseHolder.holdSpecies.getEnvironmentalAdaptation() + "\n";
                        }
                    case Darkness:
                        if (!DatabaseHolder.outcasts) {
                            return DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfDarkness() + "\n"
                                    + DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfDarkness() + "\n"
                                    + DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfTwilight() + "\n";
                        } else {
                            return DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfDarkness() + "\n"
                                    + DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfDarkness() + "\n"
                                    + DatabaseHolder.holdSpecies.getKnowledgeAndScience() + "\n"
                                    + DatabaseHolder.holdSpecies.getEnvironmentalAdaptation() + "\n";
                        }
                    case Twilight:
                        if (!DatabaseHolder.outcasts) {
                            return DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfTwilight() + "\n"
                                    + DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfTwilight() + "\n"
                                    + max(DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfLight(), DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfDarkness()) + "\n";
                        } else {
                            return DatabaseHolder.holdSpecies.getLesserTraitsAndPowersOfTwilight() + "\n"
                                    + DatabaseHolder.holdSpecies.getGreaterTraitsAndPowersOfTwilight() + "\n"
                                    + DatabaseHolder.holdSpecies.getKnowledgeAndScience() + "\n"
                                    + DatabaseHolder.holdSpecies.getEnvironmentalAdaptation() + "\n";
                        }
                }
                break;
            case Reptilia:
                return DatabaseHolder.holdSpecies.getReptiliaLineage() + "\n"
                        + DatabaseHolder.holdSpecies.getEnvironmentalAdaptability() + "\n"
                        + DatabaseHolder.holdSpecies.getExtremisAffinity() + "\n";
            case Biest:
                return DatabaseHolder.holdSpecies.getBiestialKingdoms() + "\n"
                        + DatabaseHolder.holdSpecies.getRegionalTraits() + "\n"
                        + DatabaseHolder.holdSpecies.getGeneticMutation() + "\n"
                        + DatabaseHolder.holdSpecies.getEnvironmentalAdaptation() + "\n"
                        + DatabaseHolder.holdSpecies.getSpiritualAndScientificKnowledge() + "\n";
            case Insecta:
                return DatabaseHolder.holdSpecies.getOrder() + "\n"
                        + DatabaseHolder.holdSpecies.getGeneticMorphology() + "\n"
                        + DatabaseHolder.holdSpecies.getEnvironmentalAdaptation() + "\n"
                        + DatabaseHolder.holdSpecies.getKnowledge() + "\n";
        }
        return "";
    }

    /**
     *
     * @param Date
     * @return
     */
    public static String generateIdFromDate(String Date) {
        String out = Date.replaceAll("\\.", "").replaceAll(":", "").replaceAll(" ", "");
        return out;
    }

    /**
     *
     * @param data
     * @return
     */
    public static ObservableList beautifyBattleData(String data) {
        ObservableList out = FXCollections.observableArrayList();
        out.add(data.split("\\|")[0] + " - " + data.split("\\|")[1] + ", using roster: " + data.split("\\|")[2]
                + " VS "
                + data.split("\\|")[3] + " - " + data.split("\\|")[4] + ", using roster: " + data.split("\\|")[5]);
        out.add("Points: " + data.split("\\|")[6]);
        out.add("Outcome:");
        out.add(data.split("\\|")[7].split(";")[0]);
        out.add(data.split("\\|")[7].split(";")[1]);
        out.add(data.split("\\|")[7].split(";")[2]);
        return out;
    }

    /**
     *
     * @param ruledskills
     */
    public static void getSkillModifiers(List<String> ruledskills) {
        String[][] tmp = new String[2][ruledskills.size()];
        catnumber = 0;
        List<String> wheretabl = new ArrayList<>();
        for (int i = 0; i < ruledskills.size(); i++) {
            if (i == 0) {
                wheretabl.add(ruledskills.get(i).split(">")[0]);
                tmp[0][i] = ruledskills.get(i).split(">")[0];
                catnumber++;
            }
            if (!ruledskills.get(i).split(">")[0].equals(wheretabl.get(catnumber - 1))) {
                wheretabl.add(ruledskills.get(i).split(">")[0]);
                tmp[0][i] = ruledskills.get(i).split(">")[0];
                catnumber++;
            }
        }
        int[][][] increasestable = new int[catnumber][2][15];
        for (int i = 0; i < catnumber; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 15; k++) {
                    increasestable[i][j][k] = 0;
                }
            }
        }
        for (int i = 0; i < ruledskills.size(); i++) {
            tmp[0][i] = ruledskills.get(i).split(">")[0];
            tmp[1][i] = ruledskills.get(i).split(">")[1];
            if (tmp[1][i].contains("Increase")
                    || tmp[1][i].contains("Decrease")
                    || tmp[1][i].contains("Model Size")) {
                int a = 2;
                if (tmp[1][i].contains("Increase")) {
                    a = 0;
                }
                if (tmp[1][i].contains("Decrease")
                        || tmp[1][i].contains("Model Size")
                        || tmp[1][i].contains("Increase Size")) {
                    a = 1;
                }
                for (int u = 0; u < wheretabl.size(); u++) {
                    if (tmp[0][i].equals(wheretabl.get(u))) {
                        where = u;
                    }
                }
                String stat = tmp[1][i].split(" ")[1].split("_")[0];
                switch (stat) {
                    case "Wounds":
                        increasestable[where][a][0] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Attacks":
                        increasestable[where][a][1] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Size":
                        increasestable[where][a][2] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Strength":
                        increasestable[where][a][3] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Toughness":
                        increasestable[where][a][4] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Movement":
                        increasestable[where][a][5] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Martial":
                        increasestable[where][a][6] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Ramged":
                        increasestable[where][a][7] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Defense":
                        increasestable[where][a][8] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Discipline":
                        increasestable[where][a][9] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Willpower":
                        increasestable[where][a][10] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Command":
                        increasestable[where][a][11] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "MT":
                        increasestable[where][a][12] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "RT":
                        increasestable[where][a][13] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Morale":
                        increasestable[where][a][14] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    default:
                        break;
                }
            }
        }
        for (int s = 0; s < 15; s++) {
            int maxinc = 0;
            int totdec = 0;
            for (int i = 0; i < catnumber; i++) {
                if (increasestable[i][0][s] > maxinc) {
                    maxinc = increasestable[i][0][s];
                }
                totdec += increasestable[i][1][s];
            }
            switch (s) {
                case 0:
                    BuilderFXMLController.HOLD_MODIFIERS.setWoundsModifier(maxinc - totdec);
                    break;
                case 1:
                    BuilderFXMLController.HOLD_MODIFIERS.setAttacksModifier(maxinc - totdec);
                    break;
                case 2:
                    BuilderFXMLController.HOLD_MODIFIERS.setSizeModifier(totdec);
                    break;
                case 3:
                    BuilderFXMLController.HOLD_MODIFIERS.setStrengthModifier(maxinc - totdec);
                    break;
                case 4:
                    BuilderFXMLController.HOLD_MODIFIERS.setToughnessModifier(maxinc - totdec);
                    break;
                case 5:
                    BuilderFXMLController.HOLD_MODIFIERS.setMovementModifier(maxinc - totdec);
                    break;
                case 6:
                    BuilderFXMLController.HOLD_MODIFIERS.setMartialModifier(maxinc - totdec);
                    break;
                case 7:
                    BuilderFXMLController.HOLD_MODIFIERS.setRangedModifier(maxinc - totdec);
                    break;
                case 8:
                    BuilderFXMLController.HOLD_MODIFIERS.setDefenseModifier(maxinc - totdec);
                    break;
                case 9:
                    BuilderFXMLController.HOLD_MODIFIERS.setDisciplineModifier(maxinc - totdec);
                    break;
                case 10:
                    BuilderFXMLController.HOLD_MODIFIERS.setWillpowerModifier(maxinc - totdec);
                    break;
                case 11:
                    BuilderFXMLController.HOLD_MODIFIERS.setCommandModifier(maxinc - totdec);
                    break;
                case 12:
                    BuilderFXMLController.HOLD_MODIFIERS.setMTModifier(maxinc - totdec);
                    break;
                case 13:
                    BuilderFXMLController.HOLD_MODIFIERS.setRTModifier(maxinc - totdec);
                    break;
                case 14:
                    BuilderFXMLController.HOLD_MODIFIERS.setMoraleModifier(maxinc - totdec);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *
     * @param species
     * @param culture
     * @param witch
     * @return
     */
    public static ObservableList generateProgressAndBattlesSortedList(String species, String culture, String witch) {

        Map<String, String> sorterMap = new TreeMap<>();
        if (witch.equals("both") || witch.equals("Progress")) {
            ObservableList listFromProgress = DatabaseReader.getAllProgressAndDateForCulture(species, culture);
            for (int i = 0; i < listFromProgress.size(); i++) {
                sorterMap.put(generateIdFromDate(
                        listFromProgress.get(i).toString().split("\\|")[1]),
                        "Progress: " + listFromProgress.get(i).toString().split("\\|")[0]);
            }
        }
        if (witch.equals("both") || witch.equals("Battle")) {
            ObservableList listFromBattles = DatabaseReader.getAllBattlesAndDateForCulture(species, culture);
            for (int i = 0; i < listFromBattles.size(); i++) {
                sorterMap.put(generateIdFromDate(
                        listFromBattles.get(i).toString().split("\\|")[1]),
                        "Battle: " + listFromBattles.get(i).toString().split("\\|")[0]);
            }
        }
        return FXCollections.observableArrayList(sorterMap.values());
    }

    /**
     *
     * @param rawSkills
     * @return
     */
    public static ObservableList<String> getFullSkillsDescription(String rawSkills) {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        tmp.addAll(DatabaseHolder.skillsSeparatorRepalcer(rawSkills).split(","));
        List<String> ruledskills = new ArrayList<>();
        String[] lst = new String[tmp.size()];
        ObservableList allSkills = DatabaseReader.loadAllSkillsFromDB();
        for (int i = 0; i < tmp.size(); i++) {
            int index = getSkillIndex(allSkills, tmp.get(i));
            lst[i] = tmp.get(i);
            String[] skl = (allSkills.get(index).toString().split("\\|")[2]).split(";");
            lst[i] += " (pts : " + (allSkills.get(index).toString().split("\\|")[1]) + ") : ";
            for (int j = 0; j < skl.length; j++) {
                lst[i] += ((skl[j].split("_"))[0]);
                if ((skl[j].split("_")).length >= 2) {
                    lst[i] += (" " + ((skl[j].split("_"))[1]));
                }
                if ((skl[j].split("_")).length >= 3) {
                    lst[i] += (" " + ((skl[j].split("_"))[2]));
                }
                if (j < skl.length - 1) {
                    lst[i] += ", ";
                }
                ruledskills.add((allSkills.get(index).toString().split("\\|")[6]) + ">" + skl[j]);
            }
        }
        tmp.setAll(lst);
        if (!ruledskills.get(0).equals(">")) {
            getSkillModifiers(ruledskills);
        }
        return tmp;
    }

    /**
     *
     * @param dataSpecies
     * @param dataClass
     * @param baseSpecies
     * @param BaseCulture
     * @return
     */
    public static String getSourceBaseSkills(ObservableList dataSpecies, ObservableList dataClass, String baseSpecies, String BaseCulture) {
        String skillsThis = "";
        String basedOn = "";
        String skillsBefore = "";
        if (dataClass != null
                && !dataClass.isEmpty()
                && dataClass.get(0) != null
                && dataClass.get(0).toString().split("\\|").length != 0) {
            skillsThis = dataClass.get(0).toString().split("\\|")[0];
            basedOn = dataClass.get(0).toString().split("\\|")[3];
        }
        if (!basedOn.equals("") && !basedOn.equals("null")) {
            if (basedOn.equals("<base species>")) {
                skillsBefore = dataSpecies.get(0).toString().split("\\|")[3];
            } else {
                ObservableList dataBaseClass = DatabaseReader.getClassData(baseSpecies, BaseCulture, basedOn);
                skillsBefore = getSourceBaseSkills(dataSpecies, dataBaseClass, baseSpecies, BaseCulture);
            }
        }
        String fullSkillList = !skillsThis.equals("") && !skillsThis.equals("null") && !skillsBefore.equals("") ? (skillsThis + "," + skillsBefore) : skillsBefore;
        return fullSkillList;
    }

    /**
     *
     * @param species
     * @param culture
     * @param classname
     * @param points
     * @param selHeroModel
     * @return
     */
    public static int baseCost(String species, String culture, String classname, int points, SelectionModel selHeroModel) {
        int a = 1;
        ObservableList dataSpecies = DatabaseReader.getSpeciesData(species);
        ObservableList allSkills = DatabaseReader.loadAllSkillsFromDB();
        if (classname != null && !classname.equals(BASE) && !classname.equals(LESSER) && !classname.equals(GREATER)) {
            ObservableList dataClass = DatabaseReader.getClassData(species, culture, classname);
            switch (dataClass.get(0).toString().split("\\|")[2]) {
                case "Standard Class":
                    a = 1;
                    break;
                case "Elite Class":
                    a = 2;
                    break;
                case "Leader Class":
                    a = 3;
                    break;
                case "Unique Class":
                    a = 2;
                    break;
                default:
                    break;
            }
            String[] lst = DatabaseHolder.skillsSeparatorRepalcer(dataClass.get(0).toString().split("\\|")[0]).split(",");
            String basedOn = dataClass.get(0).toString().split("\\|")[3];
            if (basedOn != null && !basedOn.equals("null") && !basedOn.equals("")) {
                points += a * baseCost(species, culture, basedOn, points, selHeroModel);
            }
            for (String lst1 : lst) {
                int index = getSkillIndex(allSkills, lst1);
                String pointCost = index < 0 ? "" : allSkills.get(index).toString().split("\\|")[1];
                if (pointCost.equals("")) {
                    pointCost = "0";
                }
                if (pointCost.contains("/")) {
                    pointCost = pointCost.split("/")[0];
                }
                if (basedOn != null && !basedOn.equals("") && !basedOn.equals("null")) {
                    if (!"".equals(pointCost)) {
                        points += Integer.parseInt(pointCost);
                    }
                }
            }
            points += Integer.parseInt(dataClass.get(0).toString().split("\\|")[4]);
        } else {
            String[] lst = DatabaseHolder.skillsSeparatorRepalcer(dataSpecies.get(0).toString().split("\\|")[3]).split(",");
            for (String lst1 : lst) {
                int index = getSkillIndex(allSkills, lst1);
                String pointCost = allSkills.get(index).toString().split("\\|")[1];
                if (pointCost.equals("")) {
                    pointCost = "0";
                }
                if (pointCost.contains("/")) {
                    pointCost = pointCost.split("/")[0];
                }
                points += Integer.parseInt(pointCost);
            }
        }
        if (!selHeroModel.isEmpty()) {
            String selHero = selHeroModel.getSelectedItem().toString();
            ObservableList dataHero = DatabaseReader.getHeroData(species, culture, selHero);
            String basedOn = dataHero.get(0).toString().split("\\|")[1];
            if (basedOn != null && !basedOn.equals("null") && !basedOn.equals("") && basedOn.equals(classname)) {
                points += Integer.parseInt(dataHero.get(0).toString().split("\\|")[2]);
            }
        }
        return points;
    }

}
