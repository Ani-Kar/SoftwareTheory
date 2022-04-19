package TCCS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DAO {
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;
    public DAO() throws SQLException {
        con = DriverManager.getConnection(Credentials.url, Credentials.user, Credentials.password);
    }

    public int register_manager(String name,String DOB,String address,String email,String password) throws SQLException {

        Statement stmt1 = con.createStatement();
        ResultSet rs = stmt1.executeQuery("select count(*) from manager");

        rs.next();
        int count = rs.getInt(1);
        if(count > 0) return 0;

        stmt = con.prepareStatement("insert into manager(Id,Name,Address,EmailId,Password, DOB) values(?,?,?,?,?,?)");

        stmt.setString(1, String.valueOf(count+1));
        stmt.setString(2, name);
        stmt.setString(3, address);
        stmt.setString(4, email);
        stmt.setString(5, password);
        stmt.setDate(6, Date.valueOf(DOB));

        stmt.executeUpdate();

        return count+1;
    }

    public boolean manager_signin(String username, String password) throws SQLException {
        stmt = con.prepareStatement("select * from manager where Id = '"+username+"' AND Password = '"+password+"'");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    public int register_employee(String name,String DOB,String address,String email,String officeId, String password) throws SQLException {

        Statement stmt1 = con.createStatement();

        ResultSet rs = stmt1.executeQuery("select count(*) from employee");

        rs.next();
        int count = rs.getInt(1);

        stmt = con.prepareStatement("insert into employee(Id,Name,Address,Email,OfficeId,Password, DOB) values(?,?,?,?,?,?,?)");
        stmt.setString(1, String.valueOf(count+1));
        stmt.setString(2, name);
        stmt.setString(3, address);
        stmt.setString(4, email);
        stmt.setString(5, officeId);
        stmt.setString(6, password);
        stmt.setDate(7, Date.valueOf(DOB));
        stmt.executeUpdate();

        return count+1;
    }

    public boolean employee_signin(String username, String password) throws SQLException {
        stmt = con.prepareStatement("select * from employee where Id = '"+username+"' AND Password = '"+password+"'");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }


    public boolean check_credentials_employee(String username, String date) throws SQLException {
        Date dob = Date.valueOf(date);
        stmt = con.prepareStatement("select * from employee where Id = '"+username+"' AND DOB = '"+dob+"'");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    public void resetemployeepassword(String username, String newpassword) throws SQLException {
        PreparedStatement preparedStmt = con.prepareStatement("UPDATE employee set Password = ? where Id = '"+username+"'");
        preparedStmt.setString(1, newpassword);
        preparedStmt.executeUpdate();
    }


    public String[] ConsignArr() throws SQLException {
        stmt = con.prepareStatement("select * from consignment");
        ResultSet res = stmt.executeQuery();


        String [] result_array = new String[100];
        int index = 0;

        while (res.next()) {
            result_array[index++] = res.getString(1);
        }
        return result_array;
    }

    public boolean check_credentials_manager  (String username, String date) throws SQLException {
        Date dob = Date.valueOf(date);
        stmt = con.prepareStatement("select * from manager where Id = '"+username+"' AND DOB = '"+dob+"'");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    public void resetmanagerpassword(String username, String newpassword) throws SQLException {
        PreparedStatement preparedStmt = con.prepareStatement("UPDATE manager set Password = ? where Id = '"+username+"'");
        preparedStmt.setString(1, newpassword);
        preparedStmt.executeUpdate();
    }

    public boolean checkcon(String conID) throws SQLException{
        stmt = con.prepareStatement("select * from consignment where Id = '"+conID+"'");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    public boolean checkoffice(String officeID) throws SQLException{
        stmt = con.prepareStatement("select * from office where Id = '"+officeID+"'");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    public boolean checkcustomer(String customerID) throws SQLException{
        stmt = con.prepareStatement("select * from customer where Id = '"+customerID+"'");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }


    public String[] getdetails(String conID) throws SQLException {
//  return a string[] with ["Booked by","Booked at","Booked on","Destination",Delivery Charges","Status","Delivered to", "Delivered On"]
        String [] details = {"","","","","","","",""};
        stmt = con.prepareStatement("select * from consignment where Id = '"+conID+"'");
        ResultSet result = stmt.executeQuery();
        if(result.next()) {
            String senderId = result.getString(3);
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from customer where Id = '" +senderId+ "'");
            if (rs1.next())  details[0] = rs1.getString("Name");

            details[1] = result.getString("sourceId");

            details[2] = result.getDate("orderdate").toString();

            details[3] = result.getString("destId");

            details[4] = String.valueOf(result.getFloat(5));
            boolean status = result.getBoolean("deliverystatus");
            if (status) {
                details[5] = "Delivered";
                String recieverId = result.getString("receiverId");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("select * from customer where Id = '" + recieverId + "'");
                if (rs2.next())  details[6] = rs2.getString("Name");
                Date date = result.getDate("despatchdate");
                details[7] = date.toString();
            }
            else {
                details[5] = "Undelivered";
                details[6] = "-";
                details[7] = "-";
            }
        }
        return details;

    }

    public String[] get_office_stats(String officeID) throws SQLException {
        String [] details = new String[8];
        stmt = con.prepareStatement("select * from office where Id = '"+officeID+"'");
        ResultSet result = stmt.executeQuery();
        result.next();

        details[0] = "Office ID: " + result.getString("Id");
        details[1] = "Rate: " + String.valueOf(result.getFloat("rate"));
        details[2] = "Total Revenue: " + String.valueOf(result.getDouble("total_revenue"));
        details[3] = "Volume Dispatched: " + String.valueOf(result.getInt("volDispatched"));
        details[4] = "Volume Stored: " + String.valueOf(result.getInt("volStored"));
        details[5] = "Average Consignment Waiting Time : " + String.valueOf(result.getFloat("waitingTime"));
        details[6] = "Number of Trucks: " + String.valueOf(result.getInt("numTruck"));
        details[7] = "Number of Consignments Dispatched: " + String.valueOf(result.getInt("numConsignment"));
        return details;
    }

    public String[] get_truck_details(String truckID) throws SQLException {
        String [] details = new String[5];
        stmt = con.prepareStatement("select * from truck where Id = '"+truckID+"'");
        ResultSet result = stmt.executeQuery();
        if(result.next()) {
            details[0] = "Truck ID: " + result.getString("Id");
            details[1] = "Current Office: " + result.getString("currentOffice");
            details[2] = "Volume Handled: " + String.valueOf(result.getInt("VolumeHandled"));
            details[3] = "Idle Time: " + String.valueOf(result.getFloat("IdleTime"));
            details[4] = "Number of Trips: " + String.valueOf(result.getInt("numTrips"));
        }

        return details;
    }

    public int addconsignment(String senderid, String recieverid, int volume, String sourcezipcode, String destinationzipcode) throws SQLException {
        Statement stmt1 = con.createStatement();

        ResultSet rs = stmt1.executeQuery("select count(*) from consignment");
        rs.next();
        int count = rs.getInt(1);
        stmt = con.prepareStatement("insert into consignment(Id,volume,senderId,receiverId,revenue, deliverystatus, sourceId, destId, orderdate) values(?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, String.valueOf(count + 1));
        stmt.setInt(2, volume);
        stmt.setString(3, senderid);
        stmt.setString(4, recieverid);
        Statement stmt2 = con.createStatement();
        ResultSet res = stmt2.executeQuery("select * from office where Id = '" + sourcezipcode + "' ");
        res.next();
        float cost = res.getFloat("rate") * volume;
        double total_revenue = res.getDouble("total_revenue");
        int volS = res.getInt("volStored");
        stmt.setFloat(5, cost);
        stmt.setBoolean(6, false);
        stmt.setString(7, sourcezipcode);
        stmt.setString(8, destinationzipcode);
        stmt.setDate(9, Date.valueOf(java.time.LocalDate.now()));
        stmt.executeUpdate();

        PreparedStatement preparedStmt3 = con.prepareStatement("UPDATE office set total_revenue =? , volStored = ? where Id = '" + sourcezipcode + "'");
        preparedStmt3.setDouble(1, total_revenue + cost);
        preparedStmt3.setInt(2, volS + volume);
        preparedStmt3.executeUpdate();


//        PreparedStatement preparedStmt4 = con.prepareStatement("select * from warehouse where destofficeId = '" + destinationzipcode + "'");
//        ResultSet rs4 = preparedStmt4.executeQuery();
//        rs4.next();
//        int toSend = rs4.getInt("volumeToSend");
//
//        PreparedStatement preparedStmt5 = con.prepareStatement("UPDATE warehouse set volumeToSend =?  where destofficeId = '" + destinationzipcode + "'");
//        preparedStmt5.setInt(1, toSend + volume);
//        preparedStmt5.executeUpdate();

        PreparedStatement s1 = con.prepareStatement("SELECT * from customer where Id = '" + senderid + "' ");
        ResultSet r1 = s1.executeQuery();
        r1.next();
        String sender = r1.getString("Name");
        PreparedStatement s2 = con.prepareStatement("SELECT * from customer where Id = '" + recieverid + "' ");
        ResultSet r2 = s2.executeQuery();
        r2.next();
        String receiver = r2.getString("Name");
        System.out.println("DETAILS OF CONSIGNMENT BOOKING");
        System.out.println("____________________________________________________________");
        System.out.println();
        System.out.println("\tConsignment with ID: " + String.valueOf(count+1));
        System.out.println("\t\tBooked by: " + sender);
        System.out.println("\t\tBooked for: " + receiver);
        System.out.println("\t\tBooked on: " + Date.valueOf(java.time.LocalDate.now()).toString());
        System.out.println("\t\tVolume: " + String.valueOf(volume));
        System.out.println("\t\tDelivery Charges: " + String.valueOf(cost));
        System.out.println("\t\tBooked at: " + sourcezipcode);
        System.out.println("\t\tDestination: " + destinationzipcode);
        System.out.println();
        System.out.println("____________________________________________________________");


        return count+1;
    }

    public int addcust(String name, String street, String city, String state) throws SQLException {

        Statement stmt1 = con.createStatement();

        ResultSet rs = stmt1.executeQuery("select count(*) from customer");
        rs.next();
        int count = rs.getInt(1);


        stmt = con.prepareStatement("insert into customer(Id, Name, Address) values(?,?,?)");
        stmt.setString(1, String.valueOf(count+1));
        stmt.setString(2, name);
        stmt.setString(3, street+ ", " + city + ", " + state);
        stmt.executeUpdate();
        return count+1;
    }

    public String[] getbranches() throws SQLException{
        stmt = con.prepareStatement("select * from office");
        ResultSet res = stmt.executeQuery();
        List<String> list = new ArrayList<>();

        while (res.next()) {
            list.add(res.getString(1));
        }
        String [] result_array = new String[list.size()];
        for(int i = 0 ; i < list.size(); i++ )
            result_array[i] = list.get(i);
        return result_array;
    }

    public String[] getConsignment() throws SQLException{
        stmt = con.prepareStatement("select * from consignment");
        ResultSet res = stmt.executeQuery();
        List<String> list = new ArrayList<>();

        while (res.next()) {
            list.add(res.getString(1));
        }
        String [] result_array = new String[list.size()];
        for(int i = 0 ; i < list.size(); i++ )
            result_array[i] = list.get(i);
        return result_array;
    }

    public String[] getTrucks() throws SQLException{
        stmt = con.prepareStatement("select * from truck");
        ResultSet res = stmt.executeQuery();
        List<String> list = new ArrayList<>();

        while (res.next()) {
            list.add(res.getString(1));
        }
        String [] result_array = new String[list.size()];
        for(int i = 0 ; i < list.size(); i++ )
            result_array[i] = list.get(i);
        return result_array;
    }

    public void changerate(Float nrate, String officeid) throws SQLException{
        PreparedStatement preparedStmt = con.prepareStatement("UPDATE office set rate = ? where Id = '"+officeid+"'");
        preparedStmt.setFloat(1, nrate);
        preparedStmt.executeUpdate();
    }

    public int addTrucktoOffice(String officeID) throws SQLException {
        Statement stmt1 = con.createStatement();

        ResultSet rs = stmt1.executeQuery("select count(*) from truck");
        rs.next();
        int count = rs.getInt(1);

        stmt = con.prepareStatement("insert into truck(Id, currentOffice, arrivedate) values(?,?,?)");
        stmt.setString(1, String.valueOf(count+1));
        stmt.setString(2, officeID);
        stmt.setDate(3, Date.valueOf(java.time.LocalDate.now()));
        stmt.executeUpdate();

        PreparedStatement preparedStmt1 = con.prepareStatement("select * from office where Id = '"+officeID+"'");
        ResultSet rs1 = preparedStmt1.executeQuery();
        rs1.next();
        int numTruck = rs1.getInt("numTruck");

        PreparedStatement preparedStmt2 = con.prepareStatement("UPDATE office set  numTruck = ? where Id = '"+officeID+"'");
        preparedStmt2.setInt(1, numTruck + 1);
        preparedStmt2.executeUpdate();

        return count+1;
    }

    public void dispatchTruck(String fromOffice, String toOffice) throws SQLException{
//        PreparedStatement preparedStmt1 = con.prepareStatement("select * from warehouse where destofficeId = '"+toOffice+"'");
//        ResultSet rs1 = preparedStmt1.executeQuery();
//        rs1.next();
//        int toSend = rs1.getInt(2);
        PreparedStatement preparedStmt1 = con.prepareStatement("select * from consignment where sourceId = '"+fromOffice+"' AND destId = '"+toOffice+"' AND deliverystatus = false ");
        ResultSet rs1 = preparedStmt1.executeQuery();
        int toSend = 0;
        while (rs1.next()){
            toSend += rs1.getInt("volume");
        }
        if(toSend < 500) return;

        PreparedStatement preparedStmt2 = con.prepareStatement("select * from office where Id = '"+fromOffice+"'");
        ResultSet rs2 = preparedStmt2.executeQuery();
        rs2.next();
        int numTrucks = rs2.getInt("numTruck");
        int volD = rs2.getInt("volDispatched");
        int volS = rs2.getInt("volStored");
        int nT = rs2.getInt("numTruck");
        int nC = rs2.getInt("numConsignment");
        float wt = rs2.getFloat("waitingTime")*nC;
        if(numTrucks <= 0) return;

        PreparedStatement preparedStmt4 = con.prepareStatement("select * from truck where currentOffice = '"+fromOffice+"' ");
        ResultSet rs4 = preparedStmt4.executeQuery();
        rs4.next();
        String truckId = rs4.getString("Id");
        int volHand = rs4.getInt("VolumeHandled");
        Date arrival = rs4.getDate("arrivedate");
        float idt = rs4.getFloat("IdleTime");
        int n = rs4.getInt("numTrips");

        PreparedStatement preparedStmt3 = con.prepareStatement("select * from consignment where sourceId = '"+fromOffice+"' AND destId = '"+toOffice+"' AND deliverystatus = false ");
        ResultSet rs3 = preparedStmt3.executeQuery();
        System.out.println();
        System.out.println("DETAILS OF DISPATCHED TRUCK & CONSIGNMENTS");
        System.out.println("Truck ID: " + truckId);
        System.out.println("Volume Loaded: " + String.valueOf(toSend));
        System.out.println("From: " + fromOffice);
        System.out.println("To: " + toOffice);
        System.out.println("Date: " + Date.valueOf(java.time.LocalDate.now()).toString());
        System.out.println();
        System.out.println("____________________________________________________________");
        while(rs3.next()){
            String[] details = new String[0];
            String conID = rs3.getString("Id");
            try {
                details = getdetails(conID);
            }
            catch (SQLException exception){
                System.out.println("SQL EXCEPTION IN GETTING DETAILS");
            }
            System.out.println();
            System.out.println("\tConsignment with ID: " + conID);
            System.out.println("\t\tBooked by: " + details[0]);
            System.out.println("\t\tBooked on: " + details[2]);
            System.out.println("\t\tVolume: " + String.valueOf(rs3.getInt("volume")));
            System.out.println("\t\tDelivery Charges: " + details[4]);
            System.out.println();
            System.out.println("____________________________________________________________");

            PreparedStatement preparedStmt5 = con.prepareStatement("UPDATE consignment set truckno =?, deliverystatus = ?, despatchdate = ? where Id = '"+conID+"'");
            preparedStmt5.setString(1, truckId);
            preparedStmt5.setBoolean(2, true);
            preparedStmt5.setDate(3, Date.valueOf(java.time.LocalDate.now()));
            preparedStmt5.executeUpdate();

//            PreparedStatement preparedStmt6 = con.prepareStatement("UPDATE truck set Volumehandled = ?  where Id = '"+truckId+"'");
//            preparedStmt6.setInt(1, volHand + rs3.getInt("volume"));
//            preparedStmt6.executeUpdate();

//            volD += rs3.getInt("volume");
//            volS -= rs3.getInt("volume");
            nC++;

            long diffInDays = Date.valueOf(java.time.LocalDate.now()).getTime() - rs3.getDate("orderdate").getTime();
            diffInDays = TimeUnit.DAYS.convert(diffInDays,TimeUnit.DAYS);
            wt += diffInDays;


        }

        try {
            PreparedStatement preparedStmt7 = con.prepareStatement("UPDATE truck set currentOffice = ?, VolumeHandled = ?, arrivedate = ?, dispatchdate = ?, IdleTime = ?, numTrips = ? where Id = '" + truckId + "'");
            preparedStmt7.setString(1, toOffice);
            preparedStmt7.setInt(2, volHand + toSend);
            preparedStmt7.setDate(3, Date.valueOf(java.time.LocalDate.now()));
            preparedStmt7.setDate(4, arrival);
            long diffInDays = Date.valueOf(java.time.LocalDate.now()).getTime() - arrival.getTime();
            diffInDays = TimeUnit.DAYS.convert(diffInDays, TimeUnit.DAYS);
            float idleT = (n * idt + diffInDays) / (n + 1);
            preparedStmt7.setFloat(5, idleT);
            preparedStmt7.setInt(6, n + 1);
            preparedStmt7.executeUpdate();
        }
        catch (SQLException exception){
//            System.out.println("Truck Updation Failed");
        }
        try {

            PreparedStatement preparedStmt8 = con.prepareStatement("UPDATE office set volDispatched = ?, volStored = ?, numTruck = ?, numConsignment = ?, waitingTime = ? where Id = '" + fromOffice + "'");
            preparedStmt8.setInt(1, volD + toSend);
            preparedStmt8.setInt(2, volS - toSend);
            preparedStmt8.setInt(3, nT - 1);
            preparedStmt8.setInt(4, nC);
            preparedStmt8.setFloat(5, wt / nC);
            preparedStmt8.executeUpdate();

            PreparedStatement preparedStmt9 = con.prepareStatement("SELECT * from office where Id = '" + toOffice + "'");
            ResultSet rs = preparedStmt9.executeQuery();
            rs.next();
            int num = rs.getInt("numTruck");
            PreparedStatement preparedStmt10 = con.prepareStatement("UPDATE office set numTruck = ? where Id = '" + toOffice + "'");
            preparedStmt10.setInt(1, num + 1);
            preparedStmt10.executeUpdate();
        }
        catch (SQLException exception){
//            System.out.println("Office Updation Failed");
        }

//        try {
//            PreparedStatement preparedStmt9 = con.prepareStatement("UPDATE warehouse set volumeToSend = ? where Id = '" + toOffice + "'");
//            System.out.println("??????");
//            preparedStmt9.setInt(1, 0);
//            System.out.println("??????");
//            preparedStmt9.executeUpdate();
//            System.out.println("??????");
//        }
//        catch (SQLException exception){
////            System.out.println("WareHouse Updation Failed");
//        }

    }

    public float get_truck_idle_Time(String truckId) throws SQLException {
        stmt = con.prepareStatement("select * from truck where Id = '"+truckId+"' ");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getFloat("IdleTime");
    }

    public float get_con_waiting_Time(String officeId) throws SQLException {
        stmt = con.prepareStatement("select * from office where Id = '"+officeId+"' ");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getFloat("waitingTime");
    }
}
