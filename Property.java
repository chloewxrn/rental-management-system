import java.util.*;
import javax.swing.JTextField;
import java.io.*;
import java.nio.file.*;

public class Property {

    protected String propertyID;
    protected String advertiserID;
    protected String advertiserName;
    protected String advertiserContact;
    protected String address;
    protected String type;
    protected String status;
    protected String township;
    protected int    roomNum;
    protected int    bathroomNum;
    protected double unitSize;
    protected double rentalPrice;
    protected double rentalRate;
    protected String facilities;
    protected String image;

    // Constructor
    public Property() {}

    public Property(String propertyID, String advertiserID,String advertiserName, String advertiserContact, String address, String type, String township, int roomNum, int bathroomNum, double unitSize, String facilities, double rentalPrice , String status,  double rentalRate, String image) {
        this.propertyID = propertyID;
        this.advertiserID =advertiserID;
        this.advertiserName = advertiserName;
        this.advertiserContact = advertiserContact;
        this.address = address;
        this.type = type;
        this.township = township;
        this.roomNum = roomNum;
        this.bathroomNum = bathroomNum;
        this.unitSize = unitSize;
        this.facilities = facilities;
        this.rentalPrice = rentalPrice;
        this.status = status;
        this.rentalRate = rentalRate;
        this.image = image;
    }



    //Ong Jia Xuan
    //To write to the Property.txt
    protected String toPropertyCSVString (){
        return propertyID + ";" + advertiserID + ";" + advertiserName + ";" + advertiserContact + ";"+  address + ";" + type + ";" + township +  ";" + roomNum + ";" + bathroomNum +  ";" +  unitSize + ";" + facilities + ";" + rentalPrice + ";" + status + ";" + rentalRate +  ";" + image ;
    }


    //Ong Jia Xuan
    //To save the changes to the file after edit the property
    public void saveAfterEdit(String propertyID, JTextField[] txt, String status, double rate) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get("Property.txt"));
        ArrayList<Property> property = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(";");
            String pptID = items[0];

            if (propertyID.equals(pptID)){
                items[0] = txt[0].getText();
                items[1] = txt[1].getText();
                items[4] = txt[2].getText();
                items[5] = txt[3].getText();
                items[6] = txt[4].getText();
                items[7] = txt[5].getText();
                items[8] = txt[6].getText();
                items[9] = txt[7].getText();
                items[10] = txt[8].getText().replaceAll(" ", "");
                items[11] = txt[9].getText();
                items[12] = status;
                items[13] = Double.toString(rate);
                items[14] = txt[10].getText();
                
            }
            property.add (new Property(items[0], items[1], items[2], items[3], items[4], items[5], items[6], Integer.parseInt(items[7]), Integer.parseInt(items[8]), Double.parseDouble(items[9]), items[10], Double.parseDouble(items[11]), items[12], Double.parseDouble(items[13]), items[14]));   
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < property.size(); i++){
           sb.append (property.get(i).toPropertyCSVString() + "\n");
       }
        Files.write(Paths.get("Property.txt") , sb.toString().getBytes());
    }

    //Ong Jia Xuan
    //To save the new added property to the file
    protected void savePropertyFile (String propertyID, String advertiserID, String advertiserName, String advertiserContact,String address, String type, String township, int roomNum, int bathroomNum, double unitSize, String facilities,  double rentalPrice, String status, double rentalRate, String image) throws IOException{
            StringBuilder sb = new StringBuilder();
            Property owner = new Property(propertyID, advertiserID, advertiserName, advertiserContact,address, type, township, roomNum,  bathroomNum,  unitSize,  facilities, rentalPrice, status, rentalRate ,image);
            sb.append(owner.toPropertyCSVString());
            String file = "Property.txt ";
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString() + "\n");
            bw.close();
        
        }


    // Ng Yoong Kee
    // Save the new property into Property.txt using BufferedWriter and FileWriter
    public void saveToFileForAgent(String toPropertyCSVString) {
        try {
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter("Property.txt", true));

            bufferWriter.write(toPropertyCSVString + "\n");

            bufferWriter.close();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }


  


}
