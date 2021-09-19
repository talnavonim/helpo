package huji.postpc.y2021.talme.myapplication;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HelpOffer {
    String req_id;
    String help_id;
    String helper_email;
    String helper_full_name;
    String requester_full_name;
    Request.RequestType requestType;
    Request.RequestStatus requestStatus;
    Timestamp offer_timestamp;

    public HelpOffer(){}

    public HelpOffer(String req_id, String helper_email, String requester_full_name, Request.RequestType requestType) {
        this.req_id = req_id;
        this.help_id = UUID.randomUUID().toString();
        this.helper_email = helper_email;
        this.requester_full_name = requester_full_name;
        this.requestType = requestType;
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

    public String getHelper_email() {
        return helper_email;
    }

    public void setHelper_email(String helper_email) {
        this.helper_email = helper_email;
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

    public Request.RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Request.RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
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
