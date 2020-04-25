package app.arxivorg.model;

import org.junit.jupiter.api.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArchiveTest {

    static Archive archive = new Archive();

    @Test
    public void testAddArticles() {

        assertNotNull(archive.getAllArticles(), "L'article est vide");

        List<String> authorlist = new ArrayList<>();
        authorlist.add("Thomas Bachlechner");
        authorlist.add("Bodhisattwa Prasad Majumder");
        authorlist.add("Huanru Henry Mao");
        authorlist.add("Garrison W. Cottrell");
        authorlist.add("Julian McAuley");
        Authors authors = new Authors(authorlist);

        List<String> categorylist = new ArrayList<>();
        categorylist.add("cs.LG");
        categorylist.add("cs.CL");
        categorylist.add("stat.ML");


        assertEquals(archive.getAllArticles().get(0).getId(), ArticleTest.article1.getId());
        assertEquals(archive.getAllArticles().get(0).getUpdated(), ArticleTest.article1.getUpdated());
        assertEquals(archive.getAllArticles().get(0).getPublished(), ArticleTest.article1.getPublished());
        assertEquals(archive.getAllArticles().get(0).getTitle(),ArticleTest.article1.getTitle());
        assertEquals(archive.getAllArticles().get(0).getSummary(), ArticleTest.article1.getSummary());
        assertEquals(archive.getAllArticles().get(0).getAuthors().getData(),ArticleTest.article1.getAuthors().getData());
        assertEquals(archive.getAllArticles().get(0).getURL_pageArxiv(), ArticleTest.article1.getURL_pageArxiv());
        assertEquals(archive.getAllArticles().get(0).getURL_PDF(), ArticleTest.article1.getURL_PDF());
        assertEquals(archive.getAllArticles().get(0).getCategory(),ArticleTest.article1.getCategory());

        assertEquals(archive.getAllArticles().get(9).getId(), ArticleTest.article10.getId());
        assertEquals(archive.getAllArticles().get(9).getUpdated(), ArticleTest.article10.getUpdated());
        assertEquals(archive.getAllArticles().get(9).getPublished(), ArticleTest.article10.getPublished());
        assertEquals(archive.getAllArticles().get(9).getTitle(),ArticleTest.article10.getTitle());
        assertEquals(archive.getAllArticles().get(9).getSummary(), ArticleTest.article10.getSummary());
        assertEquals(archive.getAllArticles().get(9).getAuthors().getData(),ArticleTest.article10.getAuthors().getData());
        assertEquals(archive.getAllArticles().get(9).getURL_pageArxiv(), ArticleTest.article10.getURL_pageArxiv());
        assertEquals(archive.getAllArticles().get(9).getURL_PDF(), ArticleTest.article10.getURL_PDF());
        assertEquals(archive.getAllArticles().get(9).getCategory(),ArticleTest.article10.getCategory());
    }

    @Test
    public void testCategoryFilter(){
        assertEquals(archive.categoryFilter("cs.LG"), "cat:cs.LG");
    }

    @Test
    public void testAuthorFilter(){
        assertEquals(archive.authorFilter("Thomas Bachlechner"), "au:Thomas+AND+au:Bachlechner");
    }


    @Test
    public void testTitleKeyWordFilter() {
        assertEquals(archive.titleKeyWordFilter("Video Caption Dataset"), "ti:Video+AND+ti:Caption+AND+ti:Dataset");
    }

    @Test
    public void testSummaryKeyWordFilter() {
        assertEquals(archive.summaryKeyWordFilter("Rezero-transformer networks faster"), "abs:Rezero-transformer+AND+abs:networks+AND+abs:faster");
    }

    @Test
    public void testDateFilter() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2015-05-06",formatter);
        archive.dateFilter(date);
        LocalDate datePublished1 = LocalDate.parse(archive.getArticle(0).getPublished().substring(0,10),formatter);
        LocalDate datePublished2 = LocalDate.parse(archive.getArticle(5).getPublished().substring(0,10),formatter);
        assert (datePublished1.isAfter(date));
        assert (datePublished2.isAfter(date));
    }

    @Test
    public void testNonListedFilter() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        archive.nonListedFilter();

        if (!archive.getAllArticles().isEmpty()){
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                File file = new File("userData.xml");

                final DocumentBuilder builder = factory.newDocumentBuilder();
                final Document document = builder.parse(file);

                final Element racine = document.getDocumentElement();
                final NodeList entry = racine.getElementsByTagName("user");

                final Element user = (Element) entry.item(0);
                final String lastConnexion = user.getElementsByTagName("LastConnexionDate").item(0).getTextContent();
                LocalDate lastConnexionDate = LocalDate.parse(lastConnexion, formatter);

                LocalDate datePublished1 = LocalDate.parse(archive.getArticle(0).getPublished().substring(0, 10), formatter);
                assert (datePublished1.isAfter(lastConnexionDate));

            } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
                e.printStackTrace();
            }
        }

    }

}