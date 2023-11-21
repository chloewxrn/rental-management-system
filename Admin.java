import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

//Chin Pei Wern
// subclass of User class
public class Admin extends User {

    private ArrayList<Property> properties = new ArrayList<>();
    //constructor
    public Admin() {}
    
    //constructor
    public Admin(String ID, String username, String name, String password) {
        super(ID, username, password, name);
    }

    //Chin Pei Wern
    //to add new admin into the system
    public void addNewAdmin(String id, String username, String password, String name) throws IOException {
        StringBuilder sb = new StringBuilder();
        String file = "User.csv";
        Admin newAdmin = new Admin (id, username, name, password);
        sb.append(newAdmin.AdminCSVString());
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString() + "\n");
        bw.close();
        file = "AdminProperty.csv";
        fw = new FileWriter(file,true);
        bw = new BufferedWriter(fw);
        StringBuilder sb1 = new StringBuilder();
        sb1.append(id);
        bw.write(sb1.toString() + "\n");
        bw.close();
    }


    //Chin Pei Wern
    // check whether username exists before in system
    public int checkDuplicates(String username)  throws IOException {
        List<String> lines = Files.readAllLines(Paths.get( "User.csv"));

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            String userNameCSV = items[1];
            if(username.equals(userNameCSV))
                return 1;
        }
        return 0;
    }

    //Chin Pei Wern
    //to approve property agent's registration
    public void addNewAgent(List<PropertyAgent> allAgent, String username) throws IOException{
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allAgent.size(); i++) {
            if (allAgent.get(i).username.equals(username)) {
                allAgent.get(i).ID += (new Date()).getTime();
                sb.append(allAgent.get(i).toAgentCSVString());
                allAgent.remove(i);
                break;
            }
        }
        String file = "PropertyAgent.csv";
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString() + "\n");
        bw.close();
        file = "User.csv";
        fw = new FileWriter(file,true);
        bw = new BufferedWriter(fw);
        bw.write(sb.toString() + "\n");
        bw.close();

    }

    //Chin Pei Wern
    //to approve property owner's registration
    public void addNewOwner(List<PropertyOwner> allOwner, String user) throws IOException{
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allOwner.size(); i++) {
            if ((allOwner.get(i).username).equals(user)) {
                (allOwner.get(i)).ID += (new Date()).getTime();
                sb.append((allOwner.get(i)).toOwnerCSVString());
                allOwner.remove(i);
                break;
            }
        }
        String file = "PropertyOwner.csv";
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString() + "\n");
        bw.close();
        file = "User.csv";
        fw = new FileWriter(file,true);
        bw = new BufferedWriter(fw);
        bw.write(sb.toString() + "\n");
        bw.close();
    }

    //Chin Pei Wern
    //to reject property owners and agents registrations
    public void deleteRegistration(List<PropertyOwner> allOwner, List<PropertyAgent> allAgent, String username, String userType) throws IOException {
        if (userType.equals("Property Owner")) {
            for (int i = 0; i < allOwner.size(); i++)
                if (username.equals(allOwner.get(i).username))
                    allOwner.remove(i);
        }
        else if (userType.equals("Property Agent")) {
            for (int j = 0; j < allAgent.size(); j++)
                if (username.equals(allAgent.get(j).username))
                    allAgent.remove(j);
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < allOwner.size(); k++) 
            sb.append(allOwner.get(k).toOwnerCSVString() + "\n");
        for (int i = 0; i < allAgent.size(); i++)
            sb.append(allAgent.get(i).toAgentCSVString() + "\n");
        Files.write(Paths.get("AdvertiserReg.csv"), sb.toString().getBytes()); 
    }

    //Chin Pei Wern
    //to save property listing into csv file of respective admin
    public void saveProperty(Property property) throws IOException {
        boolean containT = false;
        StringBuilder sb = new StringBuilder();
        List<String> lines = Files.readAllLines(Paths.get("AdminProperty.csv"));
        for (int i = 0; i < lines.size(); i++) {
                String[] items = lines.get(i).split(",");
                if (items[0].equals(ID)) {
                    sb.append(lines.get(i));
                    for (int j = 1; j < items.length; j++) {
                        if ((property.propertyID).equals(items[j])) {
                            containT = true;
                            sb.append("\n");
                            break;
                        } 
                    }
                    if (containT == false)
                        sb.append("," + property.propertyID + "\n");
                }
                else 
                    sb.append(lines.get(i) + "\n");
        }
        Files.write(Paths.get("AdminProperty.csv"), sb.toString().getBytes()); 
    }

    //Chin Pei Wern
    //to get property listing list of respective admin
    public ArrayList<Property> getProperties() throws IOException {
        ArrayList<Property> ppt = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get("AdminProperty.csv"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            if (items[0].equals(ID)) {
                if (items.length == 1)
                    break;
                else {
                    for (int j = 1; j < items.length; j++) {
                        List<String> linesP = Files.readAllLines(Paths.get("Property.txt"));
                        for (int k = 0; k < linesP.size(); k++) {
                            String[] itemsP = linesP.get(k).split(";");
                            if (itemsP[0].equals(items[j])) {
                                ppt.add(new Property(itemsP[0], itemsP[1], itemsP[2], itemsP[3], itemsP[4], itemsP[5], itemsP[6], Integer.valueOf(itemsP[7]), Integer.valueOf(itemsP[8]), Double.parseDouble(itemsP[9]), itemsP[10], Double.parseDouble(itemsP[11]), itemsP[12], Double.parseDouble(itemsP[13]), itemsP[14]));
                            }
                        }
                    }
                }
            }
        }
        properties = ppt;
        return properties;
    }

    //Chin Pei Wern
    //to return admin data into a String to save into csv file
    public String AdminCSVString() {
        return ID + ',' + username + ',' + password + ',' + name; 
    }
}
