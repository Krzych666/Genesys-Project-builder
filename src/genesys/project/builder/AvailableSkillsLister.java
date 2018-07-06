package genesys.project.builder;

import genesys.project.builder.Enums.Enmuerations.LifeDomainTree1Values;
import genesys.project.builder.Enums.Enmuerations.LifeDomainTree2Values;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import java.sql.PreparedStatement;
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
        ObservableList data = FXCollections.observableArrayList();
        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree1 FROM Skills WHERE LifeDomain = ? AND LifeDomainTree2 = ? AND Age <= ?");
        stmt.setString(1, lifeDomain.toString());
        stmt.setString(2, skillSubSet);
        stmt.setInt(3, maxAge);
        data = BuilderCORE.getData(stmt, columns2, null, 0);
        lifeDomainTree1Value = LifeDomainTree1Values.getEnum(data.get(0).toString());
        lifeDomainTree2Value = LifeDomainTree2Values.getEnum(skillSubSet);
        if (!IgnoreSkillsList.isEmpty()) {
            for (int i = 0; i < IgnoreSkillsList.size(); i++) {
                chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT LifeDomainTree3 FROM Skills WHERE (LifeDomainTree2 = ? OR LifeDomainTree3 = ?) AND SkillName = ? AND Age <= ?");
                stmt1.setString(1, skillSubSet);
                stmt1.setString(2, reptiliaSpecific[1]);
                stmt1.setString(3, IgnoreSkillsList.get(i).toString().split(" \\(p")[0]);
                stmt1.setInt(4, maxAge);
                data = BuilderCORE.getData(stmt1, columns1, null, 0);
                lifeDomainTree3.addAll(data);
            }
        }
        switch (lifeDomain) {
            case Humanoid:
                chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                stmt.setString(1, skillSubSet);
                stmt.setString(2, basic[0]);
                stmt.setInt(3, maxAge);
                data = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
                if (!data.isEmpty()) {
                    tmp.add("--" + basic[0] + "--");
                    tmp.addAll(data);
                    tmp.add(" ");
                }
                break;
            case Fey:
                chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                stmt.setString(1, skillSubSet);
                stmt.setString(2, basic[1]);
                stmt.setInt(3, maxAge);
                data = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
                if (!data.isEmpty()) {
                    tmp.add("--" + basic[1] + "--");
                    tmp.addAll(data);
                    tmp.add(" ");
                }
                break;
            case Reptilia:
                if (IgnoreSkillsList.isEmpty()) {
                    chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                    stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                    stmt.setString(1, skillSubSet);
                    stmt.setString(2, reptiliaSpecific[1]);
                    stmt.setInt(3, maxAge);
                    data = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
                    if (!data.isEmpty()) {
                        tmp.add("--" + reptiliaSpecific[1] + "--");
                        tmp.addAll(data);
                        tmp.add(" ");
                    }
                } else if ((!lifeDomainTree3.contains(reptiliaSpecific[1])) || (IgnoreSkillsList.stream().anyMatch(skill -> {
                    ObservableList<String> tmp3 = FXCollections.observableArrayList();
                    try {
                        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                        PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                        stmt3.setString(1, skillSubSet);
                        stmt3.setString(2, reptiliaSpecific[1]);
                        stmt3.setInt(3, maxAge);
                        tmp3 = BuilderCORE.getData(stmt3, columns, null, 0);
                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseModifier.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return (skill.toString().split(" \\(p")[0]).equals(tmp3.get(0));
                })) || (IgnoreSkillsList.contains("Ophidian"/*To implement for rare and ancient blodline swap*/))) {
                    chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                    stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                    stmt.setString(1, skillSubSet);
                    stmt.setString(2, reptiliaSpecific[1]);
                    stmt.setInt(3, maxAge);
                    data = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
                    if (!data.isEmpty()) {
                        tmp.add("--" + reptiliaSpecific[1] + "--");
                        tmp.addAll(data);
                        tmp.add(" ");
                    }
                }
                chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                stmt.setString(1, skillSubSet);
                stmt.setString(2, basic[1]);
                stmt.setInt(3, maxAge);
                data = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
                if (!data.isEmpty()) {
                    tmp.add("--" + basic[1] + "--");
                    tmp.addAll(data);
                    tmp.add(" ");
                }
                break;
            case Biest:
                chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND (LifeDomainTree3 = ? OR LifeDomainTree3 = ?) AND Age <= ?");
                stmt.setString(1, skillSubSet);
                stmt.setString(2, basic[0]);
                stmt.setString(3, basic[1]);
                stmt.setInt(4, maxAge);
                data = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
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
                chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND (LifeDomainTree3 = ? OR LifeDomainTree3 = ?) AND Age <= ?");
                stmt.setString(1, skillSubSet);
                stmt.setString(2, basic[0]);
                stmt.setString(3, basic[1]);
                stmt.setInt(4, maxAge);
                data = BuilderCORE.getData(stmt, columns, IgnoreSkillsList, 1);
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
            PreparedStatement stmt2 = null;
            switch (lifeDomain) {
                case Humanoid:
                    if (lifeDomainTree3.contains(basic[0])) {
                        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                        stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                        stmt2.setString(1, skillSubSet);
                        stmt2.setString(2, basic[3]);
                        stmt2.setInt(3, maxAge);
                        data = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
                        if (!data.isEmpty()) {
                            tmp.add("--" + basic[3] + "--");
                            tmp.addAll(data);
                        }
                    }
                    break;
                case Fey:
                    if (lifeDomainTree3.contains(basic[1])) {
                        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                        stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND (LifeDomainTree3 = ? OR LifeDomainTree3 = ? OR LifeDomainTree3 = ?) AND Age <= ?");
                        stmt2.setString(1, skillSubSet);
                        stmt2.setString(2, basic[2]);
                        stmt2.setString(3, basic[4]);
                        stmt2.setString(4, basic[4]);
                        stmt2.setInt(5, maxAge);
                        if (lifeDomainTree3.contains(basic[4])) {
                            stmt2.setString(4, basic[5]);
                        }
                        data = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
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
                        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                        stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                        stmt2.setString(1, skillSubSet);
                        stmt2.setString(2, basic[4]);
                        stmt2.setInt(3, maxAge);
                        data = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
                        if (!data.isEmpty()) {
                            tmp.add("--" + basic[4] + "--");
                            tmp.addAll(data);
                            tmp.add(" ");
                        }
                    }
                    if (traitNumbers[1] / 2 >= traitNumbers[2] + 1) {
                        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                        stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                        stmt2.setString(1, skillSubSet);
                        stmt2.setString(2, reptiliaSpecific[0]);
                        stmt2.setInt(3, maxAge);
                        data = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
                        if (!data.isEmpty()) {
                            tmp.add("--" + reptiliaSpecific[0] + "--");
                            tmp.addAll(data);
                        }
                    }
                    break;
                case Biest:
                    if (lifeDomainTree3.contains(basic[0]) || lifeDomainTree3.contains(basic[1])) {

                        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                        stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                        stmt2.setString(1, skillSubSet);
                        stmt2.setInt(3, maxAge);
                        switch (lifeDomainTree1Value) {
                            case BiestialKingdoms:
                            case RegionalTraits:
                                stmt2.setString(2, basic[4]);
                                break;
                            case GeneticMutation:
                            case EnvironmentalAdaptability:
                            case SpiritualandScientificKnowledge:
                                stmt2.setString(2, basic[3]);
                                break;
                            default:
                                break;
                        }
                        data = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
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
                        chooseConnection(Enums.Enmuerations.UseCases.COREdb);
                        stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT SkillName FROM Skills WHERE LifeDomainTree2 = ? AND LifeDomainTree3 = ? AND Age <= ?");
                        stmt2.setString(1, skillSubSet);
                        stmt2.setInt(3, maxAge);
                        switch (lifeDomainTree1Value) {
                            case Arachnea:
                            case Crustacea:
                            case Insecta:
                            case Myriapoda:
                            case GeneticMorphology:
                            case EnvironmentalAdaptation:
                                stmt2.setString(2, basic[4]);
                                break;
                            case GeneticMutation:
                                stmt2.setString(2, basic[3]);
                                break;
                            default:
                                break;
                        }
                        switch (lifeDomainTree2Value) {
                            case Eusociality:
                            case Combat:
                                stmt2.setString(2, basic[4]);
                                break;
                            case EnvironmentalExtremes:
                            case AdvancedKnowledge:
                            case PsychiccNodes:
                                stmt2.setString(2, basic[3]);
                                break;
                            default:
                                break;
                        }
                        data = BuilderCORE.getData(stmt2, columns, IgnoreSkillsList, 1);
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

}
