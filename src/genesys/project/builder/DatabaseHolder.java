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
import genesys.project.fxml.BuilderFXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author krzysztofg
 */
public class DatabaseHolder {

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
     * holdBattle
     */
    public static ABattle holdBattle = new ABattle();

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
     * isModyfying
     */
    public static boolean isModyfying = false;

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
     * modifiedHoldBattle
     */
    public static ABattle modifiedHoldBattle;

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
     * @param lifedomain
     * @param what
     */
    public static void creator(LifedomainValue lifedomain, Enum what) {
        isModyfying = false;
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
     * @return
     */
    public static String classLeftModify() {
        String fintex = "";
        switch (holdSpecies.Lifedomain) {
            case Humanoid:
                fintex += ((AHumanoid) holdSpecies).getStandardClass() + "\n"
                        + ((AHumanoid) holdSpecies).getEliteClass() + "\n"
                        + ((AHumanoid) holdSpecies).getLeaderClass() + "\n"
                        + ((AHumanoid) holdSpecies).getUniqueClass();
                break;
            case Fey:
                fintex += ((AFey) holdSpecies).getDiscipleClass() + "\n"
                        + ((AFey) holdSpecies).getArchlordClass() + "\n"
                        + ((AFey) holdSpecies).getParagonClass();
                break;
            case Reptilia:
                fintex += ((AReptilia) holdSpecies).getLesserClass() + "\n"
                        + ((AReptilia) holdSpecies).getCommonClass() + "\n"
                        + ((AReptilia) holdSpecies).getRareClass() + "\n"
                        + ((AReptilia) holdSpecies).getAncientClass();
                break;
            case Biest:
                fintex += ((ABiest) holdSpecies).getCommonClass() + "\n"
                        + ((ABiest) holdSpecies).getGreaterClass() + "\n"
                        + ((ABiest) holdSpecies).getLeaderClass() + "\n"
                        + ((ABiest) holdSpecies).getLegendaryClass();
                break;
            case Insecta:
                fintex += ((AInsecta) holdSpecies).getLesserClass() + "\n"
                        + ((AInsecta) holdSpecies).getCommonClass() + "\n"
                        + ((AInsecta) holdSpecies).getAdvancedClass() + "\n"
                        + ((AInsecta) holdSpecies).getApexClass();
                break;
        }
        return fintex;
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
                        ((AHumanoid) holdSpecies).setStandardClass(BuilderCORE.addRemove(((AHumanoid) holdSpecies).getStandardClass(), add));
                        break;
                    case "Elite Class":
                        ((AHumanoid) holdSpecies).setEliteClass(BuilderCORE.addRemove(((AHumanoid) holdSpecies).getEliteClass(), add));
                        break;
                    case "Leader Class":
                        ((AHumanoid) holdSpecies).setLeaderClass(BuilderCORE.addRemove(((AHumanoid) holdSpecies).getLeaderClass(), add));
                        break;
                    case "Unique Class":
                        ((AHumanoid) holdSpecies).setUniqueClass(BuilderCORE.addRemove(((AHumanoid) holdSpecies).getUniqueClass(), add));
                        break;
                    default:
                        break;
                }
                break;
            case Fey:
                switch (type) {
                    case "Disciple Class":
                        ((AFey) holdSpecies).setDiscipleClass(BuilderCORE.addRemove(((AFey) holdSpecies).getDiscipleClass(), add));
                        break;
                    case "Archlord Class":
                        ((AFey) holdSpecies).setArchlordClass(BuilderCORE.addRemove(((AFey) holdSpecies).getArchlordClass(), add));
                        break;
                    case "Paragon Class":
                        ((AFey) holdSpecies).setParagonClass(BuilderCORE.addRemove(((AFey) holdSpecies).getParagonClass(), add));
                        break;
                    default:
                        break;
                }
                break;
            case Reptilia:
                switch (type) {
                    case "Lesser Class":
                        ((AReptilia) holdSpecies).setLesserClass(BuilderCORE.addRemove(((AReptilia) holdSpecies).getLesserClass(), add));
                        break;
                    case "Common Class":
                        ((AReptilia) holdSpecies).setCommonClass(BuilderCORE.addRemove(((AReptilia) holdSpecies).getCommonClass(), add));
                        break;
                    case "Rare Class":
                        ((AReptilia) holdSpecies).setRareClass(BuilderCORE.addRemove(((AReptilia) holdSpecies).getRareClass(), add));
                        break;
                    case "Ancient Class":
                        ((AReptilia) holdSpecies).setAncientClass(BuilderCORE.addRemove(((AReptilia) holdSpecies).getAncientClass(), add));
                        break;
                    default:
                        break;
                }
                break;
            case Biest:
                switch (type) {
                    case "Common Class":
                        ((ABiest) holdSpecies).setCommonClass(BuilderCORE.addRemove(((ABiest) holdSpecies).getCommonClass(), add));
                        break;
                    case "Greater Class":
                        ((ABiest) holdSpecies).setGreaterClass(BuilderCORE.addRemove(((ABiest) holdSpecies).getGreaterClass(), add));
                        break;
                    case "Leader Class":
                        ((ABiest) holdSpecies).setLeaderClass(BuilderCORE.addRemove(((ABiest) holdSpecies).getLeaderClass(), add));
                        break;
                    case "Legendary Class":
                        ((ABiest) holdSpecies).setLegendaryClass(BuilderCORE.addRemove(((ABiest) holdSpecies).getLegendaryClass(), add));
                        break;
                    default:
                        break;
                }
                break;
            case Insecta:
                switch (type) {
                    case "Lesser Class":
                        ((AInsecta) holdSpecies).setLesserClass(BuilderCORE.addRemove(((AInsecta) holdSpecies).getLesserClass(), add));
                        break;
                    case "Common Class":
                        ((AInsecta) holdSpecies).setCommonClass(BuilderCORE.addRemove(((AInsecta) holdSpecies).getCommonClass(), add));
                        break;
                    case "Advanced Class":
                        ((AInsecta) holdSpecies).setAdvancedClass(BuilderCORE.addRemove(((AInsecta) holdSpecies).getAdvancedClass(), add));
                        break;
                    case "Apex Class":
                        ((AInsecta) holdSpecies).setApexClass(BuilderCORE.addRemove(((AInsecta) holdSpecies).getApexClass(), add));
                        break;
                }
                break;
        }
    }

    /**
     * searchFreeClassSpot
     */
    public static void searchFreeClassSpot() {
        b = 0;
        for (int i = 0; i < holdClass.length; i++) {
            if (holdClass[i].ClassName == null
                    || "".equals(holdClass[i].ClassName)) {
                b = i;
                i = numberOfClases;
            }
            if (i == numberOfClases - 1
                    && !(holdClass[i].ClassName == null
                    || "".equals(holdClass[i].ClassName))) {
                b = numberOfClases;
            }
        }
    }

    /**
     *
     * @param skills
     * @return
     */
    public static String skillsSeparatorRepalcer(String skills) {
        if (skills != null && !skills.equals("")) {
            String temp = skills.replaceAll(";", ",").replaceAll(",,", ",");
            if (temp.endsWith(",")) {
                temp = temp.substring(0, temp.length() - 1);
            }
            return temp;
        }
        return skills;
    }

    /**
     *
     * @param selSpecies
     */
    public static void loadSpeciesToHold(int selSpecies) {
        ObservableList data = DatabaseReader.getSpeciesData(selSpecies);
        holdSpecies = ASpecies.createASpecies(LifedomainValue.valueOf(data.get(0).toString().split("\\|")[0]));
        holdSpecies.setLifedomain(LifedomainValue.valueOf(data.get(0).toString().split("\\|")[0]));
        holdSpecies.setCharacteristicGroup(CharacteristicGroup.valueOf(data.get(0).toString().split("\\|")[1]));
        holdSpecies.setSpeciesName(data.get(0).toString().split("\\|")[2]);
        holdSpecies.setAge(Integer.parseInt(data.get(0).toString().split("\\|")[3]));
        holdSpecies.setSkills(data.get(0).toString().split("\\|")[4]);
        holdSpecies.setSpeciesModifiers(data.get(0).toString().split("\\|")[5]);
        modifiedHoldSpecies = holdSpecies.getClone();
    }

    /**
     *
     * @param selCulture
     */
    public static void loadCultureToHold(int selCulture) {
        ObservableList data = DatabaseReader.getCultureData(selCulture);
        holdCulture.setCultureName(data.get(0).toString().split("\\|")[0]);
        holdCulture.setParentSpeciesID(Integer.parseInt(data.get(0).toString().split("\\|")[1]));
        holdCulture.setAge(Integer.parseInt(data.get(0).toString().split("\\|")[2]));
        holdCulture.setTotalProgressionPoints(Integer.parseInt(data.get(0).toString().split("\\|")[3]));
        holdCulture.setLeftProgressionPoints(Integer.parseInt(data.get(0).toString().split("\\|")[4]));
        modifiedHoldCulture = holdCulture.getClone();
    }

    /**
     *
     * @param selClass
     * @param a
     */
    public static void loadClassToHold(int selClass, int a) {
        ObservableList data = DatabaseReader.getClassData(selClass);
        holdClass[a].setClassName(data.get(0).toString().split("\\|")[0]);
        holdClass[a].setSkills(data.get(0).toString().split("\\|")[1] + ",");
        holdClass[a].setParentCultureId(Integer.parseInt(data.get(0).toString().split("\\|")[2]));
        holdClass[a].setAdvancements(data.get(0).toString().split("\\|")[3]);
        holdClass[a].setType(data.get(0).toString().split("\\|")[4]);
        holdClass[a].setBasedOn(data.get(0).toString().split("\\|")[5]);
        holdClass[a].setAdditionalCost(data.get(0).toString().split("\\|")[6]);
        modifiedHoldClass[a] = holdClass[a].getClone();
    }

    /**
     *
     * @param selHero
     */
    public static void loadHeroToHold(int selHero) {
        ObservableList data = DatabaseReader.getHeroData(selHero);
        holdHero.setHeroName(data.get(0).toString().split("\\|")[0]);
        holdHero.setParentCultureId(Integer.parseInt(data.get(0).toString().split("\\|")[1]));
        holdHero.setAdvancements(data.get(0).toString().split("\\|")[2]);
        holdHero.setBasedOn(data.get(0).toString().split("\\|")[3]);
        holdHero.setAdditionalCost(data.get(0).toString().split("\\|")[4]);
        modifiedHoldHero = holdHero.getClone();
    }

    /**
     *
     * @param selProgress
     */
    public static void loadProgressToHold(int selProgress) {
        ObservableList data = DatabaseReader.getProgressData(selProgress);
        holdProgress.setParentCultureId(Integer.parseInt(data.get(0).toString().split("\\|")[0]));
        holdProgress.setProgressName(data.get(0).toString().split("\\|")[1]);
        holdProgress.setProgress(data.get(0).toString().split("\\|")[2]);
        holdProgress.setDate(data.get(0).toString().split("\\|")[3]);
        modifiedHoldProgress = holdProgress.getClone();
    }

    /**
     *
     * @param selRoster
     */
    public static void loadRosterToHold(int selRoster) {
        ObservableList data = DatabaseReader.getRosterData(selRoster);
        holdRoster.setParentCultureId(Integer.parseInt(data.get(0).toString().split("\\|")[0]));
        holdRoster.setRosterName(data.get(0).toString().split("\\|")[1]);
        holdRoster.setRoster(data.get(0).toString().split("\\|")[2]);
        holdRoster.setMaxPoints(data.get(0).toString().split("\\|")[3]);
        modifiedHoldRoster = holdRoster.getClone();
    }

    /**
     *
     * @param selBattle
     */
    public static void loadBattleToHold(int selBattle) {
        ObservableList data = DatabaseReader.getBattleData(selBattle);
        holdBattle.setBattleName(data.get(0).toString().split("\\|")[0]);
        holdBattle.setUserSpeciesID(Integer.parseInt(data.get(0).toString().split("\\|")[1]));
        holdBattle.setUserCultureID(Integer.parseInt(data.get(0).toString().split("\\|")[2]));
        holdBattle.setUserRosterID(Integer.parseInt(data.get(0).toString().split("\\|")[3]));
        holdBattle.setOponentSpeciesID(Integer.parseInt(data.get(0).toString().split("\\|")[4]));
        holdBattle.setOponentCultureID(Integer.parseInt(data.get(0).toString().split("\\|")[5]));
        holdBattle.setOponentRosterID(Integer.parseInt(data.get(0).toString().split("\\|")[6]));
        holdBattle.setPoints(Integer.parseInt(data.get(0).toString().split("\\|")[7]));
        holdBattle.setOutcome(data.get(0).toString().split("\\|")[8]);
        holdBattle.setDate(data.get(0).toString().split("\\|")[9]);
        holdBattle.setReplayID(Integer.parseInt(data.get(0).toString().split("\\|")[10]));
        holdBattle.setCustomData(data.get(0).toString().split("\\|")[11]);
        modifiedHoldBattle = holdBattle.getClone();
    }

    /**
     * ASpecies
     */
    @Data
    @NoArgsConstructor
    public static abstract class ASpecies {

        private int CreatedSpeciesID;
        private LifedomainValue Lifedomain;
        private CharacteristicGroup CharacteristicGroup;
        private String SpeciesName, Skills, SpeciesModifiers;
        private int Age;
        private int GeneticMutation, EnvironmentalAdaptation, KnowledgeAndScience,
                LesserTraitsAndPowersOfLight, GreaterTraitsAndPowersOfLight,
                LesserTraitsAndPowersOfDarkness, GreaterTraitsAndPowersOfDarkness,
                LesserTraitsAndPowersOfTwilight, GreaterTraitsAndPowersOfTwilight,
                ReptiliaLineage, EnvironmentalAdaptability, ExtremisAffinity,
                BiestialKingdoms, RegionalTraits, SpiritualAndScientificKnowledge,
                Clasification, Order, GeneticMorphology, Knowledge;
        private int NumberOfSkills, MaxNumberOfLowClases, MaxNumberOfMidClases, MaxNumberOfHigClases;

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
        public void setAllTraitsAndPowers(
                int LesserTraitsAndPowersOfLight,
                int GreaterTraitsAndPowersOfLight,
                int LesserTraitsAndPowersOfDarkness,
                int GreaterTraitsAndPowersOfDarkness,
                int LesserTraitsAndPowersOfTwilight,
                int GreaterTraitsAndPowersOfTwilight) {
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
            aClone.setAll(this.SpeciesName,
                    this.Skills,
                    this.SpeciesModifiers,
                    this.GeneticMutation,
                    this.EnvironmentalAdaptation,
                    this.KnowledgeAndScience,
                    this.LesserTraitsAndPowersOfLight,
                    this.GreaterTraitsAndPowersOfLight,
                    this.LesserTraitsAndPowersOfDarkness,
                    this.GreaterTraitsAndPowersOfDarkness,
                    this.LesserTraitsAndPowersOfTwilight,
                    this.GreaterTraitsAndPowersOfTwilight,
                    this.ReptiliaLineage,
                    this.EnvironmentalAdaptability,
                    this.ExtremisAffinity,
                    this.BiestialKingdoms,
                    this.RegionalTraits,
                    this.SpiritualAndScientificKnowledge,
                    this.Clasification,
                    this.Order,
                    this.GeneticMorphology,
                    this.Knowledge,
                    this.NumberOfSkills,
                    this.MaxNumberOfLowClases,
                    this.MaxNumberOfMidClases,
                    this.MaxNumberOfHigClases);
            return aClone;
        }

        private void setAll(String SpeciesName,
                String Skills, String SpeciesModifiers,
                int GeneticMutation,
                int EnvironmentalAdaptation,
                int KnowledgeAndScience,
                int LesserTraitsAndPowersOfLight,
                int GreaterTraitsAndPowersOfLight,
                int LesserTraitsAndPowersOfDarkness,
                int GreaterTraitsAndPowersOfDarkness,
                int LesserTraitsAndPowersOfTwilight,
                int GreaterTraitsAndPowersOfTwilight,
                int ReptiliaLineage,
                int EnvironmentalAdaptability,
                int ExtremisAffinity,
                int BiestialKingdoms,
                int RegionalTraits,
                int SpiritualAndScientificKnowledge,
                int Clasification,
                int Order,
                int GeneticMorphology,
                int Knowledge,
                int NumberOfSkills,
                int MaxNumberOfLowClases,
                int MaxNumberOfMidClases,
                int MaxNumberOfHigClases) {
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
    @Data
    @NoArgsConstructor
    public static class AHumanoid extends ASpecies {

        private int StandardClass, EliteClass, LeaderClass, UniqueClass;
    }

    /**
     * AFey
     */
    @Data
    @NoArgsConstructor
    public static class AFey extends ASpecies {

        private MainDomainValue MainDomain, SecondaryDomain;
        private int DiscipleClass, ArchlordClass, ParagonClass;
    }

    /**
     * AReptilia
     */
    @Data
    @NoArgsConstructor
    public static class AReptilia extends ASpecies {

        private MainLineageValue MainLineage;
        private int LesserClass, CommonClass, RareClass, AncientClass;
    }

    /**
     * ABiest
     */
    @Data
    @NoArgsConstructor
    public static class ABiest extends ASpecies {

        private MainKingdomValue MainKingdom;
        private MainRegionValue MainRegion;
        private int CommonClass, GreaterClass, LeaderClass, LegendaryClass;
    }

    /**
     * AInsecta
     */
    @Data
    @NoArgsConstructor
    public static class AInsecta extends ASpecies {

        private MainClasificationValue MainClasification;
        private MainOrderValue MainOrder;
        private int LesserClass, CommonClass, AdvancedClass, ApexClass;
    }

    /**
     * ACulture
     */
    @Data
    @NoArgsConstructor
    public static class ACulture {

        private int CreatedCulturesID, ParentSpeciesID;
        private String CultureName;
        private int Age, TotalProgressionPoints, LeftProgressionPoints;

        /**
         *
         * @param ParentSpeciesID
         * @param CultureName
         * @param Age
         * @param TotalProgressionPoints
         * @param LeftProgressionPoints
         */
        public ACulture(int ParentSpeciesID, String CultureName, int Age, int TotalProgressionPoints, int LeftProgressionPoints) {
            this.ParentSpeciesID = ParentSpeciesID;
            this.CultureName = CultureName;
            this.Age = Age;
            this.TotalProgressionPoints = TotalProgressionPoints;
            this.LeftProgressionPoints = LeftProgressionPoints;
        }

        /**
         *
         * @return
         */
        public ACulture getClone() {
            ACulture aClone = new ACulture(this.ParentSpeciesID, this.CultureName, this.Age, this.TotalProgressionPoints, this.LeftProgressionPoints);
            return aClone;
        }
    }

    /**
     * AClass
     */
    @Data
    @NoArgsConstructor
    public static class AClass {

        private int CreatedClassesID, ParentCultureId;
        private String ClassName, Skills, Advancements, Type, BasedOn, AdditionalCost;

        /**
         * clearAClass
         */
        public void clearAClass() {
            this.ClassName = this.Skills = this.Advancements = this.Type = this.BasedOn = this.AdditionalCost = "";
            this.CreatedClassesID = this.ParentCultureId = 0;
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
            aClone.ParentCultureId = this.ParentCultureId;
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
    @Data
    @NoArgsConstructor
    public static class AHero {

        private int CreatedHeroesID, ParentCultureId;
        private String HeroName, Advancements, BasedOn, AdditionalCost;

        /**
         *
         * @return
         */
        public AHero getClone() {
            AHero aClone = new AHero();
            aClone.HeroName = this.HeroName;
            aClone.ParentCultureId = this.ParentCultureId;
            aClone.Advancements = this.Advancements;
            aClone.BasedOn = this.BasedOn;
            aClone.AdditionalCost = this.AdditionalCost;
            return aClone;
        }
    }

    /**
     * AProgress
     */
    @Data
    @NoArgsConstructor
    public static class AProgress {

        private int CreatedProgressID, ParentCultureId;
        private String ProgressName, Progress, Date;

        /**
         *
         * @return
         */
        public AProgress getClone() {
            AProgress aClone = new AProgress();
            aClone.ProgressName = this.ProgressName;
            aClone.ParentCultureId = this.ParentCultureId;
            aClone.Progress = this.Progress;
            aClone.Date = this.Date;
            return aClone;
        }
    }

    /**
     * ARoster
     */
    @Data
    @NoArgsConstructor
    public static class ARoster {

        private int CreatedRosterID, ParentCultureId;
        private String RosterName, Roster, MaxPoints;

        /**
         *
         * @return
         */
        public ARoster getClone() {
            ARoster aClone = new ARoster();
            aClone.RosterName = this.RosterName;
            aClone.ParentCultureId = this.ParentCultureId;
            aClone.Roster = this.Roster;
            aClone.MaxPoints = this.MaxPoints;
            return aClone;
        }
    }

    /**
     * ABattle
     */
    @Data
    @NoArgsConstructor
    public static class ABattle {

        private int BattlesHistoryID, UserSpeciesID, UserCultureID, UserRosterID, OponentSpeciesID, OponentCultureID, OponentRosterID, Points, ReplayID;
        private String BattleName, Outcome, Date, CustomData;

        /**
         *
         * @return
         */
        public ABattle getClone() {
            ABattle aClone = new ABattle();
            aClone.BattleName = this.BattleName;
            aClone.UserSpeciesID = this.UserSpeciesID;
            aClone.UserCultureID = this.UserCultureID;
            aClone.UserRosterID = this.UserRosterID;
            aClone.OponentSpeciesID = this.OponentSpeciesID;
            aClone.OponentCultureID = this.OponentCultureID;
            aClone.OponentRosterID = this.OponentRosterID;
            aClone.Points = this.Points;
            aClone.Outcome = this.Outcome;
            aClone.Date = this.Date;
            aClone.ReplayID = this.ReplayID;
            aClone.CustomData = this.CustomData;
            return aClone;
        }
    }

    /**
     * The Modifiers
     */
    @Data
    @NoArgsConstructor
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

        @Override
        public TheModifiers clone() throws CloneNotSupportedException {
            //TheModifiers aClone = new TheModifiers(this.WoundsModifier, this.AttacksModifier, this.SizeModifier, this.StrengthModifier, this.ToughnessModifier, this.MovementModifier, this.MartialModifier, this.RangedModifier, this.DefenseModifier, this.DisciplineModifier, this.WillpowerModifier, this.CommandModifier, this.MTModifier, this.RTModifier, this.MoraleModifier);
            //return aClone;
            return (TheModifiers) super.clone();
        }
    }

}
