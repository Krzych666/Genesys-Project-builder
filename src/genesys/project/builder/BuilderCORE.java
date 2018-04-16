/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue;
import static genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue.Biest;
import static genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue.Fey;
import static genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue.Humanoid;
import static genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue.Insecta;
import static genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue.Reptilia;
import genesys.project.builder.BuilderCORE.Enmuerations.UseCases;
import genesys.project.fxml.BuilderFXMLController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

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
    public static final String REPTILIASKILS = "Lineage";

    /**
     * REPTILIASUBSKILS = "Ancestry"
     */
    public static final String REPTILIASUBSKILS = "Bloodline";

    /**
     * @param args the command line arguments
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
            cCORE = DriverManager.getConnection("jdbc:sqlite:GenPr_CORE.db");
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
            cUserData = DriverManager.getConnection("jdbc:sqlite:GenPr_UserData.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return cUserData;
    }

    /**
     *
     * @param stmt
     * @param column
     * @param exclude
     * @return
     * @throws java.sql.SQLException
     */
    public static ObservableList getData(PreparedStatement stmt, String column, ObservableList exclude) throws SQLException {
        ObservableList temp = FXCollections.observableArrayList();
        ResultSet rs = stmt.executeQuery();
        try //(            
        //PreparedStatement stmt  = conn.prepareStatement(sql);             
        //)
        {
            // loop through the result set
            while (rs.next()) {
                //System.out.println(rs.getString(column) +  "\t");
                if (exclude == null) {
                    temp.add(rs.getString(column));
                } else {
                    boolean isPresent = false;
                    for (int i = 0; i < exclude.size(); i++) {
                        if ((exclude.get(i).toString().split(" \\(p")[0]).equals(rs.getString(column))) {
                            isPresent = true;
                        }
                    }
                    if (!isPresent) {
                        temp.add(rs.getString(column));
                    }
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
            ObservableList<String> tmpget = getData(stmt, "SpeciesName", null);
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
     * @return
     * @throws java.sql.SQLException
     */
    public String[] getCharacteristics(String lifedomain) throws SQLException {
        String[] outputValues = new String[16];
        int[] CharacteristicModifiers = {BuilderFXMLController.HOLD_MODIFIERS.getStrengthModifier(), BuilderFXMLController.HOLD_MODIFIERS.getToughnessModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMovementModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMartialModifier(), BuilderFXMLController.HOLD_MODIFIERS.getRangedModifier(), BuilderFXMLController.HOLD_MODIFIERS.getDefenseModifier(), BuilderFXMLController.HOLD_MODIFIERS.getDisciplineModifier(), BuilderFXMLController.HOLD_MODIFIERS.getWillpowerModifier(), BuilderFXMLController.HOLD_MODIFIERS.getCommandModifier(), BuilderFXMLController.HOLD_MODIFIERS.getWoundsModifier(), BuilderFXMLController.HOLD_MODIFIERS.getAttacksModifier(), BuilderFXMLController.HOLD_MODIFIERS.getSizeModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMTModifier(), BuilderFXMLController.HOLD_MODIFIERS.getRTModifier(), BuilderFXMLController.HOLD_MODIFIERS.getMoraleModifier()};
        for (int j = 0; j < CHARACTERISTICS.length; j++) {
            if ("Size".equals(CHARACTERISTICS[j]) && j < 12) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt = conn.prepareStatement("SELECT " + CHARACTERISTICS[j] + " FROM StartingCharacteristics WHERE LifeDomain = ?");
                stmt.setString(1, lifedomain);
                outputValues[j] = SIZES[(Integer.parseInt(getValue(stmt, CHARACTERISTICS[j])) + CharacteristicModifiers[j])];
            } else if (j < 12) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt1 = conn.prepareStatement("SELECT " + CHARACTERISTICS[j] + " FROM StartingCharacteristics WHERE LifeDomain = ?");
                stmt1.setString(1, lifedomain);
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
        for (int i = 0; i < first.getItems().size(); i++) {
            lst.add(first.getItems().get(i));
        }
        for (int i = 0; i < second.getItems().size(); i++) {
            lst.add(second.getItems().get(i));
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
     * all enums
     */
    public static class Enmuerations {

        /**
         * Lifedomains
         */
        public enum LifedomainValue {

            /**
             * Humanoid
             */
            Humanoid("Humanoid"),
            /**
             * Fey
             */
            Fey("Fey"),
            /**
             * Reptilia
             */
            Reptilia("Reptilia"),
            /**
             * Biest
             */
            Biest("Biest"),
            /**
             * Insecta
             */
            Insecta("Insecta");

            private String lfdmn;

            private LifedomainValue(String stringVal) {
                lfdmn = stringVal;
            }

            @Override
            public String toString() {
                return lfdmn;
            }
        }

        /**
         * Main Domain Values
         */
        public enum MainDomainValue {

            /**
             * Light
             */
            Light("Path of Light"),
            /**
             * Darkness
             */
            Darkness("Path of Darkness"),
            /**
             * Twilight
             */
            Twilight("Shadows of Twilight");

            private String mdv;

            private MainDomainValue(String mdv) {
                this.mdv = mdv;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.mdv;
            }

            /**
             *
             * @param mdv
             * @return
             */
            public static MainDomainValue getEnum(String mdv) {
                for (MainDomainValue b : MainDomainValue.values()) {
                    if (b.mdv.equalsIgnoreCase(mdv)) {
                        return b;
                    }
                }
                return null;
            }
        }

        /**
         * Main Lineage Values
         */
        public enum MainLineageValue {

            /**
             * Draconic
             */
            Draconic("Draconic");

            private String mlv;

            private MainLineageValue(String mlv) {
                this.mlv = mlv;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.mlv;
            }

            /**
             *
             * @param mlv
             * @return
             */
            public static MainLineageValue getEnum(String mlv) {
                for (MainLineageValue b : MainLineageValue.values()) {
                    if (b.mlv.equalsIgnoreCase(mlv)) {
                        return b;
                    }
                }
                return null;
            }
        }

        /**
         * Classes
         */
        public enum Classes {

            /**
             * StandardClass
             */
            StandardClass("Standard Class"),
            /**
             * EliteClass
             */
            EliteClass("Elite Class"),
            /**
             * LeaderClass
             */
            LeaderClass("Leader Class"),
            /**
             * UniqueClass
             */
            UniqueClass("Unique Class"),
            /**
             * DiscipleClass
             */
            DiscipleClass("Disciple Class"),
            /**
             * ArchlordClass
             */
            ArchlordClass("Archlord Class"),
            /**
             * ParagonClass
             */
            ParagonClass("ParagonClass"),
            /**
             * LesserClass
             */
            LesserClass("Lesser Class"),
            /**
             * CommonClass
             */
            CommonClass("Common Class"),
            /**
             * RareClass
             */
            RareClass("Rare Class"),
            /**
             * AncientClass
             */
            AncientClass("Ancient Class"),
            /**
             * GreaterClass
             */
            GreaterClass("Greater Class"),
            /**
             * LegendaryClass
             */
            LegendaryClass("Legendary Class"),
            /**
             * AdvancedClass
             */
            AdvancedClass("Advanced Class"),
            /**
             * ApexClass
             */
            ApexClass("Apex Class");

            private String cls;

            private Classes(String stringVal) {
                cls = stringVal;
            }

            @Override
            public String toString() {
                return cls;
            }
        }

        /**
         * Use Cases
         */
        public enum UseCases {

            /**
             * InDB
             */
            InDB,
            /**
             * CreatingSpecies
             */
            CreatingSpecies,
            /**
             * HeroInDB
             */
            HeroInDB,
            /**
             * Hold
             */
            Hold,
            /**
             * CreatingClass
             */
            CreatingClass,
            /**
             * CreatingCulture
             */
            CreatingCulture,
            /**
             * COREdb
             */
            COREdb,
            /**
             * Userdb
             */
            Userdb
        }

        /**
         * Main Buttons
         */
        public enum MainButtons {

            /**
             * CreateCulture
             */
            CreateCulture,
            /**
             * Edit
             */
            Edit,
            /**
             * Duplicate
             */
            Duplicate,
            /**
             * Delete
             */
            Delete
        }

        /**
         * DB Tables
         */
        public enum DBTables {

            /**
             * CreatedSpecies
             */
            CreatedSpecies,
            /**
             * CreatedCultures
             */
            CreatedCultures,
            /**
             * CreatedClasses
             */
            CreatedClasses,
            /**
             * CreatedHeroes
             */
            CreatedHeroes,
            /**
             * CreatedProgress
             */
            CreatedProgress,
            /**
             * CreatedRosters
             */
            CreatedRosters
        }

        /**
         * DBClassColumns
         */
        public enum DBClassColumns {
            Skills("Skills"),
            Advancements("Advancements"),
            Type("Type"),
            BasedOn("BasedOn"),
            AdditionalCost("AdditionalCost");

            private String columns;

            private DBClassColumns(String stringVal) {
                columns = stringVal;
            }

            @Override
            public String toString() {
                return columns;
            }
        }

        /**
         * DBHeroColumns
         */
        public enum DBHeroColumns {
            Advancements("Advancements"),
            BasedOn("BasedOn"),
            AdditionalCost("AdditionalCost");
            private String columns;

            private DBHeroColumns(String stringVal) {
                columns = stringVal;
            }

            @Override
            public String toString() {
                return columns;
            }
        }

        /**
         * Creators
         */
        public enum Creators {

            /**
             * CreateSpecies
             */
            CreateSpecies,
            /**
             * CreateCulture
             */
            CreateCulture,
            /**
             * CreateClass
             */
            CreateClass,
            /**
             * CreateHero
             */
            CreateHero,
            /**
             * CreateProgress
             */
            CreateProgress,
            /**
             * CreateRoster
             */
            CreateRoster
        }

        /**
         * public enum Modificators{
         *
         */
        public enum Modificators {

            /**
             * ModifySpecies
             */
            ModifySpecies,
            /**
             * ModifyCulture
             */
            ModifyCulture,
            /**
             * ModifyClass
             */
            ModifyClass,
            /**
             * ModifyHero
             */
            ModifyHero,
            /**
             * ModifyProgress
             */
            ModifyProgress,
            /**
             * ModifyRoster
             */
            ModifyRoster
        }
    }

    /**
     * The Modifiers
     */
    public static class TheModifiers implements Cloneable {

        private int WoundsModifier;
        private int AttacksModifier;
        private int SizeModifier;
        private int StrengthModifier;
        private int ToughnessModifier;
        private int MovementModifier;
        private int MartialModifier;
        private int RangedModifier;
        private int DefenseModifier;
        private int DisciplineModifier;
        private int WillpowerModifier;
        private int CommandModifier;
        private int MTModifier;
        private int RTModifier;
        private int MoraleModifier;

        /**
         * TheModifiers
         */
        public TheModifiers() {
        }

        ;
        
        /**
         *
         * @param WoundsModifier
         * @param AttacksModifier
         * @param SizeModifier
         * @param StrengthModifier
         * @param ToughnessModifier
         * @param MovementModifier
         * @param MartialModifier
         * @param RangedModifier
         * @param DefenseModifier
         * @param DisciplineModifier
         * @param WillpowerModifier
         * @param CommandModifier
         * @param MTModifier
         * @param RTModifier
         * @param MoraleModifier
         */
        public TheModifiers(int WoundsModifier, int AttacksModifier, int SizeModifier, int StrengthModifier, int ToughnessModifier, int MovementModifier, int MartialModifier, int RangedModifier, int DefenseModifier, int DisciplineModifier, int WillpowerModifier, int CommandModifier, int MTModifier, int RTModifier, int MoraleModifier) {
            this.WoundsModifier = WoundsModifier;
            this.AttacksModifier = AttacksModifier;
            this.SizeModifier = SizeModifier;
            this.StrengthModifier = StrengthModifier;
            this.ToughnessModifier = ToughnessModifier;
            this.MovementModifier = MovementModifier;
            this.MartialModifier = MartialModifier;
            this.RangedModifier = RangedModifier;
            this.DefenseModifier = DefenseModifier;
            this.DisciplineModifier = DisciplineModifier;
            this.WillpowerModifier = WillpowerModifier;
            this.CommandModifier = CommandModifier;
            this.MTModifier = MTModifier;
            this.RTModifier = RTModifier;
            this.MoraleModifier = MoraleModifier;
        }

        /**
         * clearModifiers
         */
        public void clearModifiers() {
            this.setWoundsModifier(0);
            this.setAttacksModifier(0);
            this.setSizeModifier(0);
            this.setStrengthModifier(0);
            this.setToughnessModifier(0);
            this.setMovementModifier(0);
            this.setMartialModifier(0);
            this.setRangedModifier(0);
            this.setDefenseModifier(0);
            this.setDisciplineModifier(0);
            this.setWillpowerModifier(0);
            this.setCommandModifier(0);
            this.setMTModifier(0);
            this.setRTModifier(0);
            this.setMoraleModifier(0);
        }

        /**
         *
         * @return
         */
        public int getWoundsModifier() {
            return WoundsModifier;
        }

        /**
         *
         * @param WoundsModifier
         */
        public void setWoundsModifier(int WoundsModifier) {
            this.WoundsModifier = WoundsModifier;
        }

        /**
         *
         * @return
         */
        public int getAttacksModifier() {
            return AttacksModifier;
        }

        /**
         *
         * @param AttacksModifier
         */
        public void setAttacksModifier(int AttacksModifier) {
            this.AttacksModifier = AttacksModifier;
        }

        /**
         *
         * @return
         */
        public int getSizeModifier() {
            return SizeModifier;
        }

        /**
         *
         * @param SizeModifier
         */
        public void setSizeModifier(int SizeModifier) {
            this.SizeModifier = SizeModifier;
        }

        /**
         *
         * @return
         */
        public int getStrengthModifier() {
            return StrengthModifier;
        }

        /**
         *
         * @param StrengthModifier
         */
        public void setStrengthModifier(int StrengthModifier) {
            this.StrengthModifier = StrengthModifier;
        }

        /**
         *
         * @return
         */
        public int getToughnessModifier() {
            return ToughnessModifier;
        }

        /**
         *
         * @param ToughnessModifier
         */
        public void setToughnessModifier(int ToughnessModifier) {
            this.ToughnessModifier = ToughnessModifier;
        }

        /**
         *
         * @return
         */
        public int getMovementModifier() {
            return MovementModifier;
        }

        /**
         *
         * @param MovementModifier
         */
        public void setMovementModifier(int MovementModifier) {
            this.MovementModifier = MovementModifier;
        }

        /**
         *
         * @return
         */
        public int getMartialModifier() {
            return MartialModifier;
        }

        /**
         *
         * @param MartialModifier
         */
        public void setMartialModifier(int MartialModifier) {
            this.MartialModifier = MartialModifier;
        }

        /**
         *
         * @return
         */
        public int getRangedModifier() {
            return RangedModifier;
        }

        /**
         *
         * @param RangedModifier
         */
        public void setRangedModifier(int RangedModifier) {
            this.RangedModifier = RangedModifier;
        }

        /**
         *
         * @return
         */
        public int getDefenseModifier() {
            return DefenseModifier;
        }

        /**
         *
         * @param DefenseModifier
         */
        public void setDefenseModifier(int DefenseModifier) {
            this.DefenseModifier = DefenseModifier;
        }

        /**
         *
         * @return
         */
        public int getDisciplineModifier() {
            return DisciplineModifier;
        }

        /**
         *
         * @param DisciplineModifier
         */
        public void setDisciplineModifier(int DisciplineModifier) {
            this.DisciplineModifier = DisciplineModifier;
        }

        /**
         *
         * @return
         */
        public int getWillpowerModifier() {
            return WillpowerModifier;
        }

        /**
         *
         * @param WillpowerModifier
         */
        public void setWillpowerModifier(int WillpowerModifier) {
            this.WillpowerModifier = WillpowerModifier;
        }

        /**
         *
         * @return
         */
        public int getCommandModifier() {
            return CommandModifier;
        }

        /**
         *
         * @param CommandModifier
         */
        public void setCommandModifier(int CommandModifier) {
            this.CommandModifier = CommandModifier;
        }

        /**
         *
         * @return
         */
        public int getMTModifier() {
            return MTModifier;
        }

        /**
         *
         * @param MTModifier
         */
        public void setMTModifier(int MTModifier) {
            this.MTModifier = MTModifier;
        }

        /**
         *
         * @return
         */
        public int getRTModifier() {
            return RTModifier;
        }

        /**
         *
         * @param RTModifier
         */
        public void setRTModifier(int RTModifier) {
            this.RTModifier = RTModifier;
        }

        /**
         *
         * @return
         */
        public int getMoraleModifier() {
            return MoraleModifier;
        }

        /**
         *
         * @param MoraleModifier
         */
        public void setMoraleModifier(int MoraleModifier) {
            this.MoraleModifier = MoraleModifier;
        }

        @Override
        public TheModifiers clone() throws CloneNotSupportedException {
            //TheModifiers aClone = new TheModifiers(this.WoundsModifier, this.AttacksModifier, this.SizeModifier, this.StrengthModifier, this.ToughnessModifier, this.MovementModifier, this.MartialModifier, this.RangedModifier, this.DefenseModifier, this.DisciplineModifier, this.WillpowerModifier, this.CommandModifier, this.MTModifier, this.RTModifier, this.MoraleModifier);
            //return aClone;
            return (TheModifiers) super.clone();
        }
    }

}
