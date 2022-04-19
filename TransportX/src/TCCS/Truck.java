package TCCS;

public class Truck {
    private int truckNumber;
    private Office currentBranch;
    private int totalVolumeHandled;
    private String ArrivalTime;
    private String DispatchTime;
    private double AvdIdleTime;

    public int getTruckNumber() {
        return truckNumber;
    }
    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }
    public Office getCurrentBranch() {
        return currentBranch;
    }
    public void setCurrentBranch(Office currentBranch) {
        this.currentBranch = currentBranch;
    }
    public int getTotalVolumeHandled() {
        return totalVolumeHandled;
    }
    public void update_volume(int volume){
        this.totalVolumeHandled+=volume;
    }
    public String getArrivalTime() {
        return ArrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getDispatchTime() {
        return DispatchTime;}
    public void setDispatchTime(String dispatchTime) {
        DispatchTime = dispatchTime;
    }
    public double getAvdIdleTime() {
        return AvdIdleTime;
    }
}
