Instructions on how to compile and execute .java source files. Note*** Make sure you have JDK installed, you will need it for javac command.

1.) Open your respective Operating Systems terminal via the Windows 'Start key' or its equivalent on your respective Operating  System.
e.g., Linux users might use a 'Super key'.


2.) Modify (if incorrect directory/pathway) to the appropriate(address) location where you downloaded the HMS_Stage4_Final each respective, and then append the last folder within HMS_Stage_Final to access java source files. 
This folder is labeled 'src' and an exmple of this is below:
C:\UsersExample\danielExample\Desktop\HMS_Stage4_Final\src <- this is an example of navigating to the folder within the HMS_Stage4_Final to compile source files in 'src'
  

3.) Once you have the correct pathway leading to the .java source files within the terminal you can use the javac *java command to compile the source files alltogether.
e.g., javac *java



4.) After you have compiled the source files using the command javac you can then create the jar file with the terminal command: 
e.g., jar cvfm App.jar Manifest.txt *.class data.csv       
An Example of changing the programs app name to HotelManagement below instead of default:
e.g., jar cvfm HotelManagement.jar Manifest.txt *.class data.csv  

5.) Now an executable Jar file will be located in the 'src' folder you can create a shortcut to your desktop for ease of use if you want at this point.

Enjoy!

