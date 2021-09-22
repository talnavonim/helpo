package huji.postpc.y2021.talme.myapplication;

import com.google.firebase.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Message {
    String message;
    String user_id;
    Timestamp message_timestamp;
    public enum MessageType {RECEIVED, SENT};

    Message(){}

    public boolean isSent(String uid)
    {
        return user_id.equals(uid);
    }

    Message(String message, String user_id, Timestamp time){
        this.message = message;
        this.user_id = user_id;
        this.message_timestamp = time;
    }


    private LocalDateTime getLocalDateTime()
    {
        long mili = (message_timestamp.getSeconds() * 1000) +
                (message_timestamp.getNanoseconds() / 1000000);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(mili), ZoneId.systemDefault());
    }


    public String getMessage(){
        return message;
    }


    String getCreatedAt(){
        return ""+ message_timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    //    public void setMessage_timestamp(LocalDateTime message_timestamp) {
    public void setMessage_timestamp(Timestamp message_timestamp) {
        this.message_timestamp = message_timestamp;
    }

    public String timeToString()
    {
        LocalDateTime lcl = getLocalDateTime();
        int hour = lcl.getHour();
        int minutes = lcl.getMinute();
        return hour + ":" + minutes;
    }


    //    public LocalDateTime getMessage_timestamp() {
    public Timestamp getMessage_timestamp() {
        return message_timestamp;
    }
}
