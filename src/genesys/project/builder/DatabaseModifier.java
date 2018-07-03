/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import genesys.project.builder.Enums.Enmuerations.CharacteristicGroup;
import genesys.project.builder.Enums.Enmuerations.DBTables;
import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
import genesys.project.builder.Enums.Enmuerations.MainClasificationValue;
import static genesys.project.builder.Enums.Enmuerations.MainDomainValue;
import genesys.project.builder.Enums.Enmuerations.MainKingdomValue;
import genesys.project.builder.Enums.Enmuerations.MainLineageValue;
import genesys.project.builder.Enums.Enmuerations.MainOrderValue;
import genesys.project.builder.Enums.Enmuerations.MainRegionValue;
import genesys.project.builder.Enums.Enmuerations.UseCases;
import genesys.project.fxml.BuilderFXMLController;
import java.io.IOException;
import static java.lang.Integer.max;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author krzysztofg
 */
public class DatabaseModifier {

    /**
     * holdSpecies
     */
    public static ASpecies holdSpecies;

    /**
     * holdCulture
     */
    public static ACulture holdCulture = new ACulture();

    /**
     * holdClass
     */
    public static AClass[] holdClass = new AClass[1];

    /**
     * holdHero
     */
    public static AHero holdHero = new AHero();

    /**
     * holdProgress
     */
    public static AProgress holdProgress = new AProgress();

    /**
     * holdRoster
     */
    public static ARoster holdRoster = new ARoster();

    /**
     * fullSkillList1
     */
    public static String fullSkillList1;

    /**
     * b
     */
    public static int b = 0;

    /**
     * outcasts
     */
    public static boolean outcasts = false;

    /**
     * arcana
     */
    public static boolean arcana = false;

    /**
     * TOPDROP
     */
    public static final String TOPDROP = "--CHOOSE--";

    /**
     * numberOfClases
     */
    public static int numberOfClases;

    /**
     * currentTable
     */
    public static DBTables currentTable;

    /**
     * isModyfyinfg
     */
    public static boolean isModyfyinfg = false;

    /**
     * classIsModyfying
     */
    public static boolean classIsModyfying = false;
    /**
     * modifiedHoldSpecies
     */
    public static ASpecies modifiedHoldSpecies;

    /**
     * modifiedHoldCulture
     */
    public static ACulture modifiedHoldCulture;

    /**
     * modifiedHoldClass
     */
    public static AClass[] modifiedHoldClass;

    /**
     * modifiedHoldHero
     */
    public static AHero modifiedHoldHero;

    /**
     * modifiedHoldProgress
     */
    public static AProgress modifiedHoldProgress;

    /**
     * modifiedHoldRoster
     */
    public static ARoster modifiedHoldRoster;

    /**
     * tempDeletescript
     */
    public static String tempDeletescript = null;

    /**
     * excludeSkills
     */
    public static ListView excludeSkills;

    /**
     * classList1Holder
     */
    public static String classList1Holder;

    /**
     * ruledskills
     */
    public static ObservableList ruledskills = FXCollections.observableArrayList();

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
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* ignored */ }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    /* ignored */ }
            }
        }
    }

    /**
     *
     * @param lifedomain
     * @param what
     * @throws IOException
     */
    public static void creator(LifedomainValue lifedomain, Enum what) throws IOException {
        isModyfyinfg = false;
        holdSpecies = null;
        holdSpecies = ASpecies.createASpecies(lifedomain);
        holdSpecies.Lifedomain = lifedomain;
        holdSpecies.CharacteristicGroup = CharacteristicGroup.standard;
        holdCulture = new ACulture();
        BuilderFXMLController.HOLD_MODIFIERS.clearModifiers();
        numberOfClases = b = 0;
        switch (holdSpecies.Lifedomain) {
            case Humanoid:
                holdSpecies.SpeciesModifiers = "Arcana=" + arcana;
                break;
            case Fey:
                holdSpecies.SpeciesModifiers = "Outcasts=" + outcasts + ",SecondaryDomain=" + MainDomainValue.Twilight.getText();
                break;
            case Reptilia:
                holdSpecies.SpeciesModifiers = "Arcana=" + arcana;
                break;
            case Biest:
                holdSpecies.SpeciesModifiers = "Arcana=" + arcana;
                break;
            case Insecta:
                holdSpecies.SpeciesModifiers = "Arcana=" + arcana;
                break;
        }
    }

    /**
     *
     * @param HoldSkills
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getAddedSkills(String HoldSkills) throws SQLException {

        if (HoldSkills == null || "".equals(HoldSkills)) {
            return null;
        }
        fullSkillList1 = "";
        ruledskills.clear();
        String[] lst = HoldSkills.replaceAll(";", ",").split(",");
        String[] lstref = HoldSkills.replaceAll(";", ",").split(",");
        for (int i = 0; i < lst.length; i++) {
            if (!lstref[i].equals("")) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
                stmt.setString(1, lstref[i]);
                String[] skl = ((BuilderCORE.getValue(stmt, "SkillRules")).split(";"));
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
                stmt1.setString(1, lstref[i]);
                String ptscost = (BuilderCORE.getValue(stmt1, "PointCost"));
                lst[i] += " (pts : " + ptscost + ") : ";
                for (int j = 0; j < skl.length; j++) {
                    lst[i] = lst[i] + ((skl[j].split("_"))[0]);
                    if ((skl[j].split("_")).length >= 2) {
                        lst[i] += " " + ((skl[j].split("_"))[1]);
                    }
                    if ((skl[j].split("_")).length >= 3) {
                        lst[i] += " " + ((skl[j].split("_"))[2]);
                    }
                    if (j < skl.length - 1) {
                        lst[i] += ", ";
                    }
                    chooseConnection(UseCases.COREdb);
                    PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
                    stmt2.setString(1, lstref[i]);
                    String rulledskill = BuilderCORE.getValue(stmt2, "LifeDomainTree2");
                    ruledskills.add(rulledskill + ">" + skl[j]);
                }
            }
        }
        ObservableList<String> tmp = FXCollections.observableArrayList();
        tmp.addAll(lst);
        return tmp;
    }

    /**
     *
     * @param name
     * @return
     */
    public static String getBaseAddedSkills(String name) {
        if (fullSkillList1 == null) {
            fullSkillList1 = "";
        }
        if (name.equals(BuilderCORE.BASE)) {
            fullSkillList1 += holdSpecies.getSkills();
        } else {
            for (AClass holdClas : holdClass) {
                if (holdClas.getClassName().equals(name)) {
                    if (holdClas.getSkills() != null && !holdClas.getSkills().equals("null,") && !holdClas.getSkills().equals("") && !holdClas.getSkills().equals(";")) {
                        fullSkillList1 += holdClas.getSkills() + getBaseAddedSkills(holdClas.getBasedOn());
                    } else {
                        fullSkillList1 += getBaseAddedSkills(holdClas.getBasedOn());
                    }
                }
            }
        }
        return fullSkillList1;
    }

    /**
     *
     * @param lifeDomain
     * @param species
     * @param classname
     * @param bb
     * @param points
     * @return
     * @throws SQLException
     */
    public static int baseAddedCost(LifedomainValue lifeDomain, ASpecies species, AClass classname[], int bb, int points) throws SQLException {
        int a = 1;
        int source = 0;
        if (classname != null) {
            switch (classname[bb].Type) {
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
            if (!"".equals(classname[bb].Type) && !"null".equals(classname[bb].Type) && !(BuilderCORE.BASE.equals(classList1Holder))) {
                if (classname[bb].BasedOn != null && !classname[bb].BasedOn.equals(BuilderCORE.BASE) && !BuilderCORE.BASE.equals(classList1Holder)) {
                    String[] lst = ((classname[bb].Skills).split(","));
                    for (int s = 0; s < classname.length; s++) {
                        if (classname[bb].BasedOn.equals(classname[s].ClassName)) {
                            source = s;
                        }
                    }
                    points += a * baseAddedCost(lifeDomain, species, classname, source, points);
                    for (String lst1 : lst) {
                        chooseConnection(UseCases.COREdb);
                        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
                        stmt.setString(1, lst1);
                        String add = (BuilderCORE.getValue(stmt, "PointCost"));
                        if (add.contains("/")) {
                            add = add.split("/")[0];
                        }
                        if (!"".equals(add)) {
                            points += Integer.parseInt(add);
                        }
                    }
                } else if (classname[bb].BasedOn != null && classname[bb].BasedOn.equals(BuilderCORE.BASE) && !BuilderCORE.BASE.equals(classList1Holder) && !"".equals(classname[bb].Skills) && !",".equals(classname[bb].Skills)) {
                    String[] lst = ((classname[bb].Skills).split(","));
                    for (String lst1 : lst) {
                        chooseConnection(UseCases.COREdb);
                        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
                        stmt.setString(1, lst1);
                        String add = (BuilderCORE.getValue(stmt, "PointCost"));
                        if (add.contains("/")) {
                            add = add.split("/")[0];
                        }
                        points += Integer.parseInt(add);
                    }
                }
                if (!"".equals(species.Skills) && classname[bb].BasedOn.equals(BuilderCORE.BASE)) {
                    String[] lst = (species.Skills.replaceAll(";", ",").split(","));
                    for (String lst1 : lst) {
                        chooseConnection(UseCases.COREdb);
                        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
                        stmt.setString(1, lst1);
                        String add = BuilderCORE.getValue(stmt, "PointCost");
                        if (add.contains("/")) {
                            add = add.split("/")[0];
                        }
                        points += a * Integer.parseInt(add);
                    }
                }
            } else {
                String[] lst = (species.Skills.replaceAll(";", ",").split(","));
                for (String lst1 : lst) {
                    chooseConnection(UseCases.COREdb);
                    PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
                    stmt.setString(1, lst1);
                    String add = BuilderCORE.getValue(stmt, "PointCost");
                    if (add.contains("/")) {
                        add = add.split("/")[0];
                    }
                    points += Integer.parseInt(add);
                }
            }
        } else if (!"".equals(species.Skills)) {
            String[] lst = (species.Skills.replaceAll(";", ",").split(","));
            for (String lst1 : lst) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
                stmt.setString(1, lst1);
                String add = BuilderCORE.getValue(stmt, "PointCost");
                if (add.contains("/")) {
                    add = add.split("/")[0];
                }
                points += Integer.parseInt(add);
            }
        }
        return points;
    }

    /**
     *
     * @return @throws SQLException
     */
    public static String skillsCanTake() throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM StartingCharacteristics WHERE LifeDomain = ?");
        stmt.setString(1, holdSpecies.Lifedomain.toString());
        String rules = BuilderCORE.getValue(stmt, "StartingNumberOfSkills");
        switch (holdSpecies.Lifedomain) {
            case Humanoid:
                return rules.replace(",", "\n").replace(":", " : ");
            case Fey:
                switch (((AFey) holdSpecies).getMainDomain()) {
                    case Light:
                        if (!outcasts) {
                            return rules.split(";")[0].replace(",", "\n").replace(":", " : ");
                        } else {
                            return rules.split(";")[3].replace(",", "\n").replace(":", " : ");
                        }
                    case Darkness:
                        if (!outcasts) {
                            return rules.split(";")[1].replace(",", "\n").replace(":", " : ");
                        } else {
                            return rules.split(";")[4].replace(",", "\n").replace(":", " : ");
                        }
                    case Twilight:
                        if (!outcasts) {
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

    /**
     *
     * @return @throws SQLException
     */
    public static String classCanTake() throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM StartingCharacteristics WHERE LifeDomain =?");
        stmt.setString(1, holdSpecies.Lifedomain.toString());
        String ClassList = BuilderCORE.getValue(stmt, "ClassTypes");
        String CL[] = ClassList.split(",");
        int[] mx = {holdSpecies.MaxNumberOfLowClases, holdSpecies.MaxNumberOfMidClases, holdSpecies.MaxNumberOfHigClases, 1};
        StringBuilder fintex = new StringBuilder();
        for (int i = 0; i < CL.length; i++) {
            fintex.append(CL[i]).append(": max ").append(mx[i]).append(", current count\n");
        }
        return fintex.toString();
    }

    /**
     *
     * @return
     */
    public static String classLeftModify() {
        String fintex = "";
        switch (holdSpecies.Lifedomain) {
            case Humanoid:
                fintex += ((AHumanoid) holdSpecies).getStandardClass() + "\n" + ((AHumanoid) holdSpecies).getEliteClass() + "\n" + ((AHumanoid) holdSpecies).getLeaderClass() + "\n" + ((AHumanoid) holdSpecies).getUniqueClass();
                break;
            case Fey:
                fintex += ((AFey) holdSpecies).getDiscipleClass() + "\n" + ((AFey) holdSpecies).getArchlordClass() + "\n" + ((AFey) holdSpecies).getParagonClass();
                break;
            case Reptilia:
                fintex += ((AReptilia) holdSpecies).getLesserClass() + "\n" + ((AReptilia) holdSpecies).getCommonClass() + "\n" + ((AReptilia) holdSpecies).getRareClass() + "\n" + ((AReptilia) holdSpecies).getAncientClass();
                break;
            case Biest:
                fintex += ((ABiest) holdSpecies).getCommonClass() + "\n" + ((ABiest) holdSpecies).getGreaterClass() + "\n" + ((ABiest) holdSpecies).getLeaderClass() + "\n" + ((ABiest) holdSpecies).getLegendaryClass();
                break;
            case Insecta:
                fintex += ((AInsecta) holdSpecies).getLesserClass() + "\n" + ((AInsecta) holdSpecies).getCommonClass() + "\n" + ((AInsecta) holdSpecies).getAdvancedClass() + "\n" + ((AInsecta) holdSpecies).getApexClass();
                break;
        }
        return fintex;
    }

    private static int addRemove(int Skl, Boolean Add) {
        int tmp = Skl;
        tmp = Add ? tmp + 1 : tmp - 1;
        return tmp;
    }

    /**
     * writeToDB
     */
    public static void writeToDB() {
        //if(true){fullSkillList1=holdSpecies.Skills;}        
        holdSpecies.Skills = holdSpecies.Skills.substring(0, holdSpecies.Skills.length() - 1);
        String SpecialModificators = "";
        switch (holdSpecies.Lifedomain) {
            case Humanoid:
                SpecialModificators = holdSpecies.SpeciesModifiers;
                break;
            case Fey:
                SpecialModificators = "MainDomain=" + ((AFey) holdSpecies).getMainDomain().getText() + "," + holdSpecies.SpeciesModifiers;
                break;
            case Reptilia:
                SpecialModificators = "MainLineage=" + ((AReptilia) holdSpecies).getMainLineage().getText() + "," + holdSpecies.SpeciesModifiers;
                break;
            case Biest:
                SpecialModificators = "MainKingdom=" + ((ABiest) holdSpecies).getMainKingdom().getText() + "," + "MainRegion=" + ((ABiest) holdSpecies).getMainRegion().getText() + "," + holdSpecies.SpeciesModifiers;
                break;
            case Insecta:
                SpecialModificators = "MainClasification=" + ((AInsecta) holdSpecies).getMainClasification().getText() + "," + "MainOrder=" + ((AInsecta) holdSpecies).getMainOrder().getText() + "," + holdSpecies.SpeciesModifiers;
                break;
        }
        if (holdCulture.CultureName.equals(holdSpecies.SpeciesName)) {
            executeSQL("INSERT INTO `CreatedSpecies`(LifeDomain,CharacteristicGroup,SpeciesName,Skills,SpeciesModifiers) VALUES ('" + holdSpecies.Lifedomain.toString() + "','" + holdSpecies.CharacteristicGroup.toString() + "','" + holdSpecies.SpeciesName + "','" + holdSpecies.Skills + "','" + SpecialModificators + "');");
        }
        executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName) VALUES ('" + holdCulture.CultureName + "','" + holdCulture.SpeciesName + "');");
        for (AClass holdClas : holdClass) {
            if (holdClas.getSkills().length() > 2) {
                holdClas.setSkills(holdClas.getSkills().substring(0, holdClas.getSkills().length() - 1));
            }
            executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + holdClas.ClassName + "','" + holdClas.Skills + "','" + holdSpecies.SpeciesName + "','" + holdCulture.CultureName + "'," + null + ",'" + holdClas.Type + "','" + holdClas.BasedOn + "','" + holdClas.AdditionalCost + "');");
        }
        holdSpecies = null;
    }

    /**
     * writeRosterToDB
     */
    public static void writeRosterToDB() {
        executeSQL("INSERT INTO `CreatedRosters`(RosterName,CultureName,SpeciesName,Roster) VALUES ('" + holdRoster.RosterName + "','" + holdRoster.CultureName + "','" + holdRoster.SpeciesName + "','" + holdRoster.Roster + "');");
    }

    /**
     *
     * @return @throws SQLException
     */
    public static ObservableList populateDropdownsSpecies() throws SQLException {
        ObservableList tmp = FXCollections.observableArrayList();
        tmp.setAll(TOPDROP);
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
        tmp.setAll(TOPDROP);
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
        tmp.setAll(TOPDROP);
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE (SpeciesName =?) AND (CultureName =?)");
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
        tmp.setAll(TOPDROP);
        chooseConnection(UseCases.Userdb);
        String[] columns = {"HeroName"};
        if (TOPDROP.equals(fromwhatcl) && !(TOPDROP.equals(fromwhats) && TOPDROP.equals(fromwhatc))) {
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE (SpeciesName =?) AND (CultureName =?)");
            stmt.setString(1, fromwhats);
            stmt.setString(2, fromwhatc);
            tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        } else {
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE (SpeciesName =?) AND (CultureName =?) AND (BasedOn =?)");
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
        tmp.setAll(TOPDROP);
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
        tmp.setAll(TOPDROP);
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRosters WHERE (SpeciesName =?) AND (CultureName =?)");
        stmt.setString(1, fromwhats);
        stmt.setString(2, fromwhatc);
        String[] columns = {"RosterName"};
        tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        return tmp;
    }

    /**
     * commenceDeleting
     */
    public static void commenceDeleting() {
        executeSQL(tempDeletescript);
        tempDeletescript = null;
    }

    /**
     *
     * @param lifedomain
     * @param type
     * @param add
     */
    public static void recognizeClassDo(LifedomainValue lifedomain, String type, Boolean add) {
        switch (lifedomain) {
            case Humanoid:
                switch (type) {
                    case "Standard Class":
                        ((AHumanoid) holdSpecies).setStandardClass(addRemove(((AHumanoid) holdSpecies).getStandardClass(), add));
                        break;
                    case "Elite Class":
                        ((AHumanoid) holdSpecies).setEliteClass(addRemove(((AHumanoid) holdSpecies).getEliteClass(), add));
                        break;
                    case "Leader Class":
                        ((AHumanoid) holdSpecies).setLeaderClass(addRemove(((AHumanoid) holdSpecies).getLeaderClass(), add));
                        break;
                    case "Unique Class":
                        ((AHumanoid) holdSpecies).setUniqueClass(addRemove(((AHumanoid) holdSpecies).getUniqueClass(), add));
                        break;
                    default:
                        break;
                }
                break;
            case Fey:
                switch (type) {
                    case "Disciple Class":
                        ((AFey) holdSpecies).setDiscipleClass(addRemove(((AFey) holdSpecies).getDiscipleClass(), add));
                        break;
                    case "Archlord Class":
                        ((AFey) holdSpecies).setArchlordClass(addRemove(((AFey) holdSpecies).getArchlordClass(), add));
                        break;
                    case "Paragon Class":
                        ((AFey) holdSpecies).setParagonClass(addRemove(((AFey) holdSpecies).getParagonClass(), add));
                        break;
                    default:
                        break;
                }
                break;
            case Reptilia:
                switch (type) {
                    case "Lesser Class":
                        ((AReptilia) holdSpecies).setLesserClass(addRemove(((AReptilia) holdSpecies).getLesserClass(), add));
                        break;
                    case "Common Class":
                        ((AReptilia) holdSpecies).setCommonClass(addRemove(((AReptilia) holdSpecies).getCommonClass(), add));
                        break;
                    case "Rare Class":
                        ((AReptilia) holdSpecies).setRareClass(addRemove(((AReptilia) holdSpecies).getRareClass(), add));
                        break;
                    case "Ancient Class":
                        ((AReptilia) holdSpecies).setAncientClass(addRemove(((AReptilia) holdSpecies).getAncientClass(), add));
                        break;
                    default:
                        break;
                }
                break;
            case Biest:
                switch (type) {
                    case "Common Class":
                        ((ABiest) holdSpecies).setCommonClass(addRemove(((ABiest) holdSpecies).getCommonClass(), add));
                        break;
                    case "Greater Class":
                        ((ABiest) holdSpecies).setGreaterClass(addRemove(((ABiest) holdSpecies).getGreaterClass(), add));
                        break;
                    case "Leader Class":
                        ((ABiest) holdSpecies).setLeaderClass(addRemove(((ABiest) holdSpecies).getLeaderClass(), add));
                        break;
                    case "Legendary Class":
                        ((ABiest) holdSpecies).setLegendaryClass(addRemove(((ABiest) holdSpecies).getLegendaryClass(), add));
                        break;
                    default:
                        break;
                }
                break;
            case Insecta:
                switch (type) {
                    case "Lesser Class":
                        ((AInsecta) holdSpecies).setLesserClass(addRemove(((AInsecta) holdSpecies).getLesserClass(), add));
                        break;
                    case "Common Class":
                        ((AInsecta) holdSpecies).setCommonClass(addRemove(((AInsecta) holdSpecies).getCommonClass(), add));
                        break;
                    case "Advanced Class":
                        ((AInsecta) holdSpecies).setAdvancedClass(addRemove(((AInsecta) holdSpecies).getAdvancedClass(), add));
                        break;
                    case "Apex Class":
                        ((AInsecta) holdSpecies).setApexClass(addRemove(((AInsecta) holdSpecies).getApexClass(), add));
                        break;
                }
                break;
        }
    }

    /**
     *
     * @return @throws SQLException
     */
    public static ObservableList<String> getSkillSet() throws SQLException {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        if (holdSpecies != null) {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE LifeDomain = ?");
            stmt.setString(1, holdSpecies.Lifedomain.toString());
            String[] columns = {"LifeDomainTree1"};
            tmp = BuilderCORE.getData(stmt, columns, null, 0);
            if (LifedomainValue.Fey == holdSpecies.Lifedomain) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE (LifeDomain = 'Fey' AND (LifeDomainTree1 = ? OR LifeDomainTree1 = ?))");
                switch (((AFey) holdSpecies).getMainDomain()) {
                    case Light:
                        if (!outcasts) {
                            stmt1.setString(1, BuilderCORE.LIGHT);
                            stmt1.setString(2, BuilderCORE.TWILIGHT);
                        } else {
                            stmt1.setString(1, BuilderCORE.LIGHT);
                            stmt1.setString(2, BuilderCORE.LIGHT);
                        }
                        break;
                    case Darkness:
                        if (!outcasts) {
                            stmt1.setString(1, BuilderCORE.DARKNESS);
                            stmt1.setString(2, BuilderCORE.TWILIGHT);
                        } else {
                            stmt1.setString(1, BuilderCORE.DARKNESS);
                            stmt1.setString(2, BuilderCORE.DARKNESS);
                        }
                        break;
                    case Twilight:
                        if (!outcasts) {
                            stmt1.setString(1, BuilderCORE.TWILIGHT);
                            switch (((AFey) holdSpecies).getSecondaryDomain()) {
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
        if (arcana && holdSpecies.Lifedomain == LifedomainValue.Humanoid) {
            a = 3;
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE LifeDomain = 'Fey'");
            String[] columns = {"LifeDomainTree1"};
            tmp1 = BuilderCORE.getData(stmt, columns, null, 0);
        }
        if (outcasts && holdSpecies.Lifedomain == LifedomainValue.Fey) {
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
        ObservableList<String> tmp = FXCollections.observableArrayList();
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree2 FROM Skills WHERE LifeDomain = ? AND LifeDomainTree1 = ?");
        stmt.setString(1, holdSpecies.Lifedomain.name());
        stmt.setString(2, SkillBranch);
        String[] columns = {"LifeDomainTree2"};
        tmp = BuilderCORE.getData(stmt, columns, null, 0);
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
     * @param Skill
     * @param Add
     * @return
     * @throws SQLException
     */
    public static String skillsLeftModify(String Skill, Boolean Add) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
        stmt.setString(1, Skill);
        String SkillType1 = BuilderCORE.getValue(stmt, "LifeDomainTree1");
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills WHERE SkillName = ?");
        stmt1.setString(1, Skill);
        String SkillType3 = BuilderCORE.getValue(stmt1, "LifeDomainTree3");

        switch (SkillType1) {
            case "Genetic Mutation":
                holdSpecies.GeneticMutation = addRemove(holdSpecies.GeneticMutation, Add);
                break;
            case "Environmental Adaptation":
                holdSpecies.EnvironmentalAdaptation = addRemove(holdSpecies.EnvironmentalAdaptation, Add);
                break;
            case "Knowledge and Science":
                holdSpecies.KnowledgeAndScience = addRemove(holdSpecies.KnowledgeAndScience, Add);
                break;
            case BuilderCORE.LIGHT:
                if ("Lesser Traits".equals(SkillType3)) {
                    holdSpecies.LesserTraitsAndPowersOfLight = addRemove(holdSpecies.LesserTraitsAndPowersOfLight, Add);
                }
                if ("Greater Traits".equals(SkillType3)) {
                    holdSpecies.GreaterTraitsAndPowersOfLight = addRemove(holdSpecies.GreaterTraitsAndPowersOfLight, Add);
                }
                break;
            case BuilderCORE.DARKNESS:
                if ("Lesser Traits".equals(SkillType3)) {
                    holdSpecies.LesserTraitsAndPowersOfDarkness = addRemove(holdSpecies.LesserTraitsAndPowersOfDarkness, Add);
                }
                if ("Greater Traits".equals(SkillType3)) {
                    holdSpecies.GreaterTraitsAndPowersOfDarkness = addRemove(holdSpecies.GreaterTraitsAndPowersOfDarkness, Add);
                }
                break;
            case BuilderCORE.TWILIGHT:
                if ("Lesser Traits".equals(SkillType3)) {
                    holdSpecies.LesserTraitsAndPowersOfTwilight = addRemove(holdSpecies.LesserTraitsAndPowersOfTwilight, Add);
                }
                if ("Greater Traits".equals(SkillType3)) {
                    holdSpecies.GreaterTraitsAndPowersOfTwilight = addRemove(holdSpecies.GreaterTraitsAndPowersOfTwilight, Add);
                }
                break;
            case "Reptilia Lineages":
                holdSpecies.ReptiliaLineage = addRemove(holdSpecies.ReptiliaLineage, Add);
                break;
            case "EnvironmentalAdaptability":
                holdSpecies.EnvironmentalAdaptability = addRemove(holdSpecies.EnvironmentalAdaptability, Add);
                break;
            case "ExtremisAffinity":
                holdSpecies.ExtremisAffinity = addRemove(holdSpecies.ExtremisAffinity, Add);
                break;
            case "BiestialKingdoms":
                holdSpecies.setBiestialKingdoms(addRemove(holdSpecies.getBiestialKingdoms(), Add));
                break;
            case "RegionalTraits":
                holdSpecies.setRegionalTraits(addRemove(holdSpecies.getRegionalTraits(), Add));
                break;
            case "SpiritualAndScientificKnowledge":
                holdSpecies.setSpiritualAndScientificKnowledge(addRemove(holdSpecies.getSpiritualAndScientificKnowledge(), Add));
                break;
            case "Clasification":
                holdSpecies.setClasification(addRemove(holdSpecies.getClasification(), Add));
                break;
            case "Order":
                holdSpecies.setOrder(addRemove(holdSpecies.getOrder(), Add));
                break;
            case "GeneticMorphology":
                holdSpecies.setGeneticMorphology(addRemove(holdSpecies.getGeneticMorphology(), Add));
                break;
            case "Knowledge":
                holdSpecies.setKnowledge(addRemove(holdSpecies.getKnowledge(), Add));
                break;
            default:
                break;
        }
        switch (holdSpecies.Lifedomain) {
            case Humanoid:
                if (!arcana) {
                    return holdSpecies.GeneticMutation + "\n" + holdSpecies.EnvironmentalAdaptation + "\n" + holdSpecies.KnowledgeAndScience + "\n";
                } else {
                    return holdSpecies.GeneticMutation + "\n" + holdSpecies.EnvironmentalAdaptation + "\n" + holdSpecies.KnowledgeAndScience + "\n"; //TODO add fey skills
                }
            case Fey:
                switch (((AFey) holdSpecies).getMainDomain()) {
                    case Light:
                        if (!outcasts) {
                            return holdSpecies.LesserTraitsAndPowersOfLight + "\n" + holdSpecies.GreaterTraitsAndPowersOfLight + "\n" + holdSpecies.LesserTraitsAndPowersOfTwilight + "\n";
                        } else {
                            return holdSpecies.LesserTraitsAndPowersOfLight + "\n" + holdSpecies.GreaterTraitsAndPowersOfLight + "\n" + holdSpecies.KnowledgeAndScience + "\n" + holdSpecies.EnvironmentalAdaptation + "\n";
                        }
                    case Darkness:
                        if (!outcasts) {
                            return holdSpecies.LesserTraitsAndPowersOfDarkness + "\n" + holdSpecies.GreaterTraitsAndPowersOfDarkness + "\n" + holdSpecies.LesserTraitsAndPowersOfTwilight + "\n";
                        } else {
                            return holdSpecies.LesserTraitsAndPowersOfDarkness + "\n" + holdSpecies.GreaterTraitsAndPowersOfDarkness + "\n" + holdSpecies.KnowledgeAndScience + "\n" + holdSpecies.EnvironmentalAdaptation + "\n";
                        }
                    case Twilight:
                        if (!outcasts) {
                            return holdSpecies.LesserTraitsAndPowersOfTwilight + "\n" + holdSpecies.GreaterTraitsAndPowersOfTwilight + "\n" + max(holdSpecies.LesserTraitsAndPowersOfLight, holdSpecies.LesserTraitsAndPowersOfDarkness) + "\n";
                        } else {
                            return holdSpecies.LesserTraitsAndPowersOfTwilight + "\n" + holdSpecies.GreaterTraitsAndPowersOfTwilight + "\n" + holdSpecies.KnowledgeAndScience + "\n" + holdSpecies.EnvironmentalAdaptation + "\n";
                        }
                }
                break;
            case Reptilia:
                return holdSpecies.ReptiliaLineage + "\n" + holdSpecies.EnvironmentalAdaptability + "\n" + holdSpecies.ExtremisAffinity + "\n";
            case Biest:
                return holdSpecies.getBiestialKingdoms() + "\n" + holdSpecies.getRegionalTraits() + "\n" + holdSpecies.GeneticMutation + "\n" + holdSpecies.EnvironmentalAdaptation + "\n" + holdSpecies.getSpiritualAndScientificKnowledge() + "\n";
            case Insecta:
                return holdSpecies.getOrder() + "\n" + holdSpecies.getGeneticMorphology() + "\n" + holdSpecies.EnvironmentalAdaptation + "\n" + holdSpecies.getKnowledge() + "\n";
        }
        return "";
    }

    /**
     * searchFreeClassSpot
     */
    public static void searchFreeClassSpot() {
        b = 0;
        for (int i = 0; i < holdClass.length; i++) {
            if (holdClass[i].ClassName == null || "".equals(holdClass[i].ClassName)) {
                b = i;
                i = numberOfClases;
            }
            if (i == numberOfClases - 1 && !(holdClass[i].ClassName == null || "".equals(holdClass[i].ClassName))) {
                b = numberOfClases;
            }
        }
    }

    /**
     * setNumberOfClases
     */
    public static void setNumberOfClases() {
        AClass[] tmp = null;
        if (holdClass != null) {
            tmp = holdClass;
        }
        holdClass = new AClass[numberOfClases + 1];
        for (int i = 0; i < numberOfClases + 1; i++) {
            holdClass[i] = new AClass();
            holdClass[i].clearAClass();
        }
        if (tmp != null) {
            System.arraycopy(tmp, 0, holdClass, 0, numberOfClases);
        }
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
        ObservableList<String> tmp = FXCollections.observableArrayList();
        chooseConnection(UseCases.COREdb);
        //PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT " + itemTypeOneWord + "Name FROM Equipment" + itemTypeOneWord + " WHERE Type = ? ");
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + itemTypeOneWord + " WHERE Type = ? ");
        stmt.setString(1, "Special");
        String[] columns = {itemTypeOneWord + "Name", "SubType"};
        System.out.println(BuilderCORE.getData(stmt, columns, null, 0));
        //tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        //tmp.remove();
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
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + itemType + " WHERE " + itemType + "Name = ?");
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
                stmt.setString(2, getImprovementTypeBasedOnItemSubtype(getItemSubType(itemType, itemWithImprovements.contains("{") ? itemWithImprovements.split(" \\{")[0] : itemWithImprovements)));
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
        stmt.setString(2, getImprovementTypeBasedOnItemSubtype(getItemSubType(itemType, itemName)));
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
        String[] columns = {"SubType"};
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt;
        stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Equipment" + type + " WHERE " + type + "Name = ?");
        stmt.setString(1, itemName);
        String tmp = BuilderCORE.getData(stmt, columns, null, 0).get(0).toString();
        return tmp;
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
            case "Comapnion":
                stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Exotic Animal Attributes'");
                break;
            case "Vehicle":
                stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Chariot and Wagon Upgrades'");
                break;
            case "Heavy Military Weapon":
                stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM EquipmentImprovements WHERE Type = 'Chariot and Wagon Upgrades'");
                break;
            default:
                break;
        }
        tmp.addAll(BuilderCORE.getData(stmt, columns, null, 0));
        return tmp;
    }

    public static ObservableList loadAllSkillsFromDB() throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM Skills");
        String[] columns = {"SkillName", "PointCost", "SkillRules", "Age", "LifeDomain", "LifeDomainTree1", "LifeDomainTree2", "LifeDomainTree3"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        return data;
    }

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
     * @throws SQLException
     * @throws CloneNotSupportedException
     */
    public static void loadSpeciesToHold(String selSpecies) throws SQLException, CloneNotSupportedException {
        ObservableList data = getSpeciesData(selSpecies);
        holdSpecies = ASpecies.createASpecies(LifedomainValue.valueOf(data.get(0).toString().split("\\|")[0]));
        holdSpecies.setLifedomain(LifedomainValue.valueOf(data.get(0).toString().split("\\|")[0]));
        holdSpecies.setCharacteristicGroup(CharacteristicGroup.valueOf(data.get(0).toString().split("\\|")[1]));
        holdSpecies.setSpeciesName(selSpecies);
        holdSpecies.setAge(Integer.parseInt(data.get(0).toString().split("\\|")[2]));
        holdSpecies.setSkills(data.get(0).toString().split("\\|")[3]);
        holdSpecies.setSpeciesModifiers(data.get(0).toString().split("\\|")[4]);
        modifiedHoldSpecies = holdSpecies.getClone();
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
     * @throws java.lang.CloneNotSupportedException
     * @throws java.sql.SQLException
     */
    public static void loadCultureToHold(String selSpecies, String selCulture) throws CloneNotSupportedException, SQLException {
        ObservableList data = getCultureData(selSpecies, selCulture);
        holdCulture.setSpeciesName(selSpecies);
        holdCulture.setCultureName(selCulture);
        holdCulture.setAge(Integer.parseInt(data.get(0).toString().split("\\|")[0]));
        modifiedHoldCulture = holdCulture.getClone();
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
     * @param selClass
     * @param a
     * @throws SQLException
     */
    public static void loadClassToHold(String selSpecies, String selCulture, String selClass, int a) throws SQLException {
        ObservableList data = getClassData(selSpecies, selCulture, selClass);
        holdClass[a].setClassName(selClass);
        holdClass[a].setSkills(data.get(0).toString().split("\\|")[0] + ",");
        holdClass[a].setSpeciesName(selSpecies);
        holdClass[a].setCultureName(selCulture);
        holdClass[a].setAdvancements(data.get(0).toString().split("\\|")[1]);
        holdClass[a].setType(data.get(0).toString().split("\\|")[2]);
        holdClass[a].setBasedOn(data.get(0).toString().split("\\|")[3]);
        holdClass[a].setAdditionalCost(data.get(0).toString().split("\\|")[4]);
        modifiedHoldClass = new AClass[holdClass.length];
        modifiedHoldClass[a] = holdClass[a].getClone();
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
     * @param selHero
     * @throws SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void loadHeroToHold(String selSpecies, String selCulture, String selHero) throws SQLException, CloneNotSupportedException {
        ObservableList data = getHeroData(selSpecies, selCulture, selHero);
        holdHero.setHeroName(selHero);
        holdHero.setSpeciesName(selSpecies);
        holdHero.setCultureName(selCulture);
        holdHero.setAdvancements(data.get(0).toString().split("\\|")[0]);
        holdHero.setBasedOn(data.get(0).toString().split("\\|")[1]);
        holdHero.setAdditionalCost(data.get(0).toString().split("\\|")[2]);
        modifiedHoldHero = holdHero.getClone();
    }

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
     * @param selProgress
     * @throws SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void loadProgressToHold(String selSpecies, String selCulture, String selProgress) throws SQLException, CloneNotSupportedException {
        ObservableList data = getProgressData(selSpecies, selCulture, selProgress);
        holdProgress.setSpeciesName(selSpecies);
        holdProgress.setCultureName(selCulture);
        holdProgress.setProgressName(selProgress);
        holdProgress.setProgress(data.get(0).toString().split("\\|")[0]);
        modifiedHoldProgress = holdProgress.getClone();
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
     * @param selSpecies
     * @param selCulture
     * @param selRoster
     * @throws SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void loadRosterToHold(String selSpecies, String selCulture, String selRoster) throws SQLException, CloneNotSupportedException {
        ObservableList data = getRosterData(selSpecies, selCulture, selRoster);
        holdRoster.setSpeciesName(selSpecies);
        holdRoster.setCultureName(selCulture);
        holdRoster.setRosterName(selRoster);
        holdRoster.setRoster(data.get(0).toString().split("\\|")[0]);
        modifiedHoldRoster = holdRoster.getClone();
    }
// privatize

    /**
     *
     * @param newSpecies
     */
    public static void modifySpeciesName(String newSpecies) {
        executeSQL("UPDATE CreatedSpecies SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedCultures SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedClasses SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedHeroes SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedProgress SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedRosters SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.getSpeciesName() + "'");
    }

    /**
     *
     * @throws SQLException
     */
    public static void modifySpecies() throws SQLException {
        executeSQL("UPDATE CreatedSpecies SET Lifedomain='" + holdSpecies.getLifedomain() + "', CharacteristicGroup='" + holdSpecies.getCharacteristicGroup() + "', SpeciesName='" + holdSpecies.getSpeciesName() + "', Age='" + holdSpecies.getAge() + "', Skills='" + holdSpecies.getSkills() + "', SpeciesModifiers='" + holdSpecies.getSpeciesModifiers() + "' WHERE SpeciesName='" + modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedCultures SET SpeciesName='" + holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedClasses SET SpeciesName='" + holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedHeroes SET SpeciesName='" + holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedProgress SET SpeciesName='" + holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + modifiedHoldSpecies.getSpeciesName() + "'");
        executeSQL("UPDATE CreatedRosters SET SpeciesName='" + holdSpecies.getSpeciesName() + "' WHERE SpeciesName='" + modifiedHoldSpecies.getSpeciesName() + "'");
        checkClassSkillsSpecies();
    }

    private static void checkClassSkillsSpecies() throws SQLException {
        StringBuilder newFoundSkills = new StringBuilder();
        for (String split1 : holdSpecies.getSkills().split(",")) {
            boolean present = false;
            for (String split : modifiedHoldSpecies.getSkills().split(",")) {
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
        stmt.setString(1, holdSpecies.getSpeciesName());
        String[] columns = {"CultureName"};
        ObservableList tmpCult = BuilderCORE.getData(stmt, columns, null, 0);
        for (int i = 0; i < tmpCult.size(); i++) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT ClassName FROM CreatedClasses WHERE SpeciesName=? AND CultureName=?");
            stmt1.setString(1, holdSpecies.getSpeciesName());
            stmt1.setString(2, tmpCult.get(i).toString());
            String[] columns1 = {"ClassName"};
            ObservableList tmpClass = BuilderCORE.getData(stmt1, columns1, null, 0);
            for (int ii = 0; ii < tmpClass.size(); ii++) {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName=? AND CultureName=? AND ClassName=?");
                stmt2.setString(1, holdSpecies.getSpeciesName());
                stmt2.setString(2, tmpCult.get(i).toString());
                stmt2.setString(3, tmpClass.get(ii).toString());
                String tmpSkill = BuilderCORE.getValue(stmt2, "Skills");
                String tmpSkillRep = tmpSkill;
                for (String split : deleteSkills.replaceAll(";", ",").split(",")) {
                    tmpSkillRep = tmpSkillRep.replace(split, "");
                }
                if (tmpSkillRep != null && !tmpSkillRep.equals("")) {
                    tmpSkillRep = tmpSkillRep.replace(",,", ",");
                    if (tmpSkillRep.endsWith(",")) {
                        tmpSkillRep = tmpSkillRep.substring(0, tmpSkillRep.length() - 1);
                    }
                }
                if (!tmpSkill.equals(tmpSkillRep)) {
                    executeSQL("UPDATE CreatedClasses SET Skills='" + tmpSkillRep + "' WHERE SpeciesName='" + holdSpecies.getSpeciesName() + "' AND CultureName='" + tmpCult.get(i).toString() + "' AND ClassName='" + tmpClass.get(ii).toString() + "'");
                }
            }
        }
    }

    /**
     *
     * @param newCulture
     */
    public static void modifyCultureName(String newCulture) {
        executeSQL("UPDATE CreatedCultures SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.getSpeciesName() + "' AND CultureName='" + holdCulture.getCultureName() + "'");
        executeSQL("UPDATE CreatedClasses SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.getSpeciesName() + "' AND CultureName='" + holdCulture.getCultureName() + "'");
        executeSQL("UPDATE CreatedHeroes SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.getSpeciesName() + "' AND CultureName='" + holdCulture.getCultureName() + "'");
        executeSQL("UPDATE CreatedProgress SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.getSpeciesName() + "' AND CultureName='" + holdCulture.getCultureName() + "'");
        executeSQL("UPDATE CreatedRosters SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.getSpeciesName() + "' AND CultureName='" + holdCulture.getCultureName() + "'");
    }

    /**
     * modifyCulture
     */
    public static void modifyCulture() {
        executeSQL("UPDATE CreatedCultures SET Age='" + holdCulture.getAge() + "' WHERE SpeciesName='" + holdCulture.getSpeciesName() + "' AND CultureName='" + holdCulture.getCultureName() + "'");
    }

    /**
     *
     * @param newClass
     * @param a
     */
    public static void modifyClassName(String newClass, int a) {
        executeSQL("UPDATE CreatedClasses SET BasedOn='" + newClass + "' WHERE SpeciesName='" + holdClass[a].getSpeciesName() + "' AND CultureName='" + holdClass[a].getCultureName() + "' AND BasedOn='" + holdClass[a].getClassName() + "'");
        executeSQL("UPDATE CreatedHeroes SET BasedOn='" + newClass + "' WHERE SpeciesName='" + holdClass[a].getSpeciesName() + "' AND CultureName='" + holdClass[a].getCultureName() + "' AND BasedOn='" + holdClass[a].getClassName() + "'");
        executeSQL("UPDATE CreatedClasses SET ClassName='" + newClass + "' WHERE SpeciesName='" + holdClass[a].getSpeciesName() + "' AND CultureName='" + holdClass[a].getCultureName() + "' AND ClassName='" + holdClass[a].getClassName() + "'");
    }

    /**
     *
     * @param a
     * @throws java.sql.SQLException
     */
    public static void modifyClass(int a) throws SQLException {
        if (holdClass[a].getSkills().endsWith(",")) {
            holdClass[a].setSkills(holdClass[a].getSkills().substring(0, holdClass[a].getSkills().length() - 1));
        }
        executeSQL("UPDATE CreatedClasses SET ClassName='" + holdClass[a].getClassName() + "', Skills='" + holdClass[a].getSkills() + "', SpeciesName='" + holdClass[a].getSpeciesName() + "', CultureName='" + holdClass[a].getCultureName() + "', Advancements='" + holdClass[a].getAdvancements() + "', Type='" + holdClass[a].getType() + "', BasedOn='" + holdClass[a].getBasedOn() + "', AdditionalCost='" + holdClass[a].getAdditionalCost() + "' WHERE SpeciesName='" + modifiedHoldClass[a].getSpeciesName() + "' AND CultureName='" + modifiedHoldClass[a].getCultureName() + "' AND ClassName='" + modifiedHoldClass[a].getClassName() + "'");
        executeSQL("UPDATE CreatedHeroes SET BasedOn='" + holdClass[a].getClassName() + "' WHERE SpeciesName='" + modifiedHoldClass[a].getSpeciesName() + "' AND CultureName='" + modifiedHoldClass[a].getCultureName() + "' AND BasedOn='" + modifiedHoldClass[a].getClassName() + "'");
        checkClassSkillsBasedon(a);
    }

    private static void checkClassSkillsBasedon(int a) throws SQLException {
        StringBuilder newFoundSkills = new StringBuilder();
        for (String split1 : holdClass[a].getSkills().split(",")) {
            boolean present = false;
            for (String split : modifiedHoldClass[a].getSkills().split(",")) {
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
        stmt1.setString(1, holdClass[a].getSpeciesName());
        stmt1.setString(2, holdClass[a].getCultureName());
        stmt1.setString(3, holdClass[a].getClassName());
        String[] columns1 = {"ClassName"};
        ObservableList tmpBased = BuilderCORE.getData(stmt1, columns1, null, 0);
        for (int i = 0; i < tmpBased.size(); i++) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName=? AND CultureName=? AND ClassName=?");
            stmt2.setString(1, holdClass[a].getSpeciesName());
            stmt2.setString(2, holdClass[a].getCultureName());
            stmt2.setString(3, tmpBased.get(i).toString());
            String tmpSkill = BuilderCORE.getValue(stmt2, "Skills");
            String tmpSkillRep = tmpSkill;
            for (String split : deleteSkills.replaceAll(";", ",").split(",")) {
                tmpSkillRep = tmpSkillRep.replace(split, "");
            }
            if (tmpSkillRep != null && !tmpSkillRep.equals("")) {
                tmpSkillRep = tmpSkillRep.replace(",,", ",");
                if (tmpSkillRep.endsWith(",")) {
                    tmpSkillRep = tmpSkillRep.substring(0, tmpSkillRep.length() - 1);
                }
            }
            if (!tmpSkill.equals(tmpSkillRep)) {
                executeSQL("UPDATE CreatedClasses SET Skills='" + tmpSkillRep + "' WHERE SpeciesName='" + holdClass[a].getSpeciesName() + "' AND CultureName='" + holdClass[a].getCultureName() + "' AND ClassName='" + tmpBased.get(i).toString() + "'");
            }
        }
    }

    /**
     *
     * @param newHero
     */
    public static void modifyHeroName(String newHero) {
        executeSQL("UPDATE CreatedHeroes SET HeroName='" + newHero + "' WHERE SpeciesName='" + holdHero.getSpeciesName() + "' AND CultureName='" + holdHero.getCultureName() + "' AND HeroName='" + holdHero.getHeroName() + "'");
    }

    /**
     * modifyHero
     */
    public static void modifyHero() {
        executeSQL("UPDATE CreatedHeroes SET HeroName='" + holdHero.getHeroName() + "', SpeciesName='" + holdHero.getSpeciesName() + "', CultureName='" + holdHero.getCultureName() + "', Advancements='" + holdHero.getAdvancements() + "', BasedOn='" + holdHero.getBasedOn() + "', AdditionalCost='" + holdHero.getAdditionalCost() + "', WHERE SpeciesName='" + modifiedHoldHero.getSpeciesName() + "' AND CultureName='" + modifiedHoldHero.getCultureName() + "' AND HeroName='" + modifiedHoldHero.getHeroName() + "'");
    }

    /**
     *
     * @param newProgress
     */
    public static void modifyProgressName(String newProgress) {
        executeSQL("UPDATE CreatedProgress SET ProgressName='" + newProgress + "' WHERE SpeciesName='" + holdProgress.getSpeciesName() + "' AND CultureName='" + holdProgress.getCultureName() + "' AND ProgressName='" + holdProgress.getProgressName() + "'");
    }

    /**
     * modifyProgress
     */
    public static void modifyProgress() {
        executeSQL("UPDATE CreatedProgress SET ProgressName='" + holdProgress.getProgressName() + "', SpeciesName='" + holdProgress.getSpeciesName() + "', CultureName='" + holdProgress.getCultureName() + "', Progress='" + holdProgress.getProgress() + "' WHERE SpeciesName='" + modifiedHoldProgress.getSpeciesName() + "' AND CultureName='" + modifiedHoldProgress.getCultureName() + "' AND ProgressName='" + modifiedHoldProgress.getProgressName() + "'");
    }

    /**
     *
     * @param newRoster
     */
    public static void modifyRosterName(String newRoster) {
        executeSQL("UPDATE CreatedRosters SET RosterName='" + newRoster + "' WHERE SpeciesName='" + holdRoster.getSpeciesName() + "' AND CultureName='" + holdRoster.getCultureName() + "' AND RosterName='" + holdRoster.getRosterName() + "'");
    }

    /**
     * modifyRoster
     */
    public static void modifyRoster() {
        executeSQL("UPDATE CreatedProgress SET RosterName='" + holdRoster.getRosterName() + "', SpeciesName='" + holdRoster.getSpeciesName() + "', CultureName='" + holdRoster.getCultureName() + "', Roster='" + holdRoster.getRoster() + "' WHERE SpeciesName='" + modifiedHoldRoster.getSpeciesName() + "' AND CultureName='" + modifiedHoldRoster.getCultureName() + "' AND RosterName='" + modifiedHoldRoster.getRosterName() + "'");
    }

    /**
     * ASpecies
     */
    public static abstract class ASpecies {

        @Getter
        @Setter
        private LifedomainValue Lifedomain;

        @Getter
        @Setter
        private CharacteristicGroup CharacteristicGroup;

        @Getter
        @Setter
        private String SpeciesName, Skills, SpeciesModifiers;

        @Getter
        @Setter
        private int Age;

        @Getter
        @Setter
        private int GeneticMutation, EnvironmentalAdaptation, KnowledgeAndScience,
                LesserTraitsAndPowersOfLight, GreaterTraitsAndPowersOfLight,
                LesserTraitsAndPowersOfDarkness, GreaterTraitsAndPowersOfDarkness,
                LesserTraitsAndPowersOfTwilight, GreaterTraitsAndPowersOfTwilight,
                ReptiliaLineage, EnvironmentalAdaptability, ExtremisAffinity,
                BiestialKingdoms, RegionalTraits, SpiritualAndScientificKnowledge,
                Clasification, Order, GeneticMorphology, Knowledge;

        @Getter
        @Setter
        private int NumberOfSkills, MaxNumberOfLowClases, MaxNumberOfMidClases, MaxNumberOfHigClases;

        /**
         * ASpecies
         */
        public ASpecies() {
        }

        /**
         *
         * @param lifedomainValue
         * @return
         */
        public static ASpecies createASpecies(LifedomainValue lifedomainValue) {
            switch (lifedomainValue) {
                case Humanoid:
                    return new AHumanoid();
                case Fey:
                    return new AFey();
                case Reptilia:
                    return new AReptilia();
                case Biest:
                    return new ABiest();
                case Insecta:
                    return new AInsecta();
            }
            throw new IllegalArgumentException("Species" + lifedomainValue + " is not recognized.");
        }

        /**
         *
         * @param Skills
         * @return
         */
        public String addSkills(String Skills) {
            this.Skills += Skills;
            return this.Skills;
        }

        /**
         *
         * @param LesserTraitsAndPowersOfLight
         * @param GreaterTraitsAndPowersOfLight
         * @param LesserTraitsAndPowersOfDarkness
         * @param GreaterTraitsAndPowersOfDarkness
         * @param LesserTraitsAndPowersOfTwilight
         * @param GreaterTraitsAndPowersOfTwilight
         */
        public void setAllTraitsAndPowers(int LesserTraitsAndPowersOfLight, int GreaterTraitsAndPowersOfLight, int LesserTraitsAndPowersOfDarkness, int GreaterTraitsAndPowersOfDarkness, int LesserTraitsAndPowersOfTwilight, int GreaterTraitsAndPowersOfTwilight) {
            this.LesserTraitsAndPowersOfLight = LesserTraitsAndPowersOfLight;
            this.GreaterTraitsAndPowersOfLight = GreaterTraitsAndPowersOfLight;
            this.LesserTraitsAndPowersOfDarkness = LesserTraitsAndPowersOfDarkness;
            this.GreaterTraitsAndPowersOfDarkness = GreaterTraitsAndPowersOfDarkness;
            this.LesserTraitsAndPowersOfTwilight = LesserTraitsAndPowersOfTwilight;
            this.GreaterTraitsAndPowersOfTwilight = GreaterTraitsAndPowersOfTwilight;
        }

        /**
         *
         * @return
         */
        public ASpecies getClone() {
            ASpecies aClone = createASpecies(this.Lifedomain);
            aClone.setAll(this.SpeciesName, this.Skills, this.SpeciesModifiers, this.GeneticMutation, this.EnvironmentalAdaptation, this.KnowledgeAndScience, this.LesserTraitsAndPowersOfLight, this.GreaterTraitsAndPowersOfLight, this.LesserTraitsAndPowersOfDarkness, this.GreaterTraitsAndPowersOfDarkness, this.LesserTraitsAndPowersOfTwilight, this.GreaterTraitsAndPowersOfTwilight, this.ReptiliaLineage, this.EnvironmentalAdaptability, this.ExtremisAffinity, this.BiestialKingdoms, this.RegionalTraits, this.SpiritualAndScientificKnowledge, this.Clasification, this.Order, this.GeneticMorphology, this.Knowledge, this.NumberOfSkills, this.MaxNumberOfLowClases, this.MaxNumberOfMidClases, this.MaxNumberOfHigClases);
            return aClone;
        }

        private void setAll(String SpeciesName, String Skills, String SpeciesModifiers, int GeneticMutation, int EnvironmentalAdaptation, int KnowledgeAndScience, int LesserTraitsAndPowersOfLight, int GreaterTraitsAndPowersOfLight, int LesserTraitsAndPowersOfDarkness, int GreaterTraitsAndPowersOfDarkness, int LesserTraitsAndPowersOfTwilight, int GreaterTraitsAndPowersOfTwilight, int ReptiliaLineage, int EnvironmentalAdaptability, int ExtremisAffinity, int BiestialKingdoms, int RegionalTraits, int SpiritualAndScientificKnowledge, int Clasification, int Order, int GeneticMorphology, int Knowledge, int NumberOfSkills, int MaxNumberOfLowClases, int MaxNumberOfMidClases, int MaxNumberOfHigClases) {
            this.SpeciesName = SpeciesName;
            this.Skills = Skills;
            this.SpeciesModifiers = SpeciesModifiers;
            this.GeneticMutation = GeneticMutation;
            this.EnvironmentalAdaptation = EnvironmentalAdaptation;
            this.KnowledgeAndScience = KnowledgeAndScience;
            this.LesserTraitsAndPowersOfLight = LesserTraitsAndPowersOfLight;
            this.GreaterTraitsAndPowersOfLight = GreaterTraitsAndPowersOfLight;
            this.LesserTraitsAndPowersOfDarkness = LesserTraitsAndPowersOfDarkness;
            this.GreaterTraitsAndPowersOfDarkness = GreaterTraitsAndPowersOfDarkness;
            this.LesserTraitsAndPowersOfTwilight = LesserTraitsAndPowersOfTwilight;
            this.GreaterTraitsAndPowersOfTwilight = GreaterTraitsAndPowersOfTwilight;
            this.ReptiliaLineage = ReptiliaLineage;
            this.EnvironmentalAdaptability = EnvironmentalAdaptability;
            this.ExtremisAffinity = ExtremisAffinity;
            this.BiestialKingdoms = BiestialKingdoms;
            this.RegionalTraits = RegionalTraits;
            this.SpiritualAndScientificKnowledge = SpiritualAndScientificKnowledge;
            this.Clasification = Clasification;
            this.Order = Order;
            this.GeneticMorphology = GeneticMorphology;
            this.Knowledge = Knowledge;
            this.NumberOfSkills = NumberOfSkills;
            this.MaxNumberOfLowClases = MaxNumberOfLowClases;
            this.MaxNumberOfMidClases = MaxNumberOfMidClases;
            this.MaxNumberOfHigClases = MaxNumberOfHigClases;
        }
    }

    /**
     * AHumanoid
     */
    public static class AHumanoid extends ASpecies {

        @Getter
        @Setter
        private int StandardClass, EliteClass, LeaderClass, UniqueClass;

        /**
         * AHumanoid
         */
        public AHumanoid() {
        }

    }

    /**
     * AFey
     */
    public static class AFey extends ASpecies {

        @Getter
        @Setter
        private MainDomainValue MainDomain, SecondaryDomain;

        @Getter
        @Setter
        private int DiscipleClass, ArchlordClass, ParagonClass;

        /**
         * AFey
         */
        public AFey() {
        }

    }

    /**
     * AReptilia
     */
    public static class AReptilia extends ASpecies {

        @Getter
        @Setter
        private MainLineageValue MainLineage;

        @Getter
        @Setter
        private int LesserClass, CommonClass, RareClass, AncientClass;

        /**
         * AReptilia
         */
        public AReptilia() {
        }

    }

    /**
     * ABiest
     */
    public static class ABiest extends ASpecies {

        @Getter
        @Setter
        private MainKingdomValue MainKingdom;

        @Getter
        @Setter
        private MainRegionValue MainRegion;

        @Getter
        @Setter
        private int CommonClass, GreaterClass, LeaderClass, LegendaryClass;

        /**
         * ABiest
         */
        public ABiest() {
        }

    }

    /**
     * AInsecta
     */
    public static class AInsecta extends ASpecies {

        @Getter
        @Setter
        private MainClasificationValue MainClasification;

        @Getter
        @Setter
        private MainOrderValue MainOrder;

        @Getter
        @Setter
        private int LesserClass, CommonClass, AdvancedClass, ApexClass;

        /**
         * AInsecta
         */
        public AInsecta() {
        }

    }

    /**
     * ACulture
     */
    public static class ACulture {

        @Getter
        @Setter
        private String SpeciesName, CultureName;

        @Getter
        @Setter
        private int Age;

        /**
         * aCulture
         */
        public ACulture() {
        }

        /**
         *
         * @param SpeciesName
         * @param CultureName
         * @param Age
         */
        public ACulture(String SpeciesName, String CultureName, int Age) {
            this.SpeciesName = SpeciesName;
            this.CultureName = CultureName;
            this.Age = Age;
        }

        /**
         *
         * @return
         */
        public ACulture getClone() {
            ACulture aClone = new ACulture(this.SpeciesName, this.CultureName, this.Age);
            return aClone;
        }
    }

    /**
     * AClass
     */
    public static class AClass {

        @Getter
        @Setter
        private String ClassName, Skills, SpeciesName, CultureName, Advancements, Type, BasedOn, AdditionalCost;

        /**
         * clearAClass
         */
        public void clearAClass() {
            this.ClassName = this.Skills = this.SpeciesName = this.CultureName = this.Advancements = this.Type = this.BasedOn = this.AdditionalCost = "";
        }

        /**
         *
         * @param Skills
         * @return
         */
        public String addSkills(String Skills) {
            this.Skills += Skills;
            return this.Skills;
        }

        /**
         *
         * @return
         */
        public AClass getClone() {
            AClass aClone = new AClass();
            aClone.ClassName = this.ClassName;
            aClone.Skills = this.Skills;
            aClone.SpeciesName = this.SpeciesName;
            aClone.CultureName = this.CultureName;
            aClone.Advancements = this.Advancements;
            aClone.Type = this.Type;
            aClone.BasedOn = this.BasedOn;
            aClone.AdditionalCost = this.AdditionalCost;
            return aClone;
        }
    }

    /**
     * AHero
     */
    public static class AHero {

        @Getter
        @Setter
        private String HeroName, SpeciesName, CultureName, Advancements, BasedOn, AdditionalCost;

        /**
         *
         * @return
         */
        public AHero getClone() {
            AHero aClone = new AHero();
            return aClone;
        }
    }

    /**
     * AProgress
     */
    public static class AProgress {

        @Getter
        @Setter
        private String SpeciesName, CultureName, ProgressName, Progress;

        /**
         *
         * @return
         */
        public AProgress getClone() {
            AProgress aClone = new AProgress();
            return aClone;
        }
    }

    /**
     * ARoster
     */
    public static class ARoster {

        @Getter
        @Setter
        private String RosterName, SpeciesName, CultureName, Roster;

        /**
         *
         * @return
         */
        public ARoster getClone() {
            ARoster aClone = new ARoster();
            return aClone;
        }
    }

}
