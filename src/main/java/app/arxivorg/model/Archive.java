package app.arxivorg.model;
import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


import javafx.stage.DirectoryChooser;

public class Archive {

    private List<Article> articles;
    public static List<Article> recentArticles =  getArticlesFromAPI("http://export.arxiv.org/api/query?search_query=all&start=0&max_results=100");

    public Archive() {
        this.articles = recentArticles;
    }

    public Archive(String url){
        articles = getArticlesFromAPI(url);
    }

    public List<Article> getAllArticles() {
        return articles;
    }


    public static List<Article> getArticlesFromAPI(String url){

        List<Article> articlesFromAPI = new ArrayList<>();
        URL feedUrl;

        try {
            feedUrl = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {

                final String title = entry.getTitle().replaceAll("\n\t"," ");
                final String id = entry.getUri();
                final String updated = entry.getUpdatedDate().toInstant().toString();
                final String published = entry.getPublishedDate().toInstant().toString();
                final String summary = entry.getDescription().getValue();

                List<String> authorslist = new ArrayList<>();
                for(SyndPersonImpl author : (List<SyndPersonImpl>) entry.getAuthors()){
                    authorslist.add(author.getName());
                }

                List<String> links = new ArrayList<>();
                for (SyndLinkImpl link : (List<SyndLinkImpl>) entry.getLinks()) {
                    links.add(link.getHref());
                }

                List<String> categories = new ArrayList<>();
                for (SyndCategoryImpl category : (List<SyndCategoryImpl>) entry.getCategories()) {
                    categories.add(category.getName());
                }
                Authors authors = new Authors(authorslist);
                Article article = new Article(id,updated,published,title,summary,authors,new URL(links.get(0)),new URL(links.get(1)),categories);
                articlesFromAPI.add(article);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
    } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        return articlesFromAPI;
    }


    /*
    public void addArticlesDocument(Document document) {

        try {

            final Element racine = document.getDocumentElement();
            final NodeList entry = racine.getElementsByTagName("entry");
            final int nbRacineNoeuds = entry.getLength();


            for (int i = 0; i < nbRacineNoeuds; i++) {
                if(entry.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element article = (Element) entry.item(i);

                    final String title = article.getElementsByTagName("title").item(0).getTextContent().replaceAll("\n            "," ");
                    final String id = article.getElementsByTagName("id").item(0).getTextContent();
                    final String updated = article.getElementsByTagName("updated").item(0).getTextContent();
                    final String published = article.getElementsByTagName("published").item(0).getTextContent();
                    final String summary = article.getElementsByTagName("summary").item(0).getTextContent();

                    List<String> authorslist = new ArrayList<>();
                    for(int j = 0; j < article.getElementsByTagName("author").getLength(); j++){
                        final String author = article.getElementsByTagName("author").item(j).getTextContent();
                        authorslist.add(author.trim());
                    }

                    String pdf = "";
                    String arxiv = "";
                    for(int k = 0; k < article.getElementsByTagName("link").getLength(); k++){
                        arxiv = article.getElementsByTagName("link").item(0).getAttributes().item(0).getTextContent();
                        pdf = article.getElementsByTagName("link").item(1).getAttributes().item(0).getTextContent();
                    }

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
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

     */


    public Article getArticle(int index) {
        return articles.get(index);
    }


    public Set<String> getAllCategories() {
        Set<String> categories = new TreeSet<>();
        categories.add(" All categories");
        List<Article> cat = getArticlesFromAPI("http://export.arxiv.org/api/query?search_query=all&start=0&max_results=100&sortBy=submittedDate&sortOrder=descending");
        for (Article article : cat) {
            categories.addAll(article.getCategory());
        }
        return categories;
    }


    public void filters(String category, String authors, String titleKeyword, String summaryKeyword){
        if(category.equals(" All categories") && authors.isEmpty() && titleKeyword.isEmpty() && summaryKeyword.isEmpty())
            return;
        String request = "http://export.arxiv.org/api/query?search_query=";
        if(!category.equals(" All categories")){
            request += categoryFilter(category);
        }
        if(!authors.isEmpty()){
            if(request.length() > 47) request += "&";
            request += authorFilter(authors);
        }
        if(!titleKeyword.isEmpty()){
            if(request.length() > 47) request += "&";
            request += titleKeyWordFilter(titleKeyword);
        }
        if(!summaryKeyword.isEmpty()){
            if(request.length() > 47) request += "&";
            request += summaryKeyWordFilter(summaryKeyword);
        }
        this.articles = getArticlesFromAPI(request + "&start=0&max_results=100&sortBy=" +
                "submittedDate&sortOrder=descending");
    }


    public String categoryFilter(String category) {
        return (!category.equals(" All categories")) ? "cat:" + category : "";
    }


    public String authorFilter(String authors) {
        authors = authors.replaceAll(",", " ");
        String[] tabKeyWord = authors.split(" ");
        StringBuilder param = new StringBuilder();
        for (String word : tabKeyWord) {
            param.append("au:").append(word).append("+AND+");
        }
        return param.substring(0,param.length()-5);
    }


    public String titleKeyWordFilter(String keyword) {
        keyword = keyword.replaceAll(",", " ");
        String[] tabKeyWord = keyword.split(" ");
        StringBuilder param = new StringBuilder();
        for (String word : tabKeyWord) {
            param.append("ti:").append(word).append("+AND+");
        }
        return param.substring(0,param.length()-5);
    }

    public String summaryKeyWordFilter(String keyword) {
        keyword = keyword.replaceAll(",", " ");
        String[] tabKeyWord = keyword.split(" ");
        StringBuilder param = new StringBuilder();
        for (String word : tabKeyWord) {
            param.append("abs:").append(word).append("+AND+");
        }
        return param.substring(0,param.length()-5);
    }


    public void dateFilter(LocalDate stringDate) {
        List<Article> articlesSelected = new ArrayList<>();
        for (Article article : articles) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(article.getPublished().substring(0, 10),formatter);
            if (stringDate.isBefore(date) || stringDate.equals(date)) {
                System.out.println("apres");
                articlesSelected.add(article);
            }
        }
        articles = articlesSelected;
    }

    public void nonListedFilter(){

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

            File file = new File("userData.xml");

            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(file);

            final Element racine = document.getDocumentElement();
            final NodeList entry = racine.getElementsByTagName("user");

            if (entry.item(0).getNodeType() == Node.ELEMENT_NODE) {
                final Element user = (Element) entry.item(0);
                final String lastConnexion = user.getElementsByTagName("LastConnexionDate").item(0).getTextContent();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate lastConnexionDate = LocalDate.parse(lastConnexion,formatter);
                dateFilter(lastConnexionDate);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadArticles(List<Article> articles) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select some directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = directoryChooser.showDialog(null);

        if (dir == null) return " ";

        for (Article article : articles) {
            String title_syntaxValid = article.getTitle().replaceAll("\\p{Punct}", " ");
            String destination = dir.getAbsolutePath() + "\\" + title_syntaxValid + ".pdf";
            InputStream in;
            String urlString = "https://" + article.getURL_PDF().toString().substring(7);

            try {
                in = new URL(urlString).openStream();
                Files.copy(in, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dir.getAbsolutePath();
    }


    public List<Article> articlesPublishedBy(String authorNAme) {
        List<Article> articleList = new ArrayList<>();
        for (Article article : articles) {
            if (article.getAuthors().contains(authorNAme))
                articleList.add(article);
        }
        return articleList;
    }
}