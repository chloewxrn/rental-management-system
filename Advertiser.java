// Ong Jia Xuan
//Advertiser class is a subclass of User class and also the parent class of Property Owner and Property Agent class
public class Advertiser extends User{ 
    protected String icNum;
    protected String gender;
    protected String email;
    protected String contactNumber;

   public Advertiser(){}

    public Advertiser(String ID, String username, String password, String name, String icNum,String gender, String email, String contactNumber){
            super(ID,username, password, name);
            this.icNum = icNum;
            this.gender = gender;
            this.email = email;
            this.contactNumber = contactNumber; 
    }

    //Ong Jia Xuan
    //to get the name of the advertiser
    public String getName(){
        return name;
    }

     //Ong Jia Xuan
    //to get the contact number of the advertiser
    public String getContactNum(){
        return contactNumber;
    }

     //Ong Jia Xuan
    //to get the ID of the advertiser
    public String getID(){
        return ID;
    }

}