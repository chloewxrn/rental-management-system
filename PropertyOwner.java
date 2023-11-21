import java.io.*;

//Ong Jia Xuan
//PropertyOwner class is a subclass of Advertiser
public class PropertyOwner extends Advertiser{


    public PropertyOwner(){}
    
    public PropertyOwner(String ID, String username, String password, String name, String icNum, String gender, String email, String contactNumber ){
       super(ID, username, password, name, icNum, gender, email, contactNumber);

    }

    //Ong Jia Xuan
    //To write the owner registration details 
    public String toOwnerCSVString(){
        return ID + "," + username + ',' + password + ',' + name + ',' + icNum + "," + gender + ',' + email + ',' + contactNumber ;
    }

    //Ong Jia Xuan
    //To save the owner registration details to the Advertiser Registration csv 
    public void saveOwnerReg (String ID, String username, String password, String name, String icNum, String gender, String email, String contactNum) throws IOException{
        StringBuilder sb = new StringBuilder();
        PropertyOwner pOwner = new PropertyOwner(ID, username, password, name, icNum, gender, email, contactNum);
        sb.append(pOwner.toOwnerCSVString());
        String file = "AdvertiserReg.csv";
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString() + "\n");
        bw.close();
       
    }
}
