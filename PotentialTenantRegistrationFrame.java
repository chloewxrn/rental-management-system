import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.awt.event.*;
import java.io.IOException;
import java.util.Calendar;


// Observer DP at tenantSubmit
// Wong Yi Teng
// User interface for registration for Potential Tenant
public class PotentialTenantRegistrationFrame extends JPanel implements ActionListener {
    JButton tenantSubmit;
    JTextField t_tenantusername, t_tenantpassword, t_tenantemail, t_tenantname;
    private Switchable switcher;
        // Wong Yi Teng
        // Registration Frame for tenant 
    PotentialTenantRegistrationFrame(Switchable switcher) {
        this.switcher = switcher;
        setLayout(null);
        JLabel tenantRegTitle = new JLabel("Potential Tenant Registration Form");
        tenantRegTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        tenantRegTitle.setSize(300, 30);
        tenantRegTitle.setLocation(450, 30);
        add(tenantRegTitle);

        JLabel tenantUsername = new JLabel("Username");
        tenantUsername.setSize(100, 20);
        tenantUsername.setLocation(450, 100);
        add(tenantUsername);

        t_tenantusername = new JTextField();
        t_tenantusername.setSize(190, 20);
        t_tenantusername.setLocation(550, 100);
        add(t_tenantusername);

        JLabel tenantPassword = new JLabel("Password");
        tenantPassword.setSize(100, 20);
        tenantPassword.setLocation(450, 150);
        add(tenantPassword);

        t_tenantpassword = new JTextField();
        t_tenantpassword.setSize(190, 20);
        t_tenantpassword.setLocation(550, 150);
        add(t_tenantpassword);

        JLabel tenantName = new JLabel("Name");
        tenantName.setSize(100, 20);
        tenantName.setLocation(450, 200);
        add(tenantName);

        t_tenantname = new JTextField();
        t_tenantname.setSize(190, 20);
        t_tenantname.setLocation(550, 200);
        add(t_tenantname);

        JLabel tenantEmail = new JLabel("Email");
        tenantEmail.setSize(100, 20);
        tenantEmail.setLocation(450, 250);
        add(tenantEmail);

        t_tenantemail = new JTextField();
        t_tenantemail.setSize(190, 20);
        t_tenantemail.setLocation(550, 250);
        add(t_tenantemail);

        tenantSubmit = new JButton("Submit");
        tenantSubmit.setSize(100, 20);
        tenantSubmit.setLocation(500, 290);
        tenantSubmit.addActionListener(this);
        add(tenantSubmit);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tenantSubmit) {
            PotentialTenant potentialTenant = new PotentialTenant();
            Registration tenant = new Registration("PotentialTenant");

            String ID_Tenant = randomTenantID();
            try {
                if (tenant.checkDuplicatesTenant(ID_Tenant,t_tenantusername.getText(), t_tenantemail.getText(),
                        "PotentialTenant") == 0) {

                            // Create Account in the database
                            potentialTenant.createAccount(ID_Tenant,t_tenantusername.getText(), t_tenantpassword.getText(),
                            t_tenantname.getText(), t_tenantemail.getText());
                            JOptionPane.showMessageDialog(null, "Registration Succeed !");
                            switcher.show("login");

      }

                else if (tenant.checkDuplicatesTenant(ID_Tenant,t_tenantusername.getText(), t_tenantemail.getText(),
                        "PotentialTenant") == 1) {
                    JOptionPane.showMessageDialog(null,
                            "This email is registered. Please try again using other email");

                }

                else if (tenant.checkDuplicatesTenant(ID_Tenant,t_tenantusername.getText(), t_tenantemail.getText(),
                        "PotentialTenant") == 2) {
                    JOptionPane.showMessageDialog(null, "This username is taken. Please try again using.");

                }

                else if (tenant.checkDuplicatesTenant(ID_Tenant,t_tenantusername.getText(), t_tenantemail.getText(),
                        "PotentialTenant") == 3) {
                        // If ID generated is existed, add 1 for the ID.
                        ID_Tenant += 1;
                        potentialTenant.createAccount(ID_Tenant,t_tenantusername.getText(), t_tenantpassword.getText(),
                        t_tenantname.getText(), t_tenantemail.getText());
        }

            }

            catch (IOException e1) {
                e1.printStackTrace();

            }
        }
    }


    // Wong Yi Teng
    // Generate TenantID
    private static String randomTenantID(){
            Date today = Calendar.getInstance().getTime();
            long epochTime = today.getTime();
            String ID = "T" + Long.toString(epochTime);
            
            return ID;
    }

}