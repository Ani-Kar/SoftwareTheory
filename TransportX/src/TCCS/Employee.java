package TCCS;

public class Employee extends Manager {
    private Office office;

    public Employee(String id, String name, String address, String dob, String email,String password, Office office){
        super(id, name, address, dob, email, password);
        this.office = office;
    }
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

}
