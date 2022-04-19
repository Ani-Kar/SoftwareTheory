package TCCS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Office {
    private String id;
    private String address;
    private String phoneNumber;
    private double rate;
    private ArrayList<Consignment> listConsignment;
    private Queue<Truck> listTruck;
    private long volumeStored;
    private long volumeDispatched;
    private double totalRevenue;
    private double avgWaitingTime;

    public Office(String id, String address, String phoneNumber, double rate){
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rate = rate;
        this.listConsignment = new ArrayList<>();
        this.listTruck = new LinkedList<>();
        this.volumeStored = 0;
        this.volumeDispatched = 0;
        this.totalRevenue = 0;
        this.avgWaitingTime = 0;
    }
    public String getId(){
        return id;
    }
    public void setId(String ID){
        id = ID;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String add){
        address = add;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public double getRate(){
        return rate;
    }
    public void updateRate(double price){
        rate = price;
    }
    public void addConsignment(Consignment pack){
        listConsignment.add(pack);
    }
    public int getNumConsignment(){
        return listConsignment.size();
    }
    public int getNumTruck(){
        return listTruck.size();
    }
    public void addTruck(Truck truck){
        listTruck.add(truck);
    }
    public Truck dispatchTruck() throws IllegalAccessException{
        if(!listTruck.isEmpty())
            return listTruck.remove();
        throw new IllegalAccessException();
    }
    public long getStoredVolume(){
        return volumeStored;
    }
    public void updateStoredVolume( long increment){
        volumeStored+=increment;
    }
    public long getDispatchedVolume(){
        return volumeDispatched;
    }
    public void updateDispatchedVolume( long increment){
        volumeStored+=increment;
    }
    public double getTotalRevenue() {
        return totalRevenue;
    }
    public void updateRevenue( double amount){
        totalRevenue+=amount;
    }
    public double getAvgWaitingTime() {
        return avgWaitingTime;
    }

}
