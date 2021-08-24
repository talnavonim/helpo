package huji.postpc.y2021.talme.myapplication;


import com.google.firebase.Timestamp;
import com.google.type.DateTime;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Request implements Serializable {

    //needs to have timeout
    String req_id;
    String request_email;
    public enum RequestType {GROCERIES, MAIL};
    RequestType type;
    public enum RequestStatus {WAITING, IN_PROGRESS, READY, DONE}
    RequestStatus status;
    double lat;
    double lng;
    String address;
    HashMap<String, Integer> groceriesAmounts;
    String mailLocation;
    String mailType;
    Timestamp request_timestamp;

    //for requests type groceries
    public Request(String req_id, String request_email, RequestStatus status, double lat, double lng, String address, HashMap<String, Integer> groceriesAmounts){
        this.req_id = req_id;
        this.request_email = request_email;
        this.type = RequestType.GROCERIES;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.groceriesAmounts = new HashMap<>(groceriesAmounts);
        this.request_timestamp = Timestamp.now();
    }

    //for requests type mail
    public Request(String req_id, String request_email, RequestStatus status, double lat, double lng, String address, String mailLocation, String mailType){
        this.req_id = req_id;
        this.request_email = request_email;
        this.type = RequestType.MAIL;
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
        request_email = "";
        type = RequestType.GROCERIES;
        status = RequestStatus.WAITING;
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

    public Request(String email, RequestType type, double lat, double lng, String address, int eggs, int bread, int milk, int oil, int sugar, String mailL, String c){
        req_id = UUID.randomUUID().toString();
        request_email = email;
        this.type = type;
        status = RequestStatus.WAITING;
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

    @Override
    public String toString() {
        switch (this.type){
            case GROCERIES:
                String shop = "";
                for(String key : this.groceriesAmounts.keySet()){
                    int val = this.groceriesAmounts.get(key);
                    if(val > 0){
                        shop += val + " ";
                        shop += key + " ";
                    }
                }
                return "Hello, i need some help with groceries, I need: "+shop;
            case MAIL:

        }

        return "Request{" +
                "req_id='" + req_id + '\'' +
                ", request_email='" + request_email + '\'' +
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

    public String getRequest_email() {
        return request_email;
    }

    public void setRequest_email(String request_email) {
        this.request_email = request_email;
    }
}
