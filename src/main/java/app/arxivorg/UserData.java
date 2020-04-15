package app.arxivorg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserData {
    public static void main(final String[] args) throws IOException, IOException {

        //Création de la date utilisateur (avec la date actuelle)
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        System.out.println(date.format(currentDate));

        File file = new File("userData.txt");

        //Créer le fichier s'il n'existe pas
        if(!file.exists()){
            file.createNewFile();
        }

        //Ecriture dans ce fichier de la date créée
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(String.valueOf(currentDate));
        bw.close();

        System.out.println("Modifications terminées !");

        //Il faut ensuite que ce fichier soit actualisé à chaque démarrage du logiciel (ou récupération d'articles) !!!

    }
}
