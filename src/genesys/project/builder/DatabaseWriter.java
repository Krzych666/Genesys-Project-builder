/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import genesys.project.builder.Enums.Enmuerations.UseCases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import genesys.project.fxml.ErrorController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;

/**
 *
 * @author krzysztofg
 */
public class DatabaseWriter {

    /**
     * tempDeletescript
     */
    public static String tempDeletescript = null;

    public static final String WHERESTRING1 = " WHERE (";
    public static final String WHERESTRING2 = " WHERE ";
    public static final String ANDSTRING1 = "') AND (";
    public static final String ANDSTRING2 = "' AND ";
    public static final String ANDSTRING3 = "') AND ";
    public static final String ORSTRING1 = "') OR ";
    public static final String[] DELETEFROMSTRING = {
        "DELETE FROM CreatedSpecies",
        "DELETE FROM CreatedCultures",
        "DELETE FROM CreatedClasses",
        "DELETE FROM CreatedHeroes",
        "DELETE FROM CreatedProgress",
        "DELETE FROM CreatedRosters",
        "DELETE FROM BattleHistory"};
    public static final String[] NAMESTRING = {
        "SpeciesName = '",
        "CultureName = '",
        "ClassName = '",
        "HeroName = '",
        "ProgressName = '",
        "RosterName = '",
        "(UserSpecies = '",
        "UserCulture = '",
        "(OponentSpecies = '",
        "OponentCulture = '",
        "BattleName = '"};

    /**
     *
     * @param sql
     */
    public static void executeSQL(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = BuilderCORE.DBUserDataConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            ErrorController.ErrorControllerMethod(e);
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    ErrorController.ErrorControllerMethod(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    ErrorController.ErrorControllerMethod(e);
                }
            }
        }
    }

    /**
     * writeToDB
     */
    public static void writeToDB() {
        //if(true){fullSkillList1=DatabaseHolder.holdSpecies.Skills;}        
        DatabaseHolder.holdSpecies.setSkills(DatabaseHolder.holdSpecies.getSkills().substring(0, DatabaseHolder.holdSpecies.getSkills().length() - 1));
        String SpecialModificators = "";
        switch (DatabaseHolder.holdSpecies.getLifedomain()) {
            case Humanoid:
                SpecialModificators = DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
            case Fey:
                SpecialModificators = "MainDomain=" + ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).getMainDomain().getText() + ","
                        + DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
            case Reptilia:
                SpecialModificators = "MainLineage=" + ((DatabaseHolder.AReptilia) DatabaseHolder.holdSpecies).getMainLineage().getText() + ","
                        + DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
            case Biest:
                SpecialModificators = "MainKingdom=" + ((DatabaseHolder.ABiest) DatabaseHolder.holdSpecies).getMainKingdom().getText() + ","
                        + "MainRegion=" + ((DatabaseHolder.ABiest) DatabaseHolder.holdSpecies).getMainRegion().getText() + ","
                        + DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
            case Insecta:
                SpecialModificators = "MainClasification=" + ((DatabaseHolder.AInsecta) DatabaseHolder.holdSpecies).getMainClasification().getText() + ","
                        + "MainOrder=" + ((DatabaseHolder.AInsecta) DatabaseHolder.holdSpecies).getMainOrder().getText() + ","
                        + DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
        }
        if (DatabaseHolder.holdCulture.getCultureName().equals(DatabaseHolder.holdSpecies.getSpeciesName())) {
            executeSQL("INSERT INTO `CreatedSpecies`"
                    + "(LifeDomain,CharacteristicGroup,SpeciesName,Skills,SpeciesModifiers) "
                    + "VALUES ('"
                    + DatabaseHolder.holdSpecies.getLifedomain().toString() + "','"
                    + DatabaseHolder.holdSpecies.getCharacteristicGroup().toString() + "','"
                    + DatabaseHolder.holdSpecies.getSpeciesName() + "','"
                    + DatabaseHolder.holdSpecies.getSkills() + "','"
                    + SpecialModificators + "');");
        }
        executeSQL("INSERT INTO `CreatedCultures`"
                + "(CultureName,ParentSpeciesID) "
                + "VALUES ('"
                + DatabaseHolder.holdCulture.getCultureName() + "','"
                + DatabaseHolder.holdCulture.getParentSpeciesID() + "');");
        for (DatabaseHolder.AClass holdClas : DatabaseHolder.holdClass) {
            if (holdClas.getClassName() != null && !holdClas.getClassName().equals("")) {
                if (holdClas.getSkills().length() > 2) {
                    holdClas.setSkills(holdClas.getSkills().substring(0, holdClas.getSkills().length() - 1));
                }
                executeSQL("INSERT INTO `CreatedClasses`"
                        + "(ClassName,Skills,ParentCultureId,Advancements,Type,BasedOn,AdditionalCost)"
                        + " VALUES ('"
                        + holdClas.getClassName() + "','"
                        + holdClas.getSkills() + "','"
                        + holdClas.getParentCultureId() + "',"
                        + null + ",'"
                        + holdClas.getType() + "','"
                        + holdClas.getBasedOn() + "','"
                        + holdClas.getAdditionalCost() + "');");
            }
        }
        DatabaseHolder.holdSpecies = null;
    }

    /**
     * writeRosterToDB
     */
    public static void writeRosterToDB() {
        executeSQL("INSERT INTO `CreatedRosters`"
                + "(ParentCultureId,RosterName,Roster,MaxPoints) "
                + "VALUES ('"
                + DatabaseHolder.holdRoster.getParentCultureId() + "','"
                + DatabaseHolder.holdRoster.getRosterName() + "','"
                + DatabaseHolder.holdRoster.getRoster() + "','"
                + DatabaseHolder.holdRoster.getMaxPoints() + "');");
    }

    /**
     * writeBattleToDB
     */
    public static void writeBattleToDB() {
        executeSQL("INSERT INTO `BattleHistory`"
                + "(BattleName,UserSpecies,UserCulture,UserRoster,OponentSpecies,OponentCulture,OponentRoster,Points,Outcome,Date,ReplayID,CustomData) "
                + "VALUES ('"
                + DatabaseHolder.holdBattle.getBattleName() + "','"
                + DatabaseHolder.holdBattle.getUserSpeciesID() + "','"
                + DatabaseHolder.holdBattle.getUserCultureID() + "','"
                + DatabaseHolder.holdBattle.getUserRosterID() + "','"
                + DatabaseHolder.holdBattle.getOponentSpeciesID() + "','"
                + DatabaseHolder.holdBattle.getOponentCultureID() + "','"
                + DatabaseHolder.holdBattle.getOponentRosterID() + "','"
                + DatabaseHolder.holdBattle.getPoints() + "','"
                + DatabaseHolder.holdBattle.getOutcome() + "','"
                + DatabaseHolder.holdBattle.getDate() + "','"
                + DatabaseHolder.holdBattle.getReplayID() + "','"
                + DatabaseHolder.holdBattle.getCustomData() + "');");
    }

    /**
     * commenceDeleting
     */
    public static void commenceDeleting() {
        executeSQL(tempDeletescript);
        tempDeletescript = null;
    }

// privatize
    /**
     *
     * @param speciesID
     * @param newSpecies
     */
    public static void modifySpeciesName(int speciesID, String newSpecies) {
        executeSQL("UPDATE CreatedSpecies SET SpeciesName='" + newSpecies + "' WHERE CreatedSpeciesID='" + speciesID + "'");
    }

    /**
     * modifySpecies
     *
     * @param speciesID
     */
    public static void modifySpecies(int speciesID) {
        executeSQL("UPDATE CreatedSpecies "
                + "SET "
                + "Lifedomain='" + DatabaseHolder.holdSpecies.getLifedomain()
                + "', CharacteristicGroup='" + DatabaseHolder.holdSpecies.getCharacteristicGroup()
                + "', SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName()
                + "', Age='" + DatabaseHolder.holdSpecies.getAge()
                + "', Skills='" + DatabaseHolder.holdSpecies.getSkills()
                + "', SpeciesModifiers='" + DatabaseHolder.holdSpecies.getSpeciesModifiers()
                + "' WHERE "
                + "CreatedSpeciesID='" + speciesID + "'");
        checkClassSkillsSpecies();
    }

    private static void checkClassSkillsSpecies() {
        try {
            StringBuilder newFoundSkills = new StringBuilder();
            for (String split1 : DatabaseHolder.holdSpecies.getSkills().split(",")) {
                boolean present = false;
                for (String split : DatabaseHolder.modifiedHoldSpecies.getSkills().split(",")) {
                    if (split.equals(split1)) {
                        present = true;
                    }
                }
                if (!present) {
                    newFoundSkills.append(split1).append(",");
                }
            }
            String deleteSkills = newFoundSkills.toString();
            if (deleteSkills.length() >= 2) {
                deleteSkills = deleteSkills.substring(0, deleteSkills.length() - 1);
            }
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT CultureName FROM CreatedClasses WHERE SpeciesName=?");
            stmt.setString(1, DatabaseHolder.holdSpecies.getSpeciesName());
            String[] columns = {"CultureName"};
            ObservableList tmpCult = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < tmpCult.size(); i++) {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT ClassName FROM CreatedClasses WHERE SpeciesName=? AND CultureName=?");
                stmt1.setString(1, DatabaseHolder.holdSpecies.getSpeciesName());
                stmt1.setString(2, tmpCult.get(i).toString());
                String[] columns1 = {"ClassName"};
                ObservableList tmpClass = BuilderCORE.getData(stmt1, columns1, null, 0);
                for (int ii = 0; ii < tmpClass.size(); ii++) {
                    chooseConnection(UseCases.Userdb);
                    PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName=? AND CultureName=? AND ClassName=?");
                    stmt2.setString(1, DatabaseHolder.holdSpecies.getSpeciesName());
                    stmt2.setString(2, tmpCult.get(i).toString());
                    stmt2.setString(3, tmpClass.get(ii).toString());
                    String tmpSkill = BuilderCORE.getValue(stmt2, "Skills");
                    String tmpSkillRep = DatabaseHolder.skillsSeparatorRepalcer(tmpSkill);
                    for (String split : DatabaseHolder.skillsSeparatorRepalcer(deleteSkills).split(",")) {
                        tmpSkillRep = tmpSkillRep.replace(split, "");
                    }
                    tmpSkillRep = DatabaseHolder.skillsSeparatorRepalcer(tmpSkillRep);
                    if (!tmpSkill.equals(tmpSkillRep)) {
                        executeSQL("UPDATE CreatedClasses "
                                + "SET "
                                + "Skills='" + tmpSkillRep
                                + "' WHERE "
                                + "SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName()
                                + "' AND "
                                + "CultureName='" + tmpCult.get(i).toString()
                                + "' AND "
                                + "ClassName='" + tmpClass.get(ii).toString() + "'");
                    }
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param cultureID
     * @param NewCultureName
     */
    public static void modifyCultureName(int cultureID, String NewCultureName) {
        executeSQL("UPDATE CreatedCultures "
                + "SET "
                + "CultureName='" + NewCultureName
                + "' WHERE "
                + "CreatedCulturesID='" + cultureID + "'");
    }

    /**
     * modifyCulture
     *
     * @param cultureID
     */
    public static void modifyCulture(int cultureID) {
        executeSQL("UPDATE CreatedCultures "
                + "SET "
                + "Age='" + DatabaseHolder.holdCulture.getAge()
                + "', TotalProgressionPoints='" + DatabaseHolder.holdCulture.getTotalProgressionPoints()
                + "', LeftProgressionPoints='" + DatabaseHolder.holdCulture.getLeftProgressionPoints()
                + "' WHERE "
                + "CreatedCulturesID='" + cultureID + "'");
    }

    /**
     *
     * @param classID
     * @param newClassName
     * @param a
     */
    public static void modifyClassName(int classID, String newClassName, int a) {
        executeSQL("UPDATE CreatedClasses "
                + "SET "
                + "ClassName='" + newClassName
                + "' WHERE "
                + "CreatedClassesID='" + classID + "'");
        rosterUpdater(
                DatabaseHolder.holdClass[a].getClassName(),
                newClassName,
                DatabaseHolder.holdClass[a].getParentCultureId());
    }

    /**
     *
     * @param classID
     * @param a
     */
    public static void modifyClass(int classID, int a) {
        if (a < DatabaseHolder.modifiedHoldClass.length - 1) {
            if (DatabaseHolder.holdClass[a].getSkills().endsWith(",")) {
                DatabaseHolder.holdClass[a].setSkills(DatabaseHolder.holdClass[a].getSkills().substring(0, DatabaseHolder.holdClass[a].getSkills().length() - 1));
            }
            executeSQL("UPDATE CreatedClasses "
                    + "SET "
                    + "ClassName='" + DatabaseHolder.holdClass[a].getClassName()
                    + "', Skills='" + DatabaseHolder.holdClass[a].getSkills()
                    + "', ParentCultureId='" + DatabaseHolder.holdClass[a].getParentCultureId()
                    + "', Advancements='" + DatabaseHolder.holdClass[a].getAdvancements()
                    + "', Type='" + DatabaseHolder.holdClass[a].getType()
                    + "', BasedOn='" + DatabaseHolder.holdClass[a].getBasedOn()
                    + "', AdditionalCost='" + DatabaseHolder.holdClass[a].getAdditionalCost()
                    + "' WHERE "
                    + "CreatedClassesID='" + classID + "'");

            checkClassSkillsBasedon(classID, a);
        } else {
            executeSQL("INSERT INTO `CreatedClasses`"
                    + "(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) "
                    + "VALUES ('"
                    + DatabaseHolder.holdClass[a].getClassName() + "','"
                    + DatabaseHolder.holdClass[a].getSkills() + "','"
                    + DatabaseHolder.holdSpecies.getSpeciesName() + "','"
                    + DatabaseHolder.holdCulture.getCultureName() + "',"
                    + null + ",'"
                    + DatabaseHolder.holdClass[a].getType() + "','"
                    + DatabaseHolder.holdClass[a].getBasedOn() + "','"
                    + DatabaseHolder.holdClass[a].getAdditionalCost() + "');");
        }
        rosterUpdater(
                DatabaseHolder.modifiedHoldClass[a].getClassName(),
                DatabaseHolder.holdClass[a].getClassName(),
                DatabaseHolder.modifiedHoldClass[a].getParentCultureId());
    }

    private static void checkClassSkillsBasedon(int classID, int a) {
        try {
            StringBuilder newFoundSkills = new StringBuilder();
            for (String split1 : DatabaseHolder.holdClass[a].getSkills().split(",")) {
                boolean present = false;
                for (String split : DatabaseHolder.modifiedHoldClass[a].getSkills().split(",")) {
                    if (split.equals(split1)) {
                        present = true;
                    }
                }
                if (!present) {
                    newFoundSkills.append(split1).append(",");
                }
            }
            String deleteSkills = newFoundSkills.toString();
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE BasedOn=?");
            stmt1.setInt(1, classID);
            String[] columns1 = {"CreatedClassesID"};
            ObservableList tmpBased = BuilderCORE.getData(stmt1, columns1, null, 0);
            for (int i = 0; i < tmpBased.size(); i++) {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE CreatedClassesID=?");
                stmt2.setInt(1, Integer.parseInt(tmpBased.get(i).toString()));
                String tmpSkill = BuilderCORE.getValue(stmt2, "Skills");
                String tmpSkillRep = DatabaseHolder.skillsSeparatorRepalcer(tmpSkill);
                for (String split : DatabaseHolder.skillsSeparatorRepalcer(deleteSkills).split(",")) {
                    tmpSkillRep = tmpSkillRep.replace(split, "");
                }
                tmpSkillRep = DatabaseHolder.skillsSeparatorRepalcer(tmpSkillRep);
                if (!tmpSkill.equals(tmpSkillRep)) {
                    executeSQL("UPDATE CreatedClasses "
                            + "SET "
                            + "Skills='" + tmpSkillRep
                            + "' WHERE "
                            + "CreatedClassesID='" + tmpBased.get(i).toString() + "'");
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param heroID
     * @param newHeroName
     */
    public static void modifyHeroName(int heroID, String newHeroName) {
        executeSQL("UPDATE CreatedHeroes "
                + "SET "
                + "HeroName='" + newHeroName
                + "' WHERE "
                + "CreatedHeroesID='" + heroID + "'");
        rosterUpdater(
                DatabaseHolder.holdHero.getHeroName(),
                newHeroName,
                DatabaseHolder.holdHero.getParentCultureId());
    }

    /**
     * modifyHero
     *
     * @param heroID
     */
    public static void modifyHero(int heroID) {
        executeSQL("UPDATE CreatedHeroes "
                + "SET "
                + "HeroName='" + DatabaseHolder.holdHero.getHeroName()
                + "', ParentCultureId='" + DatabaseHolder.holdHero.getParentCultureId()
                + "', Advancements='" + DatabaseHolder.holdHero.getAdvancements()
                + "', BasedOn='" + DatabaseHolder.holdHero.getBasedOn()
                + "', AdditionalCost='" + DatabaseHolder.holdHero.getAdditionalCost()
                + "', WHERE "
                + "CreatedHeroesID='" + heroID + "'");
        rosterUpdater(
                DatabaseHolder.modifiedHoldHero.getHeroName(),
                DatabaseHolder.holdHero.getHeroName(),
                DatabaseHolder.modifiedHoldHero.getParentCultureId());
    }

    /**
     *
     * @param progressID
     * @param newProgress
     */
    public static void modifyProgressName(int progressID, String newProgress) {
        executeSQL("UPDATE CreatedProgress "
                + "SET "
                + "ProgressName='" + newProgress
                + "' WHERE "
                + "CreatedProgressID='" + progressID + "'");
    }

    /**
     * modifyProgress
     *
     * @param progressID
     */
    public static void modifyProgress(int progressID) {
        executeSQL("UPDATE CreatedProgress "
                + "SET "
                + "ProgressName='" + DatabaseHolder.holdProgress.getProgressName()
                + "', ParentCultureId='" + DatabaseHolder.holdProgress.getParentCultureId()
                + "', Progress='" + DatabaseHolder.holdProgress.getProgress()
                + "', Date='" + DatabaseHolder.holdProgress.getDate()
                + "' WHERE "
                + "CreatedProgressD='" + progressID + "'");
    }

    /**
     *
     * @param rosterID
     * @param newRoster
     */
    public static void modifyRosterName(int rosterID, String newRoster) {
        executeSQL("UPDATE CreatedRosters "
                + "SET "
                + "RosterName='" + newRoster
                + "' WHERE "
                + "CreatedRostersID='" + rosterID + "'");
    }

    /**
     * modifyRoster
     *
     * @param rosterID
     */
    public static void modifyRoster(int rosterID) {
        executeSQL("UPDATE CreatedRosters "
                + "SET "
                + "RosterName='" + DatabaseHolder.holdRoster.getRosterName()
                + "', CultureName='" + DatabaseHolder.holdRoster.getParentCultureId()
                + "', Roster='" + DatabaseHolder.holdRoster.getRoster()
                + "', MaxPoints='" + DatabaseHolder.holdRoster.getMaxPoints()
                + "' WHERE "
                + "CreatedRostersID='" + rosterID + "'");
    }

    /**
     *
     * @param oldUnitName
     * @param newUnitName
     * @param CultureID
     */
    public static void rosterUpdater(String oldUnitName, String newUnitName, int CultureID) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE ParentCultureId=?");
            stmt.setInt(1, CultureID);
            String[] columns = {"CreatedRostersID", "Roster"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                Boolean modified = false;
                StringBuilder correctedRoster = new StringBuilder();
                ObservableList rosterEntrys = FXCollections.observableArrayList();
                rosterEntrys.addAll(
                        data.get(i).toString().split("\\|")[1].contains(";")
                        ? data.get(i).toString().split("\\|")[1].split(";")
                        : data.get(i).toString().split("\\|")[1]);
                for (int j = 0; j < rosterEntrys.size(); j++) {
                    if (rosterEntrys.get(j).toString().split(" x")[0].equals(oldUnitName)) {
                        modified = true;
                        correctedRoster.append(rosterEntrys.get(j).toString().replaceFirst(oldUnitName, newUnitName));
                    } else {
                        correctedRoster.append(rosterEntrys.get(j).toString());
                    }
                    if (j < rosterEntrys.size() - 1) {
                        correctedRoster.append(";");
                    }
                }
                if (modified) {
                    executeSQL("UPDATE CreatedRosters "
                            + "SET "
                            + "Roster='" + correctedRoster.toString()
                            + "' WHERE "
                            + "CreatedRostersID = " + data.get(i).toString().split("\\|")[0] + "");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param battleID
     * @param newBattle
     */
    public static void modifyBattleName(int battleID, String newBattle) {
        executeSQL("UPDATE BattleHistory "
                + "SET "
                + "BattleName='" + newBattle
                + "' WHERE "
                + "BattleHistoryID = '" + battleID + "'");
    }

    /**
     * modifyRoster
     *
     * @param battleID
     */
    public static void modifyBattle(int battleID) {
        executeSQL("UPDATE BattleHistory "
                + "SET "
                + "BattleName='" + DatabaseHolder.holdBattle.getBattleName()
                + "', UserSpeciesID ='" + DatabaseHolder.holdBattle.getUserSpeciesID()
                + "', UserCultureID='" + DatabaseHolder.holdBattle.getUserCultureID()
                + "', UserRosterID='" + DatabaseHolder.holdBattle.getUserRosterID()
                + "', OponentSpeciesID='" + DatabaseHolder.holdBattle.getOponentSpeciesID()
                + "', OponentCultureID='" + DatabaseHolder.holdBattle.getOponentCultureID()
                + "', OponentRosterID='" + DatabaseHolder.holdBattle.getOponentRosterID()
                + "', Points='" + DatabaseHolder.holdBattle.getPoints()
                + "', Outcome='" + DatabaseHolder.holdBattle.getOutcome()
                + "', Date='" + DatabaseHolder.holdBattle.getDate()
                + "', ReplayID='" + DatabaseHolder.holdBattle.getReplayID()
                + "', CustomData='" + DatabaseHolder.holdBattle.getCustomData()
                + "' WHERE "
                + "BattleHistoryID = '" + battleID + "'");
    }

    /**
     *
     * @param selSpeciesID
     * @param newSpecesName
     *
     */
    public static void duplicateSpecies(int selSpeciesID, String newSpecesName) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedSpecies WHERE CreatedSpeciesID =?");
            stmt.setInt(1, selSpeciesID);
            String[] columns = {"LifeDomain", "CharacteristicGroup", "Age", "Skills", "SpeciesModifiers"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            executeSQL("INSERT INTO `CreatedSpecies`"
                    + "(LifeDomain, CharacteristicGroup,SpeciesName,Age,Skills,SpeciesModifiers) "
                    + "VALUES ('"
                    + data.get(0).toString().split("\\|")[0] + "','"
                    + data.get(0).toString().split("\\|")[1] + "','"
                    + newSpecesName + "','"
                    + data.get(0).toString().split("\\|")[2] + "''"
                    + data.get(0).toString().split("\\|")[3] + "''"
                    + data.get(0).toString().split("\\|")[4] + "');");
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param newCultureName
     * @param selCultureID
     *
     */
    public static void duplicateOneCulture(int selCultureID, String newCultureName) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt;
            String[] columns = {"ParentSpeciesID", "Age", "TotalProgressionPoints", "LeftProgressionPoints"};
            stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedCultures WHERE CreatedCulturesID =?");
            stmt.setInt(1, selCultureID);
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            executeSQL("INSERT INTO `CreatedCultures`"
                    + "(CultureName,ParentSpeciesID,Age,TotalProgressionPoints,LeftProgressionPoints) "
                    + "VALUES ('"
                    + newCultureName + "','"
                    + data.get(0).toString().split("\\|")[0] + "','"
                    + data.get(0).toString().split("\\|")[1] + "','"
                    + data.get(0).toString().split("\\|")[2] + "','"
                    + data.get(0).toString().split("\\|")[3] + "');");
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selSpeciesID
     * @param newSpeciesID
     *
     */
    public static void duplicateAllCulturesAndRelatedDataFromSpecies(int selSpeciesID, int newSpeciesID) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt;
            String[] columns = {"CreatedCulturesID", "CultureName", "Age", "TotalProgressionPoints", "LeftProgressionPoints"};
            stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedCultures WHERE ParentSpeciesID =?");
            stmt.setInt(1, selSpeciesID);
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `CreatedCultures`"
                        + "(CultureName,ParentSpeciesID,Age,TotalProgressionPoints,LeftProgressionPoints) "
                        + "VALUES ('"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + newSpeciesID + "','"
                        + data.get(i).toString().split("\\|")[2] + "','"
                        + data.get(i).toString().split("\\|")[3] + "','"
                        + data.get(i).toString().split("\\|")[4] + "');");
                int newCultureID = DatabaseReader.getCultureID(newSpeciesID, data.get(i).toString().split("\\|")[1]);
                duplicateAllClassesFromCulture(Integer.parseInt(data.get(i).toString().split("\\|")[0]), newCultureID);
                duplicateAllHeroesFromCulture(Integer.parseInt(data.get(i).toString().split("\\|")[0]), newCultureID);
                duplicateAllProgressFromCulture(Integer.parseInt(data.get(i).toString().split("\\|")[0]), newCultureID);
                duplicateAllRostersFromCulture(Integer.parseInt(data.get(i).toString().split("\\|")[0]), newCultureID);
                duplicateAllBattlesFromCulture(Integer.parseInt(data.get(i).toString().split("\\|")[0]), newCultureID);
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selClassID
     * @param newClassName
     *
     */
    public static void duplicateOneClass(int selClassID, String newClassName) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE CreatedClassesID =?");
            stmt.setInt(1, selClassID);
            String[] columns = {"Skills", "ParentCultureId", "Advancements", "Type", "BasedOn", "AdditionalCost"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            executeSQL("INSERT INTO `CreatedClasses`"
                    + "(ClassName,Skills,ParentCultureId,Advancements,Type,BasedOn,AdditionalCost) "
                    + "VALUES ('"
                    + newClassName + "','"
                    + data.get(0).toString().split("\\|")[0] + "','"
                    + data.get(0).toString().split("\\|")[1] + "','"
                    + data.get(0).toString().split("\\|")[2] + "','"
                    + data.get(0).toString().split("\\|")[3] + "','"
                    + data.get(0).toString().split("\\|")[4] + "','"
                    + data.get(0).toString().split("\\|")[5] + "');");
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selCultureID
     * @param newCultureID
     *
     */
    public static void duplicateAllClassesFromCulture(int selCultureID, int newCultureID) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE ParentCultureId =?");
            stmt.setInt(1, selCultureID);
            String[] columns = {"ClassName", "Skills", "Advancements", "Type", "BasedOn", "AdditionalCost"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `CreatedClasses`"
                        + "(ClassName,Skills,ParentCultureId,Advancements,Type,BasedOn,AdditionalCost) "
                        + "VALUES ('"
                        + data.get(i).toString().split("\\|")[0] + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + newCultureID + "','"
                        + data.get(i).toString().split("\\|")[2] + "','"
                        + data.get(i).toString().split("\\|")[3] + "','"
                        + data.get(i).toString().split("\\|")[4] + "','"
                        + data.get(i).toString().split("\\|")[5] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selHeroID
     * @param newHeroName
     *
     */
    public static void duplicateOneHero(int selHeroID, String newHeroName) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE CreatedHeroesID =?");
            stmt.setInt(1, selHeroID);
            String[] columns = {"ParentCultureId", "Advancements", "BasedOn", "AdditionalCost"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `CreatedHeroes`"
                        + "(HeroName,ParentCultureId,Advancements,BasedOn,AdditionalCost) "
                        + "VALUES ('"
                        + newHeroName + "','"
                        + data.get(i).toString().split("\\|")[0] + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + data.get(i).toString().split("\\|")[2] + "','"
                        + data.get(i).toString().split("\\|")[3] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selCultureID
     * @param newCultureID
     *
     */
    public static void duplicateAllHeroesFromCulture(int selCultureID, int newCultureID) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE ParentCultureId =?");
            stmt.setInt(1, selCultureID);
            String[] columns = {"HeroName", "Advancements", "BasedOn", "AdditionalCost"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `CreatedHeroes`"
                        + "(HeroName,ParentCultureId,Advancements,BasedOn,AdditionalCost) "
                        + "VALUES ('"
                        + data.get(i).toString().split("\\|")[0] + "','"
                        + newCultureID + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + data.get(i).toString().split("\\|")[2] + "','"
                        + data.get(i).toString().split("\\|")[3] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selProgressID
     * @param newProgressName
     *
     */
    public static void duplicateOneProgress(int selProgressID, String newProgressName) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedProgress WHERE CreatedProgressID =?");
            stmt.setInt(1, selProgressID);
            String[] columns = {"ParentCultureId", "Progress", "Date"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `CreatedProgress`"
                        + "(ParentCultureId,ProgressName,Progress,Date) "
                        + "VALUES ('"
                        + data.get(i).toString().split("\\|")[0] + "','"
                        + newProgressName + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + data.get(i).toString().split("\\|")[2] + "');");

            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selCultureID
     * @param newCultureID
     *
     */
    public static void duplicateAllProgressFromCulture(int selCultureID, int newCultureID) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedProgress WHERE ParentCultureId =?");
            stmt.setInt(1, selCultureID);
            String[] columns = {"ProgressName", "Progress", "Date"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `CreatedProgress`"
                        + "(ParentCultureId,ProgressName,Progress,Date) "
                        + "VALUES ('"
                        + newCultureID + "','"
                        + data.get(i).toString().split("\\|")[0] + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + data.get(i).toString().split("\\|")[2] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selRosterID
     * @param newRosterName
     *
     */
    public static void duplicateOneRoster(int selRosterID, String newRosterName) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE CreatedRostersID =?");
            stmt.setInt(1, selRosterID);
            String[] columns = {"ParentCultureId", "Roster", "MaxPoints"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `CreatedRosters`"
                        + "(RosterName,ParentCultureId,Roster,MaxPoints) "
                        + "VALUES ('"
                        + newRosterName + "','"
                        + data.get(i).toString().split("\\|")[0] + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + data.get(i).toString().split("\\|")[2] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selCultureID
     * @param newCultureID
     *
     */
    public static void duplicateAllRostersFromCulture(int selCultureID, int newCultureID) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE ParentCultureId =?");
            stmt.setInt(1, selCultureID);
            String[] columns = {"RosterName", "Roster", "MaxPoints"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `CreatedRosters`"
                        + "(RosterName,ParentCultureId,Roster,MaxPoints) "
                        + "VALUES ('"
                        + data.get(i).toString().split("\\|")[0] + "','"
                        + newCultureID + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + data.get(i).toString().split("\\|")[2] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selBattleID
     * @param newBattleName
     *
     */
    public static void duplicateOneBattle(int selBattleID, String newBattleName) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM BattleHistory WHERE BattleHistoryID =?");
            stmt.setInt(1, selBattleID);
            String[] columns = {"BattleName", "UserSpecies", "UserCulture", "UserRoster", "OponentSpecies", "OponentCulture", "OponentRoster", "Points", "Outcome", "Date", "ReplayID"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `BattleHistory`"
                        + "(BattleName,UserSpecies,UserCulture,UserRoster,OponentSpecies,OponentCulture,OponentRoster,Points,Outcome,Date,ReplayID) "
                        + "VALUES ('"
                        + newBattleName + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + data.get(i).toString().split("\\|")[2] + "','"
                        + data.get(i).toString().split("\\|")[3] + "','"
                        + data.get(i).toString().split("\\|")[4] + "','"
                        + data.get(i).toString().split("\\|")[5] + "','"
                        + data.get(i).toString().split("\\|")[6] + "','"
                        + data.get(i).toString().split("\\|")[7] + "','"
                        + data.get(i).toString().split("\\|")[8] + "','"
                        + data.get(i).toString().split("\\|")[9] + "','"
                        + data.get(i).toString().split("\\|")[10] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selCultureID
     * @param newCultureID
     *
     */
    public static void duplicateAllBattlesFromCulture(int selCultureID, int newCultureID) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM BattleHistory WHERE UserCultureID =? or OponentCultureID=?");
            stmt.setInt(1, selCultureID);
            stmt.setInt(2, selCultureID);
            String[] columns = {"BattleName", "UserSpeciesID", "UserCultureID", "UserRosterID", "OponentSpeciesID", "OponentCultureID", "OponentRosterID", "Points", "Outcome", "Date", "ReplayID"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                executeSQL("INSERT INTO `BattleHistory`"
                        + "(BattleName,UserSpeciesID,UserCultureID,UserRosterID,OponentSpeciesID,OponentCultureID,OponentRosterID,Points,Outcome,Date,ReplayID) "
                        + "VALUES ('"
                        + data.get(i).toString().split("\\|")[0] + "','"
                        + data.get(i).toString().split("\\|")[1] + "','"
                        + (data.get(i).toString().split("\\|")[2].equals(selCultureID) ? newCultureID : data.get(i).toString().split("\\|")[2]) + "','"
                        + data.get(i).toString().split("\\|")[3] + "','"
                        + data.get(i).toString().split("\\|")[4] + "','"
                        + (data.get(i).toString().split("\\|")[5].equals(selCultureID) ? newCultureID : data.get(i).toString().split("\\|")[5]) + "','"
                        + data.get(i).toString().split("\\|")[6] + "','"
                        + data.get(i).toString().split("\\|")[7] + "','"
                        + data.get(i).toString().split("\\|")[8] + "','"
                        + data.get(i).toString().split("\\|")[9] + "','"
                        + data.get(i).toString().split("\\|")[10] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
