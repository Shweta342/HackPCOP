package com.example.hack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapterClass extends FirebaseRecyclerAdapter<ComplaintList.model, adapterClass.viewholder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adapterClass(@NonNull FirebaseRecyclerOptions<ComplaintList.model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull ComplaintList.model model) {
        viewholder.name.setText(model.getName());
        viewholder.age.setText(String.valueOf(model.getAge()));
        viewholder.phone.setText(String.valueOf(model.getPhone()));
        viewholder.type.setText((CharSequence) model.getType());
        viewholder.description.setText(model.getDescription());
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlevieww,parent,false);
        return new viewholder(view);
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView name, age, phone, type, description;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            phone = itemView.findViewById(R.id.Phone);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.description);
        }
    }
}
