package app.arxivorg.model;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

public class Archive {

    private List<Article> articles;

    public Archive() {
        this.articles = new ArrayList<>();

    }

    public List<Article> getArticles() {
        return articles;
    }

    public void addArticles() {

        //Récupération d'une instance de la classe "DocumentBuilderFactory"
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

             //Création d'un parseur
            final DocumentBuilder builder = factory.newDocumentBuilder();

             //Création d'un Document
            final Document document= builder.parse(new File("test.xml"));

             //Récupération de l'Element racine
            final Element racine = document.getDocumentElement();

            //Affichage de l'élément racine
            System.out.println("\n*************RACINE************");
            System.out.println(racine.getNodeName());

             //Récupération des articles
            final NodeList entry = racine.getElementsByTagName("entry");
            final int nbRacineNoeuds = entry.getLength();


            for (int i = 0; i < nbRacineNoeuds; i++) {
                System.out.println("\n*************ARTICLE " + (i+1) + "************\n");

                if(entry.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element article = (Element) entry.item(i);

                    //Récupération des id, mises à jour, dates de publication, titres, résumés
                    final String title = article.getElementsByTagName("title").item(0).getTextContent();
                    final String id = article.getElementsByTagName("id").item(0).getTextContent();
                    final String updated = article.getElementsByTagName("updated").item(0).getTextContent();
                    final String published = article.getElementsByTagName("published").item(0).getTextContent();
                    final String summary = article.getElementsByTagName("summary").item(0).getTextContent();


                    //Affichage des id, mises à jour, dates de publication, titres, résumés
                    System.out.println("id : " +  id);
                    System.out.println("updated : " +  updated);
                    System.out.println("published : " +  published + "\n");
                    System.out.println("title : " +  title + "\n");
                    System.out.println("summary : " +  summary);
                    System.out.println("auteur : ");

                    List<String> authorslist = new ArrayList<>();

                    //Récupérations de tous les auteurs pour chaque article
                    for(int j = 0; j < article.getElementsByTagName("author").getLength(); j++){
                        final String author = article.getElementsByTagName("author").item(j).getTextContent().trim();
                        authorslist.add(author);
                        //Affichage des auteurs
                        System.out.println(author);
                    }

                    String pdf = "";
                    String arxiv = "";
                    //Récupération des liens pdf et arxiv
                    for(int k = 0; k < article.getElementsByTagName("link").getLength(); k++){
                        final String links = article.getElementsByTagName("link").item(k).getAttributes().item(0).getTextContent();
                        arxiv = article.getElementsByTagName("link").item(0).getAttributes().item(0).getTextContent();
                        pdf = article.getElementsByTagName("link").item(1).getAttributes().item(0).getTextContent();
                        //Affichage des liens
                        System.out.println("link : " + links);
                    }

                    List<String> categorylist = new ArrayList<>();
                    //Récupération des catégories
                    for (int l = 0; l < article.getElementsByTagName("category").getLength(); l++){
                        final String category = article.getElementsByTagName("category").item(l).getAttributes().item(1).getTextContent();
                        categorylist.add(category);
                        //Affichage des catégories
                        System.out.println("category : " + category);
                    }

                    Authors authors = new Authors(authorslist);
                    Article article1 = new Article(id,updated,published,title,summary,authors,arxiv,pdf,categorylist);
                    articles.add(article1);
                }
            }
        }
        catch (final ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (final SAXException e)
        {
            e.printStackTrace();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

    }
}

