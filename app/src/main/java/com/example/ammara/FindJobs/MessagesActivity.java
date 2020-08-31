package com.example.ammara.FindJobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Api.DataService;
import Api.RetrofitClientInstance;
import Models.Message;
import RVAdapters.MessageListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private Button btn;
    private EditText msg_box;
    private List<Message> messages = new ArrayList<>();
    int conId;
    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Intent intent = getIntent();
        conId = intent.getIntExtra("conId",0);
        SharedPreferences prefs = getSharedPreferences("credentials", MODE_PRIVATE);
        int currentUserId = prefs.getInt("currentUserId", 0);
        getReferences();
        mMessageAdapter = new MessageListAdapter(MessagesActivity.this, messages,currentUserId);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(MessagesActivity.this));
        loadMessages();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calander = Calendar.getInstance();
                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm:ss a");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                String time = simpleTimeFormat.format(calander.getTime());
                String date = simpleDateFormat.format(calander.getTime());

                final Message msg = new Message(0,
                        currentUserId,
                        conId,
                        time+" "+date,
                        msg_box.getText().toString());
                DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
                Call<Message> call = service.sendMsg(conId,
                        msg);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (response.isSuccessful()) {
                            messages.add(msg);
                            msg_box.getText().clear();
                            loadMessages();
                            //mMessageAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MessagesActivity.this, response.code() + "", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {

                    }
                });
            }
        });
        mHandler = new Handler();
        startRepeatingTask();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    private void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void startRepeatingTask() {
        mStatusChecker.run();
    }

    private void loadMessages() {
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<List<Message>> call = service.getAllMsgss(conId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    messages = response.body();
                    mMessageAdapter.setmMessageList(messages);
                    mMessageAdapter.notifyDataSetChanged();
                    mMessageRecycler.scrollToPosition(messages.size()-1);

                } else {
                    Toast.makeText(MessagesActivity.this, response.code() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    private void getReferences() {
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        btn = findViewById(R.id.btn_send);
        msg_box = findViewById(R.id.msg_box);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                loadMessages();
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

}
