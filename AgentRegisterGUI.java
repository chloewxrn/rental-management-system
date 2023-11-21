import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Observer DP at submitBtn
// Ng Yoong Kee
// For Agent to do the registration
public class AgentRegisterGUI extends JPanel{
    private Switchable switcher;
    // Ng Yoong Kee
    // Main interface of the registration form
    AgentRegisterGUI(Switchable switcher) {
        this.switcher = switcher;
        repaint();
        revalidate();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        // Create title label
        JLabel title = new JLabel("Agent Registration Form");
        title.setFont(new Font("Book Antiqua", Font.PLAIN, 20));


        // Create textfields
        JTextField usernameTxField, nameTxField, ICtxField, phoneTxField, emailTxField, licenceCodeTxField, 
        agencyNameTxField, agencyRegNoTxField;

        usernameTxField = new JTextField(30);
        nameTxField = new JTextField();
        ICtxField = new JTextField();
        phoneTxField = new JTextField();
        emailTxField = new JTextField();
        licenceCodeTxField = new JTextField();
        agencyNameTxField = new JTextField();
        agencyRegNoTxField = new JTextField();


        // Creat password field
        JPasswordField passwordField = new JPasswordField();


        // Create gender combo box
        String[] gender = {"Male", "Female"};
        JComboBox<String> comboGender = new JComboBox<>(gender);


        // Create submit button and add action
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    
                    // if all the text fields are filled
                    if(usernameTxField.getText().isEmpty() == false && passwordField.getPassword().toString().isEmpty() == false && 
                       nameTxField.getText().isEmpty() == false && ICtxField.getText().isEmpty() == false && 
                       phoneTxField.getText().isEmpty() == false && emailTxField.getText().isEmpty() == false && 
                       licenceCodeTxField.getText().isEmpty() == false && agencyNameTxField.getText().isEmpty() == false && 
                       agencyRegNoTxField.getText().isEmpty() == false) {

                        Registration agentRegister = new Registration("PropertyAgent");
                        Boolean validation = agentRegister.checkDuplicatesAgent(usernameTxField.getText(), ICtxField.getText(), agentRegister.userType);

                        if(validation == true) { // if no duplicate
                            
                            PropertyAgent propertyAgent = new PropertyAgent("C", usernameTxField.getText(), new String(passwordField.getPassword()), 
                                                                            nameTxField.getText(), ICtxField.getText(), comboGender.getSelectedItem().toString(), 
                                                                            emailTxField.getText(), phoneTxField.getText(), licenceCodeTxField.getText(), 
                                                                            agencyNameTxField.getText(), agencyRegNoTxField.getText());


                            String toAgentCSV = propertyAgent.toAgentCSVString();
                            propertyAgent.saveAgentCSV(toAgentCSV);

                            JOptionPane.showMessageDialog(null, "Register Successful.");
                            switcher.show("login");

                        }else {
                            JOptionPane.showMessageDialog(null, "Username or IC already existed.");
                        }

                    }else {
                        JOptionPane.showMessageDialog(null, "The block cannot be blank.");
                    }
                    
                }catch(Exception ex) {}
            }
        });


        // Add labels, textfields, password field, combo box and submit button
        gbc.insets = new Insets(10, 0, 20, 0);  // component external padding
        gbc.ipady = 10;  // component internal padding
        int y = 0;
        gbc.gridx = 0;  // coordinate x for component
        gbc.gridy = y;  // coordinate y for component
        gbc.gridwidth = 3;  // component width of grid
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);  // component external padding
        y += 1;
        gbc.gridy = y;
        add(new JLabel("Agent Username: "), gbc);

        gbc.gridx = 1;
        add(usernameTxField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Password: "), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Agent Name: "), gbc);

        gbc.gridx = 1;
        add(nameTxField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Agent Gender: "), gbc);

        gbc.gridx =1;
        add(comboGender, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Agent IC NO: "), gbc);

        gbc.gridx = 1;
        add(ICtxField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Agent Phone NO: "), gbc);

        gbc.gridx = 1;
        add(phoneTxField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Agent Email: "), gbc);

        gbc.gridx = 1;
        add(emailTxField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Agent Licence Code: "), gbc);

        gbc.gridx = 1;
        add(licenceCodeTxField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Agency Name: "), gbc);

        gbc.gridx = 1;
        add(agencyNameTxField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        add(new JLabel("Agency Registration Number: "), gbc);

        gbc.gridx = 1;
        add(agencyRegNoTxField, gbc);

        y += 1;
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weighty = 0.5;
        add(submitBtn, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
    }
}
  