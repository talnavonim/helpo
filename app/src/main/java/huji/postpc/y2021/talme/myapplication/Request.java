package huji.postpc.y2021.talme.myapplication;


import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Request implements Serializable {

    //needs to have timeout
    String req_id;
    String help_offer_id;
    String user_id;
    public enum RequestType {Groceries, Mail};
    RequestType type;
    public enum RequestStatus {Waiting, Ongoing, Done, Rated, Hidden}
    RequestStatus status;
    double lat;
    double lng;
    String address;
    HashMap<String, Integer> groceriesAmounts;
    String mailLocation;
    String mailType;
    transient Timestamp request_timestamp; //timestamp is none Serializable maybe use parcable instead

    //for requests type groceries
    public Request(String req_id, String user_id, RequestStatus status, double lat, double lng, String address, HashMap<String, Integer> groceriesAmounts){
        this.req_id = req_id;
        this.user_id = user_id;
        this.type = RequestType.Groceries;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.groceriesAmounts = new HashMap<>(groceriesAmounts);
        this.request_timestamp = Timestamp.now();
    }

    //for requests type mail
    public Request(String req_id, String user_id, RequestStatus status, double lat, double lng, String address, String mailLocation, String mailType){
        this.req_id = req_id;
        this.user_id = user_id;
        this.type = RequestType.Mail;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.mailLocation = mailLocation;
        this.mailType = mailType;
        this.request_timestamp = Timestamp.now();
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getMailType() {
        return mailType;
    }

    String comment;
    String geohash;
    String full_name;



    public Request(){
//        req_id = UUID.randomUUID().toString();
//        request_email = "";
//        type = RequestType.SHOPPING;
//        status = RequestStatus.WAITING;
//        location = "NONE";
//        address = "NONE";
//        groceriesAmounts = new HashMap<String, Integer>();
//        initialGroceries();
    }

    public Request(String id, String name){
        req_id = id;
        user_id = "";
        type = RequestType.Groceries;
        status = RequestStatus.Waiting;
        help_offer_id = "";
        lat = 0;
        lng = 0;
        address = "NONE";
        groceriesAmounts = new HashMap<String, Integer>();
        initialGroceries();
        full_name = name;
    }

    public Timestamp getRequest_timestamp() {
        return request_timestamp;
    }

    public void setRequest_timestamp(Timestamp request_timestamp) {
        this.request_timestamp = request_timestamp;
    }

    private void initialGroceries(){
        groceriesAmounts.put("eggs", 0);
        groceriesAmounts.put("bread", 0);
        groceriesAmounts.put("milk", 0);
        groceriesAmounts.put("oil", 0);
        groceriesAmounts.put("sugar", 0);
    }

    public Request(String user_id, RequestType type, double lat, double lng, String address, int eggs, int bread, int milk, int oil, int sugar, String mailL, String c){
        req_id = UUID.randomUUID().toString();
        this.user_id = user_id;
        this.type = type;
        status = RequestStatus.Waiting;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        groceriesAmounts = new HashMap<String, Integer>();
        groceriesAmounts.put("eggs", eggs);
        groceriesAmounts.put("bread", bread);
        groceriesAmounts.put("milk", milk);
        groceriesAmounts.put("oil", oil);
        groceriesAmounts.put("sugar", sugar);
        mailLocation = mailL;
        comment = c;
    }


    public String phraseRequest() {
        switch (this.type){
            case Groceries:
                String shop = "";
                for(String key : this.groceriesAmounts.keySet()){
                    int val = this.groceriesAmounts.get(key);
                    if(val > 0){
                        shop += val + " ";
                        shop += key + " ";
                    }
                }
                return "Hello, i need some help with groceries, I need: "+shop;
            case Mail:

        }


        return "Request{" +
                "req_id='" + req_id + '\'' +
                ", request_user_id='" + user_id + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", latitude='" + lat + '\'' +
                ", longitude='" + lng + '\'' +
                ", address='" + address + '\'' +
                ", groceriesAmounts=" + groceriesAmounts +
                ", mailLocation='" + mailLocation + '\'' +
                ", comment='" + comment + '\'' +
                ", geohash='" + geohash + '\'' +
                ", full_name='" + full_name + '\'' +
                '}';
    }


    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setMailLocation(String mailLocation) {
        this.mailLocation = mailLocation;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getMailLocation() {
        return mailLocation;
    }

    public String getComment() {
        return comment;
    }

    public String getGeohash() {
        return geohash;
    }

    public int addSugar(){
        int sugar = groceriesAmounts.get("sugar");
        if(sugar != 3){
            groceriesAmounts.put("sugar", sugar+1);
        }
        return groceriesAmounts.get("sugar");
    }

    public int removeSugar(){
        int sugar = groceriesAmounts.get("sugar");
        if(sugar > 0){
            groceriesAmounts.put("sugar", sugar-1);
        }
        return groceriesAmounts.get("sugar");
    }

    public int addOil(){
        int oil = groceriesAmounts.get("oil");
        if(oil < 3){
            groceriesAmounts.put("oil", oil+1);
        }
        return groceriesAmounts.get("oil");
    }

    public int removeOil(){
        int oil = groceriesAmounts.get("oil");
        if(oil > 0){
            groceriesAmounts.put("oil", oil-1);
        }
        return groceriesAmounts.get("oil");
    }

    public int addMilk(){
        int milk = groceriesAmounts.get("milk");
        if(milk < 3){
            groceriesAmounts.put("milk", milk+1);
        }
        return groceriesAmounts.get("milk");
    }

    public int removeMilk(){
        int milk = groceriesAmounts.get("milk");
        if(milk > 0){
            groceriesAmounts.put("milk", milk-1);
        }
        return groceriesAmounts.get("milk");
    }

    public int addEggs(){
        int eggs = groceriesAmounts.get("eggs");
        if(eggs < 5){
            groceriesAmounts.put("eggs", eggs+1);
        }
        return groceriesAmounts.get("eggs");
    }

    public int removeEggs(){
        int eggs = groceriesAmounts.get("eggs");
        if(eggs > 0){
            groceriesAmounts.put("eggs", eggs-1);
        }
        return groceriesAmounts.get("eggs");
    }

    public int addBread(){
        int bread = groceriesAmounts.get("bread");
        if(bread < 4){
            groceriesAmounts.put("bread", bread+1);
        }
        return groceriesAmounts.get("bread");
    }

    public int removeBread(){
        int bread = groceriesAmounts.get("bread");
        if(bread > 0){
            groceriesAmounts.put("bread", bread-1);
        }
        return groceriesAmounts.get("bread");
    }


    public HashMap<String, Integer> getGroceriesAmounts() {
        return groceriesAmounts;
    }

    public void setGroceriesAmounts(HashMap<String, Integer> groceriesAmounts) {
        this.groceriesAmounts = groceriesAmounts;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHelp_offer_id() {
        return help_offer_id;
    }

    public void setHelp_offer_id(String help_offer_id) {
        this.help_offer_id = help_offer_id;
    }
}
