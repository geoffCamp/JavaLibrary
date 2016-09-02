Objective: To create a graphical user interface for  an existing library content management system.

Limitations: This GUI solution is only capable of handling Book and Journal references.

Use: Users can build the program by compiling the Java files with the “javac *.java” terminal command and can then run the compiled files by moving to the package directory and running the command “java library search.LibrarySearch <output> <input>”. Users can test the program by adding a book or journal entry in the “Add” tab or by specifying search conditions in the “Search” tab.

Testing: The program was tested for varying and unexpected types of input as well as other exceptions (See testing document for more details).  

Improvements: Possible improvements include adding the option to include additional reference types and implementing an autoloading search feature. 

Notes:
 - program does not check for duplicates until search query is submitted
 - run program: Specify at least one string argument as output path, two to specify input file path as well 
 - input and output files must have extension shown in finder
 - separate authors by comma