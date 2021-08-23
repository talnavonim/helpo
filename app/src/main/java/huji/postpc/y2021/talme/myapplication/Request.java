package huji.postpc.y2021.talme.myapplication;


import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Request implements Serializable {
    //needs to have timeout
    String req_id;
    String request_email;
    public enum RequestType {Groceries, MAIL};
    RequestType type;
    public enum RequestStatus {WAITING, IN_PROGRESS, READY, DONE}
    RequestStatus status;
    String location;
    String address;
    HashMap<String, Integer> groceriesAmounts = new HashMap<String, Integer>();
    String mailLocation;
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
        type = RequestType.SHOPPING;
        status = RequestStatus.WAITING;
        location = "NONE";
        address = "NONE";
        groceriesAmounts = new HashMap<String, Integer>();
        initialGroceries();
        full_name = name;
    }

    private void initialGroceries(){
        groceriesAmounts.put("eggs", 0);
        groceriesAmounts.put("bread", 0);
        groceriesAmounts.put("milk", 0);
        groceriesAmounts.put("oil", 0);
        groceriesAmounts.put("shuger", 0);
    }

    public Request(String email, RequestType type, String location, String address, int eggs, int bread, int milk, int oil, int shuger, String mailL, String c){
        req_id = UUID.randomUUID().toString();
        request_email = email;
        this.type = type;
        status = RequestStatus.WAITING;
        this.location = location;
        this.address = address;
        groceriesAmounts = new HashMap<String, Integer>();
        groceriesAmounts.put("eggs", eggs);
        groceriesAmounts.put("bread", bread);
        groceriesAmounts.put("milk", milk);
        groceriesAmounts.put("oil", oil);
        groceriesAmounts.put("shuger", shuger);
        mailLocation = mailL;
        comment = c;
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

    public int addShuger(){
        int shuger = groceriesAmounts.get("shuger");
        if(shuger != 3){
            groceriesAmounts.put("shuger", shuger+1);
        }
        return groceriesAmounts.get("shuger");
    }

    public int removeShuger(){
        int shuger = groceriesAmounts.get("shuger");
        if(shuger > 0){
            groceriesAmounts.put("shuger", shuger-1);
        }
        return groceriesAmounts.get("shuger");
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
