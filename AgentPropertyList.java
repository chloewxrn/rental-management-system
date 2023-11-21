import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileReader;
import java.io.BufferedReader;

// Observer DP at addBtn, EditButtonEditor
// Ng Yoong Kee
// A panel for the Property Agent to view, add, edit and deactivate their properties.
public class AgentPropertyList extends JPanel{

    String[] columnName = new String[] {"Property ID", "Agent ID", "Address", "Type", "Township", "Number of Room", "Number of Bathroom", 
            "Unit Size (sq. ft)", "Facilities", "Rental Price (RM)", "Image(URL)", "Status", "Action"};
    DefaultTableModel tableModel;
    JTable table = new JTable();
    JScrollPane scrollPane;
    PropertyAgent agent;
    private Switchable switcher;
    JButton addBtn;
    String loginAgentID, loginAgentName, loginAgentContact, selectStatus_Edit, selectStatus_Add;
    JTextField[] propertyFormTxField, editPanelTxField;
    JPanel editPanel, addPanel;
    JLabel[] editPanelLabel;
    JCheckBox statusCheckBox_Edit, statusCheckBox_Add;
    String[] type = {"Select Property Type", "Apartment/Flat", "Condominium", "Terrace/Link/Townhouse", "Semi-D", "Bungalow"};
    JComboBox<String> comboType_Edit, comboType_Add;
    double rentalRate;


    //Ong Jia Xuan & Ng Yoong Kee
    //To get the ID, name and contact number of the property agent after login
    public void receive(PropertyAgent agent){
        this.agent = agent;
        loginAgentID = agent.ID;
        loginAgentName = agent.name;
        loginAgentContact = agent.contactNumber;
        searchGetAgentProperty();
    }


    // Ng Yoong Kee
    // display the table of agent's property and an Add button
    public AgentPropertyList(Switchable switcher) {
        this.switcher = switcher;

        table.getTableHeader().setReorderingAllowed(false);                  // make the table non-draggable
        table.setPreferredScrollableViewportSize(table.getPreferredSize());  // set table's viewport size
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);             // fit the table width
        table.setFillsViewportHeight(false);                                 // no fit the table height
        table.setRowSelectionAllowed(true);

        scrollPane = new JScrollPane(table);

        addBtn = new JButton("Add");
        addBtn.addActionListener(new AddBtnActionListener());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        topPanel.add(addBtn);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }


    // Ng Yoong Kee
    // search and get the agent's properties from Property.txt based on loginAgentID
    private void searchGetAgentProperty() {
        BufferedReader bufferedReader;
        String row;
        tableModel = new DefaultTableModel(columnName, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make the table non-editable except the last column
                if(column == 12) { return true; }
                return false;
            }
        };

        try {
            bufferedReader = new BufferedReader(new FileReader("Property.txt"));

            while((row = bufferedReader.readLine()) != null) {
                String[] items = row.split(";");
                String agentIDFromFile = items[1];

                if(agentIDFromFile.charAt(0) == 'C' && agentIDFromFile.equals(loginAgentID)) {
                    String[] agentRowData = new String[] {items[0], items[1], items[4], items[5], items[6], items[7], items[8], items[9],  items[10], items[11], items[14], items[12],  "null"};
                    tableModel.addRow(agentRowData);
                }
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        tableModel.removeRow(0);
        table.setModel(tableModel);

        // Edit Button Renderer and Editor
        table.getColumnModel().getColumn(12).setCellRenderer(new EditButtonRenderer());
        table.getColumnModel().getColumn(12).setCellEditor(new EditButtonEditor());
    }


    // Ng Yoong Kee
    // create an editPanel for the Agent to edit the properties
    private void createEditPanel() {

        editPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int numOfColumn = 12; // minus "Action" column
        editPanelLabel = new JLabel[numOfColumn];       
        editPanelTxField = new JTextField[numOfColumn]; 

        statusCheckBox_Edit = new JCheckBox("Active");
        statusCheckBox_Edit.setSelected(true);
        selectStatus_Edit = "Active";

        comboType_Edit = new JComboBox<>(type);

        // use GridBagConstraints to determine the layout
        gbc.ipady = 10;
        gbc.ipadx = 45;
        gbc.insets = new Insets(10, 15, 0, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // define editPanelLabel[] and editPanelTxField[].Then, add Label, CheckBox, TxField and ComboBox into editPanel
        for(int count = 0; count < numOfColumn; count++) {
            editPanelLabel[count] = new JLabel(table.getColumnName(count));
            editPanelTxField[count] = new JTextField(30);

            gbc.gridy = count;
            gbc.gridx = 0;
            editPanel.add(editPanelLabel[count], gbc);

            gbc.gridx = 1;
            if(table.getColumnName(count) == "Status") { // count==11
                editPanel.add(statusCheckBox_Edit, gbc);
            }else if(table.getColumnName(count) == "Type") { // count==3
                editPanel.add(comboType_Edit, gbc);
            }else {
                editPanel.add(editPanelTxField[count], gbc);//count==0 1 2 4 5 6 7 8 9 10
            }

            if(table.getColumnName(count) == "Property ID" || table.getColumnName(count) == "Agent ID") { // count==0 or 1
                editPanelTxField[count].setEditable(false);
            }
        }
    }



    // Ng Yoong Kee
    // Create an add-new property form and add it into addPanel
    private void newAgentPropertyForm() {
        repaint();
        revalidate();
        //setLayout(new GridBagLayout());
        addPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        // create textfields for Property Form
        int txtFieldLength = 30;
        int numOfTxField = 12;
        propertyFormTxField = new JTextField[numOfTxField];
        for(int count=0; count<numOfTxField; count++) {
            propertyFormTxField[count] = new JTextField(txtFieldLength);
        }


        // check box - select to active or not
        statusCheckBox_Add = new JCheckBox("Active");
        statusCheckBox_Add.setSelected(true);

        // combo box for type
        comboType_Add = new JComboBox<>(type);


        // use local date time for Property ID
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyHHmmss");
        String strDateTime = dateTimeFormatter.format(localDateTime);


        // Property ID and Agent ID are non-editable
        propertyFormTxField[0].setText("P" + strDateTime);
        propertyFormTxField[0].setEditable(false);

        propertyFormTxField[1].setText(loginAgentID);
        propertyFormTxField[1].setEditable(false);


        // use GridBagConstraints to add component into addPanel
        gbc.ipady = 10;
        gbc.ipadx = 45;
        gbc.insets = new Insets(10, 15, 0, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int locationY = 0;
        gbc.gridx = 0;  // coordinate x for component
        gbc.gridy = locationY;  // coordinate y for component
        addPanel.add(new JLabel("Property ID: "), gbc);

        gbc.gridx = 1;
        int count = 0;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Agent ID: "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Address: "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Type: "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(comboType_Add, gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Township: "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Number of Room: "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Number of Bathroom: "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Unit Size (sq. ft): "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Facilities: "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Rental Price (RM): "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Image (URL) : "), gbc);

        gbc.gridx = 1;
        count++;
        addPanel.add(propertyFormTxField[count], gbc);

        gbc.gridx = 0;
        locationY++;
        gbc.gridy = locationY;
        addPanel.add(new JLabel("Status: "), gbc);

        gbc.gridx = 1;
        addPanel.add(statusCheckBox_Add, gbc);
    }


    // Ng Yoong Kee
    // When click Add button, show an OptionPane with a New Property Form. Then, save the new property into Property.txt
    class AddBtnActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            newAgentPropertyForm();
            String[] options = {"Save", "Cancel"};

            int save_cancel_option = JOptionPane.showOptionDialog(AgentPropertyList.this, addPanel, "Add New Property", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if(save_cancel_option == JOptionPane.YES_OPTION) {
                selectStatus_Add = "Active";
                if(statusCheckBox_Add.isSelected() == false) {
                    selectStatus_Add = "Inactive";
                }

                double rentalPrice = Double.parseDouble(propertyFormTxField[9].getText());
                double unitSize = Double.parseDouble(propertyFormTxField[7].getText());
                double tempRentalRate = rentalPrice / unitSize;
                rentalRate = (double) (Math.round(tempRentalRate * 10.0) / 10.0);
                
                Property property = new Property(propertyFormTxField[0].getText(), loginAgentID, loginAgentName, loginAgentContact, 
                                        propertyFormTxField[2].getText(), comboType_Add.getSelectedItem().toString(), 
                                        propertyFormTxField[4].getText(), Integer.parseInt(propertyFormTxField[5].getText()), 
                                        Integer.parseInt(propertyFormTxField[6].getText()), unitSize, 
                                        propertyFormTxField[8].getText().replaceAll(" ", ""), 
                                        rentalPrice, selectStatus_Add, rentalRate, propertyFormTxField[10].getText());
                

                String toPropertyCSV = property.toPropertyCSVString();
                property.saveToFileForAgent(toPropertyCSV);

                searchGetAgentProperty();
                
                JOptionPane.showMessageDialog(null, "Added SuccessFul.");
            }
        }
    }



    // Ng Yoong Kee
    // add "Edit" button at the last column of the table
    class EditButtonRenderer extends JButton implements TableCellRenderer{
        public EditButtonRenderer() {
            setText("Edit");
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected == true)
                setBackground(table.getSelectionBackground());
            else
                setBackground(table.getBackground());
            return this;
        }
    }



    // Ng Yoong Kee
    // Popup the editPanel when clicking the "Edit" button
    class EditButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
        JButton button;

        public EditButtonEditor() {
            button = new JButton("Edit");
            button.addActionListener(this);
            button.setBorderPainted(false);
        }

        public void actionPerformed(ActionEvent evt) {

            createEditPanel();

            String[] properties = new String[11];
            int row = table.getSelectedRow();
            for (int i = 0; i < properties.length; i++) {
                // Status,i==11, Type,i==3, Other,i==0 1 2 4 5 6 7 8 9 10
                properties[i] = table.getModel().getValueAt(row, i).toString();
                
                if(i == 3) {
                    comboType_Edit.setSelectedItem(properties[i]);
                }else{
                    editPanelTxField[i].setText(properties[i]);
                }
            }

            String[] option = {"Save", "Cancel"};
            int save_option = JOptionPane.showOptionDialog(AgentPropertyList.this, editPanel, "Edit Property", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0]);

            Property editProperty = new Property();
            if(save_option == JOptionPane.YES_OPTION) {

                if(statusCheckBox_Edit.isSelected() == false) {
                    selectStatus_Edit = "Inactive";
                }
                editPanelTxField[3].setText(comboType_Edit.getSelectedItem().toString());

                try { 
                    editProperty.saveAfterEdit(editPanelTxField[0].getText(), editPanelTxField, selectStatus_Edit, rentalRate); 
                }catch(Exception ex) { ex.printStackTrace(); }

                searchGetAgentProperty();
            }
            fireEditingStopped(); //Make the renderer reappear.
            
        }

        public Object getCellEditorValue() {
            return table.getSelectedRow();
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected == true)
                setBackground(table.getSelectionBackground());
            else
                setBackground(table.getBackground());
            return button;
        }
    }



}
