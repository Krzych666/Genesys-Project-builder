/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author krzysztofg
 */
public class Enums {

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

            private String lifedomainvalue;

            private LifedomainValue(String lifedomainvalue) {
                this.lifedomainvalue = lifedomainvalue;
            }

            @Override
            public String toString() {
                return this.lifedomainvalue;
            }
        }

        /**
         *LifeDomainTree1Values
         */
        public enum LifeDomainTree1Values {
            GeneticMutation("Genetic Mutation"),
            EnvironmentalAdaptation("Environmental Adaptation"),
            KnowledgeandScience("Knowledge and Science"),
            PathofLight("Path of Light"),
            ShadowsofTwilight("Shadows of Twilight"),
            PrimordialForces("Primordial Forces"),
            PathofDarkness("Path of Darkness"),
            ReptiliaLineages("Reptilia Lineages"),
            EnvironmentalAdaptability("Environmental Adaptability"),
            ExtremisAffinity("Extremis Affinity"),
            BiestialKingdoms("Biestial Kingdoms"),
            RegionalTraits("Regional Traits"),
            SpiritualandScientificKnowledge("Spiritual and Scientific Knowledge"),
            Arachnea("Arachnea"),
            Crustacea("Crustacea"),
            Insecta("Insecta"),
            Myriapoda("Myriapoda"),
            GeneticMorphology("Genetic Morphology"),
            Knowledge("Knowledge");

            private String lifedomaintree1value;

            private LifeDomainTree1Values(String lifedomaintree1value) {
                this.lifedomaintree1value = lifedomaintree1value;
            }

            @Override
            public String toString() {
                return this.lifedomaintree1value;
            }

            /**
             *
             * @param lifedomaintree1value
             * @return
             */
            public static LifeDomainTree1Values getEnum(String lifedomaintree1value) {
                for (LifeDomainTree1Values b : LifeDomainTree1Values.values()) {
                    if (b.lifedomaintree1value.equalsIgnoreCase(lifedomaintree1value)) {
                        return b;
                    }
                }
                return null;
            }
        }

        public enum LifeDomainTree2Values {
            AdaptiveSpecies("Adaptive Species"),
            Albinism("Albinism"),
            Dwarfism("Dwarfism"),
            Giantism("Giantism"),
            Goblyn("Goblyn"),
            KnowledgeableSpecies("Knowledgeable Species"),
            PhysicalToughness("Physical Toughness"),
            Tetrachromacy("Tetrachromacy"),
            Aberration("Aberration"),
            AdaptiveAdvantage("Adaptive Advantage"),
            AnimalKinship("Animal Kinship"),
            Survivalist("Survivalist"),
            CulturalandSocialAdaptations("Cultural and Social Adaptations"),
            EnvironmentalExtremes("Environmental Extremes"),
            Fayrie("Fay-rie"),
            Feral("Feral"),
            Mobility("Mobility"),
            ReligiousBelief("Religious Belief"),
            TheSacredFaiths("The Sacred Faiths"),
            ScientificAdaptability("Scientific Adaptability"),
            WealthofResources("Wealth of Resources"),
            AdvancedArchery("Advanced Archery"),
            AdvancedMeleeWeaponry("Advanced Melee Weaponry"),
            Alchemy("Alchemy"),
            Armor("Armor"),
            ArcaneSecrets("Arcane Secrets"),
            CombatTactics("Combat Tactics"),
            Command("Command"),
            Concentration("Concentration"),
            ExoticBeasts("Exotic Beasts"),
            ForbiddenKnowledge("Forbidden Knowledge"),
            GunpowderWeapons("Gunpowder Weapons"),
            HeavyMilitaryWeapons("Heavy Military Weapons"),
            MilitaryTrainingTechniques("Military Training Techniques"),
            StrengthTraining("Strength Training"),
            WarMachines("War Machines"),
            TheWeaponsmith("The Weaponsmith"),
            Celestial("Celestial"),
            Justice("Justice"),
            Truth("Truth"),
            Heroism("Heroism"),
            Healing("Healing"),
            Courage("Courage"),
            Ascended("Ascended"),
            Protection("Protection"),
            Primal("Primal"),
            Fire("Fire"),
            Air("Air"),
            Earth("Earth"),
            Water("Water"),
            Ethereal("Ethereal"),
            Chaos("Chaos"),
            Order("Order"),
            Time("Time"),
            Infernal("Infernal"),
            Corruption("Corruption"),
            Deceit("Deceit"),
            Madness("Madness"),
            Pain("Pain"),
            Fear("Fear"),
            Death("Death"),
            Destruction("Destruction"),
            Draconic("Draconic"),
            Troglodyte("Troglodyte"),
            Saurien("Saurien"),
            Tuatara("Tuatara"),
            Gargoyle("Gargoyle"),
            Ophidian("Ophidian"),
            SocialAdaptation("Social Adaptation"),
            AdaptiveAdvantages("Adaptive Advantages"),
            WeaponAdaptation("Weapon Adaptation"),
            ExtremisAcid("Extremis Acid"),
            ExtremisCold("Extremis Cold"),
            ExtremisElectricity("Extremis Electricity"),
            ExtremisFire("Extremis Fire"),
            ExtremisPoison("Extremis Poison"),
            ExtremisRadiation("Extremis Radiation"),
            ExtremisResonance("Extremis Resonance"),
            Ursidae("Ursidae"),
            CanusLupis("Canus Lupis"),
            AvianAves("Avian Aves"),
            Bor("Bor"),
            Ovis("Ovis"),
            Taurus("Taurus"),
            Feline("Feline"),
            Vermin("Vermin"),
            Caballis("Caballis"),
            Ichthyes("Ichthyes"),
            Caverns("Caverns"),
            Desert("Desert"),
            Forests("Forests"),
            Marsh("Marsh"),
            Mountains("Mountains"),
            Moon("Moon"),
            Oceans("Oceans"),
            Plains("Plains"),
            Sky("Sky"),
            Tundra("Tundra"),
            MonstrousBiests("Monstrous Biests"),
            BreedingAdaptations("Breeding Adaptations"),
            GoverningIdeology("Governing Ideology"),
            OmnivoreAdaptations("Omnivore Adaptations"),
            PredatorialAdvantage("Predatorial Advantage"),
            PreyAdaptations("Prey Adaptations"),
            AmbushTactics("Ambush Tactics"),
            Armory("Armory"),
            Occult("Occult"),
            Shamanism("Shamanism"),
            Arachnid("Arachnid"),
            Scorpionoid("Scorpionoid"),
            Decapod("Decapod"),
            Isopod("Isopod"),
            Coleoptera("Coleoptera"),
            Dipteran("Dipteran"),
            Formicadae("Formicadae"),
            Mantid("Mantid"),
            Vespidae("Vespidae"),
            Centipedea("Centipedea"),
            Millipedea("Millipedea"),
            Exoskeleton("Exoskeleton"),
            GeneticMutations("Genetic Mutations"),
            Circulatory("Circulatory"),
            BioWeaponry("BioWeaponry"),
            Eusociality("Eusociality"),
            Combat("Combat"),
            AdvancedKnowledge("Advanced Knowledge"),
            PsychiccNodes("Psychicc Nodes");

            private String lifedomaintree2value;

            private LifeDomainTree2Values(String lifedomaintree2value) {
                this.lifedomaintree2value = lifedomaintree2value;
            }

            @Override
            public String toString() {
                return this.lifedomaintree2value;
            }

            /**
             *
             * @param lifedomaintree2value
             * @return
             */
            public static LifeDomainTree2Values getEnum(String lifedomaintree2value) {
                for (LifeDomainTree2Values b : LifeDomainTree2Values.values()) {
                    if (b.lifedomaintree2value.equalsIgnoreCase(lifedomaintree2value)) {
                        return b;
                    }
                }
                return null;
            }
        }

        public enum CharacteristicGroup {
            standard("standard"),            
            Ursidae("Ursidae"),
            CanusLupis("Canus Lupis"),
            AvianAves("Avian Aves"),
            Bor("Bor"),
            Ovis("Ovis"),
            Taurus("Taurus"),
            Feline("Feline"),
            Vermin("Vermin"),
            Caballis("Caballis"),
            Ichthyes("Ichthyes"),
            Arachnea("Arachnea"),
            Crustacea("Crustacea"),
            Insecta("Insecta"),
            Myriapoda("Myriapoda");

            private String characteristicgroup;

            private CharacteristicGroup(String characteristicgroup) {
                this.characteristicgroup = characteristicgroup;
            }

            @Override
            public String toString() {
                return this.characteristicgroup;
            }

            /**
             *
             * @param characteristicgroup
             * @return
             */
            public static CharacteristicGroup getEnum(String characteristicgroup) {
                for (CharacteristicGroup b : CharacteristicGroup.values()) {
                    if (b.characteristicgroup.equalsIgnoreCase(characteristicgroup)) {
                        return b;
                    }
                }
                return null;
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

            private String maindomainvalue;

            private MainDomainValue(String maindomainvalue) {
                this.maindomainvalue = maindomainvalue;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.maindomainvalue;
            }

            /**
             *
             * @param maindomainvalue
             * @return
             */
            public static MainDomainValue getEnum(String maindomainvalue) {
                for (MainDomainValue b : MainDomainValue.values()) {
                    if (b.maindomainvalue.equalsIgnoreCase(maindomainvalue)) {
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
            Draconic("Draconic"),
            Troglodyte("Troglodyte"),
            Saurien("Saurien"),
            Tuatara("Tuatara"),
            Gargoyle("Gargoyle"),
            Ophidian("Ophidian");

            private String mainlineagevalue;

            private MainLineageValue(String mainlineagevalue) {
                this.mainlineagevalue = mainlineagevalue;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.mainlineagevalue;
            }

            /**
             *
             * @param mainlineagevalue
             * @return
             */
            public static MainLineageValue getEnum(String mainlineagevalue) {
                for (MainLineageValue b : MainLineageValue.values()) {
                    if (b.mainlineagevalue.equalsIgnoreCase(mainlineagevalue)) {
                        return b;
                    }
                }
                return null;
            }
        }

        /**
         * Main Kingdom Values
         */
        public enum MainKingdomValue {
            NONE(""),
            Ursidae("Ursidae"),
            CanusLupis("Canus Lupis"),
            AvianAves("Avian Aves"),
            Bor("Bor"),
            Ovis("Ovis"),
            Taurus("Taurus"),
            Feline("Feline"),
            Vermin("Vermin"),
            Caballis("Caballis"),
            Ichthyes("Ichthyes");

            private String mainkingdomvalue;

            private MainKingdomValue(String mainkingdomvalue) {
                this.mainkingdomvalue = mainkingdomvalue;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.mainkingdomvalue;
            }

            /**
             *
             * @param mainkingdomvalue
             * @return
             */
            public static MainKingdomValue getEnum(String mainkingdomvalue) {
                for (MainKingdomValue b : MainKingdomValue.values()) {
                    if (b.mainkingdomvalue.equalsIgnoreCase(mainkingdomvalue)) {
                        return b;
                    }
                }
                return null;
            }
        }

        /**
         * Main Region Values
         */
        public enum MainRegionValue {
            NONE(""),
            Caverns("Caverns"),
            Desert("Desert"),
            Forests("Forests"),
            Marsh("Marsh"),
            Mountains("Mountains"),
            Moon("Moon"),
            Oceans("Oceans"),
            Plains("Plains"),
            Sky("Sky"),
            Tundra("Tundra");
            private String mainregionvalue;

            private MainRegionValue(String mainregionvalue) {
                this.mainregionvalue = mainregionvalue;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.mainregionvalue;
            }

            /**
             *
             * @param mainregionvalue
             * @return
             */
            public static MainRegionValue getEnum(String mainregionvalue) {
                for (MainRegionValue b : MainRegionValue.values()) {
                    if (b.mainregionvalue.equalsIgnoreCase(mainregionvalue)) {
                        return b;
                    }
                }
                return null;
            }
        }

        /**
         * Main Clasification Values
         */
        public enum MainClasificationValue {
            NONE(""),
            Arachnea("Arachnea"),
            Crustacea("Crustacea"),
            Insecta("Insecta"),
            Myriapoda("Myriapoda");
            private String mainclasificationvalue;

            private MainClasificationValue(String mainclasificationvalue) {
                this.mainclasificationvalue = mainclasificationvalue;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.mainclasificationvalue;
            }

            /**
             *
             * @param mainclasificationvalue
             * @return
             */
            public static MainClasificationValue getEnum(String mainclasificationvalue) {
                for (MainClasificationValue b : MainClasificationValue.values()) {
                    if (b.mainclasificationvalue.equalsIgnoreCase(mainclasificationvalue)) {
                        return b;
                    }
                }
                return null;
            }
        }

        /**
         * Main Order Values
         */
        public enum MainOrderValue {
            NONE(""),
            Arachnid("Arachnid"),
            Scorpionoid("Scorpionoid"),
            Decapod("Decapod"),
            Isopod("Isopod"),
            Coleoptera("Coleoptera"),
            Dipteran("Dipteran"),
            Formicadae("Formicadae"),
            Mantid("Mantid"),
            Vespidae("Vespidae"),
            Centipedea("Centipedea"),
            Millipedea("Millipedea");
            private String mainordervalue;

            private MainOrderValue(String mainordervalue) {
                this.mainordervalue = mainordervalue;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.mainordervalue;
            }

            /**
             *
             * @param mainordervalue
             * @return
             */
            public static MainOrderValue getEnum(String mainordervalue) {
                for (MainOrderValue b : MainOrderValue.values()) {
                    if (b.mainordervalue.equalsIgnoreCase(mainordervalue)) {
                        return b;
                    }
                }
                return null;
            }
        }

        public enum PrimaryChooserValue {
            NONE("-None-"),
            Light("Path of Light"),
            Darkness("Path of Darkness"),
            Twilight("Shadows of Twilight"),
            Ursidae("Ursidae"),
            CanusLupis("Canus Lupis"),
            AvianAves("Avian Aves"),
            Bor("Bor"),
            Ovis("Ovis"),
            Taurus("Taurus"),
            Feline("Feline"),
            Vermin("Vermin"),
            Caballis("Caballis"),
            Ichthyes("Ichthyes"),
            Arachnea("Arachnea"),
            Crustacea("Crustacea"),
            Insecta("Insecta"),
            Myriapoda("Myriapoda");

            private String primarychooservalue;

            private PrimaryChooserValue(String primarychooservalue) {
                this.primarychooservalue = primarychooservalue;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.primarychooservalue;
            }

            /**
             *
             * @param primarychooservalue
             * @return
             */
            public static PrimaryChooserValue getEnum(String primarychooservalue) {
                for (PrimaryChooserValue b : PrimaryChooserValue.values()) {
                    if (b.primarychooservalue.equalsIgnoreCase(primarychooservalue)) {
                        return b;
                    }
                }
                return null;
            }
        }

        public enum SecondaryChooserValue {
            NONE("-None-"),
            Light("Path of Light"),
            Darkness("Path of Darkness"),
            Caverns("Caverns"),
            Desert("Desert"),
            Forests("Forests"),
            Marsh("Marsh"),
            Mountains("Mountains"),
            Moon("Moon"),
            Oceans("Oceans"),
            Plains("Plains"),
            Sky("Sky"),
            Tundra("Tundra"),
            Arachnid("Arachnid"),
            Scorpionoid("Scorpionoid"),
            Decapod("Decapod"),
            Isopod("Isopod"),
            Coleoptera("Coleoptera"),
            Dipteran("Dipteran"),
            Formicadae("Formicadae"),
            Mantid("Mantid"),
            Vespidae("Vespidae"),
            Centipedea("Centipedea"),
            Millipedea("Millipedea");

            private String secondarychooservalue;

            private SecondaryChooserValue(String secondarychooservalue) {
                this.secondarychooservalue = secondarychooservalue;
            }

            /**
             *
             * @return
             */
            public String getText() {
                return this.secondarychooservalue;
            }

            /**
             *
             * @param secondarychooservalue
             * @return
             */
            public static SecondaryChooserValue getEnum(String secondarychooservalue) {
                for (SecondaryChooserValue b : SecondaryChooserValue.values()) {
                    if (b.secondarychooservalue.equalsIgnoreCase(secondarychooservalue)) {
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

        @Getter
        @Setter
        private int WoundsModifier;

        @Getter
        @Setter
        private int AttacksModifier;

        @Getter
        @Setter
        private int SizeModifier;

        @Getter
        @Setter
        private int StrengthModifier;

        @Getter
        @Setter
        private int ToughnessModifier;

        @Getter
        @Setter
        private int MovementModifier;

        @Getter
        @Setter
        private int MartialModifier;

        @Getter
        @Setter
        private int RangedModifier;

        @Getter
        @Setter
        private int DefenseModifier;

        @Getter
        @Setter
        private int DisciplineModifier;

        @Getter
        @Setter
        private int WillpowerModifier;

        @Getter
        @Setter
        private int CommandModifier;

        @Getter
        @Setter
        private int MTModifier;

        @Getter
        @Setter
        private int RTModifier;

        @Getter
        @Setter
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

        @Override
        public TheModifiers clone() throws CloneNotSupportedException {
            //TheModifiers aClone = new TheModifiers(this.WoundsModifier, this.AttacksModifier, this.SizeModifier, this.StrengthModifier, this.ToughnessModifier, this.MovementModifier, this.MartialModifier, this.RangedModifier, this.DefenseModifier, this.DisciplineModifier, this.WillpowerModifier, this.CommandModifier, this.MTModifier, this.RTModifier, this.MoraleModifier);
            //return aClone;
            return (TheModifiers) super.clone();
        }
    }
}
