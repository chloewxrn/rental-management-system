import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Ng Yoong Kee
// PropertyAgent class is a subclass of Advertiser class
public class PropertyAgent extends Advertiser{
    protected String agentLicenseCode;
    protected String agencyName;
    protected String agencyRegNo;

    // Ng Yoong Kee
    // Default Constructor
    public PropertyAgent() {}

    // Ng Yoong Kee
    // initialize new Property Agent
    public PropertyAgent(String id, String username, String password, String name, String icNum, String gender, String email, String contactNumber, String agentLicenseCode, String agencyName, String agencyRegNo) {
        super(id, username, password, name, icNum, gender, email, contactNumber);
        this.agentLicenseCode = agentLicenseCode;
        this.agencyName = agencyName;
        this.agencyRegNo = agencyRegNo;
    }

    // Ng Yoong Kee
    // make the variables become a string
    public String toAgentCSVString() {
        return ID + "," + username + "," + password + "," + name + "," + icNum + "," + gender + "," + email + "," + contactNumber + "," + agentLicenseCode + "," + agencyName + "," + agencyRegNo;
    }

    // Ng Yoong Kee
    // save the new Agent into csv file
    public void saveAgentCSV(String toAgentCSVString) {
        try {
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter("AdvertiserReg.csv", true));

            bufferWriter.write(toAgentCSVString + "\n");

            bufferWriter.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
