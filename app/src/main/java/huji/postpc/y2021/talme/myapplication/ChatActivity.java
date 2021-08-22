package huji.postpc.y2021.talme.myapplication;

//import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MessageListAdapter adapter;
//        ChatMessagesHolder chatMessagesHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recycler_gchat);

        adapter = new MessageListAdapter();
        bubbleAdapter.setChatMessagesHolder(chatMessagesHolder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bubbleAdapter);
        EditText editText = findViewById(R.id.editTextInsertTask);
        FloatingActionButton floatingActionButton = findViewById(R.id.buttonSend);
        floatingActionButton.setOnClickListener(v->{
            int length = editText.getText().toString().length();
            if(length != 0){
                //TODO call amitsour model
                chatMessagesHolder.addChat(editText.getText().toString(), true);

                bubbleAdapter.notifyDataSetChanged();
                botMessenger.setUserMessage(editText.getText().toString());
                editText.setText("");
                chatMessagesHolder.addChat(botMessenger.responseMessage(), false);
                bubbleAdapter.notifyDataSetChanged();
            }

        });

    }
}