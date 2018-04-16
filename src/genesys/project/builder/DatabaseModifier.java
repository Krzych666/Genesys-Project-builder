/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import genesys.project.builder.BuilderCORE.Enmuerations.DBTables;
import genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue;
import static genesys.project.builder.BuilderCORE.Enmuerations.MainDomainValue;
import genesys.project.builder.BuilderCORE.Enmuerations.MainLineageValue;
import genesys.project.builder.BuilderCORE.Enmuerations.UseCases;
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
    public static ACulture holdCulture;

    /**
     * holdClass
     */
    public static AClass[] holdClass;

    /**
     * holdHero
     */
    public static AHero holdHero;

    /**
     * holdProgress
     */
    public static AProgress holdProgress;

    /**
     * holdRoster
     */
    public static ARoster holdRoster;

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
                holdSpecies.SpeciesModifiers = "";  //TODO
                break;
            case Insecta:
                holdSpecies.SpeciesModifiers = "";  //TODO
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
        if (holdSpecies.getSkills() == null || "".equals(holdSpecies.getSkills())) {
            return null;
        }
        if (HoldSkills == null || "".equals(HoldSkills)) {
            return null;
        }
        fullSkillList1 = "";
        ruledskills.clear();
        String[] lst = HoldSkills.split(";");
        String[] lstref = HoldSkills.split(";");
        for (int i = 0; i < lst.length; i++) {
            if (!lstref[i].equals("")) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT SkillRules FROM Skills WHERE SkillName = ?");
                stmt.setString(1, lstref[i]);
                String[] skl = ((BuilderCORE.getValue(stmt, "SkillRules")).split(";"));
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
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
                    PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomainTree2 FROM Skills WHERE SkillName = ?");
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
            for (int i = 0; i < numberOfClases; i++) {
                if (holdClass[i].getClassName().equals(name)) {
                    if (holdClass[i].getSkills() != null && !holdClass[i].getSkills().equals("null,") && !holdClass[i].getSkills().equals("") && !holdClass[i].getSkills().equals(";")) {
                        fullSkillList1 += holdClass[i].getSkills() + getBaseAddedSkills(holdClass[i].getBasedOn());
                    } else {
                        fullSkillList1 += getBaseAddedSkills(holdClass[i].getBasedOn());
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
                    String[] lst = ((classname[bb].Skills).split(";"));
                    for (int s = 0; s < classname.length; s++) {
                        if (classname[bb].BasedOn.equals(classname[s].ClassName)) {
                            source = s;
                        }
                    }
                    points += a * baseAddedCost(lifeDomain, species, classname, source, points);
                    for (String lst1 : lst) {
                        chooseConnection(UseCases.COREdb);
                        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
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
                    String[] lst = ((classname[bb].Skills).split(";"));
                    for (String lst1 : lst) {
                        chooseConnection(UseCases.COREdb);
                        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
                        stmt.setString(1, lst1);
                        String add = (BuilderCORE.getValue(stmt, "PointCost"));
                        if (add.contains("/")) {
                            add = add.split("/")[0];
                        }
                        points += Integer.parseInt(add);
                    }
                }
                if (!"".equals(species.Skills) && classname[bb].BasedOn.equals(BuilderCORE.BASE)) {
                    String[] lst = (species.Skills.split(";"));
                    for (String lst1 : lst) {
                        chooseConnection(UseCases.COREdb);
                        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
                        stmt.setString(1, lst1);
                        String add = BuilderCORE.getValue(stmt, "PointCost");
                        if (add.contains("/")) {
                            add = add.split("/")[0];
                        }
                        points += a * Integer.parseInt(add);
                    }
                }
            } else {
                String[] lst = (species.Skills.split(";"));
                for (String lst1 : lst) {
                    chooseConnection(UseCases.COREdb);
                    PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
                    stmt.setString(1, lst1);
                    String add = BuilderCORE.getValue(stmt, "PointCost");
                    if (add.contains("/")) {
                        add = add.split("/")[0];
                    }
                    points += Integer.parseInt(add);
                }
            }
        } else if (!"".equals(species.Skills)) {
            String[] lst = (species.Skills.split(";"));
            for (String lst1 : lst) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
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
        String[] show = new String[21];
        int sh = 0;
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT StartingNumberOfSkills FROM StartingCharacteristics WHERE LifeDomain = ?");
        stmt.setString(1, holdSpecies.Lifedomain.toString());
        String rules = BuilderCORE.getValue(stmt, "StartingNumberOfSkills");
        if (holdSpecies.Lifedomain == LifedomainValue.Humanoid) {
            show = rules.split(",");
            sh = 3;
        }
        if (holdSpecies.Lifedomain == LifedomainValue.Fey) {
            int a = 0;
            int aa = 0;
            sh = 21;
            for (int i = 0; i < sh; i++) {
                show[i] = rules.split(";")[a].split(",")[aa];
                aa++;
                if (i == 2) {
                    a = 1;
                    aa = 0;
                }
                if (i == 5) {
                    a = 2;
                    aa = 0;
                }
                if (i == 8) {
                    a = 3;
                    aa = 0;
                }
                if (i == 12) {
                    a = 4;
                    aa = 0;
                }
                if (i == 16) {
                    a = 5;
                    aa = 0;
                }
            }
        }
        if (holdSpecies.Lifedomain == LifedomainValue.Reptilia) {
            show[0] = rules;
            sh = 1;
        }
        if (holdSpecies.Lifedomain == LifedomainValue.Biest) {
            System.out.println("Biest not supported yet");  //TODO
        }
        if (holdSpecies.Lifedomain == LifedomainValue.Insecta) {
            System.out.println("Insecta not supported yet");  //TODO
        }
        String[] fintex = {"", "", "", "", "", ""};
        int q = 0;
        for (int i = 0; i < sh; i++) {
            if (i < 3) {
                q = 0;
            }
            if (i >= 3 && i < 6) {
                q = 1;
            }
            if (i >= 6 && i < 9) {
                q = 2;
            }
            if (i >= 9 && i < 13) {
                q = 3;
            }
            if (i >= 13 && i < 17) {
                q = 4;
            }
            if (i >= 17 && i < 21) {
                q = 5;
            }
            fintex[q] += show[i] + "\n";
        }
        int ch = 0;
        switch (holdSpecies.Lifedomain) {
            case Humanoid:
                ch = 0;
                break;
            case Fey:
                switch (((AFey) holdSpecies).MainDomain) {
                    case Light:
                        if (!outcasts) {
                            ch = 0;
                        } else {
                            ch = 3;
                        }
                        break;
                    case Darkness:
                        if (!outcasts) {
                            ch = 1;
                        } else {
                            ch = 4;
                        }
                        break;
                    case Twilight:
                        if (!outcasts) {
                            ch = 2;
                        } else {
                            ch = 5;
                        }
                        break;
                }
                break;
            case Reptilia:
                ch = 0;
                break;
            case Biest:  //TODO
                ch = 0;
                break;
            case Insecta:  //TODO
                ch = 0;
                break;
        }
        return fintex[ch];
    }

    /**
     *
     * @return @throws SQLException
     */
    public static String classCanTake() throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ClassTypes FROM StartingCharacteristics WHERE LifeDomain =?");
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
                fintex += ((AHumanoid) holdSpecies).StandardClass + "\n" + ((AHumanoid) holdSpecies).EliteClass + "\n" + ((AHumanoid) holdSpecies).LeaderClass + "\n" + ((AHumanoid) holdSpecies).UniqueClass;
                break;
            case Fey:
                fintex += ((AFey) holdSpecies).DiscipleClass + "\n" + ((AFey) holdSpecies).ArchlordClass + "\n" + ((AFey) holdSpecies).ParagonClass;
                break;
            case Reptilia:
                fintex += ((AReptilia) holdSpecies).LesserClass + "\n" + ((AReptilia) holdSpecies).CommonClass + "\n" + ((AReptilia) holdSpecies).RareClass + "\n" + ((AReptilia) holdSpecies).AncientClass;
                break;
            case Biest: //TODO
                break;
            case Insecta:
                break;  //TODO
        }
        return fintex;
    }

    private static int addRemove(int Skl, Boolean Add) {
        int tmp = Skl;
        if (Add) {
            tmp++;
        }
        if (!Add) {
            tmp--;
        }
        return tmp;
    }

    /**
     * writeToDB
     */
    public static void writeToDB() {
        //if(true){fullSkillList1=holdSpecies.Skills;}        
        holdSpecies.Skills = holdSpecies.Skills.substring(0, holdSpecies.Skills.length() - 1);
        String SpMod = "";
        switch (holdSpecies.Lifedomain) {
            case Humanoid:
                SpMod = holdSpecies.SpeciesModifiers;
                break;
            case Fey:
                SpMod = "MainDomain=" + ((AFey) holdSpecies).MainDomain.getText() + "," + holdSpecies.SpeciesModifiers;
                break;
            case Reptilia:
                SpMod = "MainLineage=" + ((AReptilia) holdSpecies).MainLineage.getText() + "," + holdSpecies.SpeciesModifiers;
                break;
            case Biest:
                SpMod = "Main_Biest" + "," + holdSpecies.SpeciesModifiers;  //TODO
                break;
            case Insecta:
                SpMod = "Main_Insecta" + "," + holdSpecies.SpeciesModifiers;    //TODO
                break;
        }
        if (holdCulture.CultureName.equals(holdSpecies.SpeciesName)) {
            executeSQL("INSERT INTO `CreatedSpecies`(LifeDomain,SpeciesName,Skills,SpeciesModifiers) VALUES ('" + holdSpecies.Lifedomain.toString() + "','" + holdSpecies.SpeciesName + "','" + holdSpecies.Skills + "','" + SpMod + "');");
        }
        executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName) VALUES ('" + holdCulture.CultureName + "','" + holdCulture.SpeciesName + "');");
        for (int i = 0; i < numberOfClases; i++) {
            if (holdClass[i].getSkills().length() > 2) {
                holdClass[i].setSkills(holdClass[i].getSkills().substring(0, holdClass[i].getSkills().length() - 1));
            }
            executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + holdClass[i].ClassName + "','" + holdClass[i].Skills + "','" + holdSpecies.SpeciesName + "','" + holdCulture.CultureName + "'," + null + ",'" + holdClass[i].Type + "','" + holdClass[i].BasedOn + "','" + holdClass[i].AdditionalCost + "');");
        }
        holdSpecies = null;
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
        tmp.addAll(BuilderCORE.getData(stmt, "SpeciesName", null));
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
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT CultureName FROM CreatedCultures WHERE SpeciesName = ?");
        stmt.setString(1, fromwhat);
        tmp.addAll(BuilderCORE.getData(stmt, "CultureName", null));
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
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ClassName FROM CreatedClasses WHERE (SpeciesName =?) AND (CultureName =?)");
        stmt.setString(1, fromwhats);
        stmt.setString(2, fromwhatc);
        tmp.addAll(BuilderCORE.getData(stmt, "ClassName", null));
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
        if (TOPDROP.equals(fromwhatcl) && !(TOPDROP.equals(fromwhats) && TOPDROP.equals(fromwhatc))) {
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT HeroName FROM CreatedHeroes WHERE (SpeciesName =?) AND (CultureName =?)");
            stmt.setString(1, fromwhats);
            stmt.setString(2, fromwhatc);
            tmp.addAll(BuilderCORE.getData(stmt, "HeroName", null));
        } else {
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT HeroName FROM CreatedHeroes WHERE (SpeciesName =?) AND (CultureName =?) AND (BasedOn =?)");
            stmt.setString(1, fromwhats);
            stmt.setString(2, fromwhatc);
            stmt.setString(3, fromwhatcl);
            tmp.addAll(BuilderCORE.getData(stmt, "HeroName", null));
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
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ProgressName FROM CreatedProgress WHERE (SpeciesName =?) AND (CultureName =?)");
        stmt.setString(1, fromwhats);
        stmt.setString(2, fromwhatc);
        tmp.addAll(BuilderCORE.getData(stmt, "ProgressName", null));
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
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT RosterName FROM CreatedRosters WHERE (SpeciesName =?) AND (CultureName =?)");
        stmt.setString(1, fromwhats);
        stmt.setString(2, fromwhatc);
        tmp.addAll(BuilderCORE.getData(stmt, "RosterName", null));
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
     * @param type
     * @param add
     */
    public static void recognizeClassDo(String type, Boolean add) {
        switch (type) {
            case "Standard Class":
                ((AHumanoid) holdSpecies).StandardClass = addRemove(((AHumanoid) holdSpecies).StandardClass, add);
                break;
            case "Elite Class":
                ((AHumanoid) holdSpecies).EliteClass = addRemove(((AHumanoid) holdSpecies).EliteClass, add);
                break;
            case "Leader Class":
                ((AHumanoid) holdSpecies).LeaderClass = addRemove(((AHumanoid) holdSpecies).LeaderClass, add);
                break;
            case "Unique Class":
                ((AHumanoid) holdSpecies).UniqueClass = addRemove(((AHumanoid) holdSpecies).UniqueClass, add);
                break;
            case "Disciple Class":
                ((AFey) holdSpecies).DiscipleClass = addRemove(((AFey) holdSpecies).DiscipleClass, add);
                break;
            case "Archlord Class":
                ((AFey) holdSpecies).ArchlordClass = addRemove(((AFey) holdSpecies).ArchlordClass, add);
                break;
            case "Paragon Class":
                ((AFey) holdSpecies).ParagonClass = addRemove(((AFey) holdSpecies).ParagonClass, add);
                break;
            case "Lesser Class":
                ((AReptilia) holdSpecies).LesserClass = addRemove(((AReptilia) holdSpecies).LesserClass, add);
                break;
            case "Common Class":
                ((AReptilia) holdSpecies).CommonClass = addRemove(((AReptilia) holdSpecies).CommonClass, add);
                break;
            case "Rare Class":
                ((AReptilia) holdSpecies).RareClass = addRemove(((AReptilia) holdSpecies).RareClass, add);
                break;
            case "Ancient Class":
                ((AReptilia) holdSpecies).AncientClass = addRemove(((AReptilia) holdSpecies).AncientClass, add);
                break;
            default:
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
            tmp = BuilderCORE.getData(stmt, "LifeDomainTree1", null);
            if (LifedomainValue.Fey == holdSpecies.Lifedomain) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE (LifeDomain = 'Fey' AND (LifeDomainTree1 = ? OR LifeDomainTree1 = ?))");
                switch (((AFey) holdSpecies).MainDomain) {
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
                            switch (((AFey) holdSpecies).SecondaryDomain) {
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
                tmp = BuilderCORE.getData(stmt1, "LifeDomainTree1", null);

            }
        }
        int a = 0;
        ObservableList<String> tmp1 = FXCollections.observableArrayList();
        if (arcana && holdSpecies.Lifedomain == LifedomainValue.Humanoid) {
            a = 3;
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE LifeDomain = 'Fey'");
            tmp1 = BuilderCORE.getData(stmt, "LifeDomainTree1", null);
        }
        if (outcasts && holdSpecies.Lifedomain == LifedomainValue.Fey) {
            a = 2;
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE (LifeDomain = 'Humanoid' AND LifeDomainTree1 != 'Genetic Mutation')");
            tmp1 = BuilderCORE.getData(stmt, "LifeDomainTree1", null);
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

    public static ObservableList<String> getSubSkillSet(String SkillBranch) throws SQLException {
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree2 FROM Skills WHERE LifeDomainTree1 = ?");
        stmt.setString(1, SkillBranch);
        ObservableList<String> tmp = BuilderCORE.getData(stmt, "LifeDomainTree2", null);
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
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomainTree1 FROM Skills WHERE SkillName = ?");
        stmt.setString(1, Skill);
        String SkillType1 = BuilderCORE.getValue(stmt, "LifeDomainTree1");
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomainTree3 FROM Skills WHERE SkillName = ?");
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
            case "Draconic":
                holdSpecies.TraitsFromDraconicLineage = addRemove(holdSpecies.TraitsFromDraconicLineage, Add);
                break;
            default:
                break;
        }
        String fintex = "";
        if (holdSpecies.Lifedomain == LifedomainValue.Humanoid) {
            if (!arcana) {
                fintex = holdSpecies.GeneticMutation + "\n" + holdSpecies.EnvironmentalAdaptation + "\n" + holdSpecies.KnowledgeAndScience + "\n";
            } else {
                fintex = holdSpecies.GeneticMutation + "\n" + holdSpecies.EnvironmentalAdaptation + "\n" + holdSpecies.KnowledgeAndScience + "\n"; //TODO add fey skills
            }
        }
        if (holdSpecies.Lifedomain == LifedomainValue.Fey) {
            switch (((AFey) holdSpecies).MainDomain) {
                case Light:
                    if (!outcasts) {
                        fintex = holdSpecies.LesserTraitsAndPowersOfLight + "\n" + holdSpecies.GreaterTraitsAndPowersOfLight + "\n" + holdSpecies.LesserTraitsAndPowersOfTwilight + "\n";
                    } else {
                        fintex = holdSpecies.LesserTraitsAndPowersOfLight + "\n" + holdSpecies.GreaterTraitsAndPowersOfLight + "\n" + holdSpecies.KnowledgeAndScience + "\n" + holdSpecies.EnvironmentalAdaptation + "\n";
                    }
                    break;
                case Darkness:
                    if (!outcasts) {
                        fintex = holdSpecies.LesserTraitsAndPowersOfDarkness + "\n" + holdSpecies.GreaterTraitsAndPowersOfDarkness + "\n" + holdSpecies.LesserTraitsAndPowersOfTwilight + "\n";
                    } else {
                        fintex = holdSpecies.LesserTraitsAndPowersOfDarkness + "\n" + holdSpecies.GreaterTraitsAndPowersOfDarkness + "\n" + holdSpecies.KnowledgeAndScience + "\n" + holdSpecies.EnvironmentalAdaptation + "\n";
                    }
                    break;
                case Twilight:
                    if (!outcasts) {
                        fintex = holdSpecies.LesserTraitsAndPowersOfTwilight + "\n" + holdSpecies.GreaterTraitsAndPowersOfTwilight + "\n" + max(holdSpecies.LesserTraitsAndPowersOfLight, holdSpecies.LesserTraitsAndPowersOfDarkness) + "\n";
                    } else {
                        fintex = holdSpecies.LesserTraitsAndPowersOfTwilight + "\n" + holdSpecies.GreaterTraitsAndPowersOfTwilight + "\n" + holdSpecies.KnowledgeAndScience + "\n" + holdSpecies.EnvironmentalAdaptation + "\n";
                    }
                    break;
            }
        }
        if (holdSpecies.Lifedomain == LifedomainValue.Reptilia) {
            fintex = holdSpecies.TraitsFromDraconicLineage + "\n";
        }

        if (holdSpecies.Lifedomain == LifedomainValue.Biest) {
            //TODO
        }
        if (holdSpecies.Lifedomain == LifedomainValue.Insecta) {
            //TODO
        }

        return fintex;
    }

    /**
     * searchFreeClassSpot
     */
    public static void searchFreeClassSpot() {
        b = 0;
        for (int i = 0; i < numberOfClases; i++) {
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

    //change to private and do setters/gettera
    /**
     *
     * @param selSpecies
     * @throws SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void loadSpecies(String selSpecies) throws SQLException, CloneNotSupportedException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomain FROM CreatedSpecies WHERE SpeciesName =?");
        stmt.setString(1, selSpecies);
        holdSpecies = ASpecies.createASpecies(LifedomainValue.valueOf(BuilderCORE.getValue(stmt, "LifeDomain")));
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomain FROM CreatedSpecies WHERE SpeciesName =?");
        stmt1.setString(1, selSpecies);
        holdSpecies.Lifedomain = LifedomainValue.valueOf(BuilderCORE.getValue(stmt1, "LifeDomain"));
        holdSpecies.SpeciesName = selSpecies;
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedSpecies WHERE SpeciesName =?");
        stmt2.setString(1, selSpecies);
        holdSpecies.Skills = BuilderCORE.getValue(stmt2, "Skills");
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT SpeciesModifiers FROM CreatedSpecies WHERE SpeciesName =?");
        stmt3.setString(1, selSpecies);
        holdSpecies.SpeciesModifiers = BuilderCORE.getValue(stmt3, "SpeciesModifiers");
        modifiedHoldSpecies = holdSpecies.getClone();
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @throws java.lang.CloneNotSupportedException
     */
    public static void loadCulture(String selSpecies, String selCulture) throws CloneNotSupportedException {
        holdCulture.setSpeciesName(selSpecies);
        holdCulture.setCultureName(selCulture);
        modifiedHoldCulture = holdCulture.getClone();
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selClass
     * @param a
     * @throws SQLException
     */
    public static void loadClass(String selSpecies, String selCulture, String selClass, int a) throws SQLException {
        holdClass[a].ClassName = selClass;
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedClasses WHERE SpeciesName=? AND CultureName =? AND ClassName =?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        stmt.setString(3, selClass);
        holdClass[a].Skills = BuilderCORE.getValue(stmt, "Skills") + ",";
        holdClass[a].SpeciesName = selSpecies;
        holdClass[a].CultureName = selCulture;
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT Advancements FROM CreatedClasses WHERE SpeciesName=? AND CultureName =? AND ClassName =?");
        stmt1.setString(1, selSpecies);
        stmt1.setString(2, selCulture);
        stmt1.setString(3, selClass);
        holdClass[a].Advancements = BuilderCORE.getValue(stmt1, "Advancements");
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT Type FROM CreatedClasses WHERE SpeciesName=? AND CultureName =? AND ClassName =?");
        stmt2.setString(1, selSpecies);
        stmt2.setString(2, selCulture);
        stmt2.setString(3, selClass);
        holdClass[a].Type = BuilderCORE.getValue(stmt2, "Type");
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedClasses WHERE SpeciesName=? AND CultureName =? AND ClassName =?");
        stmt3.setString(1, selSpecies);
        stmt3.setString(2, selCulture);
        stmt3.setString(3, selClass);
        holdClass[a].BasedOn = BuilderCORE.getValue(stmt3, "BasedOn");
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt4 = BuilderCORE.getConnection().prepareStatement("SELECT AdditionalCost FROM CreatedClasses WHERE SpeciesName=? AND CultureName =? AND ClassName =?");
        stmt4.setString(1, selSpecies);
        stmt4.setString(2, selCulture);
        stmt4.setString(3, selClass);
        holdClass[a].AdditionalCost = BuilderCORE.getValue(stmt4, "AdditionalCost");
        modifiedHoldClass = new AClass[holdClass.length];
        modifiedHoldClass[a] = holdClass[a].getClone();
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selHero
     * @throws SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void loadHero(String selSpecies, String selCulture, String selHero) throws SQLException, CloneNotSupportedException {
        holdHero.HeroName = selHero;
        holdHero.SpeciesName = selSpecies;
        holdHero.CultureName = selCulture;
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Advancements FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND HeroName =?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        stmt.setString(3, selHero);
        holdHero.Advancements = BuilderCORE.getValue(stmt, "Advancements");
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND HeroName =?");
        stmt1.setString(1, selSpecies);
        stmt1.setString(2, selCulture);
        stmt1.setString(3, selHero);
        holdHero.BasedOn = BuilderCORE.getValue(stmt1, "BasedOn");
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT AdditionalCost FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND HeroName =?");
        stmt2.setString(1, selSpecies);
        stmt2.setString(2, selCulture);
        stmt2.setString(3, selHero);
        holdHero.AdditionalCost = BuilderCORE.getValue(stmt2, "AdditionalCost");
        modifiedHoldHero = holdHero.getClone();
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selProgress
     * @throws SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void loadProgress(String selSpecies, String selCulture, String selProgress) throws SQLException, CloneNotSupportedException {
        holdProgress.SpeciesName = selSpecies;
        holdProgress.CultureName = selCulture;
        holdProgress.ProgressName = selProgress;
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Progress FROM CreatedProgress WHERE SpeciesName =? AND CultureName =? AND ProgressName =?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        stmt.setString(3, selProgress);
        holdProgress.Progress = BuilderCORE.getValue(stmt, "Progress");
        modifiedHoldProgress = holdProgress.getClone();
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selRoster
     * @throws SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void loadRoster(String selSpecies, String selCulture, String selRoster) throws SQLException, CloneNotSupportedException {
        holdRoster.SpeciesName = selSpecies;
        holdRoster.CultureName = selCulture;
        holdRoster.RosterName = selRoster;
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Roster FROM CreatedRosters WHERE SpeciesName =? AND CultureName =? AND RosterName =?");
        stmt.setString(1, selSpecies);
        stmt.setString(2, selCulture);
        stmt.setString(3, selRoster);
        holdRoster.Roster = BuilderCORE.getValue(stmt, "Roster");
        modifiedHoldRoster = holdRoster.getClone();
    }
// privatize

    /**
     *
     * @param newSpecies
     */
    public static void modifySpeciesName(String newSpecies) {
        executeSQL("UPDATE CreatedSpecies SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedCultures SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedClasses SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedHeroes SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedProgress SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedRosters SET SpeciesName='" + newSpecies + "' WHERE SpeciesName='" + holdSpecies.SpeciesName + "'");
    }

    /**
     *
     * @throws SQLException
     */
    public static void modifySpecies() throws SQLException {
        executeSQL("UPDATE CreatedSpecies SET Lifedomain='" + holdSpecies.Lifedomain + "', SpeciesName='" + holdSpecies.SpeciesName + "', Skills='" + holdSpecies.Skills + "', SpeciesModifiers='" + holdSpecies.SpeciesModifiers + "' WHERE SpeciesName='" + modifiedHoldSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedCultures SET SpeciesName='" + holdSpecies.SpeciesName + "' WHERE SpeciesName='" + modifiedHoldSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedClasses SET SpeciesName='" + holdSpecies.SpeciesName + "' WHERE SpeciesName='" + modifiedHoldSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedHeroes SET SpeciesName='" + holdSpecies.SpeciesName + "' WHERE SpeciesName='" + modifiedHoldSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedProgress SET SpeciesName='" + holdSpecies.SpeciesName + "' WHERE SpeciesName='" + modifiedHoldSpecies.SpeciesName + "'");
        executeSQL("UPDATE CreatedRosters SET SpeciesName='" + holdSpecies.SpeciesName + "' WHERE SpeciesName='" + modifiedHoldSpecies.SpeciesName + "'");
        checkClassSkillsSpecies();
    }

    private static void checkClassSkillsSpecies() throws SQLException {
        StringBuilder newFoundSkills = new StringBuilder();
        for (String split1 : holdSpecies.getSkills().split(";")) {
            boolean present = false;
            for (String split : modifiedHoldSpecies.getSkills().split(";")) {
                if (split.equals(split1)) {
                    present = true;
                }
            }
            if (!present) {
                newFoundSkills.append(split1).append(";");
            }
        }
        String deleteSkills = newFoundSkills.toString();
        if (deleteSkills.length() >= 2) {
            deleteSkills = deleteSkills.substring(0, deleteSkills.length() - 1);
        }
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT CultureName FROM CreatedClasses WHERE SpeciesName=?");
        stmt.setString(1, holdSpecies.SpeciesName);
        ObservableList tmpCult = BuilderCORE.getData(stmt, "CultureName", null);
        for (int i = 0; i < tmpCult.size(); i++) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT ClassName FROM CreatedClasses WHERE SpeciesName=? AND CultureName=?");
            stmt1.setString(1, holdSpecies.SpeciesName);
            stmt1.setString(2, tmpCult.get(i).toString());
            ObservableList tmpClass = BuilderCORE.getData(stmt1, "ClassName", null);
            for (int ii = 0; ii < tmpClass.size(); ii++) {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedClasses WHERE SpeciesName=? AND CultureName=? AND ClassName=?");
                stmt2.setString(1, holdSpecies.SpeciesName);
                stmt2.setString(2, tmpCult.get(i).toString());
                stmt2.setString(3, tmpClass.get(ii).toString());
                String tmpSkill = BuilderCORE.getValue(stmt2, "Skills");
                String tmpSkillRep = tmpSkill;
                for (String split : deleteSkills.split(";")) {
                    tmpSkillRep = tmpSkillRep.replace(split, "");
                }
                if (tmpSkillRep != null && !tmpSkillRep.equals("")) {
                    tmpSkillRep = tmpSkillRep.replace(";;", ";");
                    if (tmpSkillRep.endsWith(";")) {
                        tmpSkillRep = tmpSkillRep.substring(0, tmpSkillRep.length() - 1);
                    }
                }
                if (!tmpSkill.equals(tmpSkillRep)) {
                    executeSQL("UPDATE CreatedClasses SET Skills='" + tmpSkillRep + "' WHERE SpeciesName='" + holdSpecies.SpeciesName + "' AND CultureName='" + tmpCult.get(i).toString() + "' AND ClassName='" + tmpClass.get(ii).toString() + "'");
                }
            }
        }
    }

    /**
     *
     * @param newCulture
     */
    public static void modifyCultureName(String newCulture) {
        executeSQL("UPDATE CreatedCultures SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.SpeciesName + "' AND CultureName='" + holdCulture.CultureName + "'");
        executeSQL("UPDATE CreatedClasses SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.SpeciesName + "' AND CultureName='" + holdCulture.CultureName + "'");
        executeSQL("UPDATE CreatedHeroes SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.SpeciesName + "' AND CultureName='" + holdCulture.CultureName + "'");
        executeSQL("UPDATE CreatedProgress SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.SpeciesName + "' AND CultureName='" + holdCulture.CultureName + "'");
        executeSQL("UPDATE CreatedRosters SET CultureName='" + newCulture + "' WHERE SpeciesName='" + holdCulture.SpeciesName + "' AND CultureName='" + holdCulture.CultureName + "'");
    }

    /**
     *
     * @param newClass
     * @param a
     */
    public static void modifyClassName(String newClass, int a) {
        executeSQL("UPDATE CreatedClasses SET BasedOn='" + newClass + "' WHERE SpeciesName='" + holdClass[a].SpeciesName + "' AND CultureName='" + holdClass[a].CultureName + "' AND BasedOn='" + holdClass[a].ClassName + "'");
        executeSQL("UPDATE CreatedHeroes SET BasedOn='" + newClass + "' WHERE SpeciesName='" + holdClass[a].SpeciesName + "' AND CultureName='" + holdClass[a].CultureName + "' AND BasedOn='" + holdClass[a].ClassName + "'");
        executeSQL("UPDATE CreatedClasses SET ClassName='" + newClass + "' WHERE SpeciesName='" + holdClass[a].SpeciesName + "' AND CultureName='" + holdClass[a].CultureName + "' AND ClassName='" + holdClass[a].ClassName + "'");
    }

    /**
     *
     * @param a
     * @throws java.sql.SQLException
     */
    public static void modifyClass(int a) throws SQLException {
        if (holdClass[a].Skills.endsWith(";")) {
            holdClass[a].Skills = holdClass[a].Skills.substring(0, holdClass[a].Skills.length() - 1);
        }
        executeSQL("UPDATE CreatedClasses SET ClassName='" + holdClass[a].ClassName + "', Skills='" + holdClass[a].Skills + "', SpeciesName='" + holdClass[a].SpeciesName + "', CultureName='" + holdClass[a].CultureName + "', Advancements='" + holdClass[a].Advancements + "', Type='" + holdClass[a].Type + "', BasedOn='" + holdClass[a].BasedOn + "', AdditionalCost='" + holdClass[a].AdditionalCost + "' WHERE SpeciesName='" + modifiedHoldClass[a].SpeciesName + "' AND CultureName='" + modifiedHoldClass[a].CultureName + "' AND ClassName='" + modifiedHoldClass[a].ClassName + "'");
        executeSQL("UPDATE CreatedHeroes SET BasedOn='" + holdClass[a].ClassName + "' WHERE SpeciesName='" + modifiedHoldClass[a].SpeciesName + "' AND CultureName='" + modifiedHoldClass[a].CultureName + "' AND BasedOn='" + modifiedHoldClass[a].ClassName + "'");
        checkClassSkillsBasedon(a);
    }

    private static void checkClassSkillsBasedon(int a) throws SQLException {
        StringBuilder newFoundSkills = new StringBuilder();
        for (String split1 : holdClass[a].getSkills().split(";")) {
            boolean present = false;
            for (String split : modifiedHoldClass[a].getSkills().split(";")) {
                if (split.equals(split1)) {
                    present = true;
                }
            }
            if (!present) {
                newFoundSkills.append(split1).append(";");
            }
        }
        String deleteSkills = newFoundSkills.toString();
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT ClassName FROM CreatedClasses WHERE SpeciesName=? AND CultureName=? AND BasedOn=?");
        stmt1.setString(1, holdClass[a].SpeciesName);
        stmt1.setString(2, holdClass[a].CultureName);
        stmt1.setString(3, holdClass[a].ClassName);
        ObservableList tmpBased = BuilderCORE.getData(stmt1, "ClassName", null);
        for (int i = 0; i < tmpBased.size(); i++) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedClasses WHERE SpeciesName=? AND CultureName=? AND ClassName=?");
            stmt2.setString(1, holdClass[a].SpeciesName);
            stmt2.setString(2, holdClass[a].CultureName);
            stmt2.setString(3, tmpBased.get(i).toString());
            String tmpSkill = BuilderCORE.getValue(stmt2, "Skills");
            String tmpSkillRep = tmpSkill;
            for (String split : deleteSkills.split(";")) {
                tmpSkillRep = tmpSkillRep.replace(split, "");
            }
            if (tmpSkillRep != null && !tmpSkillRep.equals("")) {
                tmpSkillRep = tmpSkillRep.replace(",,", ",");
                if (tmpSkillRep.endsWith(";")) {
                    tmpSkillRep = tmpSkillRep.substring(0, tmpSkillRep.length() - 1);
                }
            }
            if (!tmpSkill.equals(tmpSkillRep)) {
                executeSQL("UPDATE CreatedClasses SET Skills='" + tmpSkillRep + "' WHERE SpeciesName='" + holdClass[a].SpeciesName + "' AND CultureName='" + holdClass[a].CultureName + "' AND ClassName='" + tmpBased.get(i).toString() + "'");
            }
        }
    }

    /**
     *
     * @param newHero
     */
    public static void modifyHeroName(String newHero) {
        executeSQL("UPDATE CreatedHeroes SET HeroName='" + newHero + "' WHERE SpeciesName='" + holdHero.SpeciesName + "' AND CultureName='" + holdHero.CultureName + "' AND HeroName='" + holdHero.HeroName + "'");
    }

    /**
     * modifyHero
     */
    public static void modifyHero() {
        executeSQL("UPDATE CreatedHeroes SET HeroName='" + holdHero.HeroName + "', SpeciesName='" + holdHero.SpeciesName + "', CultureName='" + holdHero.CultureName + "', Advancements='" + holdHero.Advancements + "', BasedOn='" + holdHero.BasedOn + "', AdditionalCost='" + holdHero.AdditionalCost + "', WHERE SpeciesName='" + modifiedHoldHero.SpeciesName + "' AND CultureName='" + modifiedHoldHero.CultureName + "' AND HeroName='" + modifiedHoldHero.HeroName + "'");
    }

    /**
     *
     * @param newProgress
     */
    public static void modifyProgressName(String newProgress) {
        executeSQL("UPDATE CreatedProgress SET ProgressName='" + newProgress + "' WHERE SpeciesName='" + holdProgress.SpeciesName + "' AND CultureName='" + holdProgress.CultureName + "' AND ProgressName='" + holdProgress.ProgressName + "'");
    }

    /**
     * modifyProgress
     */
    public static void modifyProgress() {
        executeSQL("UPDATE CreatedProgress SET ProgressName='" + holdProgress.ProgressName + "', SpeciesName='" + holdProgress.SpeciesName + "', CultureName='" + holdProgress.CultureName + "', Progress='" + holdProgress.Progress + "' WHERE SpeciesName='" + modifiedHoldProgress.SpeciesName + "' AND CultureName='" + modifiedHoldProgress.CultureName + "' AND ProgressName='" + modifiedHoldProgress.ProgressName + "'");
    }

    /**
     *
     * @param newRoster
     */
    public static void modifyRosterName(String newRoster) {
        executeSQL("UPDATE CreatedRosters SET RosterName='" + newRoster + "' WHERE SpeciesName='" + holdRoster.SpeciesName + "' AND CultureName='" + holdRoster.CultureName + "' AND RosterName='" + holdRoster.RosterName + "'");
    }

    /**
     * modifyRoster
     */
    public static void modifyRoster() {
        executeSQL("UPDATE CreatedProgress SET RosterName='" + holdRoster.RosterName + "', SpeciesName='" + holdRoster.SpeciesName + "', CultureName='" + holdRoster.CultureName + "', Roster='" + holdRoster.Roster + "' WHERE SpeciesName='" + modifiedHoldRoster.SpeciesName + "' AND CultureName='" + modifiedHoldRoster.CultureName + "' AND RosterName='" + modifiedHoldRoster.RosterName + "'");
    }

    /**
     * ASpecies
     */
    public static abstract class ASpecies {

        private LifedomainValue Lifedomain;
        private String SpeciesName, Skills, SpeciesModifiers;
        private int GeneticMutation, EnvironmentalAdaptation, KnowledgeAndScience,
                LesserTraitsAndPowersOfLight, GreaterTraitsAndPowersOfLight,
                LesserTraitsAndPowersOfDarkness, GreaterTraitsAndPowersOfDarkness,
                LesserTraitsAndPowersOfTwilight, GreaterTraitsAndPowersOfTwilight,
                TraitsFromDraconicLineage;
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
         * @return
         */
        public String getSpeciesName() {
            return SpeciesName;
        }

        /**
         *
         * @param SpeciesName
         */
        public void setSpeciesName(String SpeciesName) {
            this.SpeciesName = SpeciesName;
        }

        /**
         *
         * @return
         */
        public LifedomainValue getLifedomain() {
            return Lifedomain;
        }

        /**
         *
         * @param Lifedomain
         */
        public void setLifedomain(LifedomainValue Lifedomain) {
            this.Lifedomain = Lifedomain;
        }

        /**
         *
         * @return
         */
        public String getSkills() {
            return Skills;
        }

        /**
         *
         * @param Skills
         */
        public void setSkills(String Skills) {
            this.Skills = Skills;
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
        public int getNumberOfSkills() {
            return NumberOfSkills;
        }

        /**
         *
         * @param NumberOfSkills
         */
        public void setNumberOfSkills(int NumberOfSkills) {
            this.NumberOfSkills = NumberOfSkills;
        }

        /**
         *
         * @return
         */
        public String getSpeciesModifiers() {
            return SpeciesModifiers;
        }

        /**
         *
         * @param SpeciesModifiers
         */
        public void setSpeciesModifiers(String SpeciesModifiers) {
            this.SpeciesModifiers = SpeciesModifiers;
        }

        /**
         *
         * @return
         */
        public int getGeneticMutation() {
            return GeneticMutation;
        }

        /**
         *
         * @param GeneticMutation
         */
        public void setGeneticMutation(int GeneticMutation) {
            this.GeneticMutation = GeneticMutation;
        }

        /**
         *
         * @return
         */
        public int getEnvironmentalAdaptation() {
            return EnvironmentalAdaptation;
        }

        /**
         *
         * @param EnvironmentalAdaptation
         */
        public void setEnvironmentalAdaptation(int EnvironmentalAdaptation) {
            this.EnvironmentalAdaptation = EnvironmentalAdaptation;
        }

        /**
         *
         * @return
         */
        public int getKnowledgeAndScience() {
            return KnowledgeAndScience;
        }

        /**
         *
         * @param KnowledgeAndScience
         */
        public void setKnowledgeAndScience(int KnowledgeAndScience) {
            this.KnowledgeAndScience = KnowledgeAndScience;
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
        public int getLesserTraitsAndPowersOfLight() {
            return LesserTraitsAndPowersOfLight;
        }

        /**
         *
         * @param LesserTraitsAndPowersOfLight
         */
        public void setLesserTraitsAndPowersOfLight(int LesserTraitsAndPowersOfLight) {
            this.LesserTraitsAndPowersOfLight = LesserTraitsAndPowersOfLight;
        }

        /**
         *
         * @return
         */
        public int getGreaterTraitsAndPowersOfLight() {
            return GreaterTraitsAndPowersOfLight;
        }

        /**
         *
         * @param GreaterTraitsAndPowersOfLight
         */
        public void setGreaterTraitsAndPowersOfLight(int GreaterTraitsAndPowersOfLight) {
            this.GreaterTraitsAndPowersOfLight = GreaterTraitsAndPowersOfLight;
        }

        /**
         *
         * @return
         */
        public int getLesserTraitsAndPowersOfDarkness() {
            return LesserTraitsAndPowersOfDarkness;
        }

        /**
         *
         * @param LesserTraitsAndPowersOfDarkness
         */
        public void setLesserTraitsAndPowersOfDarkness(int LesserTraitsAndPowersOfDarkness) {
            this.LesserTraitsAndPowersOfDarkness = LesserTraitsAndPowersOfDarkness;
        }

        /**
         *
         * @return
         */
        public int getGreaterTraitsAndPowersOfDarkness() {
            return GreaterTraitsAndPowersOfDarkness;
        }

        /**
         *
         * @param GreaterTraitsAndPowersOfDarkness
         */
        public void setGreaterTraitsAndPowersOfDarkness(int GreaterTraitsAndPowersOfDarkness) {
            this.GreaterTraitsAndPowersOfDarkness = GreaterTraitsAndPowersOfDarkness;
        }

        /**
         *
         * @return
         */
        public int getLesserTraitsAndPowersOfTwilight() {
            return LesserTraitsAndPowersOfTwilight;
        }

        /**
         *
         * @param LesserTraitsAndPowersOfTwilight
         */
        public void setLesserTraitsAndPowersOfTwilight(int LesserTraitsAndPowersOfTwilight) {
            this.LesserTraitsAndPowersOfTwilight = LesserTraitsAndPowersOfTwilight;
        }

        /**
         *
         * @return
         */
        public int getGreaterTraitsAndPowersOfTwilight() {
            return GreaterTraitsAndPowersOfTwilight;
        }

        /**
         *
         * @param GreaterTraitsAndPowersOfTwilight
         */
        public void setGreaterTraitsAndPowersOfTwilight(int GreaterTraitsAndPowersOfTwilight) {
            this.GreaterTraitsAndPowersOfTwilight = GreaterTraitsAndPowersOfTwilight;
        }

        /**
         *
         * @return
         */
        public int getTraitsFromDraconicLineage() {
            return TraitsFromDraconicLineage;
        }

        /**
         *
         * @param TraitsFromDraconicLineage
         */
        public void setTraitsFromDraconicLineage(int TraitsFromDraconicLineage) {
            this.TraitsFromDraconicLineage = TraitsFromDraconicLineage;
        }

        /**
         *
         * @return
         */
        public int getMaxNumberOfLowClases() {
            return MaxNumberOfLowClases;
        }

        /**
         *
         * @param MaxNumberOfLowClases
         */
        public void setMaxNumberOfLowClases(int MaxNumberOfLowClases) {
            this.MaxNumberOfLowClases = MaxNumberOfLowClases;
        }

        /**
         *
         * @return
         */
        public int getMaxNumberOfMidClases() {
            return MaxNumberOfMidClases;
        }

        /**
         *
         * @param MaxNumberOfMidClases
         */
        public void setMaxNumberOfMidClases(int MaxNumberOfMidClases) {
            this.MaxNumberOfMidClases = MaxNumberOfMidClases;
        }

        /**
         *
         * @return
         */
        public int getMaxNumberOfHigClases() {
            return MaxNumberOfHigClases;
        }

        /**
         *
         * @param MaxNumberOfHigClases
         */
        public void setMaxNumberOfHigClases(int MaxNumberOfHigClases) {
            this.MaxNumberOfHigClases = MaxNumberOfHigClases;
        }

        /**
         *
         * @return
         */
        public ASpecies getClone() {
            ASpecies aClone = createASpecies(this.Lifedomain);
            aClone.setAll(this.SpeciesName, this.Skills, this.SpeciesModifiers, this.GeneticMutation, this.EnvironmentalAdaptation, this.KnowledgeAndScience, this.LesserTraitsAndPowersOfLight, this.GreaterTraitsAndPowersOfLight, this.LesserTraitsAndPowersOfDarkness, this.GreaterTraitsAndPowersOfDarkness, this.LesserTraitsAndPowersOfTwilight, this.GreaterTraitsAndPowersOfTwilight, this.TraitsFromDraconicLineage, this.NumberOfSkills, this.MaxNumberOfLowClases, this.MaxNumberOfMidClases, this.MaxNumberOfHigClases);
            return aClone;
        }

        private void setAll(String SpeciesName, String Skills, String SpeciesModifiers, int GeneticMutation, int EnvironmentalAdaptation, int KnowledgeAndScience, int LesserTraitsAndPowersOfLight, int GreaterTraitsAndPowersOfLight, int LesserTraitsAndPowersOfDarkness, int GreaterTraitsAndPowersOfDarkness, int LesserTraitsAndPowersOfTwilight, int GreaterTraitsAndPowersOfTwilight, int TraitsFromDraconicLineage, int NumberOfSkills, int MaxNumberOfLowClases, int MaxNumberOfMidClases, int MaxNumberOfHigClases) {
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
            this.TraitsFromDraconicLineage = TraitsFromDraconicLineage;
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

        private int StandardClass, EliteClass, LeaderClass, UniqueClass;

        /**
         * AHumanoid
         */
        public AHumanoid() {
        }
    ;

    }

    /**
     *AFey
     */
    public static class AFey extends ASpecies {

        private MainDomainValue MainDomain, SecondaryDomain;
        private int DiscipleClass, ArchlordClass, ParagonClass;//privatize

        /**
         * AFey
         */
        public AFey() {
        }

        ;

        /**
         *
         * @return
         */
        public MainDomainValue getMainDomain() {
            return MainDomain;
        }

        ;

        /**
         *
         * @param MainDomain
         */
        public void setMainDomain(MainDomainValue MainDomain) {
            this.MainDomain = MainDomain;
        }

        /**
         *
         * @return
         */
        public MainDomainValue getSecondaryDomain() {
            return SecondaryDomain;
        }

        ;

        /**
         *
         * @param SecondaryDomain
         */
        public void setSecondaryDomain(MainDomainValue SecondaryDomain) {
            this.SecondaryDomain = SecondaryDomain;
        }
    }

    /**
     * AReptilia
     */
    public static class AReptilia extends ASpecies {

        private MainLineageValue MainLineage;
        private int LesserClass, CommonClass, RareClass, AncientClass;

        /**
         * AReptilia
         */
        public AReptilia() {
        }

        ;

        /**
         *
         * @return
         */
        public MainLineageValue getMainDomain() {
            return MainLineage;
        }

        ;

        /**
         *
         * @param MainLineage
         */
        public void setMainLineage(MainLineageValue MainLineage) {
            this.MainLineage = MainLineage;
        }
    }

    /**
     * ABiest
     */
    public static class ABiest extends ASpecies {

        /**
         * ABiest
         */
        public ABiest() {
        }
    ;

    }
                                
    /**
     *AInsecta
     */
    public static class AInsecta extends ASpecies {

        /**
         * AInsecta
         */
        public AInsecta() {
        }
    ;

    }
        
    /**
     *ACulture
     */
    public static class ACulture {

        private String SpeciesName, CultureName;

        /**
         * aCulture
         */
        public ACulture() {
        }

        ;

        /**
         *
         * @param SpeciesName
         * @param CultureName
         */
        public ACulture(String SpeciesName, String CultureName) {
            this.SpeciesName = SpeciesName;
            this.CultureName = CultureName;
        }

        ;

        /**
         *
         * @return
         */
        public String getSpeciesName() {
            return SpeciesName;
        }

        /**
         *
         * @param SpeciesName
         */
        public void setSpeciesName(String SpeciesName) {
            this.SpeciesName = SpeciesName;
        }

        /**
         *
         * @return
         */
        public String getCultureName() {
            return CultureName;
        }

        /**
         *
         * @param CultureName
         */
        public void setCultureName(String CultureName) {
            this.CultureName = CultureName;
        }

        /**
         *
         * @return
         */
        public ACulture getClone() {
            ACulture aClone = new ACulture(this.SpeciesName, this.CultureName);
            return aClone;
        }
    }

    /**
     * AClass
     */
    public static class AClass {

        private String ClassName, Skills, SpeciesName, CultureName, Advancements, Type, BasedOn, AdditionalCost;

        /**
         * clearAClass
         */
        public void clearAClass() {
            this.ClassName = this.Skills = this.SpeciesName = this.CultureName = this.Advancements = this.Type = this.BasedOn = this.AdditionalCost = "";
        }

        /**
         *
         * @return
         */
        public String getClassName() {
            return ClassName;
        }

        /**
         *
         * @param ClassName
         */
        public void setClassName(String ClassName) {
            this.ClassName = ClassName;
        }

        /**
         *
         * @return
         */
        public String getSkills() {
            return Skills;
        }

        /**
         *
         * @param Skills
         */
        public void setSkills(String Skills) {
            this.Skills = Skills;
        }

        /**
         *
         * @param Skills
         */
        public void addSkills(String Skills) {
            this.Skills += Skills;
        }

        /**
         *
         * @return
         */
        public String getBasedOn() {
            return BasedOn;
        }

        /**
         *
         * @param BasedOn
         */
        public void setBasedOn(String BasedOn) {
            this.BasedOn = BasedOn;
        }

        /**
         *
         * @return
         */
        public String getType() {
            return Type;
        }

        /**
         *
         * @param Type
         */
        public void setType(String Type) {
            this.Type = Type;
        }

        /**
         *
         * @return
         */
        public String getAdditionalCost() {
            return AdditionalCost;
        }

        /**
         *
         * @param AdditionalCost
         */
        public void setAdditionalCost(String AdditionalCost) {
            this.AdditionalCost = AdditionalCost;
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

        private String HeroName, SpeciesName, CultureName, Advancements, BasedOn, AdditionalCost;

        /**
         *
         * @return
         */
        public String getHeroName() {
            return HeroName;
        }

        /**
         *
         * @param HeroName
         */
        public void setHeroName(String HeroName) {
            this.HeroName = HeroName;
        }

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

        private String SpeciesName, CultureName, ProgressName, Progress;

        /**
         *
         * @return
         */
        public String getProgressName() {
            return ProgressName;
        }

        /**
         *
         * @param ProgressName
         */
        public void setProgressName(String ProgressName) {
            this.ProgressName = ProgressName;
        }

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

        private String RosterName, SpeciesName, CultureName, Roster;

        /**
         *
         * @return
         */
        public String getRosterName() {
            return RosterName;
        }

        /**
         *
         * @param RosterName
         */
        public void setRosterName(String RosterName) {
            this.RosterName = RosterName;
        }

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
