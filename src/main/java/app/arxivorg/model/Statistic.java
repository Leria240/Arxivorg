package app.arxivorg.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistic {



    private Map<String,Integer> countArticlesByCategory(Archive archive){

        Map<String,Integer> numbersOfArticlesByCategory = new HashMap<>();
        for(String category:archive.getPossibleCategories()){
            numbersOfArticlesByCategory.put(category,0);
        }
        for(Article article:archive.getSelectedArticles()){
            for (String category: article.getCategory()){
                int count = numbersOfArticlesByCategory.get(category);
                numbersOfArticlesByCategory.put(category,count+1);
            }

        }
        return numbersOfArticlesByCategory;
    }


}
