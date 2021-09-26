package huji.postpc.y2021.talme.myapplication;


import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Request implements Serializable {


    String req_id;
    String help_offer_id;
    String user_id;
    public enum RequestType {Groceries, Other, Mail};
    RequestType type;
    public enum RequestStatus {Waiting, Ongoing, Done, Rated, Hidden}
    RequestStatus status;
    double lat;
    double lng;
    String address;
    transient Timestamp request_timestamp; //timestamp is none Serializable maybe use parcable instead
    String description;
    String geohash;
    String full_name;

    public Request(String user_id, RequestType type, String address, String description, String full_name, double lat, double lng) {
        this.req_id = UUID.randomUUID().toString();
        this.help_offer_id = "";
        this.user_id = user_id;
        this.type = type;
        status = RequestStatus.Waiting;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        request_timestamp = Timestamp.now();
        this.description = description;
        geohash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(lat, lng));
        this.full_name = full_name;
    }

    public Request(){}


    public Timestamp getRequest_timestamp() {
        return request_timestamp;
    }


    public String phraseRequest() {
        return description;
    }




    public String getFull_name() {
        return full_name;
    }

    public String getDescription() {
        return description;
    }

    public String getGeohash() {
        return geohash;
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



    public double getLat() {
        return lat;
    }


    public double getLng() {
        return lng;
    }


    public String getReq_id() {
        return req_id;
    }


    public String getUser_id() {
        return user_id;
    }


    public String getHelp_offer_id() {
        return help_offer_id;
    }

    public void setHelp_offer_id(String help_offer_id) {
        this.help_offer_id = help_offer_id;
    }
}
