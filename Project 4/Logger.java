import java.io.FileWriter;
import java.io.IOException;


// SINGLETON PATTERN using EAGER INSTANTIATION
public class Logger {
    // field for storing the instance is declared static and initialized to null for lazy instantiation
    static int day;
    private static Logger instance = new Logger(day);

    // constructor is declared private to prevent multiple instances
    private Logger(int simDay) {
        day = simDay;
    }

    // controls the access to singleton instance
    public static Logger getInstance(int simDay) {
        if (day != simDay) {
            Logger.instance = new Logger(simDay); // change logger instance if different sim day than is currently being tracked.
        }
        return Logger.instance;
    }

    //See for details on try-catch and file creation: https://www.w3schools.com/java/java_files_create.asp
    public void writeLog(String input) {
        try {
            FileWriter myWriter = new FileWriter("Logger-" + day + ".txt", true);
            myWriter.write(input);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred in creating a new daily logger file.");
            e.printStackTrace();
        }
    }
}//end of logger class


