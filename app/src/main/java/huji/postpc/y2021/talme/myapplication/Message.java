package huji.postpc.y2021.talme.myapplication;

import android.text.format.Time;

public class Message {
    String message;
    String fullName;
    Time time;
    int type;


    Message(String message, int typeOfUser){
        this.message = message;
        time = new Time();
        time.setToNow();
        type = typeOfUser;
    }

    Message(String message, int typeOfUser, String fullName){

        this.message = message;
        time = new Time();
        time.setToNow();
        type = typeOfUser;
        this.fullName = fullName;

    }


    String getMessage(){
        return message;
    }

    String getFullName(){
        return fullName;
    }

    String getCreatedAt(){
        return ""+time;
    }

    public int getType() {
        return type;
    }

    public Time getTime() {
        return time;
    }
}
