package huji.postpc.y2021.talme.myapplication;


import java.util.HashMap;
import java.util.UUID;

public class Request {
    //needs to have timeout
    String req_id;
    String request_email;
    public enum RequestType {SHOPPING, MAIL};
    RequestType type;
    public enum RequestStatus {WAITING, IN_PROGRESS, READY, DONE}
    RequestStatus status;
    String location;
    String address;
    HashMap<String, Integer> groceriesAmounts = new HashMap<String, Integer>();
    String mailLocation;

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

    public Request(String email, RequestType type, String location, String address, int eggs, int bread, int milk, int oil, int shuger, String mailL){
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
    }

    private void initialGroceries(){
        groceriesAmounts.put("eggs", 0);
        groceriesAmounts.put("bread", 0);
        groceriesAmounts.put("milk", 0);
        groceriesAmounts.put("oil", 0);
        groceriesAmounts.put("shuger", 0);
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
