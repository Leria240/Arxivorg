package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiveTest {

    Archive archive = Archive.archiveFile1;


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
        archive.categoryFilter("cs.LG");
        assert (archive.getArticle(0).isSelected());
        assert (!archive.getArticle(1).isSelected());

        archive.selectAll();
    }

    @Test
    public void testAuthorFilter(){

        archive.authorFilter("Thomas Bachlechner");
        assert (archive.getArticle(0).isSelected());
        assert (!archive.getArticle(1).isSelected());
        assertEquals(1, archive.getSelectedArticles().size());

        Archive.archiveFile2.authorFilter("xiaoya LI , han");
        assertEquals(2, Archive.archiveFile2.getSelectedArticles().size());
        assert (Archive.archiveFile2.getArticle(933).isSelected());
        assert (Archive.archiveFile2.getArticle(747).isSelected());

        Archive.archiveFile2.selectAll();
        archive.selectAll();


    }


    @Test
    public void testTitleKeyWordFilter() {
        String titleKeyword = "cross-lingual";
        String titleKeyword2 = "Video Caption Dataset";
        String titleKeyword3 = "void";

        archive.keyWordFilter(titleKeyword,"title");
        assertEquals(1,archive.getSelectedArticles().size());
        assert (archive.getArticle(1).isSelected());
        assert (!archive.getArticle(2).isSelected());

        archive.selectAll();

        archive.keyWordFilter(titleKeyword2,"title");
        assertEquals(1,archive.getSelectedArticles().size());
        assert (archive.getArticle(2).isSelected());
        assert (!archive.getArticle(0).isSelected());

        Archive.archiveFile2.keyWordFilter(titleKeyword3,"title");
        assertEquals(2,Archive.archiveFile2.getSelectedArticles().size());
        assert (Archive.archiveFile2.getArticle(34).isSelected());
        assert (Archive.archiveFile2.getArticle(622).isSelected());

        Archive.archiveFile2.selectAll();
        archive.selectAll();

    }

    @Test
    public void testSummaryKeyWordFilter() {
        String summaryKeyword = "semantic";
        String summaryKeyword2 = "Rezero-transformer networks faster";

        archive.keyWordFilter(summaryKeyword,"summary");
        assertEquals(3,archive.getSelectedArticles().size());
        assert (archive.getArticle(1).isSelected());
        assert (archive.getArticle(5).isSelected());
        assert (archive.getArticle(7).isSelected());

        archive.selectAll();

        archive.keyWordFilter(summaryKeyword2,"summary");
        assertEquals(1, archive.getSelectedArticles().size());
        assert (archive.getArticle(0).isSelected());

        archive.selectAll();
    }


    @Test
    public void testDateFilter(){
        archive.dateFilter("2020-03-10");
        assert (archive.getArticle(0).isSelected());
        assert (!archive.getArticle(9).isSelected());

        archive.selectAll();
    }

}