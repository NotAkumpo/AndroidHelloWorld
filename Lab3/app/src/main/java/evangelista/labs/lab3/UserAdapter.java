package evangelista.labs.lab3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class UserAdapter extends RealmRecyclerViewAdapter<User, UserAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView usernameDisplay;
        TextView passwordDisplay;
        ImageButton deleteButton;
        ImageButton editButton;
        AlertDialog.Builder builder;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            usernameDisplay = itemView.findViewById(R.id.usernameDisplay);
            passwordDisplay = itemView.findViewById(R.id.passwordDisplay);

            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }


    AdminActivity activity;

    public UserAdapter(AdminActivity activity, @Nullable OrderedRealmCollection<User> data, boolean autoUpdate){
        super(data, autoUpdate);

        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = activity.getLayoutInflater().inflate(R.layout.row_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        User u = getItem(position);

        String uuid = u.getUuid();
        holder.usernameDisplay.setText(u.getName());
        holder.passwordDisplay.setText(u.getPassword());

        holder.builder = new AlertDialog.Builder(activity);
        holder.deleteButton.setTag(u);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.builder.setTitle("Caution: ")
                                .setMessage("Are you sure you want to delete this user?")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        activity.delete((User) v.getTag());
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .show();


            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openEdit(uuid);
            }
        });

    }

}
