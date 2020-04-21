package app.arxivorg.model;

import java.util.*;

public class Statistic {


    public Map<String, Integer> countArticlesByCategory(Archive archive){
        Map<String, Integer> numberOfArticlesByCategory = new HashMap<>();
        for(String category : archive.getPossibleCategories()){
            numberOfArticlesByCategory.put(category,0);
        }
        for(Article article : archive.getSelectedArticles()){
            for(String category : article.getCategory()){
                int count = numberOfArticlesByCategory.get(category);
                numberOfArticlesByCategory.put(category,count + 1);
            }
        }
        return numberOfArticlesByCategory;
    }

    public Map<String, Integer> mostProductiveAuthors(Archive archive){
        HashMap<String, Integer> numberOfArticlesByAuthors = new HashMap<>();
        for(Article article : archive.getSelectedArticles()){
            for(String author : article.getAuthors().getData()){
                int count = numberOfArticlesByAuthors.getOrDefault(author, 0);
                numberOfArticlesByAuthors.put(author, count + 1);
            }
        }
        numberOfArticlesByAuthors = sortedByValue(numberOfArticlesByAuthors);
        return numberOfArticlesByAuthors;
    }


    public static HashMap<String, Integer> sortedByValue(HashMap<String, Integer> map){
        List<Map.Entry<String, Integer>> list = new LinkedList<>( map.entrySet() );
        list.sort(Map.Entry.comparingByValue());
        HashMap<String, Integer> sorted_map = new LinkedHashMap<>();
        for(Map.Entry<String, Integer> entry : list)
            sorted_map.put( entry.getKey(), entry.getValue() );
        return sorted_map;
    }




}
