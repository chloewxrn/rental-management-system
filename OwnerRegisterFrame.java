import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

// Observer DP at ownerSubmit
//Ong Jia Xuan
//Panel for the registration of property owner
public class OwnerRegisterFrame extends JPanel implements ActionListener{

    JButton ownerSubmit ,resetButton;
    JRadioButton ownerMale, ownerFemale;
    JTextField ownerNameTextField,ownerICText, ownerContacTextField, ownerEmailText,  ownerUsernameText, ownerPasswordText;
    private Switchable switcher;

    OwnerRegisterFrame(Switchable switcher){
        this.switcher = switcher;
        setLayout(null);
        
        JLabel ownerRegTitle = new JLabel ("Property Owner Registration Form");
        ownerRegTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        ownerRegTitle.setSize(300, 30);
        ownerRegTitle.setLocation(300, 30);
        add(ownerRegTitle);

        JLabel ownerName = new JLabel("Name");
        ownerName.setSize(100, 20);
        ownerName.setLocation(250, 100);
        add(ownerName);

        ownerNameTextField = new JTextField();
        ownerNameTextField.setSize(190, 20);
        ownerNameTextField.setLocation(350, 100);
        add(ownerNameTextField);

        JLabel ownerIC = new JLabel("IC Number");
        ownerIC.setSize(100,20);
        ownerIC.setLocation(250,150);
        add(ownerIC);
        
        ownerICText = new JTextField();
        ownerICText.setSize(190,20);
        ownerICText.setLocation(350,150);
        add(ownerICText);

        JLabel ownerContact = new JLabel("Contact Number");
        ownerContact.setSize(100,20);
        ownerContact.setLocation(250, 200);
        add(ownerContact);

        ownerContacTextField = new JTextField();
        ownerContacTextField.setSize(190,20);
        ownerContacTextField.setLocation(350,200);
        add(ownerContacTextField);

        JLabel ownerEmail = new JLabel ("Email");
        ownerEmail.setSize(100,20);
        ownerEmail.setLocation(250,250);
        add(ownerEmail);

        ownerEmailText = new JTextField();
        ownerEmailText.setSize(190,20);
        ownerEmailText.setLocation(350,250);
        add(ownerEmailText);

        JLabel ownerGender = new JLabel("Gender");
        ownerGender.setSize(100,20);
        ownerGender.setLocation(250,300);
        add(ownerGender);

        ownerMale = new JRadioButton("Male");
        ownerMale.setSize(75, 20);
        ownerMale.setLocation(350,300);
        add(ownerMale);

        ownerFemale = new JRadioButton("Female");
        ownerFemale.setSize(75, 20);
        ownerFemale.setLocation(450,300);
        add(ownerFemale);
        
        JLabel ownerUsername = new JLabel("Username");
        ownerUsername.setSize(100,20);
        ownerUsername.setLocation(250, 350);
        add(ownerUsername);

        ownerUsernameText = new JTextField();
        ownerUsernameText.setSize(190,20);
        ownerUsernameText.setLocation(350, 350);
        add(ownerUsernameText);

        JLabel ownerPassword = new JLabel ("Password");
        ownerPassword.setSize(100,20);
        ownerPassword.setLocation(250, 400);
        add(ownerPassword);

        ownerPasswordText = new JTextField();
        ownerPasswordText.setSize(190,20);
        ownerPasswordText.setLocation(350, 400);
        add(ownerPasswordText);

        ownerSubmit = new JButton("Submit");
        ownerSubmit.setSize(100, 20);
        ownerSubmit.setLocation(360, 450);
        ownerSubmit.addActionListener(this);
        add(ownerSubmit);
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource() == ownerSubmit){
            Registration owner = new Registration("PropertyOwner");
            PropertyOwner propertyOwner = new PropertyOwner();
            
            try {
                
                if (owner.checkDuplicatesOwner(ownerUsernameText.getText(),ownerICText.getText(), owner.userType) == 0) {
                    if(ownerMale.isSelected() && ownerFemale.isSelected() ){
                        JOptionPane.showMessageDialog(this, " Please select one gender only!", "Alert",JOptionPane.WARNING_MESSAGE);
                    }
                    else if(ownerMale.isSelected()){
                        propertyOwner.saveOwnerReg("O",ownerUsernameText.getText(), ownerPasswordText.getText(),ownerNameTextField.getText()
                                                ,ownerICText.getText(), "Male", ownerEmailText.getText(), ownerContacTextField.getText());

                        JOptionPane.showMessageDialog(this, "Your registration has been successfully created.", "Successful",JOptionPane.INFORMATION_MESSAGE);
                        switcher.show("login");
                    }

                    else if (ownerFemale.isSelected()){
                        propertyOwner.saveOwnerReg("O",ownerUsernameText.getText(), ownerPasswordText.getText(),ownerNameTextField.getText()
                                                ,ownerICText.getText(), "Female", ownerEmailText.getText(), ownerContacTextField.getText());
                        JOptionPane.showMessageDialog(this, "Your registration has been successfully created.", "Successful",JOptionPane.INFORMATION_MESSAGE);
                        switcher.show("login");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Please select your gender!", "Alert",JOptionPane.WARNING_MESSAGE);
                    }
            }  
            
            else if (owner.checkDuplicatesOwner(ownerUsernameText.getText(),ownerICText.getText(), owner.userType) == 1){
                JOptionPane.showMessageDialog(this, "Username or IC number are existed! Please try again", "Alert",JOptionPane.WARNING_MESSAGE);

            }
     
            }

            catch (IOException e1) {
               
                e1.printStackTrace();
            }
        
        }

    }
    
}

// Observer DP at tenantButton, agentButton, ownerButton
// Ong Jia Xuan
// Panel for choosing user type account before the registration
class selectRegUserTypeFrame extends JPanel implements ActionListener{

    JLabel title;
    JButton tenantButton, agentButton, ownerButton;
    private Switchable switcher;


    selectRegUserTypeFrame(Switchable switcher){
        this.switcher = switcher;

        setLayout(null);
        

        title = new JLabel("Which user are you?");
        title.setFont(new Font("Arial", Font.PLAIN, 18));
        title.setSize(200, 30);
        title.setLocation(120, 30);

        add(title);

        tenantButton = new JButton ("Potential Tenant");
        tenantButton.setSize(150,30);
        tenantButton.setLocation(120,80);
        tenantButton.addActionListener(this);
        add(tenantButton);

        agentButton = new JButton ("Property Agent");
        agentButton.setSize(150,30);
        agentButton.setLocation(120,150);
        agentButton.addActionListener(this);
        add(agentButton);

        ownerButton = new JButton ("Property Owner");
        ownerButton.setSize(150,30);
        ownerButton.setLocation(120,220);
        ownerButton.addActionListener(this);
        add(ownerButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tenantButton){
            switcher.show("tenantReg");
        }

        else if (e.getSource() == agentButton){
            switcher.show("agentReg");
        }

        else if (e.getSource() == ownerButton){
            switcher.show("ownerReg");
        }
        
    }

}

