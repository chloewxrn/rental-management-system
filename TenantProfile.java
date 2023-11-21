import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;

// Observer DP at editProfile
// Wong Yi Teng
// Panel for PotentialTenant's profile
public class TenantProfile extends JPanel implements ActionListener {
    JButton editProfile;
    JLabel tenantName;
    JLabel username, password, email, name, ID;
    private Switchable switcher;
    PotentialTenant tenant;

    // Wong Yi Teng & Chin Pei Wern
    // to retrieve the tenant details after logged in
    public void receive(PotentialTenant tenant) {
        this.tenant = tenant;
        username = new JLabel(tenant.getUsername());
        ID = new JLabel(tenant.ID);
        username.setSize(190, 20);
        username.setLocation(550, 100);
        add(username);
        ID.setSize(190, 20);
        ID.setLocation(550, 65);
        add(ID);

        password = new JLabel(tenant.getPassword());
        password.setSize(190, 20);
        password.setLocation(550, 150);
        add(password);

        name = new JLabel(tenant.getName());
        name.setSize(190, 20);
        name.setLocation(550, 200);
        add(name);

        email = new JLabel(tenant.getEmail());
        email.setSize(190, 20);
        email.setLocation(550, 250);
        add(email);
    }

    // Wong Yi Teng
    // Panel for PotentialTenant's profile
    TenantProfile(Switchable switcher) {
        setLayout(null);
        this.switcher = switcher;

        JLabel tenantUserProfileTitles = new JLabel("Potential Tenant : User Profile");
        tenantUserProfileTitles.setFont(new Font("Arial", Font.PLAIN, 18));
        tenantUserProfileTitles.setSize(300, 30);
        tenantUserProfileTitles.setLocation(450, 30);
        add(tenantUserProfileTitles);

        JLabel tenantIDLabel = new JLabel("Tenant ID : ");
        tenantIDLabel.setSize(100, 20);
        tenantIDLabel.setLocation(450, 65);
        add(tenantIDLabel);

        JLabel tenantUsernameLabel = new JLabel("Username : ");
        tenantUsernameLabel.setSize(100, 20);
        tenantUsernameLabel.setLocation(450, 100);
        add(tenantUsernameLabel);

        JLabel tenantPasswordLabel = new JLabel("Password :");
        tenantPasswordLabel.setSize(100, 20);
        tenantPasswordLabel.setLocation(450, 150);
        add(tenantPasswordLabel);

        JLabel tenantNameLabel = new JLabel("Name :");
        tenantNameLabel.setSize(100, 20);
        tenantNameLabel.setLocation(450, 200);
        add(tenantNameLabel);

        JLabel tenantEmailLabel = new JLabel("Email :");
        tenantEmailLabel.setSize(100, 20);
        tenantEmailLabel.setLocation(450, 250);
        add(tenantEmailLabel);

        editProfile = new JButton("Edit Profile");
        editProfile.setSize(100, 20);
        editProfile.setLocation(600, 290);
        editProfile.addActionListener(this);
        // add(editProfile, BorderLayout.CENTER);
        add(editProfile);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editProfile) {
            new TenantUpdateProfile();
            switcher.show("updateProf");

        }
    }

}

// Observer DP at updateProfile
// Wong Yi Teng
// Panel for tenant to update their profile
class TenantUpdateProfile extends JPanel implements ActionListener {
    JButton updateProfile;
    JLabel tenantUsername, ID;
    JTextField chg_Password, chg_Email, chg_Name;
    PotentialTenant tenant;
    private Switchable switcher;

    // Wong Yi Teng & Chin Pei Wern
    // to retrieve the tenant details after logged in
    public void receive(PotentialTenant tenant) {
        this.tenant = tenant;

        ID = new JLabel(tenant.ID);
        ID.setSize(190, 20);
        ID.setLocation(550, 65);
        add(ID);

        tenantUsername = new JLabel(tenant.getUsername());
        tenantUsername.setSize(190, 20);
        tenantUsername.setLocation(550, 100);
        add(tenantUsername);

        chg_Password = new JTextField(tenant.getPassword());
        chg_Password.setSize(190, 20);
        chg_Password.setLocation(550, 150);
        add(chg_Password);

        chg_Name = new JTextField(tenant.getName());
        chg_Name.setSize(190, 20);
        chg_Name.setLocation(550, 200);
        add(chg_Name);

        chg_Email = new JTextField(tenant.getEmail());
        chg_Email.setSize(190, 20);
        chg_Email.setLocation(550, 250);
        add(chg_Email);

    }

    // Panel for tenant's profile
    TenantUpdateProfile() {
        setLayout(null);
        JLabel tenantUserProfileTitles = new JLabel("Potential Tenant : User Profile");
        tenantUserProfileTitles.setFont(new Font("Arial", Font.PLAIN, 18));
        tenantUserProfileTitles.setSize(300, 30);
        tenantUserProfileTitles.setLocation(450, 30);
        add(tenantUserProfileTitles);

        JLabel tenantIDLabel = new JLabel("Tenant ID : ");
        tenantIDLabel.setSize(100, 20);
        tenantIDLabel.setLocation(450, 65);
        add(tenantIDLabel);

        JLabel tenantUsernameLabel = new JLabel("Username : ");
        tenantUsernameLabel.setSize(100, 20);
        tenantUsernameLabel.setLocation(450, 100);
        add(tenantUsernameLabel);

        JLabel tenantPasswordLabel = new JLabel("Password :");
        tenantPasswordLabel.setSize(100, 20);
        tenantPasswordLabel.setLocation(450, 150);
        add(tenantPasswordLabel);

        JLabel tenantNameLabel = new JLabel("Name :");
        tenantNameLabel.setSize(100, 20);
        tenantNameLabel.setLocation(450, 200);
        add(tenantNameLabel);

        JLabel tenantEmailLabel = new JLabel("Email :");
        tenantEmailLabel.setSize(100, 20);
        tenantEmailLabel.setLocation(450, 250);
        add(tenantEmailLabel);

        updateProfile = new JButton("Save");
        updateProfile.setSize(100, 20);
        updateProfile.setLocation(500, 290);
        updateProfile.addActionListener(this);
        add(updateProfile);

    }

    // Wong Yi Teng
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateProfile) {
            try {
                if (updateTenantProfile(tenant.getUsername(), chg_Email.getText()) == 0) {

                    // Update personal details in User csv
                    List<String[]> getUserArrayList = get("User.csv");
                    for (int i = 0; i < getUserArrayList.size(); i++) {
                        if (getUserArrayList.get(i)[1].equals(tenant.getUsername())) {
                            getUserArrayList.get(i)[2] = chg_Password.getText();
                            getUserArrayList.get(i)[3] = chg_Name.getText();
                            getUserArrayList.get(i)[4] = chg_Email.getText();
                        }
                    }

                    // Save the updated personal details in User csv
                    saveUser(getUserArrayList);

                    // update tenant's profile
                    ArrayList<PotentialTenant> tenantUpdation = readTenantRegisterFromFile();
                    for (int y = 0; y < tenantUpdation.size(); y++) {
                        if (tenantUpdation.get(y).getUsername().equals(tenant.getUsername())) {
                            tenantUpdation.get(y).editProfile(chg_Password.getText(), chg_Name.getText(),
                                    chg_Email.getText());
                        }
                    }

                    // After updated, save into PotentialTenant.csv
                    saveTenantRegisterToFile(tenantUpdation);
                    JOptionPane.showMessageDialog(this, "Profile Update Successfully!");
                    switcher.show("profile");
                }

                // Check duplicate for the email.
                else if (updateTenantProfile(tenant.getUsername(), chg_Email.getText()) == 1) {
                    JOptionPane.showMessageDialog(this, "This email is registered. Please try again using other email");

                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    // Wong Yi Teng
    // Used for check the duplicate in PotentialTenant.csv
    private int updateTenantProfile(String username, String email) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("PotentialTenant.csv"));

        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            String emailCSV = items[4];
            String usernameCSV = items[1];

            if (email.equals(emailCSV) && !username.equals(usernameCSV))
                return 1;

        }

        return 0;
    }

    // Wong Yi Teng
    // After updated, saved into PotentialTenant.csv
    private void saveTenantRegisterToFile(ArrayList<PotentialTenant> potentialTenant) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < potentialTenant.size(); i++) {
            sb.append(potentialTenant.get(i).toTenantCSVString() + "\n");
        }

        Files.write(Paths.get("PotentialTenant.csv"), sb.toString().getBytes());
    }

    // Wong Yi Teng
    // Read the PotentialTenant from csv
    private ArrayList<PotentialTenant> readTenantRegisterFromFile() throws IOException {
        ArrayList<PotentialTenant> tenant = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get("PotentialTenant.csv"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            tenant.add(new PotentialTenant(items[0], items[1], items[2], items[3], items[4]));
        }
        return tenant;
    }

    // Wong Yi Teng
    // Read User.csv
    public static List<String[]> get(String filename) throws IOException {
        List<String[]> data = new ArrayList<String[]>();
        String testRow;
        BufferedReader br = new BufferedReader(new FileReader(filename));
        while ((testRow = br.readLine()) != null) {
            String[] line = testRow.split(",");
            data.add(line);
        }
        return data;
    }

    // Wong Yi Teng
    // Update in User.csv
    public void saveUser(List<String[]> getUserArrayList) throws IOException {
        FileWriter fw = new FileWriter("User.csv");
        BufferedWriter bw = new BufferedWriter(fw);
        for (String y[] : getUserArrayList) {
            bw.write((Arrays.toString(y).replace("[", "").replace("]", "").replace(" ", "")) + ",");
            bw.newLine();
        }
        bw.close();
        fw.close();

    }

}
