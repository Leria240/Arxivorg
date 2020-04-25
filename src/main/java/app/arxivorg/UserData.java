package app.arxivorg;

import app.arxivorg.model.Archive;

public class UserData {

    public static void main(final String[] args) {

        //Création de l'archive contenant tous les articles
        Archive archive = new Archive();
        archive.getArticle(1).addToFavorites();
        archive.getArticle(5).addToFavorites();
        archive.getArticle(8).addToFavorites();
    }


}
