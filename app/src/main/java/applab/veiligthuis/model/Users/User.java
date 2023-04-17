package applab.veiligthuis.model.Users;

public class User {

    public String userName;
    public String userPassword;
    public String phoneNo;
    public String email;
    public String authorisatie = "Geen";

    public User(){

    }

    public User(String userName, String userPassword, String phoneNo, String email){

        this.userName = userName;
        this.userPassword = userPassword;
        this.phoneNo = phoneNo;
        this.email = email;
    }

}
