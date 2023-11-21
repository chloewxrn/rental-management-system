import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.*;


// Observer DP at comboBox, sortCB, filterBtn, sortBtn, itemStateChanged, downloadBtn
//Chin Pei Wern
//user interface for homepage
public class HomePanel extends JPanel implements ActionListener,ItemListener {
    
    private JComboBox<String> comboBox;
    private JComboBox<String> subCB = new JComboBox<>();
    private JComboBox<String> sortCB = new JComboBox<>();
    String[] sortOption = {"Sort by", "Ascending", "Descending"};
    JButton filterBtn = new JButton("Filter");
    String option;
    String cat;
    ArrayList<String> facilitiesChoice;
    String[] category = {"Select an option", "Property Type", "Property Status", "Township", "Facilities", "Rental Price", "Owner", "Agent", "Rental Rate", "All property"};
    private Map<String, String[]> subcategory = new HashMap<>();
    JCheckBox[] cb = new JCheckBox[100];
    int lengthNum;

    JPanel gridPanel;
    JPanel p1;

    PropertyList propertyList = new PropertyList();
    String[] rentalPrice = {"Select Rental Price", "< RM1000", "RM1000-RM2000", "RM2001-RM3000", "RM3001-RM4000", "RM4001-RM5000", ">RM5000"};
    String[] rentalRate = {"Select Rental Rate", "< 1.0", "1.1-2.0", "2.1-3.0", "3.1-4.0", "4.1-5.0" , "> 5.0"};

    ArrayList<Property> properties;
    Admin admin;
    private Switchable switcher;

    public void receive (Admin user) {
        this.admin = user;
    }

    public HomePanel(Switchable switcher) {
        this.switcher = switcher;
        comboBox = new JComboBox<>(category);
        comboBox.addActionListener(this);
        sortCB = new JComboBox<>(sortOption);
        sortCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String item = (String) sortCB.getSelectedItem();
                if (item.equals("Ascending"))
                    properties = propertyList.viewSortedAscPropertyList(properties);
                else if (item.equals("Descending"))
                    properties = propertyList.viewSortedDescPropertyList(properties);
                try {
                    gridPanel.removeAll();
                    for (Property property : properties) {
                        displayProperty(property);
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JLabel title = new JLabel("WELCOME TO CYBERJAYA ONLINE RENTAL MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(120, 90, 40));

        try{ 
            properties = new ArrayList<>(propertyList.getAllProperties()); 
        }catch(IOException ex) { 
            ex.printStackTrace(); 
        }


        // Ng Yoong Kee
        // Retrieve all the Property Types from Property.txt, then save into Combo Box
        List<String> tempPropertyType = new ArrayList<String>();
        tempPropertyType.add("Select Property Type");
        for(int i=0; i<properties.size(); i++) {
            String propertyType = properties.get(i).type;
            tempPropertyType.add(propertyType);
        }
        String[] propertyType = tempPropertyType.stream().distinct().collect(Collectors.toList()).toArray(new String[]{});
        subcategory.put(category[1], propertyType);


        
        String[] list2 = {"Select Property Status", "Active", "Inactive"};
        subcategory.put(category[2], list2);

       // Wong Yi Teng
       // Retrieve ALL township from Property.txt to save in comboBox
        List<String> temp = new ArrayList<>();
        temp.add("Select TownShip");
        for(int i=0;i<properties.size();i++){
            String tamans = properties.get(i).township;
            temp.add(tamans);  
        }
        List<String> newList = temp.stream().distinct() .collect(Collectors.toList());
        String[] list3 = new String[newList.size()];
        list3 = newList.toArray(list3);
        subcategory.put(category[3], list3);



        // Wong Yi Teng
       // Retrieve ALL Facilities from Property.txt to save in comboBox
        List<String> faci = new ArrayList<>();
          for(int i=0;i<properties.size();i++){
            String fa = String.join(",", properties.get(i).facilities);
            String[] elements = fa.split(",");
            faci.addAll(Arrays.asList(elements));
        }
            faci.replaceAll(String::toUpperCase);
            ArrayList<String> listOfString = new ArrayList<String>(faci);
            HashSet<String>tempHashSet = new HashSet<String>(listOfString);
            List<String>newList2 = new ArrayList<String>(tempHashSet); 
            String[] list4 = new String[newList2.size()];
            list4 = newList2.toArray(list4);         


        subcategory.put(category[5], rentalPrice);

        
        JPanel p2 = new JPanel();

        // Wong Yi Teng
        // Retrieve ALL Facilities from Property.txt to save in comboBox
        for(int i=0;i<list4.length;i++) {
        cb[i]=new JCheckBox(list4[i]);
        cb[i].setVisible(false);
        cb[i].addItemListener(this);
        p2.add(cb[i]);  
    }

        lengthNum = list4.length;


        // Ng Yoong Kee
        // Retrieve all the Owners' Name from Property.txt, then save into Combo Box
        List<String> tempOwnerName = new ArrayList<String>();
        tempOwnerName.add("Select an Owner");
        for(int i=0; i<properties.size(); i++) {
            if(properties.get(i).advertiserID.charAt(0) == 'O') {
                String owner = properties.get(i).advertiserName;
                tempOwnerName.add(owner);
            }
        }
        String[] ownerName = tempOwnerName.stream().distinct().collect(Collectors.toList()).toArray(new String[]{});
        subcategory.put(category[6], ownerName);

        //Ng Yoong Kee
        // Retrieve all the Agents' Name from Property.txt, then save into Combo Box
        List<String> tempAgentName = new ArrayList<String>();
        tempAgentName.add("Select an Agent");
        for(int i=0; i<properties.size(); i++) {
            if(properties.get(i).advertiserID.charAt(0) == 'C') {
                String agent = properties.get(i).advertiserName;
                tempAgentName.add(agent);
            }
        }
        String[] agentName = tempAgentName.stream().distinct().collect(Collectors.toList()).toArray(new String[]{});
        subcategory.put(category[7], agentName);


        subcategory.put(category[8], rentalRate);


        //Ong Jia Xuan & Wong Yi Teng & Chin Pei Wern
        //To get the user selection from sub combo box and display the properties that fulfilled the selection
        filterBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) comboBox.getSelectedItem();
                option = String.valueOf(subCB.getSelectedItem());
                cat = item;

                if(option!=null){
                    try {
                        gridPanel.removeAll();
                        displayFilteredProperties();
                        if (cat.equals("All property")){
                            properties = propertyList.properties;
                            subCB.setVisible(false);
                        }

                        else 
                            properties = propertyList.ppt;
                        for (Property property : properties) {
                            displayProperty(property);
                        } 
                        gridPanel.revalidate();
                        gridPanel.repaint();
                        for(int i=0;i<lengthNum;i++){
                            cb[i].setSelected(false);
                            facilitiesChoice = new ArrayList<>();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }      
        });


        add(comboBox);
        add(subCB);
        p2.setLayout(new GridLayout(3,1));
        subCB.setVisible(false);
        p1 = new JPanel();
        p1.setLayout(new GridLayout(4,1));
        p1.add(title);
        p1.add(comboBox);
        p1.add(subCB);
        p1.add(p2);
        
        gridPanel = new JPanel (new GridLayout(0,2));
        add(p1, BorderLayout.NORTH);
        add(filterBtn);
        JButton sortBtn = new JButton("Sort");
        sortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                gridPanel.removeAll();
                for (Property property : properties) 
                    displayProperty(property);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(sortCB);
        
        try {
          for (Property property : properties) {
                displayProperty(property);
            }  
            if (filterBtn.getModel().isPressed())
                gridPanel.removeAll();        
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        gridPanel.revalidate();
        gridPanel.repaint();
        JScrollPane vertical = new JScrollPane(gridPanel);
        vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vertical.setMinimumSize(new Dimension(900, 200));
        vertical.setPreferredSize(new Dimension(1220, 400));
        add(vertical, BorderLayout.SOUTH);
        
    }

    //Chin Pei Wern
    //get list of properties by the option choose by user
    public void displayFilteredProperties() throws IOException {
         if (cat.equals("Township"))
            propertyList.getFilteredByTamanProperties(option);
         
        else if (cat.equals("Facilities")) 
            propertyList.getFiltererdByFacilities(facilitiesChoice);

        else if (cat.equals("Property Status"))
            propertyList.viewPropertyByStatus(option);

        else if (cat.equals("Rental Price")) {
            if (option.equals(rentalPrice[1]))
                propertyList.viewPropertyListPrice(0, 999);
            else if (option.equals(rentalPrice[2]))
                propertyList.viewPropertyListPrice(1000, 2000);
            else if (option.equals(rentalPrice[3]))
                propertyList.viewPropertyListPrice(2001, 3000);
            else if (option.equals(rentalPrice[4]))
                propertyList.viewPropertyListPrice(3001, 4000);
            else if (option.equals(rentalPrice[5]))
                propertyList.viewPropertyListPrice(4001, 5000);
            else 
                propertyList.viewPropertyListPrice(5001, -1);
        
        }
        else if(cat.equals("Property Type"))
            propertyList.filterByPropertyType(option);
            
        else if(cat.equals("Owner"))
            propertyList.filterByOwner(option);

        else if(cat.equals("Agent"))
            propertyList.filterByAgent(option);

        else if(cat.equals("Rental Rate")) {
            if (option.equals(rentalRate[1]))
                propertyList.filterByRentalRate(0.0, 0.9);
            else if (option.equals(rentalRate[2]))
                propertyList.filterByRentalRate(1.0, 2.0);
            else if (option.equals(rentalRate[3]))
                propertyList.filterByRentalRate(2.1, 3.0);
            else if (option.equals(rentalRate[4]))
                propertyList.filterByRentalRate(3.1, 4.0);
            else if (option.equals(rentalRate[5]))
                propertyList.filterByRentalRate(4.1, 5.0);
            else
                propertyList.filterByRentalRate(5.1, -1.0);
        }
    }

    //Chin Pei Wern
    //get the action performed in the jcombo box for displaying type for list of properties
    public void actionPerformed (ActionEvent e) {
        String item = (String) comboBox.getSelectedItem();
        Object obj = subcategory.get(item);
        
        if (item.equals("All property") || item.equals("Select an option")) {
            subCB.setVisible(false);
            for(int i=0;i<lengthNum;i++)
                cb[i].setVisible(false);
        }
        else if (obj != null) {
            subCB.setVisible(true);
            subCB.setModel( new DefaultComboBoxModel((String[]) obj));
            if (filterBtn.getModel().isPressed()) {
                option = String.valueOf(subCB.getSelectedItem());
                cat = item;
            }
           
           for(int i=0;i<lengthNum;i++){
              cb[i].setVisible(false);
          }
        }

        else {
            for(int i=0;i<lengthNum;i++)
                cb[i].setVisible(true);
            
            subCB.setVisible(false);
        }
    }

    // Wong Yi Teng
    // Check whether the CheckBoxes is selected or not
    public void itemStateChanged(ItemEvent e) {    
        facilitiesChoice = new ArrayList<>();
        for(int i=0;i<lengthNum;i++){
            if(cb[i].isSelected())
                facilitiesChoice.add(cb[i].getText());
        }  
    } 

    //Chin Pei Wern
    //to save the property user want to save into a txt file
    private void saveIntoTxt(String filename, Property property) throws IOException{
        File txtfile = new File(filename +".txt");
        if (txtfile.createNewFile()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Property ID: " + property.propertyID + "\n");
            sb.append("Advertiser Name: " + property.advertiserName + "\n");
            sb.append("Advertiser Contact Number: " + property.advertiserContact + "\n");
            sb.append("Address: " + property.address + "\n");
            sb.append("Township: " + property.township + "\n");
            sb.append("Rental Price: RM" + property.rentalPrice + "0\n");
            sb.append("Rental Rate: RM" + property.rentalRate + " per sq. ft\n");
            sb.append("Unit Size: " + property.unitSize + "\n");
            sb.append("Type: " + property.type + "\n");
            sb.append("Facilities: " + property.facilities + "\n");
            sb.append("Number Of Bathroom: " + property.bathroomNum + "\n");
            sb.append("Number Of Room: " + property.roomNum + "\n");
            sb.append("Status: " + property.status + "\n");
            Files.write(Paths.get(filename + ".txt") , sb.toString().getBytes());
        }

    }

    //Chin Pei Wern
    //to display each property on the user interface
    private void displayProperty (Property property) throws MalformedURLException{
        //ImageIcon propertyImage = new ImageIcon(property.getImage().getScaledInstance(300, 200, Image.SCALE_DEFAULT));
        URL url = new URL(property.image);
        ImageIcon propertyImage = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(500, 310, Image.SCALE_DEFAULT));
        JLabel propertyLabel = new JLabel();
        propertyLabel.setIcon(propertyImage);

        JLabel propertyDetails = new JLabel("<html> <p><h1> RM" + property.rentalPrice + "<h4> (RM " + property.rentalRate + " per sq. ft)" + 
        "<h3>" + property.township + "<br>" + property.address + 
        "<br> </h3> Type: "+ property.type +
        "<br> Facilities: "+ property.facilities + "<br> Room: " + property.roomNum + " | Bathroom: " + property.bathroomNum +  "  |  Built-up: " + property.unitSize + " sq. ft"+ 
        "<br> Status: " + property.status + "<br>" +"<br> </h3> Contact: " + property.advertiserContact + " (" + property.advertiserName + ")" +
        "</html>");
        
        JButton downloadBtn = new JButton("Save");
        downloadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (admin != null) {
                try {
                    admin.saveProperty(property);
                    switcher.setAdmin(admin);
                    JOptionPane.showMessageDialog(null, "Property Saved Successfully!");
                }
                catch (IOException ex) {
                    System.out.println(ex);
                }
            }
            else {
                JPanel panel = new JPanel();
                panel.add(new JLabel("Filename"));
                JTextField filename = new JTextField(20);
                panel.add(filename);
                int result = JOptionPane.showConfirmDialog(null,panel,"Enter Text File",JOptionPane.OK_CANCEL_OPTION);
                if (result  == JOptionPane.OK_OPTION) {
                    try {
                    saveIntoTxt(filename.getText(), property);
                    }
                    catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
                
            }  
            }
        });
      
        JPanel details = new JPanel();
        details.setLayout(null);
        details.add(propertyDetails);
        propertyDetails.setBounds(0,-20, 550,330);
        downloadBtn.setBounds(20,280,70,20);
        details.add(downloadBtn);

        gridPanel.add(propertyLabel);
        gridPanel.add(details);
    }
}
