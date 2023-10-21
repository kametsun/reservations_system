package model;

import java.sql.SQLException;
import initialize.MyDB;

public class User {
    private String userID;
    private String name, address;
    private String phoneNumber, email, password;

    public User(String userID, String name, String address, String phoneNumber, String email, String password){
        setUserID(userID);
        setName(name);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPassword(password);
    }

    //ユーザ登録メソッド
    public void insertUser(User user){
        String res = "";

        MyDB.connectDB();

        try {
            String sql = "INSERT INTO db_reservation.users ( user_id, name, address, phone_number, email, password ) VALUES ('";
            sql += user.userID + "','" + user.name + "','" + user.address + "','" + user.phoneNumber + "','" + user.email + "','" + user.password + "');";

            int resInt = MyDB.sqlStmt.executeUpdate(sql);
            res = resInt + "行登録しました。";
        } catch (SQLException e) {
            res = "ユーザ登録にエラーが発生しました。";
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }
        System.out.println(res);
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private void setUserID(String userID) {
        this.userID = userID;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPassword(String password) {
        this.password = password;
    }
}
