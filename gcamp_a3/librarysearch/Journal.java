/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearch;

/**
 * For creating journal objects.
 * @author Geofferson
 */
public class Journal extends Reference {  
    private String org;
    
    /**
     * 
     * @param callNumber
     * @param title
     * @param year
     * @param org 
     */
    public Journal (String callNumber, String title, int year, String org) {
        this.title = title;
        this.org = org;
        this.callNumber = callNumber;
        this.year = year;
        if (title.equals("")) {
            throw new IllegalArgumentException("Title must be specified.");
        } else if (callNumber.equals("")) {
            throw new IllegalArgumentException("Call Number must be specified.");
        }
    }
    
    /**
     * Returns the organization of the journal
     *  
     */
    public String getOrg () {
        return org;
    }
    
    @Override
    public String toString () {
       
        return title+"\n"    
               +year+"\n"
               +callNumber+"\n"
               +org+"\n";
    }
    
    @Override
    public boolean equals (Object object) {
        if (object == null) {
            return false;
        } else if (getClass() != object.getClass()) {
            return false;
        } else {
            Journal otherJournal = (Journal)object;
            return (title.equals(otherJournal.getTitle()) && org.equals(otherJournal.getOrg()) 
                    && callNumber.equals(otherJournal.getNumber()) && year == otherJournal.getYear());
        }
    }
    
}
