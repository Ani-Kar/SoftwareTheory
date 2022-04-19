package TCCS;

public class Manager {

    private String id;
    private String name;
    private String dob;
    private String address;
    private String email;
    private String password;

    public Manager(String id, String  name, String address, String dob, String email, String password){
        this.id = id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getDOB() {
        return dob;
    }
}

