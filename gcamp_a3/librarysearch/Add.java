/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Contains all static methods required to add to the library. Not used to create objects.
 * @author Geofferson
 */
public class Add {
    
    private static Scanner scan = new Scanner(System.in);
    
    private static final int BOOK = 1;
    private static final int JOURNAL = 2;
    private static String outputPath;
    
    /**
     * Add reference to array list
     * @param refs
     * @param map 
     * @param refDetails
     */
    public static void addRef(ArrayList<Reference> refs, HashMap<String,int[]> map, String[] refDetails) {
        int type;
        String title = refDetails[3];
        String callNumber = refDetails[1];
        int year = Integer.parseInt(refDetails[5]);
        
        if (refDetails[0].equals("Book")) {
            type = BOOK;
        } else {
            type = JOURNAL;
        }
        
        String publisher = "";
        String[] authors = new String[0];
        
        String org = ""; 
        
        if (checkRef(callNumber,year,refs) == false) {
            Reference newRef = null;
            if (type == BOOK) {
                publisher = refDetails[4];
                authors = getAuthors(refDetails[2]);
                try {
                    newRef = new Book(callNumber,title,year,publisher,authors);  
                } catch (Exception e) {
                    LibrarySearch.addToAddArea(e.toString());
                    return;
                }
                
            } else if (type == JOURNAL) {
                org = refDetails[6];
                try {
                    newRef = new Journal(callNumber,title,year,org); 
                } catch (Exception e) {
                    LibrarySearch.addToAddArea(e.toString());
                    return;
                }
            }
            
            refs.add(newRef);
            addToHash(newRef,refs.size()-1,map);
            addToFile(type,callNumber,title,year,publisher,authors,org);
            
        } else {
            LibrarySearch.addToAddArea("Reference duplicate detected. Add command rejected.");
        }

    }
    
    /**
     * Create hash map index
     * @param ref
     * @param listIndex
     * @param map 
     */
    public static void addToHash(Reference ref, int listIndex, HashMap<String,int[]> map) {
        String[] titles;
        int[] indexs;
        int[] intsWnew;
        
        titles = ref.getTitle().toLowerCase().split(" ");
        for (String title : titles) {
            if (map.containsKey(title)) {
                indexs = map.get(title);
                intsWnew = new int[indexs.length+1];
                System.arraycopy(indexs, 0, intsWnew, 0, indexs.length);//intsWnew = indexs;
                intsWnew[indexs.length] = listIndex; 
                map.put(title,intsWnew);
            } else {
                indexs = new int[1];
                indexs[0] = listIndex;
                map.put(title, indexs);
            }
        }
    }
    
    /**
     * Write reference to output file
     * @param type
     * @param callNumber
     * @param title
     * @param year
     * @param publisher
     * @param authors
     * @param org 
     */
    private static void addToFile(int type, String callNumber, String title, int year, String publisher, String[] authors, String org) {
        PrintWriter printFile;
        String typeString = "";
        
        if (type == 1) {
            typeString = "book";
        } else {
            typeString = "journal";
        }
        
        try {
            printFile = new PrintWriter(new BufferedWriter(new FileWriter(outputPath, true)));//
        } catch (IOException e) {
            System.out.println("Invalid output file");
            System.exit(0);
            return;
        }
        
        printFile.println("type = \""+typeString+"\"");
        printFile.println("callnumber = \""+callNumber+"\"");
        printFile.println("title = \""+title+"\"");
        if (typeString.equals("book")) {
            printFile.println("authors = \""+Arrays.toString(authors)+"\"");
            printFile.println("publisher = \""+publisher+"\"");
        }
        if (typeString.equals("journal")) {
            printFile.println("org = \""+org+"\"");
        }
        printFile.println("year = \""+year+"\"");
        printFile.println("");
        
        printFile.flush();
    }
    
    /**
     * Checks for existing book of the same call number and year
     */
    private static boolean checkRef (String callNum, int year, ArrayList<Reference> references) {
        for (Reference ref : references) {
            if (ref.getNumber().equals(callNum) && ref.getYear() == year) {
                return true;
            }
        }
        return false;
    }
    
    private static String[] getAuthors(String incAuth) {
        String[] authors;
        
        authors = incAuth.split(",");
        
        return authors;
    }
    
    public static void setOutputPath(String output) {
        outputPath = output;
    }
    
}
