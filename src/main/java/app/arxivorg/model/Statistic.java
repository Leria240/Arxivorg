package app.arxivorg.model;

import java.util.*;

public class Statistic {


    public Map<String, Integer> countArticlesByCategory(Archive archive){
        Map<String, Integer> numberOfArticlesByCategory = new HashMap<>();
        for(Article article : archive.getAllArticles()){
            for(String category : article.getCategory()){
                int count = numberOfArticlesByCategory.getOrDefault(category,0);
                numberOfArticlesByCategory.put(category,count + 1);
            }
        }
        return numberOfArticlesByCategory;
    }



    public Map<String, Integer> mostProductiveAuthors(Archive archive){
        HashMap<String, Integer> numberOfArticlesByAuthors = new HashMap<>();
        for(Article article : archive.getAllArticles()){
            for(String author : article.getAuthors().getData()){
                int count = numberOfArticlesByAuthors.getOrDefault(author, 0);
                numberOfArticlesByAuthors.put(author, count + 1);
            }
        }
        return sortedByValue_descendingOrder(numberOfArticlesByAuthors);
    }



    public TreeMap<String,Integer> nbPublicationPerDay(Archive archive){
        TreeMap<String, Integer> nbPublicationPerDay = new TreeMap<>();
        for(Article article : archive.getAllArticles()) {
            String dateOfPublication = article.getPublished().substring(0, 10);
            int count = nbPublicationPerDay.getOrDefault(dateOfPublication, 0);
            nbPublicationPerDay.put(dateOfPublication, count + 1);
        }
        return nbPublicationPerDay;
    }


    public List<Integer> nbExpressionsAppear(Archive archive, String expression){
        List<Integer> nbExpressionsAppear = Arrays.asList(0,0);
        for(Article article : archive.getAllArticles()){
            if (article.getTitle().toLowerCase().contains(expression.toLowerCase().trim()))
                nbExpressionsAppear.set(0,nbExpressionsAppear.get(0) + 1);
            if (article.getTitle().toLowerCase().contains(expression.toLowerCase().trim()))
                nbExpressionsAppear.set(1,nbExpressionsAppear.get(1) + 1);
        }
        return nbExpressionsAppear;
    }



    public static HashMap<String, Integer> sortedByValue_descendingOrder(HashMap<String, Integer> map){
        List<Map.Entry<String, Integer>> list = new LinkedList<>( map.entrySet() );
        list.sort(Map.Entry.comparingByValue((integer, t1) -> t1-integer));
        HashMap<String, Integer> sorted_map = new LinkedHashMap<>();
        for(Map.Entry<String, Integer> entry : list)
            sorted_map.put( entry.getKey(), entry.getValue() );
        return sorted_map;
    }








}




