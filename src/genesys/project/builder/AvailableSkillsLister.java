package genesys.project.builder;

import genesys.project.builder.Enums.Enmuerations.LifeDomainTree1Values;
import genesys.project.builder.Enums.Enmuerations.LifeDomainTree2Values;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author krzysztofg
 */
public class AvailableSkillsLister {

    static String[] basic = {"Primary Traits", "Lesser Traits", "Lesser Powers", "Secondary Traits", "Greater Traits", "Greater Powers"};
    static String[] reptiliaSpecific = {"Ancestral Traits", "Reptilian Lineage", "Drake Traits", "Dinosaur Traits", "Annura Traits", "Caudata Traits", "Basilisk Traits", "Naga Traits"};
    static String[] insectaSpecific = {"Arachnea Order", "Crustacea Order", "Insecta Order", "Myriapoda Order"};
    static LifeDomainTree1Values lifeDomainTree1Value;
    static LifeDomainTree2Values lifeDomainTree2Value;
    static String[] columns = {"SkillName"};
    static String[] columns1 = {"LifeDomainTree3"};
    static String[] columns2 = {"LifeDomainTree1"};

    /**
     *
     * @param lifeDomain
     * @param maxAge
     * @param skillSubSet
     * @param IgnoreSkillsList
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getAvailableSkills(Enums.Enmuerations.LifedomainValue lifeDomain, int maxAge, String skillSubSet, ObservableList IgnoreSkillsList) throws SQLException {
        ObservableList<String> tmp = FXCollections.observableArrayList();
        ObservableList<String> lifeDomainTree3 = FXCollections.observableArrayList();
        ObservableList data = DatabaseReader.getLifeDomainTree1OnLifeDomainAndLifeDomain2AndAge(columns2, lifeDomain, skillSubSet, maxAge);
        lifeDomainTree1Value = LifeDomainTree1Values.getEnum(data.get(0).toString());
        lifeDomainTree2Value = LifeDomainTree2Values.getEnum(skillSubSet);
        if (!IgnoreSkillsList.isEmpty()) {
            for (int i = 0; i < IgnoreSkillsList.size(); i++) {
                data = DatabaseReader.getLifeDomainTree3OnLifeDomainTree2OrLifeDomainTree3AndSkillNameAndAgeITERATED(columns1, skillSubSet, reptiliaSpecific, IgnoreSkillsList, maxAge, i);
                lifeDomainTree3.addAll(data);
            }
        }
        switch (lifeDomain) {
            case Humanoid:
                data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, basic[0], maxAge, IgnoreSkillsList);
                if (!data.isEmpty()) {
                    tmp.add("--" + basic[0] + "--");
                    tmp.addAll(data);
                    tmp.add(" ");
                }
                break;
            case Fey:
                data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, basic[1], maxAge, IgnoreSkillsList);
                ;
                if (!data.isEmpty()) {
                    tmp.add("--" + basic[1] + "--");
                    tmp.addAll(data);
                    tmp.add(" ");
                }
                break;
            case Reptilia:
                if (IgnoreSkillsList.isEmpty()) {
                    data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, reptiliaSpecific[1], maxAge, IgnoreSkillsList);
                    if (!data.isEmpty()) {
                        tmp.add("--" + reptiliaSpecific[1] + "--");
                        tmp.addAll(data);
                        tmp.add(" ");
                    }
                } else if ((!lifeDomainTree3.contains(reptiliaSpecific[1])) || (IgnoreSkillsList.stream().anyMatch(skill -> {
                    ObservableList<String> tmp3 = FXCollections.observableArrayList();
                    try {
                        tmp3 = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, reptiliaSpecific[1], maxAge, null);
                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return (skill.toString().split(" \\(p")[0]).equals(tmp3.get(0));
                })) || (IgnoreSkillsList.contains("Ophidian"/*To implement for rare and ancient blodline swap*/))) {
                    data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, reptiliaSpecific[1], maxAge, IgnoreSkillsList);
                    if (!data.isEmpty()) {
                        tmp.add("--" + reptiliaSpecific[1] + "--");
                        tmp.addAll(data);
                        tmp.add(" ");
                    }
                }
                data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, basic[1], maxAge, IgnoreSkillsList);
                if (!data.isEmpty()) {
                    tmp.add("--" + basic[1] + "--");
                    tmp.addAll(data);
                    tmp.add(" ");
                }
                break;
            case Biest:
                data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3OrLifeDomainTree3AndAge(columns, skillSubSet, basic[0], basic[1], maxAge, IgnoreSkillsList);
                if (!data.isEmpty()) {
                    switch (lifeDomainTree1Value) {
                        case BiestialKingdoms:
                        case RegionalTraits:
                            tmp.add("--" + basic[1] + "--");
                            break;
                        case GeneticMutation:
                        case EnvironmentalAdaptability:
                        case SpiritualandScientificKnowledge:
                            tmp.add("--" + basic[0] + "--");
                            break;
                        default:
                            break;
                    }
                    tmp.addAll(data);
                    tmp.add(" ");
                }
                break;
            case Insecta:
                data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3OrLifeDomainTree3AndAge(columns, skillSubSet, basic[0], basic[1], maxAge, IgnoreSkillsList);
                if (!data.isEmpty()) {
                    switch (lifeDomainTree1Value) {
                        case Arachnea:
                        case Crustacea:
                        case Insecta:
                        case Myriapoda:
                        case GeneticMorphology:
                        case EnvironmentalAdaptation:
                            tmp.add("--" + basic[1] + "--");
                            break;
                        case GeneticMutation:
                            tmp.add("--" + basic[0] + "--");
                            break;
                        default:
                            break;
                    }
                    switch (lifeDomainTree2Value) {
                        case Eusociality:
                        case Combat:
                            tmp.add("--" + basic[1] + "--");
                            break;
                        case EnvironmentalExtremes:
                        case AdvancedKnowledge:
                        case PsychiccNodes:
                            tmp.add("--" + basic[0] + "--");
                            break;
                        default:
                            break;
                    }
                    tmp.addAll(data);
                    tmp.add(" ");
                }
                break;
            default:
                break;
        }
        if (!IgnoreSkillsList.isEmpty()) {
            switch (lifeDomain) {
                case Humanoid:
                    if (lifeDomainTree3.contains(basic[0])) {
                        data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, basic[3], maxAge, IgnoreSkillsList);
                        if (!data.isEmpty()) {
                            tmp.add("--" + basic[3] + "--");
                            tmp.addAll(data);
                        }
                    }
                    break;
                case Fey:
                    if (lifeDomainTree3.contains(basic[1])) {
                        data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3OrLifeDomainTree3OrLifeDomainTree3AndAge(columns, skillSubSet, basic[2], basic[4], basic[5], maxAge, IgnoreSkillsList, lifeDomainTree3);
                        if (!data.isEmpty()) {
                            tmp.addAll(data);
                        }
                    }
                    break;
                case Reptilia:
                    int[] traitNumbers = {0, 0, 0};
                    lifeDomainTree3.forEach(traitLevel -> {
                        if (traitLevel.equals(basic[0]) || traitLevel.equals(basic[1])) {
                            traitNumbers[0] += 1;
                        }
                        if (traitLevel.equals(basic[3]) || traitLevel.equals(basic[4])) {
                            traitNumbers[1] += 1;

                        }
                        if (traitLevel.equals(reptiliaSpecific[0])) {
                            traitNumbers[2] += 1;
                        }
                    });

                    if (traitNumbers[0] / 2 >= traitNumbers[1] + 1) {
                        data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, basic[4], maxAge, IgnoreSkillsList);
                        if (!data.isEmpty()) {
                            tmp.add("--" + basic[4] + "--");
                            tmp.addAll(data);
                            tmp.add(" ");
                        }
                    }
                    if (traitNumbers[1] / 2 >= traitNumbers[2] + 1) {
                        data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAge(columns, skillSubSet, reptiliaSpecific[0], maxAge, IgnoreSkillsList);
                        if (!data.isEmpty()) {
                            tmp.add("--" + reptiliaSpecific[0] + "--");
                            tmp.addAll(data);
                        }
                    }
                    break;
                case Biest:
                    if (lifeDomainTree3.contains(basic[0]) || lifeDomainTree3.contains(basic[1])) {
                        data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAgeV2(columns, skillSubSet, basic[3], basic[4], maxAge, IgnoreSkillsList, lifeDomainTree1Value);
                        if (!data.isEmpty()) {
                            switch (lifeDomainTree1Value) {
                                case BiestialKingdoms:
                                case RegionalTraits:
                                    tmp.add("--" + basic[4] + "--");
                                    break;
                                case GeneticMutation:
                                case EnvironmentalAdaptability:
                                case SpiritualandScientificKnowledge:
                                    tmp.add("--" + basic[3] + "--");
                                    break;
                            }
                            tmp.addAll(data);
                        }
                    }
                    break;
                case Insecta:
                    if (lifeDomainTree3.contains(basic[0]) || lifeDomainTree3.contains(basic[1])) {
                        data = DatabaseReader.getSkillNameOnLifeDomainTree2AndLifeDomainTree3AndAgeV3(columns, skillSubSet, basic[3], basic[4], maxAge, IgnoreSkillsList, lifeDomainTree1Value, lifeDomainTree2Value);
                        if (!data.isEmpty()) {
                            switch (lifeDomainTree1Value) {
                                case Arachnea:
                                case Crustacea:
                                case Insecta:
                                case Myriapoda:
                                case GeneticMorphology:
                                case EnvironmentalAdaptation:
                                    tmp.add("--" + basic[4] + "--");
                                    break;
                                case GeneticMutation:
                                    tmp.add("--" + basic[3] + "--");
                                    break;
                                default:
                                    break;
                            }
                            switch (lifeDomainTree2Value) {
                                case Eusociality:
                                case Combat:
                                    tmp.add("--" + basic[4] + "--");
                                    break;
                                case EnvironmentalExtremes:
                                case AdvancedKnowledge:
                                case PsychiccNodes:
                                    tmp.add("--" + basic[3] + "--");
                                    break;
                                default:
                                    break;
                            }
                            tmp.addAll(data);
                        }
                    }
                    break;
            }
        }
        return tmp;
    }

    public static String getSkillsRules(String skills) throws SQLException {
        ObservableList allSkills = DatabaseReader.loadAllSkillsFromDB();
        StringBuilder skillsRules = new StringBuilder();
        for (String split : skills.replaceAll(";", ",").split(",")) {
            if (!split.equals("")) {
                int index = BuilderCORE.getSkillIndex(allSkills, split);
                skillsRules.append(allSkills.get(index).toString().split("\\|")[2]).append(";");
            }
        }
        return skillsRules.toString();
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
        DatabaseHolder.fullSkillList1 = "";
        DatabaseHolder.ruledskills.clear();
        String[] lst = HoldSkills.replaceAll(";", ",").split(",");
        String[] lstref = HoldSkills.replaceAll(";", ",").split(",");
        for (int i = 0; i < lst.length; i++) {
            if (!lstref[i].equals("")) {
                ObservableList data = DatabaseReader.loadSkillFromDB(lstref[i]);
                String[] skl = data.get(0).toString().split("\\|")[2].split(";");
                String ptscost = data.get(0).toString().split("\\|")[1];
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
                    String rulledskill = data.get(0).toString().split("\\|")[6];
                    DatabaseHolder.ruledskills.add(rulledskill + ">" + skl[j]);
                }
            }
        }
        ObservableList<String> tmp = FXCollections.observableArrayList();
        tmp.addAll(lst);
        return tmp;
    }

}
