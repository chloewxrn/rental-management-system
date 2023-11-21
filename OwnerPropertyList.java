import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Observer DP at addButton, comboBox, editComboBox, table
//Ong Jia Xuan
//Panel for the property owner to view, add, edit and deactivate his/her properties
public class OwnerPropertyList extends JPanel implements ActionListener{
    
    JTable table;
    JButton addButton;
    JPanel editPanel, topPanel, addPanel;
    JLabel label[], addLabel[];
    JTextField textField[], addText[];
    JRadioButton activeButton, addActiveButton;
    PropertyTable pTable = new PropertyTable();
    PropertyOwner owner;
    String ownerID;
    String ownerName;
    String ownerContact;
    JComboBox<String> comboBox;
    JComboBox<String> editComboBox;
    String[] category = {"Select Property Type", "Apartment/Flat", "Condominium", "Terrace/Link/Townhouse", "Semi-D", "Bungalow"};

    //Ong Jia Xuan
    //To get the ID, name and contact number of the property owner after login
    public void receive (PropertyOwner owner) {
        this.owner = owner;
        ownerID = new String(owner.getID());
        ownerName = new String(owner.getName());
        ownerContact = new String(owner.getContactNum());
        CSV csv = new CSV();
        ArrayList<String[]> ppt = csv.ReadCSVfile();
        pTable.addRowData(ppt);
    }

    public OwnerPropertyList(){
        super(new BorderLayout());

        table = new JTable(); 
        this.table.setPreferredScrollableViewportSize(new Dimension(1200, 500));
        this.table.setFillsViewportHeight(false);

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        topPanel =new JPanel(new FlowLayout(FlowLayout.LEADING));
        topPanel.add(addButton);
        add(topPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        table.addMouseListener(new Mouse());
        
        activeButton = new JRadioButton("Active");
        activeButton.setSize(75, 20);
        activeButton.setSelected(true);
        
        this.table.setModel(pTable);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        int column = this.table.getColumnCount() - 1; //minus 1 for the edit column

        addActiveButton = new JRadioButton("Active");
        activeButton.setSize(75, 20);
        activeButton.setSelected(true);

        editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(column,3));

        addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(column,3));
        
        label = new JLabel[column];
        textField = new JTextField[column];

        addLabel = new JLabel[column];
        addText = new JTextField[column];

        comboBox = new JComboBox<>(category);
        comboBox.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) comboBox.getSelectedItem(); 
            }

        });
        

        for (int k = 0; k < column; k++){
            addLabel[k] = new JLabel(table.getColumnName(k));
            addText[k] = new JTextField(20);
           
            if(k==0 || k==1 || k==2 || k==4 || k==5 || k==6 || k==7 || k==8 || k==9 ){
                addPanel.add(addLabel[k]);
                addPanel.add(addText[k]);
            }

            if(k==3){ //type
                addPanel.add(addLabel[k]);
                addPanel.add(comboBox);
            }


            if(k == 11){ //status
                addLabel[k].setText("Status");
                addPanel.add(addLabel[k]);
                addPanel.add(addActiveButton);
                addActiveButton.setSelected(true);
            }

            if(k == 10){
                addLabel[k].setText("Image (URL)");
                addPanel.add(addLabel[k]);
                addPanel.add(addText[k]);
            }

            if(table.getColumnName(k) == "Property ID" ||table.getColumnName(k) == "Owner ID"){
                addText[k].setEditable(false); 
            }
            
        }
        editComboBox = new JComboBox<>(category);
        editComboBox.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) comboBox.getSelectedItem(); 
            }

        });
        for (int i = 0; i < column; i++){
            label[i] = new JLabel(table.getColumnName(i));
            textField[i] = new JTextField(20);
            

            editPanel.add(label[i]);
            if(i==0 || i==1 || i==2 || i==4 || i==5 || i==6 || i==7 || i==8 || i==9){
                editPanel.add(label[i]);
                editPanel.add(textField[i]);
            }

            if(i==3){ //type
                editPanel.add(label[i]);
                editPanel.add(editComboBox);
            }


            if(i == 11){ //status
                label[i].setText("Status");
                editPanel.add(label[i]);
                editPanel.add(activeButton);
            }

            if(i == 10){
                label[i].setText("Image (URL)");
                editPanel.add(label[i]);
                editPanel.add(textField[i]);
            }


            if(table.getColumnName(i) == "Property ID" ||table.getColumnName(i) == "Owner ID"){
               textField[i].setEditable(false);}
           
        }

        this.table.setFocusable(true);
        this.table.setRowSelectionAllowed(true);
        this.table.getColumnModel().getColumn(12).setCellRenderer(new LabelCellRenderer());

    }

    //Ong Jia Xuan
    //To read the Property.txt and insert the data into the JTable
    class CSV {
        ArrayList<String[]> Property = new ArrayList<String[]>();
        String[] row;
       
        public ArrayList<String[]> ReadCSVfile() {
        ArrayList<Property> property = new ArrayList<Property>();
        ArrayList<String[]> pptArray = new ArrayList<String[]>();;
        String[] ppt;
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("Property.txt"));
            for (int i = 0; i < lines.size(); i++){
            String[] items = lines.get(i).split(";");
            property.add (new Property(items[0], items[1], items[2], items[3], items[4], items[5], items[6], Integer.valueOf(items[7]), Integer.valueOf(items[8]), Double.parseDouble(items[9]), items[10], Double.parseDouble(items[11]), items[12], Double.parseDouble(items[13]), items[14]));
            if (items[1].equals(ownerID)){
            ppt = new String[]{items[0], items[1], items[4], items[5], items[6], items[7], items[8], items[9],  items[10], items[11], items[14], items[12],  "Edit"};
            pptArray.add(ppt);
            }
           
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return pptArray;
        }
    }

    

    //Ong Jia Xuan
    //To insert the column name and row data for the JTable
    class PropertyTable extends AbstractTableModel{

        String[] columns = new String [] { "Property ID", "Owner ID","Address", "Type", "Township", "Number of Room", "Number of Bathroom", "Unit Size (sq. ft)", "Facilities", "Rental Price (RM)", "Image","Status", "Action"};
        ArrayList<String[]> rowData = new ArrayList<String[]>();

        public void addRowData(ArrayList<String[]> rowData) {
            this.rowData = rowData;
            this.fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return rowData.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return rowData.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column){
            return columns[column];
        }

    }

    //Ong Jia Xuan
    //To pop up a panel for the property owner to edit his/her properties if the speficic "Edit" row is clicked
    class Mouse extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent evt){
            int x = evt.getX();
            int y = evt.getY();
            int row = table.rowAtPoint(new Point(x,y));
            int column = table.columnAtPoint(new Point(x,y));
            if (column == 12){
                String pptArray[] = new String[table.getColumnCount() - 1];
                for (int i = 0 ; i < pptArray.length ; i++){
                    pptArray[i] = (String)table.getValueAt(row,i);
                    textField[i].setText(pptArray[i]);
                    editComboBox.setSelectedItem(pptArray[3]);
                }

                UIManager.put("OptionPane.minimumSize",new Dimension(400,450));
                int result = JOptionPane.showConfirmDialog(OwnerPropertyList.this,editPanel,"Edit Property",JOptionPane.OK_CANCEL_OPTION);
                Property editProperty = new Property();
                 try {
           
                        if(result == JOptionPane.OK_OPTION){ 
                            double r = Double.parseDouble(textField[9].getText()) / Double.parseDouble(textField[7].getText());
                            double rate = (double) (Math.round(r*10.0)/10.0); 
                            String type = (String) editComboBox.getSelectedItem();
                            textField[3].setText(type);

                        if(activeButton.isSelected()){
                            editProperty.saveAfterEdit(textField[0].getText(),textField,"Active", rate);
                        }

                        else{
                           editProperty.saveAfterEdit(textField[0].getText(),textField,"Inactive", rate);
                        }

                        CSV newcsv = new CSV();
                        table.setModel(pTable);
                        ArrayList<String[]> ppt = newcsv.ReadCSVfile();
                        pTable.addRowData(ppt);
                    }
           
                } catch (IOException e) {
                   
                    e.printStackTrace();
                }
            }
             
            }
        }

    //Ong Jia Xuan
    //To design the "Edit" column in the JTable
    public class LabelCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,Object Value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table,Value,isSelected, hasFocus,row, column);
            String value = (String)Value;
            JLabel edit =(JLabel)component;
            edit.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            edit.setBackground(new Color(255,255,204));
            edit.setHorizontalTextPosition(SwingUtilities.CENTER);
            edit.setHorizontalAlignment(SwingUtilities.CENTER);
            edit.setText(value);
            return edit;
        }
    }

    //Ong Jia Xuan
    //To pop up a panel to let the property owner add a new property if the add button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {

        LocalDateTime lDateTime = LocalDateTime.now();
        DateTimeFormatter dtFm = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
        String strDateTime = dtFm.format(lDateTime);

        addText[0].setText("P" + strDateTime);
        addText[1].setText(ownerID);
        if (e.getSource() == addButton){
       
        Property addProperty = new Property();
        UIManager.put("OptionPane.minimumSize",new Dimension(400,500));
        int addResult = JOptionPane.showConfirmDialog(OwnerPropertyList.this,addPanel,"Add Property",JOptionPane.OK_CANCEL_OPTION);
        String type = (String) comboBox.getSelectedItem();

                try {
                    
                    if(addResult == JOptionPane.OK_OPTION){
                         double r = Double.parseDouble(addText[9].getText()) / Double.parseDouble(addText[7].getText());
                         double rate = (double) (Math.round(r*10.0)/10.0);
                    if(addActiveButton.isSelected()){

                        addProperty.savePropertyFile(addText[0].getText(), ownerID, ownerName, ownerContact,
                        addText[2].getText(), type, 
                        addText[4].getText(), Integer.parseInt(addText[5].getText()), 
                        Integer.parseInt(addText[6].getText()), 
                        Double.parseDouble(addText[7].getText()), addText[8].getText().replaceAll(" ", ""), 
                        Double.parseDouble(addText[9].getText()), "Active", 
                        rate, addText[10].getText());
                    }
                    
                    else{
                        
                        addProperty.savePropertyFile(addText[0].getText(), ownerID, ownerName, ownerContact,
                        addText[2].getText(), type, 
                        addText[4].getText(), Integer.parseInt(addText[5].getText()), 
                         Integer.parseInt(addText[6].getText()), 
                         Double.parseDouble(addText[7].getText()), addText[8].getText().replaceAll(" ", ""), 
                         Double.parseDouble(addText[9].getText()), "Inactive", 
                         rate, addText[10].getText());
                    }
                        
                    }

                    CSV newcsv = new CSV();
                    table.setModel(pTable);
                    ArrayList<String[]> ppt = newcsv.ReadCSVfile();
                    pTable.addRowData(ppt);
                    for (int m = 0; m < 12; m++){
                        addText[m].setText("");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
       }
    }
}


