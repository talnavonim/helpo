package huji.postpc.y2021.talme.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class myRequestHolder {
    List<Request> my_requests;

    public myRequestHolder(){
        my_requests = new ArrayList<>();
    }

    public void addMyRequestMail(String req_id, String request_email, Request.RequestStatus status, String location, String address, String mailLocation, String mailType){
        Request new_request = new Request(req_id, request_email, status, location, address, mailLocation, mailType);
        this.my_requests.add(new_request);
    }
    public void addMyRequestGroceries(String req_id, String request_email, Request.RequestStatus status, String location, String address, HashMap<String, Integer> groceriesAmounts){
        Request new_request = new Request(req_id, request_email, status, location, address, groceriesAmounts);
        this.my_requests.add(new_request);
    }

    public List<Request> getMyRequest(){return my_requests;}
}
