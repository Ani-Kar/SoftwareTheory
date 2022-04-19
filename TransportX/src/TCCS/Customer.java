package TCCS;

import java.util.ArrayList;

public class Customer {
    private String id;
    private String name;
    private String address;
    private ArrayList<Consignment> listConsignment;
    
    public Customer(String id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
        this.listConsignment = new ArrayList<>();
    }

    public String getId(){
        return id;
    }
    public void setID(String ID) {
        this.id = ID;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public void addConsignment(Consignment pack){
        this.listConsignment.add(pack);
    }

    public void setId(String id) {
        this.id = id;
    }
    public String viewConsignmentDetails(String packID) {
        String packdetails = null;
        for (Consignment pack : this.listConsignment){
            if(pack.getID().equals(packID)) packdetails =  pack.toString();
        }
        if(packdetails == null) return "No such Consignment available\n";
        return packdetails;
    }
    
}
