package huji.postpc.y2021.talme.myapplication;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class myRequestList implements Serializable {
    List<Request> my_requests;
    private Context context;

    public myRequestList(){
        my_requests = new ArrayList<>();
    }

    public myRequestList(Context c){
        my_requests = new ArrayList<>();
        this.context = c;
    }

    public void addRequest(Request new_request){
        if(new_request.getType() == Request.RequestType.GROCERIES){
            addMyRequestGroceries(new_request.getReq_id(), new_request.getUser_id(), new_request.getStatus(), new_request.getLat(), new_request.getLng(), new_request.getAddress(), new_request.getGroceriesAmounts());
        }
        else{
            addMyRequestMail(new_request.getReq_id(), new_request.getUser_id(), new_request.getStatus(), new_request.getLat(), new_request.getLng(), new_request.getAddress(), new_request.getMailLocation(), new_request.getMailType());
        }
//        this.sendBroadcast();
    }

    public myRequestList(ArrayList<Request> requests){
        my_requests = new ArrayList<Request>(requests);
    }

    public void addMyRequestMail(String req_id, String request_email, Request.RequestStatus status, double lat, double lng, String address, String mailLocation, String mailType){
        Request new_request = new Request(req_id, request_email, status, lat, lng, address, mailLocation, mailType);
        this.my_requests.add(new_request);
    }
    public void addMyRequestGroceries(String req_id, String request_email, Request.RequestStatus status, double lat, double lng, String address, HashMap<String, Integer> groceriesAmounts){
        Request new_request = new Request(req_id, request_email, status, lat, lng, address, groceriesAmounts);
        this.my_requests.add(new_request);
    }

    public List<Request> getMyRequest(){return my_requests;}

    public ArrayList<Request> getCoppies(){
        return new ArrayList<>(my_requests);
    }

    public void sendBroadcast(){
        Intent broadcast = new Intent("db_changed");
        ArrayList <Request> requests = this.getCoppies();
        myRequestList db = new myRequestList(requests);
        broadcast.putExtra("db", db);
        context.sendBroadcast(broadcast);
    }
}
