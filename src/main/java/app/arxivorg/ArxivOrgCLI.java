package app.arxivorg;

import app.arxivorg.model.Archive;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArxivOrgCLI {
    public static void main(String[] args) {

        Archive archive = new Archive();
        File file = new File("atomFile1.xml");
        archive.addArticles(file);

        System.out.println("Welcome to the arXiv organizer!");

        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");

        System.out.println("Input your command: ");
        Scanner scanner = new Scanner(System.in);

        List<String> category = new ArrayList<>();
        category.add("cs.CL");

         if(args[1].equals(archive.categoryFilter(category))){
             System.out.println(archive.categoryFilter(category));
         }

        System.out.println("Sorry, I can't do anything yet ! (Read: " + scanner.nextLine() + ")");
        scanner.close();

    }
}
