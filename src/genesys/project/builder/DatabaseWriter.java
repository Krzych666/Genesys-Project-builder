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

/**
 *
 * @author krzysztofg
 */
public class DatabaseWriter {

    /**
     * tempDeletescript
     */
    public static String tempDeletescript = null;

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
            ErrorController.ErrorController(e);
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    ErrorController.ErrorController(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    ErrorController.ErrorController(e);
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
                SpecialModificators = "MainDomain=" + ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).getMainDomain().getText() + "," + DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
            case Reptilia:
                SpecialModificators = "MainLineage=" + ((DatabaseHolder.AReptilia) DatabaseHolder.holdSpecies).getMainLineage().getText() + "," + DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
            case Biest:
                SpecialModificators = "MainKingdom=" + ((DatabaseHolder.ABiest) DatabaseHolder.holdSpecies).getMainKingdom().getText() + "," + "MainRegion=" + ((DatabaseHolder.ABiest) DatabaseHolder.holdSpecies).getMainRegion().getText() + "," + DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
            case Insecta:
                SpecialModificators = "MainClasification=" + ((DatabaseHolder.AInsecta) DatabaseHolder.holdSpecies).getMainClasification().getText() + "," + "MainOrder=" + ((DatabaseHolder.AInsecta) DatabaseHolder.holdSpecies).getMainOrder().getText() + "," + DatabaseHolder.holdSpecies.getSpeciesModifiers();
                break;
        }
        if (DatabaseHolder.holdCulture.getCultureName().equals(DatabaseHolder.holdSpecies.getSpeciesName())) {
            executeSQL("INSERT INTO `CreatedSpecies`(LifeDomain,CharacteristicGroup,SpeciesName,Skills,SpeciesModifiers) VALUES ('" + DatabaseHolder.holdSpecies.getLifedomain().toString() + "','" + DatabaseHolder.holdSpecies.getCharacteristicGroup().toString() + "','" + DatabaseHolder.holdSpecies.getSpeciesName() + "','" + DatabaseHolder.holdSpecies.getSkills() + "','" + SpecialModificators + "');");
        }
        executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName) VALUES ('" + DatabaseHolder.holdCulture.getCultureName() + "','" + DatabaseHolder.holdCulture.getSpeciesName() + "');");
        for (DatabaseHolder.AClass holdClas : DatabaseHolder.holdClass) {
            if (holdClas.getClassName() != null && !holdClas.getClassName().equals("")) {
                if (holdClas.getSkills().length() > 2) {
                    holdClas.setSkills(holdClas.getSkills().substring(0, holdClas.getSkills().length() - 1));
                }
                executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + holdClas.getClassName() + "','" + holdClas.getSkills() + "','" + DatabaseHolder.holdSpecies.getSpeciesName() + "','" + DatabaseHolder.holdCulture.getCultureName() + "'," + null + ",'" + holdClas.getType() + "','" + holdClas.getBasedOn() + "','" + holdClas.getAdditionalCost() + "');");
            }
        }
        DatabaseHolder.holdSpecies = null;
    }

    /**
     * writeRosterToDB
     */
    public static void writeRosterToDB() {
        executeSQL("INSERT INTO `CreatedRosters`(RosterName,CultureName,SpeciesName,Roster,MaxPoints) VALUES ('" + DatabaseHolder.holdRoster.getRosterName() + "','" + DatabaseHolder.holdRoster.getCultureName() + "','" + DatabaseHolder.holdRoster.getSpeciesName() + "','" + DatabaseHolder.holdRoster.getRoster() + "','" + DatabaseHolder.holdRoster.getMaxPoints() + "');");
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
     * @param newSpecies
     */
    public static void modifySpeciesName(String newSpecies) {
        executeSQL("UPDATE CreatedSpecies SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedCultures SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedClasses SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedHeroes SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedProgress SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedRosters SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "'");
    }

    /**
     * modifySpecies
     *
     */
    public static void modifySpecies() {
        executeSQL("UPDATE CreatedSpecies SET Lifedomain='" + DatabaseHolder.holdSpecies.getLifedomain() + "', CharacteristicGroup='" + DatabaseHolder.holdSpecies.getCharacteristicGroup() + "', SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "', Age='" + DatabaseHolder.holdSpecies.getAge() + "', Skills='" + DatabaseHolder.holdSpecies.getSkills() + "', SpeciesModifiers='" + DatabaseHolder.holdSpecies.getSpeciesModifiers() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedCultures SET SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedClasses SET SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedHeroes SET SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedProgress SET SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedRosters SET SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldSpecies.getSpeciesName() + "'");
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
                        executeSQL("UPDATE CreatedClasses SET Skills='" + tmpSkillRep + "' WHERE SpeciesName='" + DatabaseHolder.holdSpecies.getSpeciesName() + "' AND CultureName='" + tmpCult.get(i).toString() + "' AND ClassName='" + tmpClass.get(ii).toString() + "'");
                    }
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param newCulture
     */
    public static void modifyCultureName(String newCulture) {
        executeSQL("UPDATE CreatedCultures SET CultureName='" + newCulture + "' WHERE SpeciesName='" + DatabaseHolder.holdCulture.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdCulture.getCultureName() + "'");
        executeSQL("UPDATE CreatedClasses SET CultureName='" + newCulture + "' WHERE SpeciesName='" + DatabaseHolder.holdCulture.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdCulture.getCultureName() + "'");
        executeSQL("UPDATE CreatedHeroes SET CultureName='" + newCulture + "' WHERE SpeciesName='" + DatabaseHolder.holdCulture.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdCulture.getCultureName() + "'");
        executeSQL("UPDATE CreatedProgress SET CultureName='" + newCulture + "' WHERE SpeciesName='" + DatabaseHolder.holdCulture.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdCulture.getCultureName() + "'");
        executeSQL("UPDATE CreatedRosters SET CultureName='" + newCulture + "' WHERE SpeciesName='" + DatabaseHolder.holdCulture.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdCulture.getCultureName() + "'");
    }

    /**
     * modifyCulture
     */
    public static void modifyCulture() {
        executeSQL("UPDATE CreatedCultures SET Age='" + DatabaseHolder.holdCulture.getAge() + "', TotalProgressionPoints='" + DatabaseHolder.holdCulture.getTotalProgressionPoints() + "', LeftProgressionPoints='" + DatabaseHolder.holdCulture.getLeftProgressionPoints() + "' WHERE SpeciesName='" + DatabaseHolder.holdCulture.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdCulture.getCultureName() + "'");
    }

    /**
     *
     * @param newClass
     * @param a
     */
    public static void modifyClassName(String newClass, int a) {
        executeSQL("UPDATE CreatedClasses SET BasedOn='" + newClass + "' WHERE SpeciesName='" + DatabaseHolder.holdClass[a].getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdClass[a].getCultureName() + "' AND BasedOn='" + DatabaseHolder.holdClass[a].getClassName() + "'");
        executeSQL("UPDATE CreatedHeroes SET BasedOn='" + newClass + "' WHERE SpeciesName='" + DatabaseHolder.holdClass[a].getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdClass[a].getCultureName() + "' AND BasedOn='" + DatabaseHolder.holdClass[a].getClassName() + "'");
        executeSQL("UPDATE CreatedClasses SET ClassName='" + newClass + "' WHERE SpeciesName='" + DatabaseHolder.holdClass[a].getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdClass[a].getCultureName() + "' AND ClassName='" + DatabaseHolder.holdClass[a].getClassName() + "'");
    }

    /**
     *
     * @param a
     */
    public static void modifyClass(int a) {
        if (a < DatabaseHolder.modifiedHoldClass.length - 1) {
            if (DatabaseHolder.holdClass[a].getSkills().endsWith(",")) {
                DatabaseHolder.holdClass[a].setSkills(DatabaseHolder.holdClass[a].getSkills().substring(0, DatabaseHolder.holdClass[a].getSkills().length() - 1));
            }
            executeSQL("UPDATE CreatedClasses SET ClassName='" + DatabaseHolder.holdClass[a].getClassName() + "', Skills='" + DatabaseHolder.holdClass[a].getSkills() + "', SpeciesName='" + DatabaseHolder.holdClass[a].getSpeciesName() + "', CultureName='" + DatabaseHolder.holdClass[a].getCultureName() + "', Advancements='" + DatabaseHolder.holdClass[a].getAdvancements() + "', Type='" + DatabaseHolder.holdClass[a].getType() + "', BasedOn='" + DatabaseHolder.holdClass[a].getBasedOn() + "', AdditionalCost='" + DatabaseHolder.holdClass[a].getAdditionalCost() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldClass[a].getSpeciesName() + "' AND CultureName='" + DatabaseHolder.modifiedHoldClass[a].getCultureName() + "' AND ClassName='" + DatabaseHolder.modifiedHoldClass[a].getClassName() + "'");
            executeSQL("UPDATE CreatedHeroes SET BasedOn='" + DatabaseHolder.holdClass[a].getClassName() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldClass[a].getSpeciesName() + "' AND CultureName='" + DatabaseHolder.modifiedHoldClass[a].getCultureName() + "' AND BasedOn='" + DatabaseHolder.modifiedHoldClass[a].getClassName() + "'");
            checkClassSkillsBasedon(a);
        } else {
            executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + DatabaseHolder.holdClass[a].getClassName() + "','" + DatabaseHolder.holdClass[a].getSkills() + "','" + DatabaseHolder.holdSpecies.getSpeciesName() + "','" + DatabaseHolder.holdCulture.getCultureName() + "'," + null + ",'" + DatabaseHolder.holdClass[a].getType() + "','" + DatabaseHolder.holdClass[a].getBasedOn() + "','" + DatabaseHolder.holdClass[a].getAdditionalCost() + "');");
        }
    }

    private static void checkClassSkillsBasedon(int a) {
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
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName=? AND CultureName=? AND BasedOn=?");
            stmt1.setString(1, DatabaseHolder.holdClass[a].getSpeciesName());
            stmt1.setString(2, DatabaseHolder.holdClass[a].getCultureName());
            stmt1.setString(3, DatabaseHolder.holdClass[a].getClassName());
            String[] columns1 = {"ClassName"};
            ObservableList tmpBased = BuilderCORE.getData(stmt1, columns1, null, 0);
            for (int i = 0; i < tmpBased.size(); i++) {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName=? AND CultureName=? AND ClassName=?");
                stmt2.setString(1, DatabaseHolder.holdClass[a].getSpeciesName());
                stmt2.setString(2, DatabaseHolder.holdClass[a].getCultureName());
                stmt2.setString(3, tmpBased.get(i).toString());
                String tmpSkill = BuilderCORE.getValue(stmt2, "Skills");
                String tmpSkillRep = DatabaseHolder.skillsSeparatorRepalcer(tmpSkill);
                for (String split : DatabaseHolder.skillsSeparatorRepalcer(deleteSkills).split(",")) {
                    tmpSkillRep = tmpSkillRep.replace(split, "");
                }
                tmpSkillRep = DatabaseHolder.skillsSeparatorRepalcer(tmpSkillRep);
                if (!tmpSkill.equals(tmpSkillRep)) {
                    executeSQL("UPDATE CreatedClasses SET Skills='" + tmpSkillRep + "' WHERE SpeciesName='" + DatabaseHolder.holdClass[a].getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdClass[a].getCultureName() + "' AND ClassName='" + tmpBased.get(i).toString() + "'");
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param newHero
     */
    public static void modifyHeroName(String newHero) {
        executeSQL("UPDATE CreatedHeroes SET HeroName='" + newHero + "' WHERE SpeciesName='" + DatabaseHolder.holdHero.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdHero.getCultureName() + "' AND HeroName='" + DatabaseHolder.holdHero.getHeroName() + "'");
    }

    /**
     * modifyHero
     */
    public static void modifyHero() {
        executeSQL("UPDATE CreatedHeroes SET HeroName='" + DatabaseHolder.holdHero.getHeroName() + "', SpeciesName='" + DatabaseHolder.holdHero.getSpeciesName() + "', CultureName='" + DatabaseHolder.holdHero.getCultureName() + "', Advancements='" + DatabaseHolder.holdHero.getAdvancements() + "', BasedOn='" + DatabaseHolder.holdHero.getBasedOn() + "', AdditionalCost='" + DatabaseHolder.holdHero.getAdditionalCost() + "', WHERE SpeciesName='" + DatabaseHolder.modifiedHoldHero.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.modifiedHoldHero.getCultureName() + "' AND HeroName='" + DatabaseHolder.modifiedHoldHero.getHeroName() + "'");
    }

    /**
     *
     * @param newProgress
     */
    public static void modifyProgressName(String newProgress) {
        executeSQL("UPDATE CreatedProgress SET ProgressName='" + newProgress + "' WHERE SpeciesName='" + DatabaseHolder.holdProgress.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdProgress.getCultureName() + "' AND ProgressName='" + DatabaseHolder.holdProgress.getProgressName() + "'");
    }

    /**
     * modifyProgress
     */
    public static void modifyProgress() {
        executeSQL("UPDATE CreatedProgress SET ProgressName='" + DatabaseHolder.holdProgress.getProgressName() + "', SpeciesName='" + DatabaseHolder.holdProgress.getSpeciesName() + "', CultureName='" + DatabaseHolder.holdProgress.getCultureName() + "', Progress='" + DatabaseHolder.holdProgress.getProgress() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldProgress.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.modifiedHoldProgress.getCultureName() + "' AND ProgressName='" + DatabaseHolder.modifiedHoldProgress.getProgressName() + "'");
    }

    /**
     *
     * @param newRoster
     */
    public static void modifyRosterName(String newRoster) {
        executeSQL("UPDATE CreatedRosters SET RosterName='" + newRoster + "' WHERE SpeciesName='" + DatabaseHolder.holdRoster.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.holdRoster.getCultureName() + "' AND RosterName='" + DatabaseHolder.holdRoster.getRosterName() + "'");
    }

    /**
     * modifyRoster
     */
    public static void modifyRoster() {
        executeSQL("UPDATE CreatedRosters SET RosterName='" + DatabaseHolder.holdRoster.getRosterName() + "', SpeciesName='" + DatabaseHolder.holdRoster.getSpeciesName() + "', CultureName='" + DatabaseHolder.holdRoster.getCultureName() + "', Roster='" + DatabaseHolder.holdRoster.getRoster() + "', MaxPoints='" + DatabaseHolder.holdRoster.getMaxPoints() + "' WHERE SpeciesName='" + DatabaseHolder.modifiedHoldRoster.getSpeciesName() + "' AND CultureName='" + DatabaseHolder.modifiedHoldRoster.getCultureName() + "' AND RosterName='" + DatabaseHolder.modifiedHoldRoster.getRosterName() + "'");
    }

    /**
     *
     * @param selSpecies
     * @param duplicateNewNameValue
     *
     */
    public static void duplicateSpecies(String selSpecies, String duplicateNewNameValue) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedSpecies WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"LifeDomain", "CharacteristicGroup", "Age", "Skills", "SpeciesModifiers"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            executeSQL("INSERT INTO `CreatedSpecies`(LifeDomain, CharacteristicGroup,SpeciesName,Age,Skills,SpeciesModifiers) VALUES ('" + data.get(0).toString().split("\\|")[0] + "','" + data.get(0).toString().split("\\|")[1] + "','" + duplicateNewNameValue + "','" + data.get(0).toString().split("\\|")[2] + "''" + data.get(0).toString().split("\\|")[3] + "''" + data.get(0).toString().split("\\|")[4] + "');");
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param duplicateNewNameValue
     *
     */
    public static void duplicateCulture(String selSpecies, String selCulture, String duplicateNewNameValue) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("--all--".equals(selCulture) ? "SELECT * FROM CreatedCultures WHERE SpeciesName =?" : "SELECT * FROM CreatedCultures WHERE SpeciesName =? AND CultureName =");
            stmt.setString(1, selSpecies);
            if (!"--all--".equals(selCulture)) {
                stmt.setString(2, selCulture);
            }
            String[] columns = {"CultureName", "Age", "TotalProgressionPoints", "LeftProgressionPoints"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            if ("--all--".equals(selCulture)) {
                for (int i = 0; i < data.size(); i++) {
                    executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName,Age,TotalProgressionPoints,LeftProgressionPoints) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "');");
                }
            } else {
                executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName,Age,TotalProgressionPoints,LeftProgressionPoints) VALUES ('" + duplicateNewNameValue + "','" + selSpecies + "','" + data.get(0).toString().split("\\|")[1] + "','" + data.get(0).toString().split("\\|")[2] + "','" + data.get(0).toString().split("\\|")[3] + "');");
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selClass
     * @param duplicateNewNameValue
     *
     */
    public static void duplicateClass(String selSpecies, String selCulture, String selClass, String duplicateNewNameValue) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"ClassName", "Skills", "CultureName", "Advancements", "Type", "BasedOn", "AdditionalCost"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                if ("--all--".equals(selClass) && "--all--".equals(selCulture)) {
                    executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + data.get(i).toString().split("\\|")[1] + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "','" + data.get(i).toString().split("\\|")[5] + "','" + data.get(i).toString().split("\\|")[6] + "');");
                } else if ("--all--".equals(selClass) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture)) {
                    executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + data.get(i).toString().split("\\|")[1] + "','" + selSpecies + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "','" + data.get(i).toString().split("\\|")[5] + "','" + data.get(i).toString().split("\\|")[6] + "');");
                } else if (!"--all--".equals(selClass) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selClass)) {
                    executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[1] + "','" + selSpecies + "','" + selCulture + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "','" + data.get(i).toString().split("\\|")[5] + "','" + data.get(i).toString().split("\\|")[6] + "');");
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selHero
     * @param duplicateNewNameValue
     *
     */
    public static void duplicateHero(String selSpecies, String selCulture, String selHero, String duplicateNewNameValue) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"HeroName", "CultureName", "Advancements", "BasedOn", "AdditionalCost"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                if ("--all--".equals(selHero) && "--all--".equals(selCulture)) {
                    executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "');");
                } else if ("--all--".equals(selHero) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture)) {
                    executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + selSpecies + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "');");
                } else if (!"--all--".equals(selHero) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selHero)) {
                    executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + duplicateNewNameValue + "','" + selSpecies + "','" + selCulture + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "');");
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selProgress
     * @param duplicateNewNameValue
     *
     */
    public static void duplicateProgress(String selSpecies, String selCulture, String selProgress, String duplicateNewNameValue) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedProgress WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"CultureName", "ProgressName", "Progress"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                if ("--all--".equals(selProgress) && "--all--".equals(selCulture)) {
                    executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[0] + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "');");
                } else if ("--all--".equals(selProgress) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture)) {
                    executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + selSpecies + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "');");
                } else if (!"--all--".equals(selProgress) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selProgress)) {
                    executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + selSpecies + "','" + selCulture + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[2] + "');");
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selRoster
     * @param duplicateNewNameValue
     *
     */
    public static void duplicateRoster(String selSpecies, String selCulture, String selRoster, String duplicateNewNameValue) {
        try {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"RosterName", "CultureName", "Roster", "MaxPoints"};
            ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
            for (int i = 0; i < data.size(); i++) {
                if ("--all--".equals(selRoster) && "--all--".equals(selCulture)) {
                    executeSQL("INSERT INTO `CreatedRosters`(RosterName,SpeciesName,CultureName,Roster,MaxPoints) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "');");
                } else if ("--all--".equals(selRoster) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture)) {
                    executeSQL("INSERT INTO `CreatedRosters`(RosterName,SpeciesName,CultureName,Roster,MaxPoints) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + selSpecies + "','" + duplicateNewNameValue + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "');");
                } else if (!"--all--".equals(selRoster) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selRoster)) {
                    executeSQL("INSERT INTO `CreatedRosters`(RosterName,SpeciesName,CultureName,Roster,MaxPoints) VALUES ('" + duplicateNewNameValue + "','" + selSpecies + "','" + selCulture + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "');");
                }
            }
        } catch (SQLException ex) {
            ErrorController.ErrorController(ex);
            Logger.getLogger(DatabaseWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
