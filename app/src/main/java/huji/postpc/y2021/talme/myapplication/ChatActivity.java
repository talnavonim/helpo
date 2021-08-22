package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class    ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MessageListAdapter adapter;
    ChatMessageHolder chatMessagesHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recycler_gchat);
        adapter = new MessageListAdapter();
        chatMessagesHolder = new ChatMessageHolder();
        adapter.setChatMessagesHolder(chatMessagesHolder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        EditText editText = findViewById(R.id.editTextInsertTask);
        Button sendButton = findViewById(R.id.button_gchat_send);

        sendButton.setOnClickListener(v->{
            int length = editText.getText().toString().length();
            if(length != 0){
                //TODO call amitsour model
                chatMessagesHolder.addChat(editText.getText().toString(), 1);

                adapter.notifyDataSetChanged();
                editText.setText("");

            }

        });

    }
}