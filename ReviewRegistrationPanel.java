import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.table.*;
import javax.swing.event.CellEditorListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

// Observer DP at rejectBtn, approveBtn 
//Chin Pei Wern
//panel for admin to approve and reject property owners and agents registration
public class ReviewRegistrationPanel extends JPanel {
    Admin admin = new Admin();
    JButton rejectBtn = new JButton("Reject");
    JButton approveBtn = new JButton("Approve");
    JButton viewDetailsBtn = new JButton("View Details");
    JTable table;
    DefaultTableModel model;
    List<PropertyOwner> allOwner = new ArrayList<>();
    List<PropertyAgent> allAgent = new ArrayList<>();
    List<String[]> ownerRegData = new ArrayList<>();
    List<String[]> agentRegData = new ArrayList<>();
    String[] cols = new String[] {"User", "Username", "Name", "E-mail", "Action"};
    
    //Chin Pei Wern
    //to get which admin object is accessing the system
    public void receive (Admin admin) {
        this.admin = admin;
    }

    //Chin Pei Wern
    //constructor (display components)
    ReviewRegistrationPanel() {
        try {
        List<String> linesO = Files.readAllLines(Paths.get("AdvertiserReg.csv"));
            for (int i = 0; i < linesO.size(); i++) {
                String[] items = linesO.get(i).split(",");
                if (items[0].equals("O")) {
                    allOwner.add (new PropertyOwner(items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7]));
                    String[] colData = new String[] {"Property Owner", items[1], items[3], items[6], null};
                    ownerRegData.add(colData);
                }
                else {
                    allAgent.add (new PropertyAgent(items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7], items[8], items[9], items[10]));
                    String[] colData = new String[] {"Property Agent", items[1], items[3], items[6], null};
                    ownerRegData.add(colData);
                }
            }
        }
        catch (IOException ex) {
            System.out.println (ex.toString());
        }
        model = new DefaultTableModel(ownerRegData.toArray(new Object[][] {}),cols);
        table = new JTable(model);
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(table));
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(200);  
        columnModel.getColumn(0).setPreferredWidth(100);  
        columnModel.getColumn(1).setPreferredWidth(180);  
        columnModel.getColumn(4).setPreferredWidth(200);
        setLayout(new BorderLayout());
        table.setRowHeight(40);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JLabel title = new JLabel("Review User Registrations");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        add(title, BorderLayout.NORTH);
        add(table, BorderLayout.SOUTH);
        add(new JScrollPane(table));
    }
    
    //Chin Pei Wern
    //to display buttons 'reject' and 'approve' on the jtable in every row
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            JButton rejectBtn = new JButton ("Reject");
            JButton approveBtn = new JButton("Approve");
            add(approveBtn);
            add(rejectBtn);
        }

        //Chin Pei Wern
        //to change the background of the selected cell to the same background as the jtable
        @Override
        public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            if (isSelected)
                this.setBackground(table.getSelectionBackground());
            else 
                this.setBackground(table.getBackground());
            return this;
        }
    }

    //Chin Pei Wern
    //to make the 'approve' and 'reject' buttons in jtable to be able to perform actions
    class ButtonEditor extends JPanel implements TableCellEditor {
        public ButtonEditor (JTable table) {
            add(approveBtn);
            add(rejectBtn);
            rejectBtn.addActionListener(new ActionListener() {

                //will delete owner/agent's registration if clicked
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelCellEditing();
                    int confirm = JOptionPane.showConfirmDialog(null, "Confirm to Reject?");
                    if (confirm == 0) {
                    int row = table.getSelectedRow();
                    try {
                        admin.deleteRegistration(allOwner, allAgent, table.getValueAt(row, 1).toString(), table.getValueAt(row, 0).toString());
                    }
                    catch (IOException ex) {
                        System.out.println (ex.toString());
                    }   
                    model.removeRow(row);
                    table.changeSelection(row, 3, false, false);  
                }
                }
            });

            approveBtn.addActionListener(new ActionListener() {
                //will approve and add owner/agent's registration details into the system if clicked
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelCellEditing();
                    int confirm = JOptionPane.showConfirmDialog(null, "Confirm to Approve?");
                    if (confirm == 0) {
                    int row = table.getSelectedRow();
                    try {
                        if (table.getValueAt(row, 0).equals("Property Owner")) 
                            admin.addNewOwner(allOwner, (String) table.getValueAt(row, 1));
                        else
                            admin.addNewAgent(allAgent, (String) table.getValueAt(row, 1));
                    }
                    catch (IOException ex) {
                        System.out.println(ex.toString());
                    }
                    model.removeRow(row);
                    //Clear the previous selection and ensure the new cell is selected.
                    table.changeSelection(row, 3, false, false);
                    
                    StringBuilder sbDel = new StringBuilder();
                    for (int k = 0; k < allOwner.size(); k++) 
                        sbDel.append(allOwner.get(k).toOwnerCSVString() + "\n");
                    for (int k = 0; k < allAgent.size(); k++)
                        sbDel.append(allAgent.get(k).toAgentCSVString() + "\n");
                    try {
                        Files.write(Paths.get("AdvertiserReg.csv"), sbDel.toString().getBytes());
                    }
                    catch (IOException ex) {
                        System.out.println();
                    }
                }
                }
            });
        }
        
        //set the cell of selected button the same background as the jtable
        @Override 
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) 
                this.setBackground(table.getSelectionBackground());
            else 
                this.setBackground(table.getBackground());
            return this;
        }
        @Override 
        public Object getCellEditorValue() {
            return "";
        }
        @Override 
        public boolean isCellEditable(java.util.EventObject e) {
            return true;
        }
        @Override
        public void addCellEditorListener(CellEditorListener l) {}
        @Override
        public void cancelCellEditing() {}
        @Override
        public void removeCellEditorListener (CellEditorListener l) {}
        @Override
        public boolean shouldSelectCell(EventObject evt) {return true;}
        @Override
        public boolean stopCellEditing() {return true;}  
    }
}

