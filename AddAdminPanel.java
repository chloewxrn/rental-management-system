import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Date;
import java.awt.*;
import java.util.ArrayList;
import java.net.URL;

// Observer DP at createBtn, cancelBtn
// Chin Pei Wern
// panel for adding new admin into the system
public class AddAdminPanel extends JPanel {

    Admin admin;

    //Chin Pei Wern
    //to get which admin object is accessing the system
    public void receive (Admin admin) {
        this.admin = admin;
    }

    //Chin Pei Wern
    //constructor (display components)
    AddAdminPanel() {
        JLabel addAdminLabel = new JLabel("<html><h1> Add Admin<br></h1></html>");
        JLabel accInfoLabel = new JLabel("Account Information");
        JLabel usernameLabel = new JLabel("USERNAME");
        JLabel adminIDLabel = new JLabel ("ADMIN ID:");
        JLabel fullNameLabel = new JLabel("FULL NAME");
        JLabel passwordLabel = new JLabel ("PASSWORD");
        JLabel confirmPassLabel = new JLabel ("PASSWORD CONFIRMATION");
        JTextField usernameTxt = new JTextField();
        JLabel adminIDTxt = new JLabel("A"+ (new Date()).getTime());
        JTextField fullNameTxt = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton createBtn = new JButton ("Add New Admin");
        createBtn.setBounds(200, 400, 150, 30);

        JButton cancelBtn = new JButton ("Cancel");
        cancelBtn.setBounds(500, 400, 100, 30);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                passwordField.setText("");
                confirmPasswordField.setText("");
                usernameTxt.setText("");
                fullNameTxt.setText("");
                adminIDTxt.setText("A" + (new Date()).getTime());
            }
        });
        
        Admin admin = new Admin();
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                char[] passwordTxt = passwordField.getPassword();
                String password = new String (passwordTxt);
                char[] confirmPass = confirmPasswordField.getPassword();
                String confirmTxt = new String (confirmPass);
                String username = usernameTxt.getText();
                try {
                    if (password.equals(confirmTxt) == false) 
                        JOptionPane.showMessageDialog(null, "Password and Password Confirmation is Different!");
                    else if (admin.checkDuplicates(username) == 1)
                        JOptionPane.showMessageDialog(null, "Username already exist!");
                    else {
                        admin.addNewAdmin((String) adminIDTxt.getText(), (String)usernameTxt.getText(), password, (String) fullNameTxt.getText());
                        JOptionPane.showMessageDialog(null, "New Admin Account Successfully Created!");
                        passwordField.setText("");
                        confirmPasswordField.setText("");
                        usernameTxt.setText("");
                        fullNameTxt.setText("");
                        adminIDTxt.setText("A" + (new Date()).getTime());
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.setLayout(null);
        add(addAdminLabel);
        add(accInfoLabel);
        add(usernameLabel);
        add(usernameTxt);
        add(adminIDLabel);
        add(adminIDTxt);
        add(fullNameLabel);
        add(fullNameTxt);
        add(passwordLabel);
        add(passwordField);
        add(confirmPassLabel);
        add(confirmPasswordField);
        add(createBtn);
        add(cancelBtn);

        addAdminLabel.setBounds(300, 30, 150, 30);
        accInfoLabel.setBounds(200, 80, 150, 30);
        usernameLabel.setBounds(200, 150, 150, 30);
        usernameTxt.setBounds(400, 150, 150, 30);
        adminIDLabel.setBounds(200, 100, 150, 30);
        adminIDTxt.setBounds(400, 100, 150, 30);
        fullNameLabel.setBounds(200, 200, 150, 30);
        fullNameTxt.setBounds(400, 200, 150, 30);
        passwordLabel.setBounds(200, 250, 150, 30);
        passwordField.setBounds(400, 250, 150, 30);
        confirmPassLabel.setBounds(200, 300, 200, 30);
        confirmPasswordField.setBounds(400, 300, 150, 30);
    }
}  

//Chin Pei Wern
//to display admin's saved listings
class AdminPropertyPanel extends JPanel {
    Admin admin;
    JPanel gridPanel;
    JScrollPane vertical;
    ArrayList<Property> properties;

    //Chin Pei Wern
    //to get which admin object is accessing the system
    public void receive (Admin admin) {
        this.admin = admin;
    }

    //Chin Pei Wern
    //constructor (display components)
    AdminPropertyPanel() {
        JLabel title = new JLabel("ADMIN'S PROPERTY LISTINGS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(120, 90, 40));
        gridPanel = new JPanel (new GridLayout(0,2));
        vertical = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        vertical.setMinimumSize(new Dimension(900, 200));
        vertical.setPreferredSize(new Dimension(1220, 600));
        add(title, BorderLayout.NORTH);
        add(vertical, BorderLayout.SOUTH);
    }


    //Chin Pei Wern
    // update the panel if new property is saved into listing
    public void refreshTable() {
        try {
            gridPanel.removeAll();
            properties = admin.getProperties();
            for (Property property: properties) {
                URL url = new URL(property.image);
                ImageIcon propertyImage = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(500, 310, Image.SCALE_DEFAULT));
                JLabel propertyLabel = new JLabel();
                propertyLabel.setIcon(propertyImage);
        
                JLabel propertyDetails = new JLabel("<html> <p><h1> RM" + property.rentalPrice + "<h4> (RM " + property.rentalRate + " per sq. ft)" + 
                "<h3>" + property.township + "<br>" + property.address + 
                "<br> </h3> Type: "+ property.type +
                "<br> Facilities: "+ property.facilities + "<br> Room: " + property.roomNum + " | Bathroom: " + property.bathroomNum +  "  | Built-up: " + property.unitSize + " sq. ft"+ 
                "<br> Status: " + property.status + "<br>" +"<br> </h3> Contact: " + property.advertiserContact + " (" + property.advertiserName + ")" +
                "</html>");
                JPanel details = new JPanel();
                details.setLayout(null);
                details.add(propertyDetails);
                propertyDetails.setBounds(0,-20, 550,330);

                gridPanel.add(propertyLabel);
                gridPanel.add(details);
            }
        }
            catch (IOException ex) {
                ex.printStackTrace();
            }
    }
}

