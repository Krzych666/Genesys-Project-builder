/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import static genesys.project.builder.BuilderCORE.chooseConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author krzysztof.g
 */
public class DatabaseSwiper {

    /**
     * databaseSwiper
     */
    public static void databaseSwiper() {
        try {
            ObservableList speciesList = DatabaseReader.getSpeciesList();
            speciesList.removeAll(" ");
            for (Enums.Enmuerations.LifedomainValue domain : BuilderCORE.DOMAINS) {
                speciesList.remove("--" + domain.toString() + "--");
            }
            if (!speciesList.isEmpty()) {
                StringBuilder notInSpeciesString = new StringBuilder();
                for (Object species : speciesList) {
                    notInSpeciesString.append("'").append(species.toString()).append("',");
                }
                notInSpeciesString = new StringBuilder(notInSpeciesString.substring(0, notInSpeciesString.length() - 1));
                StringBuilder temp = new StringBuilder();
                temp.append(
                        DatabaseWriter.DELETEFROMSTRING[1]).append(DatabaseWriter.WHERESTRING2).append("SpeciesName NOT IN (").append(notInSpeciesString.toString()).append(");")
                        .append(DatabaseWriter.DELETEFROMSTRING[2]).append(DatabaseWriter.WHERESTRING2).append("SpeciesName NOT IN (").append(notInSpeciesString.toString()).append(");")
                        .append(DatabaseWriter.DELETEFROMSTRING[3]).append(DatabaseWriter.WHERESTRING2).append("SpeciesName NOT IN (").append(notInSpeciesString.toString()).append(");")
                        .append(DatabaseWriter.DELETEFROMSTRING[4]).append(DatabaseWriter.WHERESTRING2).append("SpeciesName NOT IN (").append(notInSpeciesString.toString()).append(");");
                DatabaseWriter.tempDeletescript = temp.toString();
                DatabaseWriter.commenceDeleting();
            }
            chooseConnection(Enums.Enmuerations.UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT CultureName FROM CreatedCultures");
            String[] columns = {"CultureName"};
            ObservableList culturesList = BuilderCORE.getData(stmt, columns, null, 0);
            if (!culturesList.isEmpty()) {
                StringBuilder notInCulturesString = new StringBuilder();
                for (Object cultures : culturesList) {
                    notInCulturesString.append("'").append(cultures.toString()).append("',");
                }
                notInCulturesString = new StringBuilder(notInCulturesString.substring(0, notInCulturesString.length() - 1));
                StringBuilder temp = new StringBuilder();
                temp.append(DatabaseWriter.DELETEFROMSTRING[2]).append(DatabaseWriter.WHERESTRING2).append("CultureName NOT IN (").append(notInCulturesString.toString()).append(");")
                        .append(DatabaseWriter.DELETEFROMSTRING[3]).append(DatabaseWriter.WHERESTRING2).append("CultureName NOT IN (").append(notInCulturesString.toString()).append(");")
                        .append(DatabaseWriter.DELETEFROMSTRING[4]).append(DatabaseWriter.WHERESTRING2).append("CultureName NOT IN (").append(notInCulturesString.toString()).append(");");
                DatabaseWriter.tempDeletescript = temp.toString();
                DatabaseWriter.commenceDeleting();
            }
            chooseConnection(Enums.Enmuerations.UseCases.Userdb);
            stmt = BuilderCORE.getConnection().prepareStatement("SELECT DISTINCT ClassName FROM CreatedClasses");
            columns[0] = "ClassName";
            ObservableList classesList = BuilderCORE.getData(stmt, columns, null, 0);
            if (!classesList.isEmpty()) {
                StringBuilder notInClassesString = new StringBuilder("'<base species>',");
                for (Object cultures : classesList) {
                    notInClassesString.append("'").append(cultures.toString()).append("',");
                }
                notInClassesString = new StringBuilder(notInClassesString.substring(0, notInClassesString.length() - 1));
                StringBuilder temp = new StringBuilder();
                temp.append(DatabaseWriter.DELETEFROMSTRING[2]).append(DatabaseWriter.WHERESTRING2).append("BasedOn NOT IN (").append(notInClassesString.toString()).append(");")
                        .append(DatabaseWriter.DELETEFROMSTRING[3]).append(DatabaseWriter.WHERESTRING2).append("BasedOn NOT IN (").append(notInClassesString.toString()).append(");");
                DatabaseWriter.tempDeletescript = temp.toString();
                DatabaseWriter.commenceDeleting();
            }
            //.append(DatabaseWriter.DELETEFROMSTRING[5]).append(DatabaseWriter.WHERESTRING1).append("SpeciesName NOT IN (").append(notInSpeciesString).append("');"); keep loose rosters for battle history?
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
