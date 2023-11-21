import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// Observer DP at JMenuItem
//Chin Pei Wern
//connect different jpanel together
public class MainGUI extends JFrame implements Switchable, ActionListener {
    JFrame frame = new JFrame ("ONLINE RENTAL MANAGEMENT SYSTEM");
    CardLayout layout = new CardLayout(20,20);
    JPanel mainPanel = new JPanel(layout);
    TenantProfile profilePanel;
    TenantUpdateProfile updateProfile;
    OwnerPropertyList boardList;
    ReviewRegistrationPanel review;
    AddAdminPanel adminPanel;
    ReportGenerate reportPanel;
    AgentPropertyList agentPropertyList;
    AdminPropertyPanel adminPropertyPanel;
    HomePanel homeTT;



    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
    private JMenuBar menuBar;
    private static JMenu adminBar,ownerBar,tenantBar,agentBar;
    // Tenant's menuItems
    private JMenuItem editProfileTenant,logout, reportTenant, profileTenant;
    // 
    private JMenuItem home;
    // Addtional
    // Owner's Menu Item
    private JMenuItem ownerProperty, reportOwner, homeOwner, logoutOwner;
    // admin menu
    private JMenuItem adminReview, addAdmin, homeAdmin, reportAdmin, logoutAdmin, adminProperty;
    // Agent's Menu Item
    private JMenuItem agentProperty, homeAgent, reportAgent, logoutAgent ;

    public MainGUI() {

        // Addtional 
        menuBar = new JMenuBar();
        menuBar.add(Box.createHorizontalGlue());

        // Admin Bar
        adminBar = new JMenu(" Admin");
        menuBar.add(adminBar);
        adminBar.setVisible(false);
        adminReview = new JMenuItem("Review Registration");
        addAdmin = new JMenuItem("Add new admin");
        homeAdmin = new JMenuItem("Home");
        reportAdmin = new JMenuItem("Report");
        logoutAdmin = new JMenuItem("Logout");
        adminProperty = new JMenuItem("Saved Listings");
        
        logoutAdmin.addActionListener(this);
        addAdmin.addActionListener(this);
        adminReview.addActionListener(this);
        homeAdmin.addActionListener(this);
        reportAdmin.addActionListener(this);
        adminBar.add(homeAdmin);
        adminBar.add(reportAdmin);
        adminBar.add(adminReview);
        adminBar.add(addAdmin);
        adminBar.add(adminProperty);
        adminBar.add(logoutAdmin);
        adminProperty.addActionListener(this);

        // Owner Bar
        ownerBar = new JMenu(" Owner");
        menuBar.add(ownerBar);
        ownerBar.setVisible(false);
        ownerProperty = new JMenuItem("My Property");
        homeOwner = new JMenuItem("Home");
        logoutOwner = new JMenuItem("Logout");
        reportOwner = new JMenuItem("Report");
        logoutOwner.addActionListener(this);
        reportOwner.addActionListener(this);
        ownerProperty.addActionListener(this);
        homeOwner.addActionListener(this);
        ownerBar.add(homeOwner);
        ownerBar.add(ownerProperty);
        ownerBar.add(reportOwner);
        ownerBar.add(logoutOwner);


        // Tenant Bar
        tenantBar = new JMenu(" Tenant");
        menuBar.add(tenantBar);
        tenantBar.setVisible(false);
        profileTenant = new JMenuItem("Profile");
        profileTenant.addActionListener(this);
       // editProfileTenant = new JMenuItem("Edit profile");
       // editProfileTenant.addActionListener(this);
        reportTenant = new JMenuItem("Report");
        reportTenant.addActionListener(this);
        logout = new JMenuItem("Logout");
        logout.addActionListener(this);
        home = new JMenuItem("Home");
        home.addActionListener(this);

        tenantBar.add(home);
        tenantBar.add(profileTenant);
        // tenantBar.add(editProfileTenant);
        tenantBar.add(reportTenant);
        tenantBar.add(logout);


        // Agent Bar
        agentBar = new JMenu(" Agent");
        menuBar.add(agentBar);
        agentBar.setVisible(false);
        agentProperty = new JMenuItem("My Property");
        homeAgent = new JMenuItem("Home");
        reportAgent = new JMenuItem("Report");
        logoutAgent = new JMenuItem("Logout");
        agentProperty.addActionListener(this);
        homeAgent.addActionListener(this);
        reportAgent.addActionListener(this);
        logoutAgent.addActionListener(this);
        agentBar.add(agentProperty);
        agentBar.add(homeAgent);
        agentBar.add(reportAgent);
        agentBar.add(logoutAgent);
        
        frame.add(menuBar);
        frame.setJMenuBar(menuBar);
        menuBar.setVisible(true);
    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
        LoginPanel loginPanel = new LoginPanel(this);
        mainPanel.add(loginPanel, "login");
        
        review = new ReviewRegistrationPanel();
        mainPanel.add(review, "review");
        adminPanel = new AddAdminPanel();
        mainPanel.add(adminPanel, "addAdmin");
        //HomePanel homePanel = new HomePanel();
        //mainPanel.add(homePanel, "home");   
        updateProfile = new TenantUpdateProfile();
        mainPanel.add(updateProfile, "updateProf");
        OwnerRegisterFrame ownerRegister = new OwnerRegisterFrame(this);
        mainPanel.add(ownerRegister, "ownerReg");
        AgentRegisterGUI agentReg = new AgentRegisterGUI(this);
        mainPanel.add(agentReg, "agentReg");
        selectRegUserTypeFrame userType = new selectRegUserTypeFrame(this);
        mainPanel.add(userType, "userType");
        PotentialTenantRegistrationFrame tenantReg = new PotentialTenantRegistrationFrame(this);
        mainPanel.add(tenantReg, "tenantReg");

        boardList = new OwnerPropertyList();
        mainPanel.add(boardList, "boardList");

        agentPropertyList = new AgentPropertyList(this);  
        mainPanel.add(agentPropertyList, "agentPropertyList");

        homeTT = new HomePanel(this);
        mainPanel.add(homeTT, "homepage");

        reportPanel = new ReportGenerate();
        mainPanel.add(reportPanel, "report");
        adminPropertyPanel = new AdminPropertyPanel();
        mainPanel.add(adminPropertyPanel, "adminProperty");

        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "login");



        frame.getContentPane().add(mainPanel);
        frame.setLocation(new Point(100,0));
        frame.setSize(1150, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
    }

    //Chin Pei Wern
    //to change between different jpanel using cardlayout by overriding the method in Switchable
    @Override
    public void show (String card) {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, card);
    }


   
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(home) || e.getSource().equals(homeAdmin) || e.getSource().equals(homeOwner) || e.getSource().equals(homeAgent) ){
            show("homepage");
        }

        else if(e.getSource().equals(editProfileTenant)){
            show("updateProf");
        }   

        else if(e.getSource().equals(profileTenant)){
            show("profile");
        }
        
        else if(e.getSource().equals(ownerProperty)){
            show("boardList");
        }
        else if (e.getSource().equals(addAdmin))
            show("addAdmin");
        else if (e.getSource().equals(adminReview))
            show("review");
        else if (e.getSource().equals(adminProperty))
            show("adminProperty");

        else if (e.getSource().equals(reportAdmin) || e.getSource().equals(reportOwner) || e.getSource().equals(reportTenant) || e.getSource().equals(reportAgent)){
            show("report");
        }
        else if(e.getSource().equals(agentProperty))
            show("agentPropertyList");       

        else if (e.getSource().equals(logout) || e.getSource().equals(logoutAdmin) || e.getSource().equals(logoutOwner) || e.getSource().equals(logoutAgent)){
            frame.dispose();
            new MainGUI();
        }
    }
    
    //Chin Pei Wern
    //to get the which tenant logged into the system
    @Override
    public void setTenant (PotentialTenant tenant) {
        profilePanel = new TenantProfile(this);
        profilePanel.receive(tenant);
        updateProfile.receive(tenant);
       
        mainPanel.add(profilePanel, "profile");
         show("homepage");
        // show("profile");
        assignMenu("tenant");
    }

    //Chin Pei Wern
    //to get the which property agent logged into the system
    @Override
    public void setAgent (PropertyAgent agent) {
        agentPropertyList.receive(agent);
        show("homepage");
        assignMenu("agent");
    }

    //Chin Pei Wern
    //to get the which property owner logged into the system
    @Override
    public void setOwner (PropertyOwner owner) {
        boardList.receive(owner);
        show("homepage");
        assignMenu("owner");
    }

    //Chin Pei Wern
    //to get the which admin logged into the system
    @Override
    public void setAdmin (Admin admin) {
        adminPanel.receive(admin);
        review.receive(admin);
        adminPropertyPanel.receive(admin);
        adminPropertyPanel.refreshTable();
        homeTT.receive(admin);
        show("homepage");
        assignMenu("admin");
    }

    // Additional 
    public static void assignMenu(String role){
        if(role.equals("user")){
            ownerBar.setVisible(false);
            tenantBar.setVisible(false);
            agentBar.setVisible(false);
         }

        else if(role.equals("admin")){
            adminBar.setVisible(true);  }

        else if(role.equals("owner")){
            ownerBar.setVisible(true);   }

        else if(role.equals("tenant")){
            tenantBar.setVisible(true); }

        else if(role.equals("agent")){
            agentBar.setVisible(true);   }

    }

    public static void main (String[] args) {
        new MainGUI();   
    }
}


