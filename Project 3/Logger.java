import java.io.IOException;
import java.io.FileWriter;

public class Logger {
    //create a new file for each new simDay
    int day;
    //See for details on try-catch and file creation: https://www.w3schools.com/java/java_files_create.asp
    public Logger(int simDay) {
        day = simDay;
    }

    public void writeLog(String input) {
        try {
            FileWriter myWriter = new FileWriter("Logger-" + day + ".txt");
            myWriter.write(input);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred in creating a new daily logger file.");
            e.printStackTrace();
        }
    }
}//end of logger class


