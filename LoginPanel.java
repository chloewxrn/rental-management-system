import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


// Observer DP at loginBtn, registerBtn
//Chin Pei Wern
//log in user interface
public class LoginPanel extends JPanel implements ActionListener {
    
    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();
    private Switchable switcher;

    //Chin Pei Wern
    //constructor (display components)
    LoginPanel (Switchable switcher) {
        //to allow to switch between panel
        this.switcher = switcher;

        JLabel title = new JLabel("<html><h1> WELCOME TO CYBERJAYA ONLINE RENTAL MANAGEMENT SYSTEM</h1></html>");
        title.setBounds(200, 30, 1000, 50);
        JLabel usernameLabel = new JLabel("USERNAME");
        usernameLabel.setBounds(400, 150, 150, 30);
        username.setBounds(550, 150, 200, 30);
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setBounds(400, 200, 100, 30);
        password.setBounds(550, 200, 200, 30);
        this.setLayout(null);
        add(title);
        add(usernameLabel);
        add(username);
        add(passLabel);
        add(password);

        JButton loginBtn = new JButton("Log in");
        loginBtn.setBounds(500, 270, 90, 25);
        loginBtn.addActionListener(this);
        add(loginBtn);

        JLabel noAcc = new JLabel("Does not have an account? ");
        noAcc.setBounds(400, 400, 200, 25);
        add(noAcc);
        JButton registerBtn = new JButton("Register now");
        registerBtn.setBounds(600, 400, 140, 25);
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent evt) {
                switcher.show("userType");
            }
        });
        add(registerBtn);
    }

    //Chin Pei Wern
    //to get username and password from textfield after log in button is clicked
    @Override
    public void actionPerformed (ActionEvent evt) {
        String userTxt = username.getText();
        char[] passTxt = password.getPassword();
        String pass = new String (passTxt);
        boolean flag = false;
        try {
            List<String> lines = Files.readAllLines(Paths.get("User.csv"));

            for (int i = 0; i < lines.size(); i++) {
                String[] items = lines.get(i).split(",");
                if (userTxt.equals(items[1])) {
                    flag = true;
                    if (pass.equals(items[2])) {
                        JOptionPane.showMessageDialog(null, "Login Successful");
                        if (items[0].charAt(0) == 'T') {
                            PotentialTenant tenant = new PotentialTenant(items[0], items[1], items[2], items[3], items[4]);
                            switcher.setTenant(tenant);
                        }
                        else if (items[0].charAt(0) == 'A') {
                            Admin admin = new Admin(items[0], items[1], items[2], items[3]);
                            switcher.setAdmin(admin);
                        }
                        else if(items[0].charAt(0) == 'O') {
                            PropertyOwner owner = new PropertyOwner(items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7]);
                            switcher.setOwner(owner);
                        }
                        else {
                            PropertyAgent agent = new PropertyAgent(items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7], items[8], items[9], items[10]);
                            switcher.setAgent(agent);
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Incorrect Password");
                }
            }
                if (!flag)
                    JOptionPane.showMessageDialog(null, "The username you entered does not exist");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
