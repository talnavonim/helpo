package huji.postpc.y2021.talme.myapplication;



public class Request {
    //needs to have timeout
    int req_id;
    String request_email;
    public enum RequestType {SHOPPING, MAIL};
    RequestType type;
    public enum RequestStatus {WAITING, IN_PROGRESS, READY, DONE}
    RequestStatus status;
    String location;
    String address;
    //

}
