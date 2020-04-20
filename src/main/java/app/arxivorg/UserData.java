package app.arxivorg;

import app.arxivorg.model.Archive;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserData {

    static Element racine = new Element("user");
    static org.jdom2.Document document = new Document(racine);

    //Création de la date utilisateur (avec la date actuelle)
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime currentDate = LocalDateTime.now();
    String theDate = date.format(currentDate);

    static void display() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    static void save() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream("userData.xml"));

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void update(File file){
        Archive archive = new Archive();
        for(int i = 0; i < archive.getSelectedArticles().size(); i++){
            racine.getChild("lastDateConnexion").setText(theDate);
        }
    }

    public static void main(final String[] args) {

        //Création de la date utilisateur (avec la date actuelle)
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDate = LocalDateTime.now();
        String theDate = date.format(currentDate);
        System.out.println(theDate);

        //Création article favoris
        Archive archive = new Archive();
        for(int i = 0; i < archive.getSelectedArticles().size(); i++){
            boolean bool = archive.getArticle(i).isFavoriteItem();
            String str = String.valueOf(bool);
        }

        Element lastDateConnexion = new Element("lastDateConnexion");
        lastDateConnexion.setText(theDate);
        racine.addContent(lastDateConnexion);

        Element isFavorite = new Element("isFavorite");
        isFavorite.setText("true");
        racine.addContent(isFavorite);

        display();
        save();

        System.out.println("File updated !");

    }



}
