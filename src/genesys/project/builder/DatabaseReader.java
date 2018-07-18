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
import javafx.scene.control.ListView;

/**
 *
 * @author krzysztofg
 */
public class DatabaseReader {

    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList loadAllSkillsFromDB() throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills");
        String[] columns = {"SkillName", "PointCost", "SkillRules", "Age", "LifeDomain", "LifeDomainTree1", "LifeDomainTree2", "LifeDomainTree3"};
        return BuilderCORE.getData(stmt, columns, null, 0);
    }

    /**
     *
     * @param SkillName
     * @return
     * @throws SQLException
     */
    public static ObservableList loadSkillFromDB(String SkillName) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
        stmt.setString(1, SkillName);
        String[] columns = {"SkillName", "PointCost", "SkillRules", "Age", "LifeDomain", "LifeDomainTree1", "LifeDomainTree2", "LifeDomainTree3"};
        return BuilderCORE.getData(stmt, columns, null, 0);
    }

    /**
     *
     * @param SkillRuleName
     * @return
     * @throws SQLException
     */
    public static String getSkillRuleExplanation(String SkillRuleName) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM SkillRules WHERE SkillRuleName = ?");
        stmt.setString(1, SkillRuleName);
        return BuilderCORE.getValue(stmt, "SkillRuleExplanation");
    }

    /**
     *
     * @return @throws SQLException
     */
    public static String classCanTake() throws SQLException {
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
        return fintex.toString();
    }

        /**
     *
     * @return @throws SQLException
     */
    public static ObservableList<String> getSpeciesList() throws SQLException {
        ObservableList<String> tmp = FXCollections.observableArrayList();
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
        return tmp;
    }
    
    /**
     *
     * @return @throws SQLException
     */
    public static ObservableList populateDropdownsSpecies() throws SQLException {
        ObservableList tmp = FXCollections.observableArrayList();
        tmp.setAll(DatabaseHolder.TOPDROP);
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SpeciesName FROM CreatedSpecies");
        String[] columns = {"SpeciesName"};
        tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        return tmp;
    }

    /**
     *
     * @param fromwhat
     * @return
     * @throws SQLException
     */
    public static ObservableList populateDropdownsCultures(String fromwhat) throws SQLException {
        ObservableList tmp = FXCollections.observableArrayList();
        tmp.setAll(DatabaseHolder.TOPDROP);
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedCultures WHERE SpeciesName = ?");
        stmt.setString(1, fromwhat);
        String[] columns = {"CultureName"};
        tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        return tmp;
    }

    /**
     *
     * @param fromwhats
     * @param fromwhatc
     * @return
     * @throws SQLException
     */
    public static ObservableList populateDropdownsClasses(String fromwhats, String fromwhatc) throws SQLException {
        ObservableList tmp = FXCollections.observableArrayList();
        tmp.setAll(DatabaseHolder.TOPDROP);
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName =? AND CultureName =?");
        stmt.setString(1, fromwhats);
        stmt.setString(2, fromwhatc);
        String[] columns = {"ClassName"};
        tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        return tmp;
    }

    /**
     *
     * @param fromwhats
     * @param fromwhatc
     * @param fromwhatcl
     * @return
     * @throws SQLException
     */
    public static ObservableList populateDropdownsHeroes(String fromwhats, String fromwhatc, String fromwhatcl) throws SQLException {
        ObservableList tmp = FXCollections.observableArrayList();
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
        return tmp;
    }

    /**
     *
     * @param fromwhats
     * @param fromwhatc
     * @return
     * @throws SQLException
     */
    public static ObservableList populateDropdownsProgress(String fromwhats, String fromwhatc) throws SQLException {
        ObservableList tmp = FXCollections.observableArrayList();
        tmp.setAll(DatabaseHolder.TOPDROP);
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedProgress WHERE (SpeciesName =?) AND (CultureName =?)");
        stmt.setString(1, fromwhats);
        stmt.setString(2, fromwhatc);
        String[] columns = {"ProgressName"};
        tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        return tmp;
    }

    /**
     *
     * @param fromwhats
     * @param fromwhatc
     * @return
     * @throws SQLException
     */
    public static ObservableList populateDropdownsRosters(String fromwhats, String fromwhatc) throws SQLException {
        ObservableList tmp = FXCollections.observableArrayList();
        tmp.setAll(DatabaseHolder.TOPDROP);
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE (SpeciesName =?) AND (CultureName =?)");
        stmt.setString(1, fromwhats);
        stmt.setString(2, fromwhatc);
        String[] columns = {"RosterName"};
        tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        return tmp;
    }

    /**
     *
     * @return @throws SQLException
     */
    public static ObservableList<String> getSkillSet() throws SQLException {
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
            ObservableList<String> outList = FXCollections.observableArrayList();
            outList.addAll(lst);
            return outList;
        }
        return null;
    }

    /**
     *
     * @param SkillBranch
     * @param onlyPrimaryChooser
     * @param onlySecondaryChooser
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getSubSkillSet(String SkillBranch, String onlyPrimaryChooser, String onlySecondaryChooser) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree2 FROM Skills WHERE LifeDomain = ? AND LifeDomainTree1 = ?");
        stmt.setString(1, DatabaseHolder.holdSpecies.getLifedomain().name());
        stmt.setString(2, SkillBranch);
        String[] columns = {"LifeDomainTree2"};
        ObservableList<String> tmp = BuilderCORE.getData(stmt, columns, null, 0);
        if (onlyPrimaryChooser != null && !onlyPrimaryChooser.equals("") && !onlyPrimaryChooser.equals("standard") && tmp.contains(onlyPrimaryChooser)) {
            return FXCollections.observableArrayList(onlyPrimaryChooser);
        }
        if (onlySecondaryChooser != null && !onlySecondaryChooser.equals("") && !onlySecondaryChooser.equals("standard") && tmp.contains(onlySecondaryChooser)) {
            return FXCollections.observableArrayList(onlySecondaryChooser);
        }
        return tmp;
    }

    /**
     *
     * @param itemType
     * @return
     * @throws SQLException
     */
    public static ObservableList getItemsNames(String itemType) throws SQLException {
        String itemTypeOneWord = itemType.replaceAll(" ", "");
        ObservableList<String> tmp = FXCollections.observableArrayList();
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + itemTypeOneWord + " WHERE Type = ? OR Type = ?");
        stmt.setString(1, "Primitive");
        stmt.setString(2, "Common");
        String[] columns = {itemTypeOneWord + "Name"};
        tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        tmp.addAll(getAvailableSpecialItemNames(itemType));
        return tmp;
    }

    public static ObservableList getAvailableSpecialItemNames(String itemType) throws SQLException {
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
        ObservableList tmp = FXCollections.observableArrayList();
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
        return tmp;
    }

    /**
     *
     * @param item
     * @param itemType
     * @return
     * @throws SQLException
     */
    public static int getItemCost(String item, String itemType) throws SQLException {
        String itemName;
        itemName = item.contains("{") ? item.split(" \\{")[0] : item;
        String itemTypeOneWord = itemType.replaceAll(" ", "");
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + itemTypeOneWord + " WHERE " + itemTypeOneWord + "Name = ?");
        stmt.setString(1, itemName);
        String[] columns = {"Cost"};
        String data = BuilderCORE.getData(stmt, columns, null, 0).get(0).toString();
        if (data.contains("/")) {
            data = data.split("/")[0];
        }
        int cost = Integer.parseInt(data) + getImprovementsCosts(item, itemType);
        return cost;
    }

    /**
     *
     * @param itemWithImprovements
     * @param itemType
     * @return
     * @throws SQLException
     */
    public static int getImprovementsCosts(String itemWithImprovements, String itemType) throws SQLException {
        int cost = 0;
        if (itemWithImprovements.contains("{")) {
            String itemImprovements = itemWithImprovements.split(" \\{")[1].split("}")[0];
            String[] improvementsList = itemImprovements.split(", ").length > 1 ? itemImprovements.split(", ") : new String[]{itemImprovements};
            String[] columns = {"Cost"};
            int howManyImprovements[] = new int[2];//TODO
            for (String improvementsList1 : improvementsList) {
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
     * @throws java.sql.SQLException
     */
    public static ObservableList getImprovementDetails(String itemName, String improvementName, String itemType) throws SQLException {
        String[] columns = {"SpecialRules"};
        ObservableList<String> tmp = FXCollections.observableArrayList();
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE ImprovementName = ? AND (Type = ? OR Type = ? OR Type = ?)");
        stmt.setString(1, improvementName);
        stmt.setString(2, BuilderCORE.getImprovementTypeBasedOnItemSubtype(getItemSubType(itemType, itemName)));
        stmt.setString(3, "Flintlock");
        stmt.setString(4, itemType.equals("Weapon") ? "Extreme Weapons" : "Resistance");
        String data = BuilderCORE.getData(stmt, columns, null, 0).get(0).toString();
        String lst[] = data.split(";").length > 1 ? data.split(";") : new String[]{data};
        tmp.addAll(lst);
        return tmp;
    }

    /**
     *
     * @param type
     * @param itemName
     * @return
     * @throws SQLException
     */
    public static String getItemSubType(String type, String itemName) throws SQLException {
        String mergeType = type.replaceAll(" ", "");
        String[] columns = {"SubType"};
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt;
        stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + mergeType + " WHERE " + mergeType + "Name = ?");
        stmt.setString(1, itemName);
        String tmp = BuilderCORE.getData(stmt, columns, null, 0).get(0).toString();
        return tmp;
    }

    /**
     *
     * @param itemName
     * @param type
     * @return
     * @throws SQLException
     */
    public static ObservableList getImprovements(String itemName, String type) throws SQLException {
        ObservableList<String> tmp = FXCollections.observableArrayList();
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
        return tmp;
    }

    //change to private and do setters/getters
    /**
     *
     * @param selSpecies
     * @return
     * @throws SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    public static ObservableList getSpeciesData(String selSpecies) throws SQLException, CloneNotSupportedException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedSpecies WHERE SpeciesName =?");
        stmt.setString(1, selSpecies);
        String[] columns = {"LifeDomain", "CharacteristicGroup", "Age", "Skills", "SpeciesModifiers"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @return
     * @throws SQLException
     */
    public static ObservableList getCultureData(String selSpecies, String selCulture) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedCultures WHERE SpeciesName = ? AND CultureName = ?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        String[] columns = {"Age"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selClass
     * @return
     * @throws SQLException
     */
    public static ObservableList getClassData(String selSpecies, String selCulture, String selClass) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName=? AND CultureName =? AND ClassName =?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        stmt.setString(3, selClass);
        String[] columns = {"Skills", "Advancements", "Type", "BasedOn", "AdditionalCost"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selHero
     * @return
     * @throws SQLException
     */
    public static ObservableList getHeroData(String selSpecies, String selCulture, String selHero) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND HeroName =?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        stmt.setString(3, selHero);
        String[] columns = {"Advancements", "BasedOn", "AdditionalCost"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selProgress
     * @return
     * @throws SQLException
     */
    public static ObservableList getProgressData(String selSpecies, String selCulture, String selProgress) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedProgress WHERE SpeciesName =? AND CultureName =? AND ProgressName =?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        stmt.setString(3, selProgress);
        String[] columns = {"Progress"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        return data;
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selRoster
     * @return
     * @throws SQLException
     */
    public static ObservableList getRosterData(String selSpecies, String selCulture, String selRoster) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE SpeciesName =? AND CultureName =? AND RosterName =?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        stmt.setString(3, selRoster);
        String[] columns = {"Roster"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        return data;
    }


    /**
     *
     * @param skillsList1
     * @return
     * @throws SQLException
     */
    public static int findMaxAge(ListView skillsList1) throws SQLException {
        StringBuilder skillsL = new StringBuilder("");
        for (int i = 0; i < skillsList1.getItems().size(); i++) {
            skillsL.append("\"").append(skillsList1.getItems().get(i).toString().split(" \\(")[0]).append("\"").append(", ");
        }
        String skills = skillsL.substring(0, skillsL.length() - 2);
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.conn.prepareStatement("SELECT max(Age) FROM Skills WHERE SkillName IN (" + skills + ")");
        String[] columns = {"max(Age)"};
        ObservableList<String> tmpget = BuilderCORE.getData(stmt, columns, null, 0);
        return Integer.parseInt(tmpget.get(0));
    }

    /**
     *
     * @param lifedomain
     * @param characteristicGroup
     * @return
     * @throws java.sql.SQLException
     */
    public static String[] getCharacteristics(String lifedomain, String characteristicGroup) throws SQLException {
        String[] outputValues = new String[16];
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
        return outputValues;
    }

    public static String getStartingNumberOfSkills(String LifeDomain) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM StartingCharacteristics WHERE LifeDomain = ?");
        stmt.setString(1, LifeDomain);
        return BuilderCORE.getValue(stmt, "StartingNumberOfSkills");
    }

    public static String[] getLdTrees(String Skill) throws SQLException {
        String[] out = new String[2];
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
        stmt.setString(1, Skill);
        out[0] = BuilderCORE.getValue(stmt, "LifeDomainTree1");
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
        stmt1.setString(1, Skill);
        out[1] = BuilderCORE.getValue(stmt1, "LifeDomainTree3");
        return out;
    }

    public static ObservableList getLifeDomainTree1OnLifeDomainAndLifeDomain2AndAge(String[] columns2, LifedomainValue lifeDomain, String skillSubSet, int maxAge) throws SQLException {
        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE LifeDomain = ? AND LifeDomainTree2 = ? AND Age <= ?");
        stmt.setString(1, lifeDomain.toString());
        stmt.setString(2, skillSubSet);
        stmt.setInt(3, maxAge);
        return BuilderCORE.getData(stmt, columns2, null, 0);
    }

    public static ObservableList getLifeDomainTree3OnLifeDomainTree2OrLifeDomainTree3AndSkillNameAndAgeITERATED(String[] columns1, String skillSubSet, String[] reptiliaSpecific, ObservableList IgnoreSkillsList, int maxAge, int i) throws SQLException {
        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree3 FROM Skills WHERE (LifeDomainTree2 = ? OR LifeDomainTree3 = ?) AND SkillName = ? AND Age <= ?");
        stmt.setString(1, skillSubSet);
        stmt.setString(2, reptiliaSpecific[1]);
        stmt.setString(3, IgnoreSkillsList.get(i).toString().split(" \\(p")[0]);
        stmt.setInt(4, maxAge);
        return BuilderCORE.getData(stmt, columns1, null, 0);
    }

    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(String[] columns, String skillSubSet, String basic, int maxAge, ObservableList IgnoreSkillsList) throws SQLException {
        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
        stmt.setString(1, skillSubSet);
        stmt.setString(2, basic);
        stmt.setInt(3, maxAge);
        return BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
    }

    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3OrLifeDomainTree3AndAge(String[] columns, String skillSubSet, String basic0, String basic1, int maxAge, ObservableList IgnoreSkillsList) throws SQLException {
        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND (LifeDomainTree3 = ? OR LifeDomainTree3 = ?) AND Age <= ?");
        stmt.setString(1, skillSubSet);
        stmt.setString(2, basic0);
        stmt.setString(3, basic1);
        stmt.setInt(4, maxAge);
        return BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
    }

    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3OrLifeDomainTree3OrLifeDomainTree3AndAge(String[] columns, String skillSubSet, String basic2, String basic4, String basic5, int maxAge, ObservableList IgnoreSkillsList, ObservableList<String> lifeDomainTree3) throws SQLException {
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
        return BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
    }

    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAgeV2(String[] columns, String skillSubSet, String basic3, String basic4, int maxAge, ObservableList IgnoreSkillsList, Enums.Enmuerations.LifeDomainTree1Values lifeDomainTree1Value) throws SQLException {
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
        return BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
    }

    public static ObservableList getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAgeV3(String[] columns, String skillSubSet, String basic3, String basic4, int maxAge, ObservableList IgnoreSkillsList, Enums.Enmuerations.LifeDomainTree1Values lifeDomainTree1Value, Enums.Enmuerations.LifeDomainTree2Values lifeDomainTree2Value) throws SQLException {
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
        return BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
    }

    public static String[] getClassTypes(String Lifedomain) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ClassTypes FROM StartingCharacteristics WHERE LifeDomain =?");
        stmt.setString(1, Lifedomain);
        return BuilderCORE.getValue(stmt, "ClassTypes").split(",");
    }

    public static int getNumberOfClases(String species, String culture) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT COUNT (*) FROM CreatedClasses WHERE SpeciesName=? AND CultureName =?");
        stmt.setString(1, species);
        stmt.setString(2, culture);
        return Integer.parseInt(BuilderCORE.getValue(stmt, "COUNT (*)"));
    }
}
