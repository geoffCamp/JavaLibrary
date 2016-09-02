/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearch;

/**
 * Base class for library references
 * @author Geofferson
 */
public abstract class Reference {
    public String title;
    public String callNumber;
    public int year;
    
    /**
     * Returns the title of the book
     *  
     */
    public String getTitle () {
        return title;
    }
    
    /**
     * Returns the call number of the book
     *  
     */
    public String getNumber () {
        return callNumber;
    }
    
    /**
     * Returns the year of the book
     *  
     */
    public int getYear () {
        return year;
    }
    
    //public abstract void printRef ();
}
