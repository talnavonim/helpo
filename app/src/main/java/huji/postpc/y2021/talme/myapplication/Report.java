package huji.postpc.y2021.talme.myapplication;

import java.util.UUID;

public class Report {
    String offender_id;
    String reporter_id;
    String offer_id;
    String report_id;
    String free_text;

    public Report(){}

    public Report(String offender_id, String reporter_id, String offer_id, String free_text) {
        this.offender_id = offender_id;
        this.reporter_id = reporter_id;
        this.offer_id = offer_id;
        this.report_id = UUID.randomUUID().toString();;
        this.free_text = free_text;
    }

    public String getOffender_id() {
        return offender_id;
    }

    public String getReporter_id() {
        return reporter_id;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public String getReport_id() {
        return report_id;
    }

    public String getFree_text() {
        return free_text;
    }
}
