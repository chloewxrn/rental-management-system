import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;


//Chin Pei Wern
//to create list of properties according to the user
public class PropertyList {
    protected ArrayList<Property> properties = new ArrayList<Property>();
    protected ArrayList<Property> ppt = new ArrayList<>();

    public PropertyList() {}

    //Chin Pei Wern
    //to get properties with certain range in rental rate
    public void viewPropertyListPrice(int minPrice, int maxPrice) {
        ppt.removeAll(ppt);
        if (maxPrice == -1) {
            for (int i = 0; i < properties.size(); i++) {
                if (properties.get(i).rentalPrice > minPrice)
                    ppt.add(properties.get(i));
            }
        }
        else { 
            for (int i = 0; i < properties.size(); i++) {
                if ((properties.get(i).rentalPrice > minPrice) && (properties.get(i).rentalPrice < maxPrice))
                    ppt.add(properties.get(i));
            }
        }
    }

    //Chin Pei Wern
    //to sort the list of properties displayed according to rental rate
    public ArrayList<Property> viewSortedAscPropertyList(ArrayList<Property> plist) {
        Collections.sort(plist, new Comparator<Property>(){
            @Override
            public int compare(Property p1, Property p2) {
                return Double.compare(p1.rentalPrice, p2.rentalPrice);
            }
        });
        return plist;
    }

    //Chin Pei Wern
    //to sort the list of properties displayed according to rental rate in descending order
    public ArrayList<Property> viewSortedDescPropertyList(ArrayList<Property> plist) {
        Collections.sort(plist, new Comparator<Property>(){
            @Override
            public int compare(Property p1, Property p2) {
                return Double.compare(p2.rentalPrice, p1.rentalPrice);
            }
        });
        return plist;
    }

    //Ong Jia Xuan
    //to get properties with the status chosen
    public void viewPropertyByStatus(String s) throws IOException{
        ppt.removeAll(ppt);
        for (int i = 0; i < properties.size(); ++i) {
            if ((properties.get(i).status.equals(s))){
               ppt.add(properties.get(i)); 
            }
        }
    }

    //Chin Pei Wern
    //get all properties from property.txt
    public ArrayList<Property> getAllProperties() throws IOException{
        loadPropertyCSV();
        return properties;
    }


    //Wong Yi Teng
    //to get properties based on the chosen township
    public void getFilteredByTamanProperties(String tamanName) throws IOException {
        ppt.removeAll(ppt);
        for (int i = 0; i < properties.size(); i++) {
            
            String temptamanName = tamanName.toUpperCase();
            if ((properties.get(i).township.toUpperCase()).equals(temptamanName))
               ppt.add(properties.get(i));             
        }
    }


    //Wong Yi Teng
    //to get properties based on the chosen facilities 
    public void getFiltererdByFacilities(ArrayList<String> facilitiesSelected) throws IOException {
        ppt.removeAll(ppt);
        for(int i=0;i<properties.size();i++){

                List<String> faci = new ArrayList<>(); // for each property       
                String fa = String.join(",", properties.get(i).facilities);
                String[] elements = fa.split(",");

                faci.addAll(Arrays.asList(elements));
                faci.replaceAll(String::toUpperCase);
               
                 boolean result = faci.containsAll(facilitiesSelected);
                 if(result==true){
                     ppt.add(properties.get(i));
                 }
        }
    }

    //Chin Pei Wern
    //load all properties from property.txt into an arraylist
    private void loadPropertyCSV() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("Property.txt"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(";");
            properties.add(new Property(items[0], items[1], items[2], items[3], items[4], items[5], items[6], Integer.valueOf(items[7]), Integer.valueOf(items[8]), Double.parseDouble(items[9]), items[10], Double.parseDouble(items[11]), items[12], Double.parseDouble(items[13]), items[14]));
        }
    }


    // Ng Yoong Kee
    // Filter the properties by Type
    public void filterByPropertyType(String propertyTypeName) {
        ppt.removeAll(ppt);
        String selectedType = propertyTypeName.toUpperCase();

        for (int i = 0; i < properties.size(); i++) {
            String typeFromFile = properties.get(i).type.toUpperCase();

            if(selectedType.equals(typeFromFile)) {
                ppt.add(properties.get(i));
            }
            
        }
    }


    // Ng Yoong Kee
    // Filter the properties by Owner
    public void filterByOwner(String owner) {
        ppt.removeAll(ppt);
        String selectedOwner = owner.toUpperCase();

        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i).advertiserID.charAt(0) == 'O') {
                String ownerFromFile = properties.get(i).advertiserName.toUpperCase();
                if(selectedOwner.equals(ownerFromFile)) {
                    ppt.add(properties.get(i));
                }
            }
        }
    }


    // Ng Yoong Kee
    // Filter the properties by Agent
    public void filterByAgent(String agent) {
        ppt.removeAll(ppt);
        String selectedAgent = agent.toUpperCase();

        for(int i = 0; i < properties.size(); i++) {
            if(properties.get(i).advertiserID.charAt(0) == 'C') {
                String agentFromFile = properties.get(i).advertiserName.toUpperCase();
                if(selectedAgent.equals(agentFromFile)) {
                    ppt.add(properties.get(i));
                }
            }
        }
    }


    // Ng Yoong Kee
    // Filter the displayed properties by Rental Rate
    public void filterByRentalRate(double minRate, double maxRate) {
        ppt.removeAll(ppt);
        if (maxRate == -1.0) {
            for (int i = 0; i < properties.size(); i++) {
                if (properties.get(i).rentalRate > minRate) 
                    ppt.add(properties.get(i));
            }
        }else {
            for (int i = 0; i < properties.size(); i++) {
                if ( (properties.get(i).rentalRate > minRate) && (properties.get(i).rentalRate < maxRate) )
                    ppt.add(properties.get(i));
            }
        }
    }
}
