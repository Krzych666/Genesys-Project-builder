/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

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
         * LifeDomainTree1Values
         */
        public enum LifeDomainTree1Values {

            /**
             * GeneticMutation
             */
            GeneticMutation("Genetic Mutation"),
            /**
             * EnvironmentalAdaptation
             */
            EnvironmentalAdaptation("Environmental Adaptation"),
            /**
             * KnowledgeandScience
             */
            KnowledgeandScience("Knowledge and Science"),
            /**
             * PathofLight
             */
            PathofLight("Path of Light"),
            /**
             * ShadowsofTwilight
             */
            ShadowsofTwilight("Shadows of Twilight"),
            /**
             * PrimordialForces
             */
            PrimordialForces("Primordial Forces"),
            /**
             * PathofDarkness
             */
            PathofDarkness("Path of Darkness"),
            /**
             * ReptiliaLineages
             */
            ReptiliaLineages("Reptilia Lineages"),
            /**
             * EnvironmentalAdaptability
             */
            EnvironmentalAdaptability("Environmental Adaptability"),
            /**
             * ExtremisAffinity
             */
            ExtremisAffinity("Extremis Affinity"),
            /**
             * BiestialKingdoms
             */
            BiestialKingdoms("Biestial Kingdoms"),
            /**
             * RegionalTraits
             */
            RegionalTraits("Regional Traits"),
            /**
             * SpiritualandScientificKnowledge
             */
            SpiritualandScientificKnowledge("Spiritual and Scientific Knowledge"),
            /**
             * Arachnea
             */
            Arachnea("Arachnea"),
            /**
             * Crustacea
             */
            Crustacea("Crustacea"),
            /**
             * Insecta
             */
            Insecta("Insecta"),
            /**
             * Myriapoda
             */
            Myriapoda("Myriapoda"),
            /**
             * GeneticMorphology
             */
            GeneticMorphology("Genetic Morphology"),
            /**
             * Knowledge
             */
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

        /**
         * LifeDomainTree2Values
         */
        public enum LifeDomainTree2Values {
            /**
             * AdaptiveSpecies
             */
            AdaptiveSpecies("Adaptive Species"),
            /**
             * Albinism
             */
            Albinism("Albinism"),
            /**
             * Dwarfism
             */
            Dwarfism("Dwarfism"),
            /**
             * Giantism
             */
            Giantism("Giantism"),
            /**
             * Goblyn
             */
            Goblyn("Goblyn"),
            /**
             * KnowledgeableSpecies
             */
            KnowledgeableSpecies("Knowledgeable Species"),
            /**
             * PhysicalToughness
             */
            PhysicalToughness("Physical Toughness"),
            /**
             * Tetrachromacy
             */
            Tetrachromacy("Tetrachromacy"),
            /**
             * Aberration
             */
            Aberration("Aberration"),
            /**
             * AdaptiveAdvantage
             */
            AdaptiveAdvantage("Adaptive Advantage"),
            /**
             * AnimalKinship
             */
            AnimalKinship("Animal Kinship"),
            /**
             * Survivalist
             */
            Survivalist("Survivalist"),
            /**
             * CulturalandSocialAdaptations
             */
            CulturalandSocialAdaptations("Cultural and Social Adaptations"),
            /**
             * EnvironmentalExtremes
             */
            EnvironmentalExtremes("Environmental Extremes"),
            /**
             * Fayrie
             */
            Fayrie("Fay-rie"),
            /**
             * Feral
             */
            Feral("Feral"),
            /**
             * Mobility
             */
            Mobility("Mobility"),
            /**
             * ReligiousBelief
             */
            ReligiousBelief("Religious Belief"),
            /**
             * TheSacredFaiths
             */
            TheSacredFaiths("The Sacred Faiths"),
            /**
             * ScientificAdaptability
             */
            ScientificAdaptability("Scientific Adaptability"),
            /**
             * WealthofResources
             */
            WealthofResources("Wealth of Resources"),
            /**
             * AdvancedArchery
             */
            AdvancedArchery("Advanced Archery"),
            /**
             * AdvancedMeleeWeaponry
             */
            AdvancedMeleeWeaponry("Advanced Melee Weaponry"),
            /**
             * Alchemy
             */
            Alchemy("Alchemy"),
            /**
             * Armor
             */
            Armor("Armor"),
            /**
             * ArcaneSecrets
             */
            ArcaneSecrets("Arcane Secrets"),
            /**
             * CombatTactics
             */
            CombatTactics("Combat Tactics"),
            /**
             * Command
             */
            Command("Command"),
            /**
             * Concentration
             */
            Concentration("Concentration"),
            /**
             * ExoticBeasts
             */
            ExoticBeasts("Exotic Beasts"),
            /**
             * ForbiddenKnowledge
             */
            ForbiddenKnowledge("Forbidden Knowledge"),
            /**
             * GunpowderWeapons
             */
            GunpowderWeapons("Gunpowder Weapons"),
            /**
             * HeavyMilitaryWeapons
             */
            HeavyMilitaryWeapons("Heavy Military Weapons"),
            /**
             * MilitaryTrainingTechniques
             */
            MilitaryTrainingTechniques("Military Training Techniques"),
            /**
             * StrengthTraining
             */
            StrengthTraining("Strength Training"),
            /**
             * WarMachines
             */
            WarMachines("War Machines"),
            /**
             * TheWeaponsmith
             */
            TheWeaponsmith("The Weaponsmith"),
            /**
             * Celestial
             */
            Celestial("Celestial"),
            /**
             * Justice
             */
            Justice("Justice"),
            /**
             * Truth
             */
            Truth("Truth"),
            /**
             * Heroism
             */
            Heroism("Heroism"),
            /**
             * Healing
             */
            Healing("Healing"),
            /**
             * Courage
             */
            Courage("Courage"),
            /**
             * Ascended
             */
            Ascended("Ascended"),
            /**
             * Protection
             */
            Protection("Protection"),
            /**
             * Primal
             */
            Primal("Primal"),
            /**
             * Fire
             */
            Fire("Fire"),
            /**
             * Air
             */
            Air("Air"),
            /**
             * Earth
             */
            Earth("Earth"),
            /**
             * Water
             */
            Water("Water"),
            /**
             * Ethereal
             */
            Ethereal("Ethereal"),
            /**
             * Chaos
             */
            Chaos("Chaos"),
            /**
             * Order
             */
            Order("Order"),
            /**
             * Time
             */
            Time("Time"),
            /**
             * Infernal
             */
            Infernal("Infernal"),
            /**
             * Corruption
             */
            Corruption("Corruption"),
            /**
             * Deceit
             */
            Deceit("Deceit"),
            /**
             * Madness
             */
            Madness("Madness"),
            /**
             * Pain
             */
            Pain("Pain"),
            /**
             * Fear
             */
            Fear("Fear"),
            /**
             * Death
             */
            Death("Death"),
            /**
             * Destruction
             */
            Destruction("Destruction"),
            /**
             * Draconic
             */
            Draconic("Draconic"),
            /**
             * Troglodyte
             */
            Troglodyte("Troglodyte"),
            /**
             * Saurien
             */
            Saurien("Saurien"),
            /**
             * Tuatara
             */
            Tuatara("Tuatara"),
            /**
             * Gargoyle
             */
            Gargoyle("Gargoyle"),
            /**
             * Ophidian
             */
            Ophidian("Ophidian"),
            /**
             * SocialAdaptation
             */
            SocialAdaptation("Social Adaptation"),
            /**
             * AdaptiveAdvantages
             */
            AdaptiveAdvantages("Adaptive Advantages"),
            /**
             * WeaponAdaptation
             */
            WeaponAdaptation("Weapon Adaptation"),
            /**
             * ExtremisAcid
             */
            ExtremisAcid("Extremis Acid"),
            /**
             * ExtremisCold
             */
            ExtremisCold("Extremis Cold"),
            /**
             * ExtremisElectricity
             */
            ExtremisElectricity("Extremis Electricity"),
            /**
             * ExtremisFire
             */
            ExtremisFire("Extremis Fire"),
            /**
             * ExtremisPoison
             */
            ExtremisPoison("Extremis Poison"),
            /**
             * ExtremisRadiation
             */
            ExtremisRadiation("Extremis Radiation"),
            /**
             * ExtremisResonance
             */
            ExtremisResonance("Extremis Resonance"),
            /**
             * Ursidae
             */
            Ursidae("Ursidae"),
            /**
             * CanusLupis
             */
            CanusLupis("Canus Lupis"),
            /**
             * AvianAves
             */
            AvianAves("Avian Aves"),
            /**
             * Bor
             */
            Bor("Bor"),
            /**
             * Ovis
             */
            Ovis("Ovis"),
            /**
             * Taurus
             */
            Taurus("Taurus"),
            /**
             * Feline
             */
            Feline("Feline"),
            /**
             * Vermin
             */
            Vermin("Vermin"),
            /**
             * Caballis
             */
            Caballis("Caballis"),
            /**
             * Ichthyes
             */
            Ichthyes("Ichthyes"),
            /**
             * Caverns
             */
            Caverns("Caverns"),
            /**
             * Desert
             */
            Desert("Desert"),
            /**
             * Forests
             */
            Forests("Forests"),
            /**
             * Marsh
             */
            Marsh("Marsh"),
            /**
             * Mountains
             */
            Mountains("Mountains"),
            /**
             * Moon
             */
            Moon("Moon"),
            /**
             * Oceans
             */
            Oceans("Oceans"),
            /**
             * Plains
             */
            Plains("Plains"),
            /**
             * Sky
             */
            Sky("Sky"),
            /**
             * Tundra
             */
            Tundra("Tundra"),
            /**
             * MonstrousBiests
             */
            MonstrousBiests("Monstrous Biests"),
            /**
             * BreedingAdaptations
             */
            BreedingAdaptations("Breeding Adaptations"),
            /**
             * GoverningIdeology
             */
            GoverningIdeology("Governing Ideology"),
            /**
             * OmnivoreAdaptations
             */
            OmnivoreAdaptations("Omnivore Adaptations"),
            /**
             * PredatorialAdvantage
             */
            PredatorialAdvantage("Predatorial Advantage"),
            /**
             * PreyAdaptations
             */
            PreyAdaptations("Prey Adaptations"),
            /**
             * AmbushTactics
             */
            AmbushTactics("Ambush Tactics"),
            /**
             * Armory
             */
            Armory("Armory"),
            /**
             * Occult
             */
            Occult("Occult"),
            /**
             * Shamanism
             */
            Shamanism("Shamanism"),
            /**
             * Arachnid
             */
            Arachnid("Arachnid"),
            /**
             * Scorpionoid
             */
            Scorpionoid("Scorpionoid"),
            /**
             * Decapod
             */
            Decapod("Decapod"),
            /**
             * Isopod
             */
            Isopod("Isopod"),
            /**
             * Coleoptera
             */
            Coleoptera("Coleoptera"),
            /**
             * Dipteran
             */
            Dipteran("Dipteran"),
            /**
             * Formicadae
             */
            Formicadae("Formicadae"),
            /**
             * Mantid
             */
            Mantid("Mantid"),
            /**
             * Vespidae
             */
            Vespidae("Vespidae"),
            /**
             * Centipedea
             */
            Centipedea("Centipedea"),
            /**
             * Millipedea
             */
            Millipedea("Millipedea"),
            /**
             * Exoskeleton
             */
            Exoskeleton("Exoskeleton"),
            /**
             * GeneticMutations
             */
            GeneticMutations("Genetic Mutations"),
            /**
             * Circulatory
             */
            Circulatory("Circulatory"),
            /**
             * BioWeaponry
             */
            BioWeaponry("BioWeaponry"),
            /**
             * Eusociality
             */
            Eusociality("Eusociality"),
            /**
             * Combat
             */
            Combat("Combat"),
            /**
             * AdvancedKnowledge
             */
            AdvancedKnowledge("Advanced Knowledge"),
            /**
             * PsychiccNodes
             */
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

        /**
         * CharacteristicGroup
         */
        public enum CharacteristicGroup {

            /**
             * standard
             */
            standard("standard"),
            /**
             * Ursidae
             */
            Ursidae("Ursidae"),
            /**
             * CanusLupis
             */
            CanusLupis("Canus Lupis"),
            /**
             * AvianAves
             */
            AvianAves("Avian Aves"),
            /**
             * Bor
             */
            Bor("Bor"),
            /**
             * Ovis
             */
            Ovis("Ovis"),
            /**
             * Taurus
             */
            Taurus("Taurus"),
            /**
             * Feline
             */
            Feline("Feline"),
            /**
             * Vermin
             */
            Vermin("Vermin"),
            /**
             * Caballis
             */
            Caballis("Caballis"),
            /**
             * Ichthyes
             */
            Ichthyes("Ichthyes"),
            /**
             * Arachnea
             */
            Arachnea("Arachnea"),
            /**
             * Crustacea
             */
            Crustacea("Crustacea"),
            /**
             * Insecta
             */
            Insecta("Insecta"),
            /**
             * Myriapoda
             */
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
            /**
             * Troglodyte
             */
            Troglodyte("Troglodyte"),
            /**
             * Saurien
             */
            Saurien("Saurien"),
            /**
             * Tuatara
             */
            Tuatara("Tuatara"),
            /**
             * Gargoyle
             */
            Gargoyle("Gargoyle"),
            /**
             * Ophidian
             */
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

            /**
             * NONE
             */
            NONE(""),
            /**
             * Ursidae
             */
            Ursidae("Ursidae"),
            /**
             * CanusLupis
             */
            CanusLupis("Canus Lupis"),
            /**
             * AvianAves
             */
            AvianAves("Avian Aves"),
            /**
             * Bor
             */
            Bor("Bor"),
            /**
             * Ovis
             */
            Ovis("Ovis"),
            /**
             * Taurus
             */
            Taurus("Taurus"),
            /**
             * Feline
             */
            Feline("Feline"),
            /**
             * Vermin
             */
            Vermin("Vermin"),
            /**
             * Caballis
             */
            Caballis("Caballis"),
            /**
             * Ichthyes
             */
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
            /**
             * NONE
             */
            NONE(""),
            /**
             * Caverns
             */
            Caverns("Caverns"),
            /**
             * Desert
             */
            Desert("Desert"),
            /**
             * Forests
             */
            Forests("Forests"),
            /**
             * Marsh
             */
            Marsh("Marsh"),
            /**
             * Mountains
             */
            Mountains("Mountains"),
            /**
             * Moon
             */
            Moon("Moon"),
            /**
             * Oceans
             */
            Oceans("Oceans"),
            /**
             * Plains
             */
            Plains("Plains"),
            /**
             * Sky
             */
            Sky("Sky"),
            /**
             * Tundra
             */
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

            /**
             * NONE
             */
            NONE(""),
            /**
             * Arachnea
             */
            Arachnea("Arachnea"),
            /**
             * Crustacea
             */
            Crustacea("Crustacea"),
            /**
             * Insecta
             */
            Insecta("Insecta"),
            /**
             * Myriapoda
             */
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

            /**
             * NONE
             */
            NONE(""),
            /**
             * Arachnid
             */
            Arachnid("Arachnid"),
            /**
             * Scorpionoid
             */
            Scorpionoid("Scorpionoid"),
            /**
             * Decapod
             */
            Decapod("Decapod"),
            /**
             * Isopod
             */
            Isopod("Isopod"),
            /**
             * Coleoptera
             */
            Coleoptera("Coleoptera"),
            /**
             * Dipteran
             */
            Dipteran("Dipteran"),
            /**
             * Formicadae
             */
            Formicadae("Formicadae"),
            /**
             * Mantid
             */
            Mantid("Mantid"),
            /**
             * Vespidae
             */
            Vespidae("Vespidae"),
            /**
             * Centipedea
             */
            Centipedea("Centipedea"),
            /**
             * Millipedea
             */
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

        /**
         * PrimaryChooserValue
         */
        public enum PrimaryChooserValue {

            /**
             * NONE
             */
            NONE("-None-"),
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
            Twilight("Shadows of Twilight"),
            /**
             * Ursidae
             */
            Ursidae("Ursidae"),
            /**
             * CanusLupis
             */
            CanusLupis("Canus Lupis"),
            /**
             * AvianAves
             */
            AvianAves("Avian Aves"),
            /**
             * Bor
             */
            Bor("Bor"),
            /**
             * Ovis
             */
            Ovis("Ovis"),
            /**
             * Taurus
             */
            Taurus("Taurus"),
            /**
             * Feline
             */
            Feline("Feline"),
            /**
             * Vermin
             */
            Vermin("Vermin"),
            /**
             * Caballis
             */
            Caballis("Caballis"),
            /**
             * Ichthyes
             */
            Ichthyes("Ichthyes"),
            /**
             * Arachnea
             */
            Arachnea("Arachnea"),
            /**
             * Crustacea
             */
            Crustacea("Crustacea"),
            /**
             * Insecta
             */
            Insecta("Insecta"),
            /**
             * Myriapoda
             */
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

        /**
         * SecondaryChooserValue
         */
        public enum SecondaryChooserValue {
            /**
             * NONE
             */
            NONE("-None-"),
            /**
             * Light
             */
            Light("Path of Light"),
            /**
             * Darkness
             */
            Darkness("Path of Darkness"),
            /**
             * Caverns
             */
            Caverns("Caverns"),
            /**
             * Desert
             */
            Desert("Desert"),
            /**
             * Forests
             */
            Forests("Forests"),
            /**
             * Marsh
             */
            Marsh("Marsh"),
            /**
             * Mountains
             */
            Mountains("Mountains"),
            /**
             * Moon
             */
            Moon("Moon"),
            /**
             * Oceans
             */
            Oceans("Oceans"),
            /**
             * Plains
             */
            Plains("Plains"),
            /**
             * Sky
             */
            Sky("Sky"),
            /**
             * Tundra
             */
            Tundra("Tundra"),
            /**
             * Arachnid
             */
            Arachnid("Arachnid"),
            /**
             * Scorpionoid
             */
            Scorpionoid("Scorpionoid"),
            /**
             * Decapod
             */
            Decapod("Decapod"),
            /**
             * Isopod
             */
            Isopod("Isopod"),
            /**
             * Coleoptera
             */
            Coleoptera("Coleoptera"),
            /**
             * Dipteran
             */
            Dipteran("Dipteran"),
            /**
             * Formicadae
             */
            Formicadae("Formicadae"),
            /**
             * Mantid
             */
            Mantid("Mantid"),
            /**
             * Vespidae
             */
            Vespidae("Vespidae"),
            /**
             * Centipedea
             */
            Centipedea("Centipedea"),
            /**
             * Millipedea
             */
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

            /**
             * Skills
             */
            Skills("Skills"),
            /**
             * Advancements
             */
            Advancements("Advancements"),
            /**
             * Type
             */
            Type("Type"),
            /**
             * BasedOn
             */
            BasedOn("BasedOn"),
            /**
             * AdditionalCost
             */
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

            /**
             * Advancements
             */
            Advancements("Advancements"),
            /**
             * BasedOn
             */
            BasedOn("BasedOn"),
            /**
             * AdditionalCost
             */
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

}
