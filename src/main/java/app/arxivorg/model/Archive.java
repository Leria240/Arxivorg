package app.arxivorg.model;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

    public Article getSelectedArticle(int index){
        int counter = 0;
        int i;
        for(i=-1; counter <= index ;i++ ) {
            if (articles.get(i+1).isSelected()) {
                counter++;
            }
        }
        return (i<0) ? articles.get(0) : articles.get(i);
    }

    public Article getArticle(int index){
        return articles.get(index);
    }

    public void selectAll(){
        for(Article article: articles){
            article.setSelected(true);
        }
    }


    public Set<String> possibleCategories(){
        Set<String> categories = new TreeSet<>();
        categories.add(" All categories");
        for(Article article: articles){
            categories.addAll(article.getCategory());
        }
        return categories;
    }

    public List<String> possiblePeriod(){
        return Arrays.asList(" All period","Last 1 hour","Last 2 hours","Last 3 hours",
                "Today","Yesterday","Last Week","Last 2 week",
                "Last month","Last 2 month","Last 3 month","Last 6 month",
                "Last year","Last 2 years","Last 3 years","Last 6 years");
    }

    public void categoryFilter (String category){
        if (category.equals(" All categories")) return;
        for (Article article: articles){
            if (!article.getCategory().contains(category)) {
                article.setSelected(false);
            }
        }
    }

    public void authorFilter (String authors) {
        authors.replace(","," ");
        String[] tabAuthors = authors.split(" ");
        for (Article article : articles) {
            for(String author: tabAuthors){
                if (!article.getAuthors().contains(author)) {
                    article.setSelected(false);
                }
            }
        }
    }

    public void keyWordFilter (String keyword){
        keyword.replaceAll(","," ");
        String[] tabKeyWords = keyword.split(" ");
        for (Article article : articles) {
            for(String word: tabKeyWords) {
                if (!article.getTitle().toLowerCase().contains(word.toLowerCase())) {
                    article.setSelected(false);
                }
            }
        }
    }

    public List<Article> dateFilter(String stringDate) throws ParseException {
        List<Article> result = new ArrayList<>();
        for (Article article : articles) {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(article.getPublished().substring(0, 10));
            Date dateLimit = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
            if (dateLimit.before(date) || dateLimit.equals(date)) {
                result.add(article);
            }
        }
        return result;
    }

    //public List<Article> nonListedFilter(String maxDate){}

}

