package app.arxivorg.model;


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



    public boolean contains(String input){
        String[] name = input.toLowerCase().trim().split(" ");
        for(String author: data){
            boolean defaultValue = true;
            String[] authorName = getNameInTab(author);
            if(name.length > authorName.length) continue;
            for(String inputName: name){
                if(!containsSamePartName(authorName,inputName)) {
                    defaultValue = false;
                    break;
                }
            }
            if (defaultValue) return true;
        }
        return false;
    }

    private boolean containsSamePartName(String[] authorName, String partNameInput){
        for(String author: authorName){
            if(partNameInput.equals(author))
                return true;
        }
        return false;
    }


    private String[] getNameInTab(String fullname) {
        String name = fullname.toLowerCase().trim();
        return name.split(" ");
    }


}
