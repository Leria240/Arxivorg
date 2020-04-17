package app.arxivorg.model;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javafx.stage.DirectoryChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Archive {

    private List<Article> articles;
    static public Archive archiveFile1 =  new Archive(new File("atomFile1.xml"));
    static public Archive archiveFile2 =  new Archive(new File("atomFile2.xml"));



    public Archive() {
        this.articles = new ArrayList<>();
    }

    public Archive(File file){
        this.articles = new ArrayList<>();
        addArticles(file);
    }

    public List<Article> getAllArticles() {
        return articles;
    }

    public List<Article> getSelectedArticles(){
        List<Article> selectedArticles = new ArrayList<>();
        for(Article article : articles) {
            if (article.isSelected()) {
                selectedArticles.add(article);
            }
        }
        return selectedArticles;
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


    public Set<String> getPossibleCategories(){
        Set<String> categories = new TreeSet<>();
        categories.add(" All categories");
        for(Article article: articles){
            categories.addAll(article.getCategory());
        }
        return categories;
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
        if(authors.equals(""))return;
        String[] tabAuthors = authors.split(",");
        for (Article article : articles) {
            for(String author: tabAuthors){
                if (!article.getAuthors().contains(author)) {
                    article.setSelected(false);
                }
            }
        }
    }

    public void keyWordFilter(String keyword,String target){
        keyword.replaceAll(","," ");
        String[] tabKeyWord = keyword.split(" ");
        for (Article article : articles) {
            if(target == "title")
                titleKeyWordFilter(tabKeyWord,article);
            if(target == "summary")
                summaryKeyWordFilter(tabKeyWord,article);
        }
    }

    public void titleKeyWordFilter (String[] tabKeyWord, Article article){
        for (String word : tabKeyWord) {
            if (!article.getTitle().toLowerCase().contains(word.toLowerCase())) {
                article.setSelected(false);
                break;
            }
        }
    }

    public void summaryKeyWordFilter (String[] tabKeyWord, Article article){
        for (String word : tabKeyWord) {
            if (!article.getSummary().toLowerCase().contains(word.toLowerCase())) {
                article.setSelected(false);
                break;
            }
        }
    }



    public void dateFilter(String stringDate){
        for (Article article : articles) {
            Date date = null;
            Date dateLimit = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(article.getPublished().substring(0, 10));
                dateLimit = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dateLimit.after(date) && !dateLimit.equals(date)) {
                article.setSelected(false);
            }
        }
    }


    public void downloadArticles(List<Article> articles){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select some directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = directoryChooser.showDialog(null);

        if (dir == null) return;

        for(Article article: articles) {
            String title_syntaxValid = article.getTitle().replaceAll(":", " ");
            String destination = dir.getAbsolutePath() + "\\" + title_syntaxValid + ".pdf";
            InputStream in = null;
            String urlString = "https://" + article.getURL_PDF().toString().substring(7);

            try {
                in = new URL(urlString).openStream();
                Files.copy(in, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Article> articlesPublishedBy(String authorNAme){
        List<Article> articleList = new ArrayList<>();
        for(Article article: articles){
            if(article.getAuthors().contains(authorNAme))
                articleList.add(article);
        }
        return articleList;
    }
}
