package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammara.FindJobs.Constants;
import com.example.ammara.FindJobs.Messages;
import com.example.ammara.FindJobs.R;

import java.util.List;

import RestfullServices.Client;
import RestfullServices.FindJobService;
import ModelClasses.Conversation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ConsAdapter
        extends RecyclerView.Adapter<ConsAdapter.RvHolder> {
    private final List<Conversation> conversations;
    private LayoutInflater layoutInflater;
    private Context context;

    public ConsAdapter(List<Conversation> conversations, Context context) {
        this.context = context;
        this.conversations = conversations;
        layoutInflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public ConsAdapter.RvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.one_conversation, viewGroup, false);
        return new RvHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsAdapter.RvHolder rvHolder, final int i) {
        //String s = conversations.get(i)._id;
        rvHolder.name.setText(conversations.get(i).getName());
        rvHolder.conId = conversations.get(i).getId();

    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class RvHolder
            extends RecyclerView.ViewHolder{
        final ConsAdapter adapter;
        public final TextView name;
        int conId = 0;

        public RvHolder(@NonNull View itemView, final ConsAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            name = itemView.findViewById(R.id.name);
            //name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_msg_name ,0, 0, 0);
            name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final CharSequence[] items = {"Delete", "Cancel"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Select The Action");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if(item == 0){
                                SharedPreferences prefs = context.getSharedPreferences(Constants.file, MODE_PRIVATE);
                                String userType = prefs.getString("type", "nothing");
                                FindJobService service = Client.getClient().create(FindJobService.class);
                                Call<Conversation> call = service.deleteCon(conId,userType);
                                call.enqueue(new Callback<Conversation>() {
                                    @Override
                                    public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(context,"Deleted!",Toast.LENGTH_SHORT).show();
                                            adapter.conversations.remove(getLayoutPosition());
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Conversation> call, Throwable t) {

                                    }
                                });
                            }else if(item == 1){
                               // Toast.makeText(context,"Cancel Clicked",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();
                    return true;
                }
            });
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(context, Messages.class);
                    myIntent.putExtra("conId",conId);
                    context.startActivity(myIntent);
                    //Toast.makeText(context,name.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
