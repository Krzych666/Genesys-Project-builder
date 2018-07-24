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
import lombok.Getter;
import lombok.Setter;

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
    public static void loadSpeciesToHold(String selSpecies) {
        ObservableList data = DatabaseReader.getSpeciesData(selSpecies);
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
     */
    public static void loadCultureToHold(String selSpecies, String selCulture) {
        ObservableList data = DatabaseReader.getCultureData(selSpecies, selCulture);
        holdCulture.setSpeciesName(selSpecies);
        holdCulture.setCultureName(selCulture);
        holdCulture.setAge(Integer.parseInt(data.get(0).toString().split("\\|")[0]));
        holdCulture.setTotalProgressionPoints(Integer.parseInt(data.get(0).toString().split("\\|")[1]));
        holdCulture.setLeftProgressionPoints(Integer.parseInt(data.get(0).toString().split("\\|")[2]));
        modifiedHoldCulture = holdCulture.getClone();
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selClass
     * @param a
     */
    public static void loadClassToHold(String selSpecies, String selCulture, String selClass, int a) {
        ObservableList data = DatabaseReader.getClassData(selSpecies, selCulture, selClass);
        holdClass[a].setClassName(selClass);
        holdClass[a].setSkills(data.get(0).toString().split("\\|")[0] + ",");
        holdClass[a].setSpeciesName(selSpecies);
        holdClass[a].setCultureName(selCulture);
        holdClass[a].setAdvancements(data.get(0).toString().split("\\|")[1]);
        holdClass[a].setType(data.get(0).toString().split("\\|")[2]);
        holdClass[a].setBasedOn(data.get(0).toString().split("\\|")[3]);
        holdClass[a].setAdditionalCost(data.get(0).toString().split("\\|")[4]);
        modifiedHoldClass[a] = holdClass[a].getClone();
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selHero
     */
    public static void loadHeroToHold(String selSpecies, String selCulture, String selHero) {
        ObservableList data = DatabaseReader.getHeroData(selSpecies, selCulture, selHero);
        holdHero.setHeroName(selHero);
        holdHero.setSpeciesName(selSpecies);
        holdHero.setCultureName(selCulture);
        holdHero.setAdvancements(data.get(0).toString().split("\\|")[0]);
        holdHero.setBasedOn(data.get(0).toString().split("\\|")[1]);
        holdHero.setAdditionalCost(data.get(0).toString().split("\\|")[2]);
        modifiedHoldHero = holdHero.getClone();
    }

    /**
     *
     * @param selSpecies
     * @param selCulture
     * @param selProgress
     */
    public static void loadProgressToHold(String selSpecies, String selCulture, String selProgress) {
        ObservableList data = DatabaseReader.getProgressData(selSpecies, selCulture, selProgress);
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
     */
    public static void loadRosterToHold(String selSpecies, String selCulture, String selRoster) {
        ObservableList data = DatabaseReader.getRosterData(selSpecies, selCulture, selRoster);
        holdRoster.setSpeciesName(selSpecies);
        holdRoster.setCultureName(selCulture);
        holdRoster.setRosterName(selRoster);
        holdRoster.setRoster(data.get(0).toString().split("\\|")[0]);
        holdRoster.setMaxPoints(data.get(0).toString().split("\\|")[1]);
        modifiedHoldRoster = holdRoster.getClone();
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
        private int Age, TotalProgressionPoints, LeftProgressionPoints;

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
         * @param TotalProgressionPoints
         * @param LeftProgressionPoints
         */
        public ACulture(String SpeciesName, String CultureName, int Age, int TotalProgressionPoints, int LeftProgressionPoints) {
            this.SpeciesName = SpeciesName;
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
            ACulture aClone = new ACulture(this.SpeciesName, this.CultureName, this.Age, this.TotalProgressionPoints, this.LeftProgressionPoints);
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
            aClone.HeroName = this.HeroName;
            aClone.SpeciesName = this.SpeciesName;
            aClone.CultureName = this.CultureName;
            aClone.Advancements = this.Advancements;
            aClone.BasedOn = this.BasedOn;
            aClone.AdditionalCost = this.AdditionalCost;
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
            aClone.ProgressName = this.ProgressName;
            aClone.SpeciesName = this.SpeciesName;
            aClone.CultureName = this.CultureName;
            aClone.Progress = this.Progress;
            return aClone;
        }
    }

    /**
     * ARoster
     */
    public static class ARoster {

        @Getter
        @Setter
        private String RosterName, SpeciesName, CultureName, Roster, MaxPoints;

        /**
         *
         * @return
         */
        public ARoster getClone() {
            ARoster aClone = new ARoster();
            aClone.RosterName = this.RosterName;
            aClone.SpeciesName = this.SpeciesName;
            aClone.CultureName = this.CultureName;
            aClone.Roster = this.Roster;
            aClone.MaxPoints = this.MaxPoints;
            return aClone;
        }
    }

}
