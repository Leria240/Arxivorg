package app.arxivorg.model;

import javafx.application.Application;
import javafx.scene.control.Hyperlink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class AuthorsTest{

    private Authors authors = new Authors(Arrays.asList("Martin Dupont", "Marie Martin", "François Cordonnier"));

    @Test
    public void testToString() {
        String expected = "Martin Dupont, Marie Martin, François Cordonnier";
        assertEquals(expected, authors.toString());
    }

    @Test
    public void testGetDataLink(){
        ArrayList<Hyperlink> link = authors.getDataLink();
        assertEquals("Martin Dupont",link.get(0).getText());
        assertEquals("Marie Martin",link.get(1).getText());
        assertEquals("François Cordonnier",link.get(2).getText());
        assertEquals(3,link.size());
    }

    @Test
    public void testContains(){
        assertTrue(authors.contains("Marie Martin"));
        assertTrue(authors.contains("MaRtin DuPont"));
        assertTrue(authors.contains("françois cordonnier"));
        assertFalse(authors.contains("chaton"));
        assertFalse(authors.contains(" "));
    }


}

