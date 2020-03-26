package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiveTest {

    @Test
    public void testAddArticles() {
        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);

        Archive archive1000 = new Archive();
        archive1000.addArticles(new File("atomFile3.xml"));

        assertNotNull(archive.getArticles(), "L'article est vide");
        assertEquals(10, archive.getArticles().size());
        assertEquals(1000,archive1000.getArticles().size());

        assertEquals(archive.getArticles().get(0).getId(), ArticleTest.article1.getId());
        assertEquals(archive.getArticles().get(0).getUpdated(), ArticleTest.article1.getUpdated());
        assertEquals(archive.getArticles().get(0).getPublished(), ArticleTest.article1.getPublished());
        assertEquals(archive.getArticles().get(0).getTitle(),ArticleTest.article1.getTitle());
        assertEquals(archive.getArticles().get(0).getSummary(), ArticleTest.article1.getSummary());
        assertEquals(archive.getArticles().get(0).getAuthors().getData(),ArticleTest.article1.getAuthors().getData());
        assertEquals(archive.getArticles().get(0).getURL_pageArxiv(), ArticleTest.article1.getURL_pageArxiv());
        assertEquals(archive.getArticles().get(0).getURL_PDF(), ArticleTest.article1.getURL_PDF());
        assertEquals(archive.getArticles().get(0).getCategory(),ArticleTest.article1.getCategory());

        assertEquals(archive.getArticles().get(9).getId(), ArticleTest.article10.getId());
        assertEquals(archive.getArticles().get(9).getUpdated(), ArticleTest.article10.getUpdated());
        assertEquals(archive.getArticles().get(9).getPublished(), ArticleTest.article10.getPublished());
        assertEquals(archive.getArticles().get(9).getTitle(),ArticleTest.article10.getTitle());
        assertEquals(archive.getArticles().get(9).getSummary(), ArticleTest.article10.getSummary());
        assertEquals(archive.getArticles().get(9).getAuthors().getData(),ArticleTest.article10.getAuthors().getData());
        assertEquals(archive.getArticles().get(9).getURL_pageArxiv(), ArticleTest.article10.getURL_pageArxiv());
        assertEquals(archive.getArticles().get(9).getURL_PDF(), ArticleTest.article10.getURL_PDF());
        assertEquals(archive.getArticles().get(9).getCategory(),ArticleTest.article10.getCategory());
    }

    @Test
    public void testAddArticle(){
        Archive archive = new Archive();
        assertEquals(0,archive.getArticles().size());
        archive.addArticle(ArticleTest.article1);
        assertEquals(1,archive.getArticles().size());
        assertTrue(archive.getArticles().contains(ArticleTest.article1));
    }

    @Test
    public void testDeleteArticle(){
        Archive archive = new Archive();
        archive.addArticle(ArticleTest.article1);
        archive.addArticle(ArticleTest.article10);

        assertTrue(archive.getArticles().contains(ArticleTest.article1));
        assertTrue(archive.getArticles().contains(ArticleTest.article10));
        assertEquals(2,archive.getArticles().size());

        archive.deleteArticle(ArticleTest.article1);

        assertFalse(archive.getArticles().contains(ArticleTest.article1));
        assertTrue(archive.getArticles().contains(ArticleTest.article10));
        assertEquals(1, archive.getArticles().size());
    }

    @Test
    public void testCategoryFilter(){
        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);
        List<String> categories = new ArrayList<>();
        categories.add("cs.LG");
        categories.add("cs.CL");

        List<Article> result = archive.categoryFilter(categories);
        assert(!result.isEmpty());
        for (int i = 0; i < result.size(); i++) {
            assert (result.get(i).getCategory().containsAll(categories));
            System.out.println(result.get(i).getTitle());
            System.out.println(result.get(i).getCategory());
        }
    }

    @Test
    public void testAuthorFilter(){
        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);

        List<String> authorslist = new ArrayList<>();
        authorslist.add("Thomas Bachlechner");
        authorslist.add("Ira Leviant");
        authorslist.add("Jiaqing Lin");
        Authors authors = new Authors(authorslist);

        List<Article> result = archive.authorFilter(authors);
        assert(!result.isEmpty());
        for (int i = 0; i < result.size(); i++) {
            assert (result.get(i).getAuthors().getData().contains(authors.getData().get(0)) ||
                    result.get(i).getAuthors().getData().contains(authors.getData().get(1)) ||
                    result.get(i).getAuthors().getData().contains(authors.getData().get(2)));
            System.out.println(result.get(i).getTitle());
            System.out.println(result.get(i).getAuthors() + "\n");
        }
    }

    @Test
    public void testKeyWordFilter() {
        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);
        String titleKeyword = new String("Cross-Lingual");
        String titleKeyword2 = new String("Video Caption Dataset");
        String summaryKeyword = new String("sticker response");
        String summaryKeyword2 = new String("ReZero-Transformer networks");

        List<Article> result = archive.keyWordFilter(titleKeyword);
        List<Article> result2 = archive.keyWordFilter(titleKeyword2);
        List<Article> result3 = archive.keyWordFilter(summaryKeyword);
        List<Article> result4 = archive.keyWordFilter(summaryKeyword2);

        assert(!result.isEmpty());
        for (int i = 0; i < result.size(); i++) {
            assert (result.get(i).getTitle().contains(titleKeyword));
            System.out.println(result.get(i).getTitle() + '\n');
        }

        assert(!result2.isEmpty());
        for(int k = 0; k < result2.size(); k++){
            assert (result2.get(k).getTitle().contains(titleKeyword2));
            System.out.println(result2.get(k).getTitle() + '\n');
        }

        assert(!result3.isEmpty());
        for(int k = 0; k < result3.size(); k++){
            assert (result3.get(k).getSummary().contains(summaryKeyword));
            System.out.println(result3.get(k).getTitle());
            System.out.println(result3.get(k).getSummary() + '\n');
        }

        assert(!result4.isEmpty());
        for(int k = 0; k < result4.size(); k++){
            assert (result4.get(k).getSummary().contains(summaryKeyword2));
            System.out.println(result4.get(k).getTitle());
            System.out.println(result3.get(k).getSummary() + '\n');
        }
    }

    @Test
    public void testDateFilter() throws ParseException {
        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);

        List<Article> result = archive.dateFilter("2020-03-08");

        for (int i = 0; i < result.size(); i++) {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(result.get(i).getPublished().substring(0, 10));
            Date dateLimit = new SimpleDateFormat("dd-MM-yyyy").parse("2020-03-08");
            assert (dateLimit.before(date) || dateLimit.equals(date));
            System.out.println(result.get(i).getTitle());
            System.out.println(result.get(i).getPublished() + '\n');
        }
    }

}