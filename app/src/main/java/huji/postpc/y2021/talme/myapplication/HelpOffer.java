package huji.postpc.y2021.talme.myapplication;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.UUID;

public class HelpOffer implements Serializable {
    String req_id;
    String help_id;
    String helper_id;
    String requester_id;
    String helper_full_name;
    String requester_full_name;
    Request.RequestType requestType;
    enum OfferStatus {Pending, Ongoing, Done, Declined, Canceled}
    OfferStatus status;
    transient Timestamp offer_timestamp;

    public HelpOffer(){}

    public HelpOffer(String req_id, String helper_id,String requester_id, String requester_full_name, Request.RequestType requestType) {
        this.req_id = req_id;
        this.help_id = UUID.randomUUID().toString();
        this.helper_id = helper_id;
        this.requester_id = requester_id;
        this.helper_full_name = HelpoApp.getInstance().full_name;
        this.requester_full_name = requester_full_name;
        this.requestType = requestType;
        this.offer_timestamp = Timestamp.now();
        this.status = OfferStatus.Pending;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getHelp_id() {
        return help_id;
    }

    public void setHelp_id(String help_id) {
        this.help_id = help_id;
    }

    public String getHelper_id() {
        return helper_id;
    }

    public void setHelper_id(String helper_id) {
        this.helper_id = helper_id;
    }

    public String getRequester_id() {
        return requester_id;
    }

    public String getRequester_full_name() {
        return requester_full_name;
    }

    public void setRequester_full_name(String requester_full_name) {
        this.requester_full_name = requester_full_name;
    }

    public Request.RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(Request.RequestType requestType) {
        this.requestType = requestType;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public Timestamp getOffer_timestamp() {
        return offer_timestamp;
    }

    public void setOffer_timestamp(Timestamp offer_timestamp) {
        this.offer_timestamp = offer_timestamp;
    }

    public String getHelper_full_name() {
        return helper_full_name;
    }

    public void setHelper_full_name(String helper_full_name) {
        this.helper_full_name = helper_full_name;
    }

}
