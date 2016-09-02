/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearch;

/**
 * For creating book objects.
 * @author Geofferson
 */
public class Book extends Reference {
    private String publisher;
    private String[] authors;
    
    
    /**
     * 
     * @param callNumber
     * @param title
     * @param year
     * @param publisher
     * @param authors 
     */
    public Book (String callNumber, String title, int year, String publisher, String[] authors) throws Exception{
        this.title = title;
        this.publisher = publisher;
        this.callNumber = callNumber;
        this.authors = authors;
        this.year = year;
        if (title.equals("")) {
            throw new IllegalArgumentException("Title must be specified.");
        } else if (callNumber.equals("")) {
            throw new IllegalArgumentException("Call Number must be specified.");
        }
    }
     
    /**
     * Returns the publisher of the book
     *  
     */
    public String getPub () {
        return publisher;
    }
     
    /**
     * Returns the authors of the book in an array
     *  
     */
    public String[] getAuthors () {
        return authors;
    }
   
    @Override
    public String toString () {
        String tempAuthors = "";
        int decider = 0;
        
        for (String auth : getAuthors()) {
            /*if (decider == 1) {*/
                tempAuthors = tempAuthors+auth+", ";
                decider = 0;
            /*} else {
                tempAuthors = tempAuthors+auth+"  ";
                 decider = 1;
            }*/
        }
        tempAuthors = tempAuthors.substring(0, tempAuthors.length() - 2);
        if (!tempAuthors.equals("")) {
            tempAuthors = tempAuthors + "\n";
        }
        
        return title+"\n"
               +year+"\n"
               +callNumber+"\n"
               +publisher+"\n"
               +tempAuthors;
               
    }
    
    @Override
    public boolean equals (Object object) {
        if (object == null) {
            return false;
        } else if (getClass() != object.getClass()) {
            return false;
        } else {
            Book otherBook = (Book)object;
            return (title.equals(otherBook.getTitle()) && publisher.equals(otherBook.getPub()) 
                    && callNumber.equals(otherBook.getNumber()) && year == otherBook.getYear());
        }
    }
    
}
