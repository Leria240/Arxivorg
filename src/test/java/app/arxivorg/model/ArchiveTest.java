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
    public void testAllCategory() {
        assert (archive.getAllCategories().contains(archive.getArticle(0).getCategory().toString()));
        assert (archive.getAllCategories().contains(archive.getArticle(50).getCategory().toString()));
        assert (archive.getAllCategories().contains(archive.getArticle(51).getCategory().toString()));
        assert (archive.getAllCategories().contains(archive.getArticle(40).getCategory().toString()));
    }

        @Test
    public void testCategoryFilter(){
        assertEquals(archive.categoryFilter("cs.LG"), "cat:cs.LG");
        assertEquals(archive.categoryFilter("astro-ph.GA"), "cat:astro-ph.GA");
        assertEquals(archive.categoryFilter("math.CO"), "cat:math.CO");
        assertEquals(archive.categoryFilter("stat.CO"), "cat:stat.CO");
    }

    @Test
    public void testAuthorFilter(){
        assertEquals(archive.authorFilter("Matthew Kwan"), "au:Matthew+AND+au:Kwan");
        assertEquals(archive.authorFilter("Alexei Shadrin"), "au:Alexei+AND+au:Shadrin");
        assertEquals(archive.authorFilter("M. Kawasaki"), "au:M.+AND+au:Kawasaki");
        assertEquals(archive.authorFilter("Zaid Hussain"), "au:Zaid+AND+au:Hussain");
    }


    @Test
    public void testTitleKeyWordFilter() {
        assertEquals(archive.titleKeyWordFilter("Complexity"), "ti:Complexity");
        assertEquals(archive.titleKeyWordFilter("Higher Dimensional"), "ti:Higher+AND+ti:Dimensional");
        assertEquals(archive.titleKeyWordFilter("Integer symmetric matrices"), "ti:Integer+AND+ti:symmetric+AND+ti:matrices");
        assertEquals(archive.titleKeyWordFilter("On surjunctive monoids"), "ti:On+AND+ti:surjunctive+AND+ti:monoids");
    }

    @Test
    public void testSummaryKeyWordFilter() {
        assertEquals(archive.summaryKeyWordFilter("Kill-all"), "abs:Kill-all");
        assertEquals(archive.summaryKeyWordFilter("Japanese rules"), "abs:Japanese+AND+abs:rules");
        assertEquals(archive.summaryKeyWordFilter("complete geometric graph"), "abs:complete+AND+abs:geometric+AND+abs:graph");
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

                final String lastConnexion = racine.getElementsByTagName("LastConnexionDate").item(0).getTextContent();
                LocalDate lastConnexionDate = LocalDate.parse(lastConnexion, formatter);

                LocalDate datePublished1 = LocalDate.parse(archive.getArticle(0).getPublished().substring(0, 10), formatter);
                assert (datePublished1.isAfter(lastConnexionDate));

            } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testArticlesPublishedBy() {
        assert (archive.articlesPublishedBy("Zhujun Zhang").get(0).getAuthors().contains("Zhujun Zhang"));
        assert (archive.articlesPublishedBy("Asaf Ferber").get(0).getAuthors().contains("Asaf Ferber"));
        assert (archive.articlesPublishedBy("Matthew Kwan").get(0).getAuthors().contains("Matthew Kwan"));
        assert (archive.articlesPublishedBy("T. C. Fujita").get(0).getAuthors().contains("T. C. Fujita"));
    }

}