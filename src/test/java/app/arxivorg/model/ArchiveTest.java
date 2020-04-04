package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiveTest {

    @Test
    public void testAddArticles() {
        Archive archive = new Archive();
        archive.addArticles(new File("atomFile1.xml"));

        assertNotNull(archive.getArticles(), "L'article est vide");

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
    public void testCategoryFilter(){
        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);
        archive.categoryFilter("cs.LG");
        assert (archive.getArticle(0).isSelected());
        assert (!archive.getArticle(1).isSelected());
    }

    @Test
    public void testAuthorFilter(){
        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);
        archive.authorFilter("Thomas Bachlechner");
        assert (archive.getArticle(0).isSelected());
        assert (!archive.getArticle(1).isSelected());
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

        archive.keyWordFilter(titleKeyword);
        assert (archive.getArticle(1).isSelected());
        assert (!archive.getArticle(2).isSelected());

        Archive archive2 = new Archive();
        File file2 = new File("atomFile1.xml");
        archive2.addArticles(file2);
        archive2.keyWordFilter(titleKeyword2);
        assert (archive2.getArticle(2).isSelected());
        assert (!archive2.getArticle(0).isSelected());

        Archive archive3 = new Archive();
        File file3 = new File("atomFile1.xml");
        archive3.addArticles(file3);
        archive3.keyWordFilter(summaryKeyword);
        assert (archive3.getArticle(5).isSelected());
        assert (!archive3.getArticle(0).isSelected());

        Archive archive4 = new Archive();
        File file4 = new File("atomFile1.xml");
        archive4.addArticles(file4);
        archive4.keyWordFilter(summaryKeyword2);
        assert (archive4.getArticle(0).isSelected());
        assert (!archive4.getArticle(1).isSelected());
    }

    @Test
    public void testDateFilter(){
        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);
        archive.dateFilter("2020-03-10");
        assert (archive.getArticle(0).isSelected());
        assert (!archive.getArticle(9).isSelected());
    }

}