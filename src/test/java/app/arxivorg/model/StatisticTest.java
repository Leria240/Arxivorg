package app.arxivorg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class StatisticTest {

    public Statistic statistic = new Statistic();
    public Archive archive = ArchiveTest.archive;


    @Test
    void countArticlesByCategoryTest() {
        Map<String, Integer> nbArticlesByCategory = statistic.countArticlesByCategory(archive);
        assertEquals(85,nbArticlesByCategory.size());
        assert(nbArticlesByCategory.containsKey("cs.DM"));
        assertEquals(6,nbArticlesByCategory.get("math.AG"));
        assertEquals(6,nbArticlesByCategory.get("math.RA"));
        // Ce test passe au moment où j'implemente cette méthode mais les chiffres change en fonction des articles de l'API

    }


    @Test
    void mostProductiveAuthorsTest() {
        Map<String, Integer> nbArticlesByAuthors = statistic.mostProductiveAuthors(archive);
        assertEquals(175,nbArticlesByAuthors.size());
        // Ce test passe au moment où j'implemente cette méthode mais les chiffres change en fonction des articles de l'API
        assert(nbArticlesByAuthors.containsKey("Matthew Kwan"));
        assert(!nbArticlesByAuthors.containsKey("Jean"));
        assertEquals(2,nbArticlesByAuthors.get("James McKee"));
    }


    @Test
    void nbPublicationPerDayTest() {
        TreeMap<String, Integer> nbPublicationPerDay = statistic.nbPublicationPerDay(archive);
        assertEquals(99,nbPublicationPerDay.size());
        assert(nbPublicationPerDay.containsKey("2008-11-20"));
        assert(!nbPublicationPerDay.containsKey("2023-04-10"));
        assertEquals(1,nbPublicationPerDay.get("2008-11-20"));
    }

    @Test
    void nbExpressionsAppear() {
        /*
        List<Integer> expression1 = statistic.nbExpressionsAppear(archive,"communications are well known");
        assertEquals(0,expression1.get(0));
        assertEquals(0,expression1.get(1));
        assertEquals(2,expression1.size());
        List<Integer> expression2 = statistic.nbExpressionsAppear(archive,"Including All the Lines");
        assertEquals(0,expression2.get(0));
        assertEquals(1,expression2.get(1));
        assertEquals(2,expression1.size());
         */
    }

    @Test
    void sortedByValue_descendingOrder() {
        Map<String,Integer> map = new HashMap<>();
        map.put("a",456);
        map.put("z",6);
        map.put("c", 39);
        int i = 0;
        for(Map.Entry data: map.entrySet()){
            if(i==0)
                assertEquals(456, data.getValue());
            if(i==1)
                assertEquals(39,data.getValue());
            if(i==2)
                    assertEquals(6,data.getValue());
            i++;
        }
    }
}