/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysearch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Initiates program operations.
 * @author Geofferson
 */
public class LibrarySearch {

    static JFrame mainFrame = new JFrame("Library Search");
    static JPanel welcomePanel = new JPanel();
    static JPanel addPanel = new JPanel();
    static JPanel searchPanel = new JPanel();
    
    final static JTextArea msgArea = new JTextArea();
    final static JTextArea sMsgArea = new JTextArea();
    
    static ArrayList<Reference> references = new ArrayList<Reference>();
    static HashMap<String,int[]> map = new HashMap<String,int[]>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String inputPath = "noinputspecified";
        String outputPath = "output.txt";
        
        if (args.length < 1) {
            System.out.println("output path must be specified.");
            System.exit(0);
        } else if (args.length >= 2) {
            inputPath = args[0];
            outputPath = args[1];
        } else if (args.length == 1) {
            outputPath = args[0];
        }
        Add.setOutputPath(outputPath);

        int refIndex = 0;
        
        if (!inputPath.equals("noinputspecified")) {
            loadFromFile(inputPath,references);
            for (Reference ref : references) {
                Add.addToHash(ref,refIndex,map);
                refIndex++;
            }
        }

        setUpMain();
        
    }
    
    /*
    Set up main frame
    */
    
    private static void setUpMain () {
        
        mainFrame.setSize(800,500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenu commands = new JMenu("Commands");
        JMenuItem add = new JMenuItem("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePanels();
                addPanel.setVisible(true);   
            }
        });
        
        JMenuItem search = new JMenuItem("Search");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePanels();         
                searchPanel.setVisible(true);
            }
        });
        
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(1);
            }
        });
        
        commands.add(add);
        commands.add(search);
        commands.add(quit);
        JMenuBar bar = new JMenuBar();
        bar.add(commands);
        mainFrame.setJMenuBar(bar);
            
        setUpWelcome();
        setUpAdd();
        setUpSearch();
        mainFrame.add(welcomePanel);
        mainFrame.add(addPanel);
        mainFrame.add(searchPanel);
        
        mainFrame.setVisible(true);
    }
    
    /*
    Set up welcome panel
    */
    
    private static void setUpWelcome () {
        
        welcomePanel.setSize(800,500);

        JLabel welcomeText = new JLabel("<html>Welcome to Library Search.<br><br>"
                + "Choose a command from the \"Commands\" menu above for<br> adding a reference,"
                + " searching references, or quitting the<br> program.<html>");

        welcomeText.setHorizontalAlignment(JLabel.CENTER);
        welcomeText.setVerticalAlignment(JLabel.CENTER);
        welcomePanel.add(welcomeText);
        
        welcomePanel.setVisible(true);
    }
    
    /*
    Set up add panel
    */
    
    private static void setUpAdd() {
        addPanel.setSize(800,450);
        addPanel.setLayout(new BorderLayout());
        
        JLabel addLabel = new JLabel("Adding a reference");
        addPanel.add(addLabel, BorderLayout.NORTH);
        
        JPanel addFields = new JPanel();
        addFields.setLayout(new GridLayout(7,1,5,5));
        addFields.setVisible(true);
        addPanel.add(addFields, BorderLayout.CENTER);
        
        JPanel addBtns = new JPanel(new GridLayout(2,1,10,40));
        addBtns.setVisible(true);
        addPanel.add(addBtns, BorderLayout.EAST);
        
        JPanel addMsgs = new JPanel(new FlowLayout(0,10,10));
        addMsgs.setVisible(true);
        
        addPanel.add(addMsgs, BorderLayout.SOUTH);
        
        /*
        Adding to fields panel 
        */
        
        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("Type:");
        final JComboBox refTypeBox = new JComboBox();
        final String[] refTypes = {"Book","Journal"};
        refTypeBox.setModel(new javax.swing.DefaultComboBoxModel(refTypes));
        typePanel.add(typeLabel);
        typePanel.add(refTypeBox);
        addFields.add(typePanel);
        
        JPanel callPanel = new JPanel();
        JLabel callLabel = new JLabel("Call No:");
        final JTextField callField = new JTextField();
        callField.setColumns(18);
        callPanel.add(callLabel);
        callPanel.add(callField);
        addFields.add(callPanel);
        
        final JPanel authPanel = new JPanel();
        JLabel authorLabel = new JLabel("Authors:");
        final JTextField authorField = new JTextField();
        authorField.setColumns(18);
        authPanel.add(authorLabel);
        authPanel.add(authorField);
        addFields.add(authPanel);
        
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Title:");
        final JTextField titleField = new JTextField();
        titleField.setColumns(20);
        titlePanel.add(titleLabel);
        titlePanel.add(titleField);
        addFields.add(titlePanel);
        
        final JPanel pubPanel = new JPanel();
        JLabel pubLabel = new JLabel("Publisher:");
        final JTextField pubField = new JTextField();
        pubField.setColumns(17);
        pubPanel.add(pubLabel);
        pubPanel.add(pubField);
        addFields.add(pubPanel);
        
        final JPanel orgPanel = new JPanel();
        JLabel orgLabel = new JLabel("Organization:");
        final JTextField orgField = new JTextField();
        orgField.setColumns(16);
        orgPanel.add(orgLabel);
        orgPanel.add(orgField);
        orgPanel.setVisible(false);
        addFields.add(orgPanel);
        
        JPanel yearPanel = new JPanel();
        JLabel yearLabel = new JLabel("Year:");
        final JTextField yearField = new JTextField();
        yearField.setColumns(20);
        yearPanel.add(yearLabel);
        yearPanel.add(yearField);
        addFields.add(yearPanel);
        
        /*
        Setting message area
        */
        JLabel msgLabel = new JLabel("Messages"); 
        
        JScrollPane scroll = new JScrollPane (msgArea, 
                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        msgArea.setLineWrap(true);
        msgArea.setColumns(50);
        msgArea.setRows(6);
        addMsgs.add(msgLabel);
        addMsgs.add(scroll);
        
        /*
        Setting buttons
        */
        JButton addBtn = new JButton();
        addBtn.setText("Add");
        addBtns.add(addBtn);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String type = refTypeBox.getSelectedItem().toString();
                String call = callField.getText();
                String auth = authorField.getText();
                String title = titleField.getText();
                String pub = pubField.getText();
                String org = orgField.getText();
                String year = yearField.getText();
                try {
                    Integer.parseInt(year);
                } catch (Exception e) {
                    addToAddArea("Please enter a valid year.");  
                    return;
                }
                if (Integer.parseInt(year) > 9999 || Integer.parseInt(year) < 1000) {
                    addToAddArea("Please enter a valid year.");
                    return;
                }
                if (title.equals("") || call.equals("")) {
                    addToAddArea("Title and call number must be specified.");
                    return;
                } else {
                    String toAdd[] = {type,call,auth,title,pub,year,org};
                    Add.addRef(references,map,toAdd);
                    
                    addToAddArea("Reference added.");
                    callField.setText("");
                    authorField.setText("");
                    titleField.setText("");
                    pubField.setText("");
                    yearField.setText("");
                    orgField.setText("");
                }
            }
        });
        JButton resetBtn = new JButton();
        resetBtn.setText("Reset");
        addBtns.add(resetBtn);
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callField.setText("");
                authorField.setText("");
                titleField.setText("");
                pubField.setText("");
                yearField.setText("");
                orgField.setText("");
            }
        });
        
        refTypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (refTypeBox.getSelectedItem().toString().equals("Book")) {
                    authPanel.setVisible(true);
                    pubPanel.setVisible(true);
                    orgPanel.setVisible(false);
                } else {
                    authPanel.setVisible(false);
                    pubPanel.setVisible(false);
                    orgPanel.setVisible(true);
                }
            }
        });
 
        addPanel.setVisible(false);
    }
    
    /*
    Set up search panel
    */
    
    private static void setUpSearch() {
        searchPanel.setSize(800,450);
        searchPanel.setLayout(new BorderLayout());
        
        JLabel searchLabel = new JLabel("Searching references");
        searchPanel.add(searchLabel, BorderLayout.NORTH);
        
        JPanel searchFields = new JPanel();
        searchFields.setLayout(new GridLayout(4,1,5,5));
        searchFields.setVisible(true);
        searchPanel.add(searchFields, BorderLayout.CENTER);
        
        JPanel searchBtns = new JPanel(new GridLayout(2,1,10,40));
        searchBtns.setVisible(true);
        searchPanel.add(searchBtns, BorderLayout.EAST);
        
        JPanel searchMsgs = new JPanel(new FlowLayout(0,10,10));
        searchMsgs.setVisible(true);
        
        searchPanel.add(searchMsgs, BorderLayout.SOUTH);
        
        /*
        Adding to fields panel 
        */
    
        JPanel callPanel = new JPanel();
        JLabel callLabel = new JLabel("Call No:");
        final JTextField callField = new JTextField();
        callField.setColumns(15);
        callPanel.add(callLabel);
        callPanel.add(callField);
        searchFields.add(callPanel);
        
        final JPanel wordsPanel = new JPanel();
        JLabel wordsLabel = new JLabel("Title Keywords:");
        final JTextField wordsField = new JTextField();
        wordsField.setColumns(13);
        wordsPanel.add(wordsLabel);
        wordsPanel.add(wordsField);
        searchFields.add(wordsPanel);
        
        JPanel syearPanel = new JPanel();
        JLabel syearLabel = new JLabel("Start Year:");
        final JTextField syearField = new JTextField();
        syearField.setColumns(15);
        syearPanel.add(syearLabel);
        syearPanel.add(syearField);
        searchFields.add(syearPanel);
        
        JPanel eyearPanel = new JPanel();
        JLabel eyearLabel = new JLabel("End Year:");
        final JTextField eyearField = new JTextField();
        eyearField.setColumns(15);
        eyearPanel.add(eyearLabel);
        eyearPanel.add(eyearField);
        searchFields.add(eyearPanel);
        
        /*
        Setting message area
        */
        JLabel msgLabel = new JLabel("Search Results"); 
        JScrollPane scroll = new JScrollPane (sMsgArea, 
                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        sMsgArea.setLineWrap(true);
        sMsgArea.setColumns(55);
        sMsgArea.setRows(6);
        searchMsgs.add(msgLabel);
        searchMsgs.add(scroll);
        
        /*
        Setting buttons
        */
        
        JButton resetBtn = new JButton();
        resetBtn.setText("Reset");
        searchBtns.add(resetBtn);
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callField.setText("");
                wordsField.setText("");
                syearField.setText("");
                eyearField.setText("");
            }
        });
        JButton searchBtn = new JButton();
        searchBtn.setText("Search");
        searchBtns.add(searchBtn);
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int e = 0;
                int s = 0;
                String call = callField.getText();
                String words = wordsField.getText();
                String syear = syearField.getText();
                String eyear = eyearField.getText();
                if (!eyear.equals("")) {
                    try {
                        e = Integer.parseInt(eyear);
                    } catch (Exception ex) {
                        addTosMsgArea("Please enter valid year values.");
                        return;
                    }
                    if (e > 9999) {
                        addTosMsgArea("Please enter a year less than 9999.");
                        return;
                    }
                } else {
                    eyear = "9999";
                }
                if (!syear.equals("")) {
                    try {
                        s = Integer.parseInt(syear);
                    } catch (Exception ex) {
                         addTosMsgArea("Please enter valid year values.");
                         return;
                    }
                    if (s < 1000) {
                        addTosMsgArea("Please enter a year greater than 1000.");
                        return;
                    }
                } else {
                    syear = "1000";
                }
                if (!syear.equals("") && !eyear.equals("")) {
                    e = Integer.parseInt(eyear);
                    s = Integer.parseInt(syear);
                    if (s>e) {
                        addTosMsgArea("Start year must occur before end year.");
                    }
                }
                String toSearch[] = {call,words,syear,eyear};
                Search.search(references,map,toSearch);  
            }
        });
 
        searchPanel.setVisible(false);
    }
    
    static void closePanels() {
        welcomePanel.setVisible(false);
        addPanel.setVisible(false);
        searchPanel.setVisible(false);
    }
    
    /**
     * Load existing references from input file
     * @param inputPath
     * @param refs 
     */
    private static void loadFromFile(String inputPath,ArrayList<Reference> refs) {
        Scanner scanFile;
        String nextRef;
        String[] partsOfString;
        String[] partsToAdd = new String[7];
        try {
            scanFile = new Scanner(new FileInputStream(inputPath));
        } catch (IOException e) {
            System.out.println("Invalid file input path.");
            System.exit(0);
            return;
        }
        
        while (scanFile.hasNextLine()) {
            nextRef = scanFile.nextLine();
            if (nextRef.equals("\n") || nextRef.equals("")) {
                continue;
            }
            partsOfString = nextRef.split(" = ");
            
            switch (partsOfString[0]) {
                case "type":
                    partsToAdd = new String[6];
                    if (partsOfString[1].equals("\"book\"")) {
                        partsToAdd[0] = "book";
                    } else if (partsOfString[1].equals("\"journal\"")) {                 
                        partsToAdd[0] = "journal";
                    }
                    break;
                case "callnumber":
                    partsToAdd[1] = trimQuotes(partsOfString[1]);
                    break;
                case "title":
                    partsToAdd[2] = trimQuotes(partsOfString[1]);
                    break;
                case "authors":
                    partsToAdd[3] = trimQuotes(trimQuotes(partsOfString[1]));
                    break;
                case "publisher":
                    partsToAdd[4] = trimQuotes(partsOfString[1]);
                    break;
                case "org":
                    partsToAdd[4] = trimQuotes(partsOfString[1]);
                    break;
                case "year":
                    partsToAdd[5] = trimQuotes(partsOfString[1]);
                    if (partsToAdd[0].equals("book")) {
                        try {
                            refs.add(new Book(partsToAdd[1],partsToAdd[2],Integer.parseInt(partsToAdd[5]),partsToAdd[4],partsToAdd[3].split(", ")));
                        } catch (Exception e) {
                            addToAddArea(e.toString());
                        }
                    } else if (partsToAdd[0].equals("journal")) {
                        
                        try {
                            refs.add(new Journal(partsToAdd[1],partsToAdd[2],Integer.parseInt(partsToAdd[5]),partsToAdd[4]));
                        } catch (Exception e) {
                            addToAddArea(e.toString());
                        }
                    }
                    Arrays.fill(partsToAdd, null);
                    
                    break;
            }
        }
          
    }
    
    /**
     * Remove quotes from input file string
     * @param toTrim
     * @return 
     */
    private static String trimQuotes(String toTrim) {
        return toTrim.substring(1, toTrim.length() - 1);
    }
    
    public static void addTosMsgArea(String toAppend) {
        LibrarySearch.sMsgArea.setText(LibrarySearch.sMsgArea.getText() + toAppend);
    } 
    
    public static void addToAddArea(String toAppend) {
        LibrarySearch.msgArea.setText(LibrarySearch.msgArea.getText() + toAppend +"\n");
    } 
    
}
