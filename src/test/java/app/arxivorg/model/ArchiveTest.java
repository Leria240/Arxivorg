package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiveTest {

    private Archive archive = new Archive();

    @Test
    public void testAddArticles(){
        archive.addArticles();
        assert (!archive.getArticles().isEmpty());
        assertEquals(archive.getArticles().get(0).getTitle(),"ReZero is All You Need: Fast Convergence at Large Depth");
    }

}
