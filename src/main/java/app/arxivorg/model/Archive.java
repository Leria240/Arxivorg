package app.arxivorg.model;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

public class Archive {

    private List<Article> articles;

    public Archive() {
        this.articles = new ArrayList<>();
    }

    public List<Article> getArticles() {
        return articles;
    }


    public void addArticles(File file) {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document= builder.parse(file);
            final Element racine = document.getDocumentElement();
            final NodeList entry = racine.getElementsByTagName("entry");
            final int nbRacineNoeuds = entry.getLength();


            for (int i = 0; i < nbRacineNoeuds; i++) {
                if(entry.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element article = (Element) entry.item(i);

                    //Récupération des id, mises à jour, dates de publication, titres, résumés
                    final String title = article.getElementsByTagName("title").item(0).getTextContent().replaceAll("\n            "," ");
                    final String id = article.getElementsByTagName("id").item(0).getTextContent();
                    final String updated = article.getElementsByTagName("updated").item(0).getTextContent();
                    final String published = article.getElementsByTagName("published").item(0).getTextContent();
                    final String summary = article.getElementsByTagName("summary").item(0).getTextContent();


                    //Récupérations de tous les auteurs pour chaque article
                    List<String> authorslist = new ArrayList<>();
                    for(int j = 0; j < article.getElementsByTagName("author").getLength(); j++){
                        final String author = article.getElementsByTagName("author").item(j).getTextContent();
                        authorslist.add(author.trim());
                    }


                    //Récupération des liens pdf et arxiv
                    String pdf = "";
                    String arxiv = "";
                    for(int k = 0; k < article.getElementsByTagName("link").getLength(); k++){
                        arxiv = article.getElementsByTagName("link").item(0).getAttributes().item(0).getTextContent();
                        pdf = article.getElementsByTagName("link").item(1).getAttributes().item(0).getTextContent();
                    }

                    //Récupération des catégories
                    List<String> categorylist = new ArrayList<>();
                    for (int l = 0; l < article.getElementsByTagName("category").getLength(); l++){
                        final String category = article.getElementsByTagName("category").item(l).getAttributes().item(1).getTextContent();
                        categorylist.add(category.trim());
                    }

                    Authors authors = new Authors(authorslist);
                    Article article1 = new Article(id,updated,published,title,summary,authors,new URL(arxiv),new URL(pdf),categorylist);
                    articles.add(article1);
                }
            }
        }
        catch (final ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteArticle(Article article){
        this.articles.remove(article);
    }

    public void addArticle(Article article){
        this.articles.add(article);
    }
}

