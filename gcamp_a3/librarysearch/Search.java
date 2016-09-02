/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Contains all static methods required to search the library. Not used to create objects.
 * @author Geofferson
 */
public class Search {
    
    private static Scanner scan = new Scanner(System.in);
    private static ArrayList<Reference> foundRefs = new ArrayList<Reference>();
    private static String year = "set";
    private static int rangeStart = 1000;
    private static int rangeEnd = 9999;
    private static String termsArray[] = {};
    private static String callNumber = "";
    
    private static final int BOOK = 1;
    private static final int JOURNAL = 2;
    
    /**
     * Begins the process of searching the library, private child methods are called from the search method.
     * @param refs
     * @param map
     * @param refDetails
     */
    public static void search(ArrayList<Reference> refs, HashMap<String,int[]> map, String [] refDetails) {
        
        beginSearch(refDetails);
        searchRefs(refs,map);
        printRefs();
        clearFounds();
        
    }
    
    /**
     * Get search terms from user
     */
    private static void beginSearch(String[] deets) {
        String terms = "";
        
        callNumber = deets[0];

        terms = deets[1];
        
        setRange(deets[2],deets[3]);
        
        termsArray = terms.split(" ");
        if (termsArray.length == 1 && termsArray[0].equals("")) {
            termsArray[0] = "thisisarandomstring"; 
        }

    }
    
    /**
     * Parse year input
     */
    private static void setRange(String s, String e) {     
        if (s.equals(e)) { //(!year.contains("-") && year.matches("\\d{4}"))
            rangeStart = Integer.parseInt(s);
            rangeEnd = Integer.parseInt(e);
        } else if (!s.equals("") && e.equals("")) { //year.endsWith("-")            
            rangeStart = Integer.parseInt(s);
            rangeEnd = 9999;
        } else if (s.equals("") && !e.equals("")) {//year.startsWith("-")            
            rangeStart = 1000;
            rangeEnd = Integer.parseInt(e);
        }  else if (!s.equals("") && !e.equals("")) { //year.contains("-")
            rangeStart = Integer.parseInt(s);
            rangeEnd = Integer.parseInt(e);
        } else {
            year = "";
            rangeStart = 1000;
            rangeEnd = 9999;
        }
    }
    
    /**
     * Filter references by keywords and add to list being printed
     * @param refs
     * @param map 
     */
 
    private static void searchRefs (ArrayList<Reference> refs, HashMap<String,int[]> map) {
        
        int bookYear = 1000;
        int add = 1;
        Reference ref = null;
        
        for (String term : termsArray) {
            term = term.toLowerCase();
            if (term.equals("thisisarandomstring")) {
                continue;
            }
            if (map.containsKey(term)) {
                for (int listIndex : map.get(term)) {
                    ref = refs.get(listIndex);
                    if (!foundRefs.contains(ref)) {
                        foundRefs.add(ref);
                    }
                } 
            }
        }
        
        ArrayList<Reference> toRemove = new ArrayList<Reference>();
        for (Reference foundRef : foundRefs) {
            String[] titles;
            
            titles = foundRef.getTitle().toLowerCase().split(" ");
            
            for (String specifiedTerm : termsArray) {
                if (!Arrays.asList(titles).contains(specifiedTerm.toLowerCase()) || !checkYearNCall(foundRef,foundRef.getYear())) {
                    toRemove.add(foundRef);
                }
            } 
        }
        for (Reference refRemove : toRemove) {
            foundRefs.remove(refRemove);
        }
        
        if (termsArray[0].equals("thisisarandomstring")) {
            for (Reference refTermE : refs) {
                
                bookYear = refTermE.getYear();
                if (checkYearNCall(refTermE,bookYear) ) {
                    foundRefs.add(refTermE);
                }
            } 
        }
        
    }
    
    /**
     * Check that specified search terms matches with year and call number.
     * @param refTermE
     * @param bookYear
     * @return 
     */
    private static boolean checkYearNCall(Reference refTermE, int bookYear) {
        
        if (!year.equals("") && (bookYear < rangeStart || bookYear > rangeEnd)) {
            return false;
        }

        if (!callNumber.equals(refTermE.getNumber()) && !callNumber.equals("")) {
            return false;
        } 
        
        return true;
    }
 
    /**
     * Print Methods
     */
    private static void printRefs() {
        
        for (Reference ref : foundRefs) {
            LibrarySearch.addTosMsgArea(ref.toString());
            LibrarySearch.addTosMsgArea("\n");
        }
        
        LibrarySearch.addTosMsgArea("**** End of Search Results *****\n\n");
    }
    
    /**
     * Empty found lists after each search
     */
    private static void clearFounds() {
        foundRefs.clear();
    }
}
