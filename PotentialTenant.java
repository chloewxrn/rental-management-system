import java.io.*;


// Wong Yi Teng
// subclass of User class
public class PotentialTenant extends User {

    protected String email;

    // Constructor
    public PotentialTenant() { }

    // Constructor
    public PotentialTenant(String ID, String username, String password, String name, String email) {
        super(ID,username, password, name);
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String chg_password) {
        password = chg_password;
    }

    public void setName(String chg_Name) {
        name = chg_Name;
    }

    public void setEmail(String chg_email) {
        email = chg_email;
    }

    // Wong Yi Teng & Chin Pei Wern
    // to add new Potential Tenant into the system after registration
    protected void createAccount (String ID, String username, String password, String name, String email) throws IOException{
        StringBuilder sb = new StringBuilder();
        PotentialTenant tenant = new PotentialTenant(ID, username, password, name, email);
        sb.append(tenant.toTenantCSVString());
        String file = "PotentialTenant.csv";
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


    // Wong Yi Teng
    // to edit profile
    protected void editProfile(String chg_password, String chg_name, String chg_email) {
        password = chg_password;
        name = chg_name;
        email = chg_email;
    }

    //Wong Yi Teng
    //to return tenant data into a String to save into csv file
    protected String toTenantCSVString() {
        return ID + ',' + username + ',' + password + ',' + name + ',' + email;
    }
}