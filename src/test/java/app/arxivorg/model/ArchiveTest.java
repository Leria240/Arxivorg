package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiveTest {

    Archive archive1 = Archive.archiveFile1;
    Archive archive2 = Archive.archiveFile2;
    Archive archive3 = Archive.archiveFile3;




    @Test
    public void testAddArticles() {

        assertNotNull(archive1.getAllArticles(), "L'article est vide");

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


        assertEquals(archive1.getAllArticles().get(0).getId(), ArticleTest.article1.getId());
        assertEquals(archive1.getAllArticles().get(0).getUpdated(), ArticleTest.article1.getUpdated());
        assertEquals(archive1.getAllArticles().get(0).getPublished(), ArticleTest.article1.getPublished());
        assertEquals(archive1.getAllArticles().get(0).getTitle(),ArticleTest.article1.getTitle());
        assertEquals(archive1.getAllArticles().get(0).getSummary(), ArticleTest.article1.getSummary());
        assertEquals(archive1.getAllArticles().get(0).getAuthors().getData(),ArticleTest.article1.getAuthors().getData());
        assertEquals(archive1.getAllArticles().get(0).getURL_pageArxiv(), ArticleTest.article1.getURL_pageArxiv());
        assertEquals(archive1.getAllArticles().get(0).getURL_PDF(), ArticleTest.article1.getURL_PDF());
        assertEquals(archive1.getAllArticles().get(0).getCategory(),ArticleTest.article1.getCategory());

        assertEquals(archive1.getAllArticles().get(9).getId(), ArticleTest.article10.getId());
        assertEquals(archive1.getAllArticles().get(9).getUpdated(), ArticleTest.article10.getUpdated());
        assertEquals(archive1.getAllArticles().get(9).getPublished(), ArticleTest.article10.getPublished());
        assertEquals(archive1.getAllArticles().get(9).getTitle(),ArticleTest.article10.getTitle());
        assertEquals(archive1.getAllArticles().get(9).getSummary(), ArticleTest.article10.getSummary());
        assertEquals(archive1.getAllArticles().get(9).getAuthors().getData(),ArticleTest.article10.getAuthors().getData());
        assertEquals(archive1.getAllArticles().get(9).getURL_pageArxiv(), ArticleTest.article10.getURL_pageArxiv());
        assertEquals(archive1.getAllArticles().get(9).getURL_PDF(), ArticleTest.article10.getURL_PDF());
        assertEquals(archive1.getAllArticles().get(9).getCategory(),ArticleTest.article10.getCategory());
    }




    @Test
    public void testCategoryFilter(){
        archive1.categoryFilter("cs.LG");
        assert (archive1.getArticle(0).isSelected());
        assert (!archive1.getArticle(1).isSelected());

        archive1.selectAll();
    }

    @Test
    public void testAuthorFilter(){

        archive3.authorFilter("Thomas Bachlechner");
        assert (archive3.getArticle(0).isSelected());
        assert (!archive3.getArticle(1).isSelected());
        assertEquals(1, archive3.getSelectedArticles().size());

        archive2.authorFilter("xiaoya LI , han");
        assertEquals(2,archive2.getSelectedArticles().size());
        assert (archive2.getArticle(933).isSelected());
        assert (archive2.getArticle(747).isSelected());

        archive2.selectAll();
        archive3.selectAll();
    }


    @Test
    public void testTitleKeyWordFilter() {
        String titleKeyword = "cross-lingual";
        String titleKeyword2 = "Video Caption Dataset";
        String titleKeyword3 = "void";

        archive1.keyWordFilter(titleKeyword,"title");
        assertEquals(1, archive1.getSelectedArticles().size());
        assert (archive1.getArticle(1).isSelected());
        assert (!archive1.getArticle(2).isSelected());

        archive1.selectAll();

        archive1.keyWordFilter(titleKeyword2,"title");
        assertEquals(1, archive1.getSelectedArticles().size());
        assert (archive1.getArticle(2).isSelected());
        assert (!archive1.getArticle(0).isSelected());

        Archive.archiveFile2.keyWordFilter(titleKeyword3,"title");
        assertEquals(2,Archive.archiveFile2.getSelectedArticles().size());
        assert (Archive.archiveFile2.getArticle(34).isSelected());
        assert (Archive.archiveFile2.getArticle(622).isSelected());

        Archive.archiveFile2.selectAll();
        archive1.selectAll();

    }

    @Test
    public void testSummaryKeyWordFilter() {
        String summaryKeyword = "semantic";
        String summaryKeyword2 = "Rezero-transformer networks faster";

        archive1.keyWordFilter(summaryKeyword,"summary");
        assertEquals(3, archive1.getSelectedArticles().size());
        assert (archive1.getArticle(1).isSelected());
        assert (archive1.getArticle(5).isSelected());
        assert (archive1.getArticle(7).isSelected());

        archive1.selectAll();

        archive1.keyWordFilter(summaryKeyword2,"summary");
        assertEquals(1, archive1.getSelectedArticles().size());
        assert (archive1.getArticle(0).isSelected());

        archive1.selectAll();
    }


    @Test
    public void testDateFilter(){
        archive1.dateFilter("2020-03-10");
        assert (archive1.getArticle(0).isSelected());
        assert (!archive1.getArticle(9).isSelected());

        archive1.selectAll();
    }

}