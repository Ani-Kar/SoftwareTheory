package TCCS;
import java.lang.*;
public class Consignment {
    private String ID;
    private int Volume;
    private Customer Sender;
    private Customer Receiver;
    private Office SrcOffice;
    private Office DestOffice;
    private Boolean DeliveryStatus;
    private Truck AssignedTruck;
    private double cost;
    private String ArrivalTime;
    private String DispatchTime;

    public String getID(){
        return ID;
    }
    public Customer getReceiver(){
        return Sender;
    }
    public Customer getSender(){
        return Receiver;
    }
    public Office getSrcOffice(){
        return SrcOffice;
    }
    public Office getDestOffice (){
        return DestOffice;
    }
    public Boolean getDeliveryStatus(){
        return DeliveryStatus;
    }
    public Truck getAssignedTruck(){
        return AssignedTruck;
    }
    public double getCost(){
        return cost;
    }
    public String getArrivalTime(){
        return ArrivalTime;
    }
    public String getDispatchTime(){
        return DispatchTime;
    }

    public int getVolume() {
        return Volume;
    }
    public void setVolume(int volume) {
        Volume = volume;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDeliveryStatus(Boolean deliveryStatus) {
        DeliveryStatus = deliveryStatus;
    }

    public void setDestOffice(Office destOffice) {
        DestOffice = destOffice;
    }

    public void setReceiver(Customer receiver) {
        Receiver = receiver;
    }

    public void setAssignedTruck(Truck assignedTruck) {
        AssignedTruck = assignedTruck;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDispatchTime(String dispatchTime) {
        DispatchTime = dispatchTime;
    }

    public void setSender(Customer sender) {
        Sender = sender;
    }

    public void setSrcOffice(Office srcOffice) {
        SrcOffice = srcOffice;
    }
    @Override
    public String toString(){
        String details = "ID: " + ID +
                        "Volume: " + getVolume();
        return  details;
    }


}
