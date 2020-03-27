package app.arxivorg.model;


import javax.swing.*;
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

    public String toString() {
        return String.join(", ", data);
    }

    public boolean contains(String firstnameOrLastname){
        for(String author: data){
            if(author.toLowerCase().contains(firstnameOrLastname.toLowerCase()))
                return true;
        }
        return false;
    }
}
