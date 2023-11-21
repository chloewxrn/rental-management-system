import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// Wong Yi Teng
// For register purpose 
public class Registration {

    protected String userType;

    // Constructor
    public Registration() {}

    // Constructor
    public Registration(String userType) {
        this.userType = userType;
    }

    //Wong Yi Teng 
    //To check the duplicate username, id and email for the potential tenant during registration
    public int checkDuplicatesTenant(String ID,String username, String email, String usertype) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(usertype + ".csv"));

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            String userNameCSV = items[1];
            String emailCSV = items[4];
            String IDCSV = items[0];

            if (username.equals(userNameCSV))
                return 2;

            if (email.equals(emailCSV))
                return 1;

            if (ID.equals(IDCSV))
                return 3 ;
        }

        return 0;
    }

    //Wong Yi Teng & Ong Jia Xuan
    //To check the duplicate username and ic for the property owner during registration
    public int checkDuplicatesOwner(String username, String ic, String usertype) throws IOException {
      
        List<String> lines = Files.readAllLines(Paths.get(usertype + ".csv"));

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            String userNameCSV = items[1];
            String icCSV = items[4];

            if(username.equals(userNameCSV) || ic.equals(icCSV) )
                return 1;

        }

        return 0;
    }

    // Ng Yoong Kee
    // To check whether the Agent's Username and IC have duplicates or not during registration
    public boolean checkDuplicatesAgent(String username, String ic, String usertype) throws IOException {
      
        List<String> lines = Files.readAllLines(Paths.get(usertype + ".csv"));

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            String userNameCSV = items[0];
            String icCSV = items[4];

            if (username.equals(userNameCSV) || ic.equals(icCSV) )
                return false;

        }

        return true;
    }

   
}


