package huji.postpc.y2021.talme.myapplication;

import java.util.ArrayList;

public class User {
    private String email; //the id of an user
    String name;
    String user_id;
//    ArrayList<Protege> proteges;
//    ArrayList<Request> my_requests;
//    ArrayList<Request> I_help;
//    Reports
    int rating;
    int raters;

    public User(String email, String name, int rating, int raters){
        this.email = email;
        this.name = name;
        this.rating = rating;
        this.raters = raters;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRaters(int raters) {
        this.raters = raters;
    }

    public String getEmail() {
        return email;
    }

    public int getRating() {
        return rating;
    }

    public int getRaters() {
        return raters;
    }

    public String getName() {
        return name;
    }
}
