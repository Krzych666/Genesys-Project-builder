/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
import genesys.project.builder.Enums.Enmuerations.UseCases;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import genesys.project.fxml.BuilderFXMLController;
import genesys.project.fxml.ErrorController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListView;

/**
 *
 * @author krzysztofg
 */
public class DatabaseReader {

    /**
     *
     * @return
     */
    public static ObservableList loadAllSkillsFromDB() {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills");
            String[] columns = {"SkillName", "PointCost", "SkillRules", "Age", "LifeDomain", "LifeDomainTree1", "LifeDomainTree2", "LifeDomainTree3"};
            out = BuilderCORE.getData(stmt, columns, null, 0);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param SkillName
     * @return
     *
     */
    public static ObservableList loadSkillFromDB(String SkillName) {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
            stmt.setString(1, SkillName);
            String[] columns = {"SkillName", "PointCost", "SkillRules", "Age", "LifeDomain", "LifeDomainTree1", "LifeDomainTree2", "LifeDomainTree3"};
            out = BuilderCORE.getData(stmt, columns, null, 0);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param SkillRuleName
     * @return
     *
     */
    public static String getSkillRuleExplanation(String SkillRuleName) {
        String out = "";
        try {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM SkillRules WHERE SkillRuleName = ?");
            stmt.setString(1, SkillRuleName);
            out = BuilderCORE.getValue(stmt, "SkillRuleExplanation");
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @return
     */
    public static String classCanTake() {
        String out = "";
        try {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM StartingCharacteristics WHERE LifeDomain =?");
            stmt.setString(1, DatabaseHolder.holdSpecies.getLifedomain().toString());
            String ClassList = BuilderCORE.getValue(stmt, "ClassTypes");
            String CL[] = ClassList.split(",");
            int[] mx = {DatabaseHolder.holdSpecies.getMaxNumberOfLowClases(), DatabaseHolder.holdSpecies.getMaxNumberOfMidClases(), DatabaseHolder.holdSpecies.getMaxNumberOfHigClases(), 1};
            StringBuilder fintex = new StringBuilder();
            for (int i = 0; i < CL.length; i++) {
                fintex.append(CL[i]).append(": max ").append(mx[i]).append(", current count\n");
            }
            out = fintex.toString();
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @return
     */
    public static ObservableList<String> getSpeciesList() {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        try {
            for (LifedomainValue domain : BuilderCORE.DOMAINS) {
                tmp.add("--" + domain.toString() + "--");
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt = BuilderCORE.conn.prepareStatement("SELECT * FROM CreatedSpecies WHERE LifeDomain = ?");
                stmt.setString(1, domain.toString());
                String[] columns = {"SpeciesName"};
                ObservableList<String> tmpget = BuilderCORE.getData(stmt, columns, null, 0);
                for (int i = 0; i < tmpget.size(); i++) {
                    tmp.add(tmpget.get(i));
                }
                tmp.add(" ");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @return
     */
    public static ObservableList populateDropdownsSpecies() {
        ObservableList tmp = FXCollections.observableArrayList();
        try {
            tmp.setAll(DatabaseHolder.TOPDROP);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SpeciesName FROM CreatedSpecies");
            String[] columns = {"SpeciesName"};
            tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param fromwhat
     * @return
     */
    public static ObservableList populateDropdownsCultures(String fromwhat) {
        ObservableList tmp = FXCollections.observableArrayList();
        try {

            tmp.setAll(DatabaseHolder.TOPDROP);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedCultures WHERE SpeciesName = ?");
            stmt.setString(1, fromwhat);
            String[] columns = {"CultureName"};
            tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param fromwhats
     * @param fromwhatc
     * @return
     */
    public static ObservableList populateDropdownsClasses(String fromwhats, String fromwhatc) {
        ObservableList tmp = FXCollections.observableArrayList();
        try {
            tmp.setAll(DatabaseHolder.TOPDROP);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName =? AND CultureName =?");
            stmt.setString(1, fromwhats);
            stmt.setString(2, fromwhatc);
            String[] columns = {"ClassName"};
            tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param fromwhats
     * @param fromwhatc
     * @param fromwhatcl
     * @return
     */
    public static ObservableList populateDropdownsHeroes(String fromwhats, String fromwhatc, String fromwhatcl) {
        ObservableList tmp = FXCollections.observableArrayList();
        try {
            tmp.setAll(DatabaseHolder.TOPDROP);
            chooseConnection(UseCases.Userdb);
            String[] columns = {"HeroName"};
            if (DatabaseHolder.TOPDROP.equals(fromwhatcl) && !(DatabaseHolder.TOPDROP.equals(fromwhats) && DatabaseHolder.TOPDROP.equals(fromwhatc))) {
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =?");
                stmt.setString(1, fromwhats);
                stmt.setString(2, fromwhatc);
                tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
            } else {
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND BasedOn =?");
                stmt.setString(1, fromwhats);
                stmt.setString(2, fromwhatc);
                stmt.setString(3, fromwhatcl);
                tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param fromwhats
     * @param fromwhatc
     * @return
     */
    public static ObservableList populateDropdownsProgress(String fromwhats, String fromwhatc) {
        ObservableList tmp = FXCollections.observableArrayList();
        try {
            tmp.setAll(DatabaseHolder.TOPDROP);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedProgress WHERE (SpeciesName =?) AND (CultureName =?)");
            stmt.setString(1, fromwhats);
            stmt.setString(2, fromwhatc);
            String[] columns = {"ProgressName"};
            tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param fromwhats
     * @param fromwhatc
     * @return
     */
    public static ObservableList populateDropdownsRosters(String fromwhats, String fromwhatc) {
        ObservableList tmp = FXCollections.observableArrayList();
        try {
            tmp.setAll(DatabaseHolder.TOPDROP);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE (SpeciesName =?) AND (CultureName =?)");
            stmt.setString(1, fromwhats);
            stmt.setString(2, fromwhatc);
            String[] columns = {"RosterName"};
            tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @return
     */
    public static ObservableList<String> getSkillSet() {
        ObservableList<String> outList = FXCollections.observableArrayList();
        try {
            ObservableList<String> tmp = FXCollections.observableArrayList();
            if (DatabaseHolder.holdSpecies != null) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE LifeDomain = ?");
                stmt.setString(1, DatabaseHolder.holdSpecies.getLifedomain().toString());
                String[] columns = {"LifeDomainTree1"};
                tmp = BuilderCORE.getData(stmt, columns, null, 0);
                if (LifedomainValue.Fey == DatabaseHolder.holdSpecies.getLifedomain()) {
                    chooseConnection(UseCases.COREdb);
                    PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE (LifeDomain = 'Fey' AND (LifeDomainTree1 = ? OR LifeDomainTree1 = ?))");
                    switch (((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).getMainDomain()) {
                        case Light:
                            if (!DatabaseHolder.outcasts) {
                                stmt1.setString(1, BuilderCORE.LIGHT);
                                stmt1.setString(2, BuilderCORE.TWILIGHT);
                            } else {
                                stmt1.setString(1, BuilderCORE.LIGHT);
                                stmt1.setString(2, BuilderCORE.LIGHT);
                            }
                            break;
                        case Darkness:
                            if (!DatabaseHolder.outcasts) {
                                stmt1.setString(1, BuilderCORE.DARKNESS);
                                stmt1.setString(2, BuilderCORE.TWILIGHT);
                            } else {
                                stmt1.setString(1, BuilderCORE.DARKNESS);
                                stmt1.setString(2, BuilderCORE.DARKNESS);
                            }
                            break;
                        case Twilight:
                            if (!DatabaseHolder.outcasts) {
                                stmt1.setString(1, BuilderCORE.TWILIGHT);
                                switch (((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).getSecondaryDomain()) {
                                    case Light:
                                        stmt1.setString(2, BuilderCORE.LIGHT);
                                        break;
                                    case Darkness:
                                        stmt1.setString(2, BuilderCORE.DARKNESS);
                                        break;
                                    default:
                                        break;
                                }

                            } else {
                                stmt1.setString(1, BuilderCORE.TWILIGHT);
                                stmt1.setString(2, BuilderCORE.TWILIGHT);
                            }
                            break;
                    }
                    String[] columns1 = {"LifeDomainTree1"};
                    tmp = BuilderCORE.getData(stmt1, columns1, null, 0);
                }
            }
            int a = 0;
            ObservableList<String> tmp1 = FXCollections.observableArrayList();
            if (DatabaseHolder.arcana && DatabaseHolder.holdSpecies.getLifedomain() == LifedomainValue.Humanoid) {
                a = 3;
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE LifeDomain = 'Fey'");
                String[] columns = {"LifeDomainTree1"};
                tmp1 = BuilderCORE.getData(stmt, columns, null, 0);
            }
            if (DatabaseHolder.outcasts && DatabaseHolder.holdSpecies.getLifedomain() == LifedomainValue.Fey) {
                a = 2;
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE (LifeDomain = 'Humanoid' AND LifeDomainTree1 != 'Genetic Mutation')");
                String[] columns = {"LifeDomainTree1"};
                tmp1 = BuilderCORE.getData(stmt, columns, null, 0);
            }
            if (tmp != null) {
                String[] lst = new String[tmp.size() + a];
                for (int i = 0; i < tmp.size(); i++) {
                    lst[i] = (tmp.get(i));
                }
                if (a != 0 && tmp1 != null) {
                    int aa = 0;
                    for (int i = tmp.size(); i < tmp.size() + a; i++) {
                        lst[i] = (tmp1.get(aa));
                        aa++;
                    }
                }
                outList.addAll(lst);
                return outList;
            }
            return null;
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outList;
    }

    /**
     *
     * @param SkillBranch
     * @param onlyPrimaryChooser
     * @param onlySecondaryChooser
     * @return
     */
    public static ObservableList<String> getSubSkillSet(String SkillBranch, String onlyPrimaryChooser, String onlySecondaryChooser) {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree2 FROM Skills WHERE LifeDomain = ? AND LifeDomainTree1 = ?");
            stmt.setString(1, DatabaseHolder.holdSpecies.getLifedomain().name());
            stmt.setString(2, SkillBranch);
            String[] columns = {"LifeDomainTree2"};
            tmp = BuilderCORE.getData(stmt, columns, null, 0);
            if (onlyPrimaryChooser != null && !onlyPrimaryChooser.equals("") && !onlyPrimaryChooser.equals("standard") && tmp.contains(onlyPrimaryChooser)) {
                return FXCollections.observableArrayList(onlyPrimaryChooser);
            }
            if (onlySecondaryChooser != null && !onlySecondaryChooser.equals("") && !onlySecondaryChooser.equals("standard") && tmp.contains(onlySecondaryChooser)) {
                return FXCollections.observableArrayList(onlySecondaryChooser);
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param itemType
     * @return
     */
    public static ObservableList getItemsNames(String itemType) {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        try {
            String itemTypeOneWord = itemType.replaceAll(" ", "");
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + itemTypeOneWord + " WHERE Type = ? OR Type = ?");
            stmt.setString(1, "Primitive");
            stmt.setString(2, "Common");
            String[] columns = {itemTypeOneWord + "Name"};
            tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
            tmp.addAll(getAvailableSpecialItemNames(itemType));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param itemName
     * @return
     */
    public static String getItemTypreFromItemName(String itemName) {
        String out = "";
        try {
            String[] columns = {"count(*)"};
            for (String EQUIPMENT_TYPES : BuilderCORE.EQUIPMENT_TYPES) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT count(*) FROM Equipment" + EQUIPMENT_TYPES.replaceAll(" ", "") + " WHERE " + EQUIPMENT_TYPES.replaceAll(" ", "") + "Name= ?");
                stmt.setString(1, itemName);
                if (BuilderCORE.getData(stmt, columns, null, 0).get(0).toString().equals("1")) {
                    return EQUIPMENT_TYPES;
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param itemType
     * @return
     */
    public static ObservableList getAvailableSpecialItemNames(String itemType) {
        ObservableList tmp = FXCollections.observableArrayList();
        try {
            String itemTypeOneWord = itemType.replaceAll(" ", "");
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + itemTypeOneWord + " WHERE Type = ? ");
            stmt.setString(1, "Special");
            String[] columns = new String[]{};
            switch (itemType) {
                case "Armor":
                case "Weapon":
                case "Companion":
                case "Vehicle":
                    columns = new String[]{itemTypeOneWord + "Name", "SubType"};
                    break;
                case "Heavy Military Weapon":
                    columns = new String[]{itemTypeOneWord + "Name", "SubType", "Pre-requisite"};
                    break;
                default:
                    break;
            }
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            DatabaseHolder.fullSkillList1 = "";
            String classSkillsRules = AvailableSkillsLister.getSkillsRules(BuilderCORE.getBaseAddedSkills(DatabaseHolder.classList1Holder));
            for (int i = 0; i < data.size(); i++) {
                String requirement = "";
                switch (itemType) {
                    case "Armor":
                        requirement = data.get(i).toString().split("\\|")[0];
                        break;
                    case "Weapon":
                        requirement = data.get(i).toString().split("\\|")[0];
                        break;
                    case "Companion":
                        requirement = data.get(i).toString().split("\\|")[0];
                        break;
                    case "Vehicle":
                        requirement = data.get(i).toString().split("\\|")[0];
                        break;
                    case "Heavy Military Weapon":
                        requirement = data.get(i).toString().split("\\|")[2];
                        break;
                    default:
                        break;
                }
                Boolean include = BuilderCORE.decider(requirement, classSkillsRules);
                if (include) {
                    tmp.add(data.get(i).toString().split("\\|")[0]);
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param ItemName
     * @return
     */
    public static int getItemCost(String ItemName) {
        int cost = 0;
        try {
            String itemNameSplit = ItemName.split(" \\{")[0];
            String itemType = getItemTypreFromItemName(itemNameSplit);
            String itemTypeOneWord = itemType.replaceAll(" ", "");
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + itemTypeOneWord + " WHERE " + itemTypeOneWord + "Name = ?");
            stmt.setString(1, itemNameSplit);
            String[] columns = {"Cost"};
            ObservableList loadedData = BuilderCORE.getData(stmt, columns, null, 0);
            String data = "";
            if (!loadedData.isEmpty()) {
                data = loadedData.get(0).toString();
            }
            if (data.contains("/")) {
                data = data.split("/")[0];
            }
            cost = Integer.parseInt(data) + getImprovementsCosts(ItemName, itemType);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cost;
    }

    /**
     *
     * @param itemWithImprovements
     * @param itemType
     * @return
     */
    public static int getImprovementsCosts(String itemWithImprovements, String itemType) {
        int cost = 0;
        if (itemWithImprovements.contains("{")) {
            String itemImprovements = itemWithImprovements.split(" \\{")[1].split("}")[0];
            String[] improvementsList = itemImprovements.split(", ");
            String[] columns = {"Cost"};
            int howManyImprovements[] = new int[2];//TODO
            for (String improvementsList1 : improvementsList) {
                try {
                    StringBuilder improvementHelper = new StringBuilder();
                    improvementHelper.append(improvementsList1.contains("X") ? improvementsList1.split("X")[0] + "X" : improvementsList1);
                    chooseConnection(UseCases.COREdb);
                    PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE ImprovementName = ? AND (Type = ? OR Type = ? OR Type = ?)");
                    stmt.setString(1, improvementHelper.toString());
                    stmt.setString(2, BuilderCORE.getImprovementTypeBasedOnItemSubtype(getItemSubType(itemType, itemWithImprovements.contains("{") ? itemWithImprovements.split(" \\{")[0] : itemWithImprovements)));
                    stmt.setString(3, "Flintlock");
                    stmt.setString(4, itemType.equals("Weapon") ? "Extreme Weapons" : "Resistance");
                    String stringCost = BuilderCORE.getData(stmt, columns, null, 0).get(0).toString();
                    if (stringCost.contains("/")) {
                        stringCost = stringCost.split("/")[Integer.parseInt(improvementsList1.split("X")[1]) > stringCost.split("/").length ? stringCost.split("/").length - 1 : Integer.parseInt(improvementsList1.split("X")[1]) - 1];
                    }
                    cost += Integer.parseInt(stringCost);
                } catch (SQLException ex) {
                    ErrorController.ErrorController(ex);
                    Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return cost;
    }

    /**
     *
     * @param itemName
     * @param improvementName
     * @param itemType
     * @return
     */
    public static ObservableList getImprovementDetails(String itemName, String improvementName, String itemType) {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        try {
            String[] columns = {"SpecialRules"};
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE ImprovementName = ? AND (Type = ? OR Type = ? OR Type = ?)");
            stmt.setString(1, improvementName);
            stmt.setString(2, BuilderCORE.getImprovementTypeBasedOnItemSubtype(getItemSubType(itemType, itemName)));
            stmt.setString(3, "Flintlock");
            stmt.setString(4, itemType.equals("Weapon") ? "Extreme Weapons" : "Resistance");
            String data = BuilderCORE.getData(stmt, columns, null, 0).get(0).toString();
            String lst[] = data.split(";");
            tmp.addAll(lst);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param type
     * @param itemName
     * @return
     */
    public static String getItemSubType(String type, String itemName) {
        String tmp = "";
        try {
            String mergeType = type.replaceAll(" ", "");
            String[] columns = {"SubType"};
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + mergeType + " WHERE " + mergeType + "Name = ?");
            stmt.setString(1, itemName);
            tmp = BuilderCORE.getData(stmt, columns, null, 0).get(0).toString();
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    /**
     *
     * @param itemName
     * @param type
     * @return
     *
     */
    public static ObservableList getImprovements(String itemName, String type) {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        try {
            String subType = getItemSubType(type, itemName);
            chooseConnection(UseCases.COREdb);
            String[] columns = {"ImprovementName"};
            PreparedStatement stmt = null;
            switch (type) {
                case "Armor":
                    stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Armor' OR Type = 'Resistance'");
                    break;
                case "Weapon":
                    switch (subType) {
                        case "Melee":
                        case "Spear":
                            stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Melee Weapon' OR Type = 'Extreme Weapons'");
                            break;
                        case "Throwing":
                        case "Bow":
                        case "Crossbow":
                        case "Alchemy":
                            stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Ranged Weapon' OR Type = 'Extreme Weapons'");
                            break;
                        case "Flintlock":
                            stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Ranged Weapon' OR Type = 'Flintlock' OR Type = 'Extreme Weapons'");
                            break;
                        default:
                            break;
                    }
                    break;
                case "Companion":
                    stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Exotic Animal Attributes'");
                    break;
                case "Vehicle":
                    stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Chariot and Wagon Upgrades'");
                    break;
                case "Heavy Military Weapon":
                    stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Military Weapon' OR Type = 'Extreme Weapons'");
                    break;
                default:
                    break;
            }
            tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    //change to private and do setters/getters
    /**
     *
     * @param selSpecies
     * @return
     *
     */
    public static ObservableList getSpeciesData(String selSpecies) {
        ObservableList data = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedSpecies WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"LifeDomain", "CharacteristicGroup", "Age", "Skills", "SpeciesModifiers"};
            data = BuilderCORE.getData(stmt, columns, null, 0);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @return
     *
     */
    public static ObservableList getCultureData(String selSpecies, String selCulture) {
        ObservableList data = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedCultures WHERE SpeciesName = ? AND CultureName = ?");
            stmt.setString(1, selSpecies);
            stmt.setString(2, selCulture);
            String[] columns = {"Age","TotalProgressionPoints","LeftProgressionPoints"};
            data = BuilderCORE.getData(stmt, columns, null, 0);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selClass
     * @return
     *
     */
    public static ObservableList getClassData(String selSpecies, String selCulture, String selClass) {
        ObservableList data = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName=? AND CultureName =? AND ClassName =?");
            stmt.setString(1, selSpecies);
            stmt.setString(2, selCulture);
            stmt.setString(3, selClass);
            String[] columns = {"Skills", "Advancements", "Type", "BasedOn", "AdditionalCost"};
            data = BuilderCORE.getData(stmt, columns, null, 0);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selHero
     * @return
     *
     */
    public static ObservableList getHeroData(String selSpecies, String selCulture, String selHero) {
        ObservableList data = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND HeroName =?");
            stmt.setString(1, selSpecies);
            stmt.setString(2, selCulture);
            stmt.setString(3, selHero);
            String[] columns = {"Advancements", "BasedOn", "AdditionalCost"};
            data = BuilderCORE.getData(stmt, columns, null, 0);

        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selProgress
     * @return
     *
     */
    public static ObservableList getProgressData(String selSpecies, String selCulture, String selProgress) {
        ObservableList data = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedProgress WHERE SpeciesName =? AND CultureName =? AND ProgressName =?");
            stmt.setString(1, selSpecies);
            stmt.setString(2, selCulture);
            stmt.setString(3, selProgress);
            String[] columns = {"Progress"};
            data = BuilderCORE.getData(stmt, columns, null, 0);

        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selRoster
     * @return
     *
     */
    public static ObservableList getRosterData(String selSpecies, String selCulture, String selRoster) {
        ObservableList data = FXCollections.observableArrayList();
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE SpeciesName =? AND CultureName =? AND RosterName =?");
            stmt.setString(1, selSpecies);
            stmt.setString(2, selCulture);
            stmt.setString(3, selRoster);
            String[] columns = {"Roster", "MaxPoints"};
            data = BuilderCORE.getData(stmt, columns, null, 0);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     *
     * @param skillsList1
     * @return
     *
     */
    public static int findMaxAge(ListView skillsList1) {
        int out = 0;
        try {
            StringBuilder skillsL = new StringBuilder("");
            for (int i = 0; i < skillsList1.getItems().size(); i++) {
                skillsL.append("\"").append(skillsList1.getItems().get(i).toString().split(" \\(")[0]).append("\"").append(", ");
            }
            String skills = skillsL.substring(0, skillsL.length() - 2);
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.conn.prepareStatement("SELECT max(Age) FROM Skills WHERE SkillName IN (" + skills + ")");
            String[] columns = {"max(Age)"};
            ObservableList<String> tmpget = BuilderCORE.getData(stmt, columns, null, 0);
            out = Integer.parseInt(tmpget.get(0));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param lifedomain
     * @param characteristicGroup
     * @return
     */
    public static String[] getCharacteristics(String lifedomain, String characteristicGroup) {
        String[] outputValues = new String[16];
        try {
            int[] CharacteristicModifiers = {BuilderFXMLController.HOLD_MODIFIERS.getStrengthModifier(), BuilderFXMLController.HOLD_MODIFIERS.getToughnessModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMovementModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMartialModifier(), BuilderFXMLController.HOLD_MODIFIERS.getRangedModifier(), BuilderFXMLController.HOLD_MODIFIERS.getDefenseModifier(), BuilderFXMLController.HOLD_MODIFIERS.getDisciplineModifier(), BuilderFXMLController.HOLD_MODIFIERS.getWillpowerModifier(), BuilderFXMLController.HOLD_MODIFIERS.getCommandModifier(), BuilderFXMLController.HOLD_MODIFIERS.getWoundsModifier(), BuilderFXMLController.HOLD_MODIFIERS.getAttacksModifier(), BuilderFXMLController.HOLD_MODIFIERS.getSizeModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMTModifier(), BuilderFXMLController.HOLD_MODIFIERS.getRTModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMoraleModifier()};
            for (int j = 0; j < BuilderCORE.CHARACTERISTICS.length; j++) {
                if ("Size".equals(BuilderCORE.CHARACTERISTICS[j]) && j < 12) {
                    chooseConnection(UseCases.COREdb);
                    PreparedStatement stmt = BuilderCORE.conn.prepareStatement("SELECT * FROM StartingCharacteristics WHERE LifeDomain = ? AND CharacteristicGroup = ?");
                    stmt.setString(1, lifedomain);
                    stmt.setString(2, characteristicGroup);
                    outputValues[j] = BuilderCORE.SIZES[(Integer.parseInt(BuilderCORE.getValue(stmt, BuilderCORE.CHARACTERISTICS[j])) + CharacteristicModifiers[j])];
                } else if (j < 12) {
                    chooseConnection(UseCases.COREdb);
                    PreparedStatement stmt1 = BuilderCORE.conn.prepareStatement("SELECT * FROM StartingCharacteristics WHERE LifeDomain = ? AND CharacteristicGroup = ?");
                    stmt1.setString(1, lifedomain);
                    stmt1.setString(2, characteristicGroup);
                    outputValues[j] = Integer.toString(Integer.parseInt(BuilderCORE.getValue(stmt1, BuilderCORE.CHARACTERISTICS[j])) + CharacteristicModifiers[j]);
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
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputValues;
    }

    /**
     *
     * @param LifeDomain
     * @return
     */
    public static String getStartingNumberOfSkills(String LifeDomain) {
        String out = "";
        try {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM StartingCharacteristics WHERE LifeDomain = ?");
            stmt.setString(1, LifeDomain);
            out = BuilderCORE.getValue(stmt, "StartingNumberOfSkills");
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param Skill
     * @return
     */
    public static String[] getLdTrees(String Skill) {
        String[] out = new String[2];
        try {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
            stmt.setString(1, Skill);
            out[0] = BuilderCORE.getValue(stmt, "LifeDomainTree1");
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
            stmt1.setString(1, Skill);
            out[1] = BuilderCORE.getValue(stmt1, "LifeDomainTree3");
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param columns2
     * @param lifeDomain
     * @param skillSubSet
     * @param maxAge
     * @return
     */
    public static ObservableList getLifeDomainTree1OnLifeDomainAndLifeDomain2AndAge(String[] columns2, LifedomainValue lifeDomain, String skillSubSet, int maxAge) {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(Enums.Enmuerations.UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE LifeDomain = ? AND LifeDomainTree2 = ? AND Age <= ?");
            stmt.setString(1, lifeDomain.toString());
            stmt.setString(2, skillSubSet);
            stmt.setInt(3, maxAge);
            out = BuilderCORE.getData(stmt, columns2, null, 0);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param columns1
     * @param skillSubSet
     * @param reptiliaSpecific
     * @param IgnoreSkillsList
     * @param maxAge
     * @param i
     * @return
     */
    public static ObservableList getLifeDomainTree3OnLifeDomainTree2OrLifeDomainTree3AndSkillNameAndAgeITERATED(String[] columns1, String skillSubSet, String[] reptiliaSpecific, ObservableList IgnoreSkillsList, int maxAge, int i) {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(Enums.Enmuerations.UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree3 FROM Skills WHERE (LifeDomainTree2 = ? OR LifeDomainTree3 = ?) AND SkillName = ? AND Age <= ?");
            stmt.setString(1, skillSubSet);
            stmt.setString(2, reptiliaSpecific[1]);
            stmt.setString(3, IgnoreSkillsList.get(i).toString().split(" \\(p")[0]);
            stmt.setInt(4, maxAge);
            out = BuilderCORE.getData(stmt, columns1, null, 0);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param columns
     * @param skillSubSet
     * @param basic
     * @param maxAge
     * @param IgnoreSkillsList
     * @return
     */
    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(String[] columns, String skillSubSet, String basic, int maxAge, ObservableList IgnoreSkillsList) {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(Enums.Enmuerations.UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
            stmt.setString(1, skillSubSet);
            stmt.setString(2, basic);
            stmt.setInt(3, maxAge);
            out = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param columns
     * @param skillSubSet
     * @param basic0
     * @param basic1
     * @param maxAge
     * @param IgnoreSkillsList
     * @return
     */
    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3OrLifeDomainTree3AndAge(String[] columns, String skillSubSet, String basic0, String basic1, int maxAge, ObservableList IgnoreSkillsList) {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(Enums.Enmuerations.UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND (LifeDomainTree3 = ? OR LifeDomainTree3 = ?) AND Age <= ?");
            stmt.setString(1, skillSubSet);
            stmt.setString(2, basic0);
            stmt.setString(3, basic1);
            stmt.setInt(4, maxAge);
            out = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param columns
     * @param skillSubSet
     * @param basic2
     * @param basic4
     * @param basic5
     * @param maxAge
     * @param IgnoreSkillsList
     * @param lifeDomainTree3
     * @return
     */
    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3OrLifeDomainTree3OrLifeDomainTree3AndAge(String[] columns, String skillSubSet, String basic2, String basic4, String basic5, int maxAge, ObservableList IgnoreSkillsList, ObservableList<String> lifeDomainTree3) {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(Enums.Enmuerations.UseCases.COREdb);
            PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND (LifeDomainTree3 = ? OR LifeDomainTree3 = ? OR LifeDomainTree3 = ?) AND Age <= ?");
            stmt2.setString(1, skillSubSet);
            stmt2.setString(2, basic2);
            stmt2.setString(3, basic4);
            stmt2.setString(4, basic4);
            stmt2.setInt(5, maxAge);
            if (lifeDomainTree3.contains(basic4)) {
                stmt2.setString(4, basic5);
            }
            out = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param columns
     * @param skillSubSet
     * @param basic3
     * @param basic4
     * @param maxAge
     * @param IgnoreSkillsList
     * @param lifeDomainTree1Value
     * @return
     */
    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAgeV2(String[] columns, String skillSubSet, String basic3, String basic4, int maxAge, ObservableList IgnoreSkillsList, Enums.Enmuerations.LifeDomainTree1Values lifeDomainTree1Value) {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(Enums.Enmuerations.UseCases.COREdb);
            PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
            stmt2.setString(1, skillSubSet);
            stmt2.setInt(3, maxAge);
            switch (lifeDomainTree1Value) {
                case BiestialKingdoms:
                case RegionalTraits:
                    stmt2.setString(2, basic4);
                    break;
                case GeneticMutation:
                case EnvironmentalAdaptability:
                case SpiritualandScientificKnowledge:
                    stmt2.setString(2, basic3);
                    break;
                default:
                    break;
            }
            out = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param columns
     * @param skillSubSet
     * @param basic3
     * @param basic4
     * @param maxAge
     * @param IgnoreSkillsList
     * @param lifeDomainTree1Value
     * @param lifeDomainTree2Value
     * @return
     */
    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAgeV3(String[] columns, String skillSubSet, String basic3, String basic4, int maxAge, ObservableList IgnoreSkillsList, Enums.Enmuerations.LifeDomainTree1Values lifeDomainTree1Value, Enums.Enmuerations.LifeDomainTree2Values lifeDomainTree2Value) {
        ObservableList out = FXCollections.observableArrayList();
        try {
            chooseConnection(Enums.Enmuerations.UseCases.COREdb);
            PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
            stmt2.setString(1, skillSubSet);
            stmt2.setInt(3, maxAge);
            switch (lifeDomainTree1Value) {
                case Arachnea:
                case Crustacea:
                case Insecta:
                case Myriapoda:
                case GeneticMorphology:
                case EnvironmentalAdaptation:
                    stmt2.setString(2, basic4);
                    break;
                case GeneticMutation:
                    stmt2.setString(2, basic3);
                    break;
                default:
                    break;
            }
            switch (lifeDomainTree2Value) {
                case Eusociality:
                case Combat:
                    stmt2.setString(2, basic4);
                    break;
                case EnvironmentalExtremes:
                case AdvancedKnowledge:
                case PsychiccNodes:
                    stmt2.setString(2, basic3);
                    break;
                default:
                    break;
            }
            out = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param Lifedomain
     * @return
     */
    public static String[] getClassTypes(String Lifedomain) {
        String[] out = new String[]{};
        try {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ClassTypes FROM StartingCharacteristics WHERE LifeDomain =?");
            stmt.setString(1, Lifedomain);
            out = BuilderCORE.getValue(stmt, "ClassTypes").split(",");
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    /**
     *
     * @param species
     * @param culture
     * @return
     */
    public static int getNumberOfClases(String species, String culture) {
        int out = 0;
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT COUNT (*) FROM CreatedClasses WHERE SpeciesName=? AND CultureName =?");
            stmt.setString(1, species);
            stmt.setString(2, culture);
            out = Integer.parseInt(BuilderCORE.getValue(stmt, "COUNT (*)"));
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }
}
