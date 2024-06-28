package com.example.realmadapterdemo2024;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class CatAdapter extends RealmRecyclerViewAdapter<Cat, CatAdapter.ViewHolder> {

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView name;
        TextView breed;
        TextView deceased;

        ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            name = itemView.findViewById(R.id.name);
            breed = itemView.findViewById(R.id.breed);
            deceased = itemView.findViewById(R.id.deceased);

            // initialize the buttons in the layout
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }




    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    MainActivity activity;

    public CatAdapter(MainActivity activity, @Nullable OrderedRealmCollection<Cat> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.row_layout, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

        // assign view to the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // gives you the data object at the given position
        Cat u = getItem(position);


        // copy all the values needed to the appropriate views
        holder.name.setText(u.getName());
        holder.breed.setText(u.getBreed());

        if (u.isDeceased())
        {
            holder.deceased.setText("Deceased");
        }
        else
        {
            holder.deceased.setText("Alive");
        }


        // NOTE: MUST BE A STRING NOT INTs, etc.
        // String.valueOf() converts most types to a string
        // holder.age.setText(String.valueOf(u.getAge()));

        // do any other initializations here as well,  e.g. Button listeners
//        holder.delete.setTag(u);
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.delete((Cat) view.getTag());
//            }
//        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.delete(u);
            }
        });




    }

}
