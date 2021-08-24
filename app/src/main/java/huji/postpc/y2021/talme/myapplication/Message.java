package huji.postpc.y2021.talme.myapplication;

import com.google.firebase.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Message {
    String message;
    String fullName;
//    LocalDateTime message_timestamp;
    Timestamp message_timestamp;
    int type;

    Message(){}

    Message(String message, int typeOfUser){
        this.message = message;
        message_timestamp = Timestamp.now();
//        message_timestamp = LocalDateTime.now();
        type = typeOfUser;
    }

    Message(String message, int typeOfUser, String fullName){

        this.message = message;
        message_timestamp = Timestamp.now();
//        message_timestamp = LocalDateTime.now();
        type = typeOfUser;
        this.fullName = fullName;

    }

    public LocalDateTime getLocalDateTime()
    {
        long mili = (message_timestamp.getSeconds() * 1000) +
                (message_timestamp.getNanoseconds() / 1000000);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(mili), ZoneId.systemDefault());
    }


    String getMessage(){
        return message;
    }

    String getFullName(){
        return fullName;
    }

    String getCreatedAt(){
        return ""+ message_timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

//    public void setMessage_timestamp(LocalDateTime message_timestamp) {
    public void setMessage_timestamp(Timestamp message_timestamp) {
        this.message_timestamp = message_timestamp;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

//    public LocalDateTime getMessage_timestamp() {
    public Timestamp getMessage_timestamp() {
        return message_timestamp;
    }
}
