package huji.postpc.y2021.talme.myapplication;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageHolder {

    List<Message> bubbleChatList;

    public ChatMessageHolder(){
        bubbleChatList = new ArrayList<>();
    }

    public void addChat(String chat, int type){
//        if(type != 1 || type != 2){
//            TODO if the type is not valid
//        }
//        Message bubbleChat = new Message(chat, type);
//        bubbleChatList.add(bubbleChat);
    }

    public void addChat(String chat, int type, String sender){
//        if(type != 1 || type != 2){
//            TODO if the type is not valid
//        }
//        Message bubbleChat = new Message(chat, type, sender);
//        bubbleChatList.add(bubbleChat);
    }

    public List<Message> getChat(){
        return bubbleChatList;
    }


}
