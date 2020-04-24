package app.arxivorg.model;
import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.crypto.Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Archive {

    private List<Article> articles;

    public Archive() {
        this.articles = getArticlesFromAPI("http://export.arxiv.org/api/query?search_query=all&start=0&max_results=100");
    }

    public List<Article> getAllArticles() {
        return articles;
    }

    public List<Article> getSelectedArticles() {
        List<Article> selectedArticles = new ArrayList<>();
        for (Article article : articles) {
            if (article.isSelected()) {
                selectedArticles.add(article);
            }
        }
        return selectedArticles;
    }


    public List<Article> getArticlesFromAPI(String url){

        List<Article> articlesFromAPI = new ArrayList<>();
        URL feedUrl = null;

        try {
            feedUrl = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {

                final String title = entry.getTitle().replaceAll("\n\t"," ");
                final String id = entry.getUri();
                final String updated = entry.getUpdatedDate().toString();
                final String published = entry.getPublishedDate().toString();
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




    public void addArticles(File file) {
    }


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

    public Article getSelectedArticle(int index) {
        int counter = 0;
        int i;
        for (i = -1; counter <= index; i++) {
            if (articles.get(i + 1).isSelected()) {
                counter++;
            }
        }
        return (i < 0) ? articles.get(0) : articles.get(i);
    }

    public Article getArticle(int index) {
        return articles.get(index);
    }

    public void selectAll() {
        for (Article article : articles) {
            article.setSelected(true);
        }
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





    public void categoryFilter(String category) {
        if (category.equals(" All categories")) return;
        for (Article article : articles) {
            if (!article.getCategory().contains(category)) {
                article.setSelected(false);
            }
        }
    }


    public void authorFilter(String authors) {
        if (authors.equals("")) return;
        String[] tabAuthors = authors.split(",");
        for (Article article : articles) {
            for (String author : tabAuthors) {
                if (!article.getAuthors().contains(author)) {
                    article.setSelected(false);
                }
            }
        }
    }

    public void keyWordFilter(String keyword, String target) {
        keyword.replaceAll(",", " ");
        String[] tabKeyWord = keyword.split(" ");
        for (Article article : articles) {
            if (target == "title")
                titleKeyWordFilter(tabKeyWord, article);
            if (target == "summary")
                summaryKeyWordFilter(tabKeyWord, article);
        }
    }

    public void titleKeyWordFilter(String[] tabKeyWord, Article article) {
        for (String word : tabKeyWord) {
            if (!article.getTitle().toLowerCase().contains(word.toLowerCase())) {
                article.setSelected(false);
                break;
            }
        }
    }

    public void summaryKeyWordFilter(String[] tabKeyWord, Article article) {
        for (String word : tabKeyWord) {
            if (!article.getSummary().toLowerCase().contains(word.toLowerCase())) {
                article.setSelected(false);
                break;
            }
        }
    }


    public void dateFilter(String stringDate) {
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

    public void nonListedFilter() throws IOException {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

            File file = new File("userData.xml");

            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(file);

            final Element racine = document.getDocumentElement();
            final NodeList entry = racine.getElementsByTagName("user");

            if (entry.item(0).getNodeType() == Node.ELEMENT_NODE) {
                final Element user = (Element) entry.item(0);
                final String lastConnexionDate = user.getElementsByTagName("LastConnexionDate").item(0).getTextContent();
                dateFilter(lastConnexionDate);
            }

            /*
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = br.readLine();
            dateFilter();
            br.close();
             */

        } catch (ParserConfigurationException | SAXException e) {
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
            InputStream in = null;
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