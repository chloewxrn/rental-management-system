//Chin Pei Wern
//this interface allows the program to switch between different jpanels and get the data of user that
//logged into the system
public interface Switchable {
   void show (String card);
   void setTenant (PotentialTenant tenant);
   void setAgent (PropertyAgent agent);
   void setOwner (PropertyOwner owner);
   void setAdmin (Admin admin);

}