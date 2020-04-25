package app.arxivorg.model;


import javafx.scene.control.Hyperlink;

import java.util.ArrayList;
import java.util.List;

public class Authors {
    private ArrayList<String> data;

    public Authors(List<String> input) {
        this.data = new ArrayList<>(input);
    }

    public ArrayList<String> getData() {
        return data;
    }

    public ArrayList<Hyperlink> getDataLink(){
        ArrayList<Hyperlink> links = new ArrayList<>();
        for(String author: data){
            links.add(new Hyperlink(author));
        }
        return links;
    }



    public String toString() {
        return String.join(", ", data);
    }



    public boolean contains(String input){
        for(String author: data) {
            if(author.toLowerCase().equals(input.toLowerCase()))
                return true;
        }
        return false;
    }


}
