package com.univalle.myappturismo;

import android.content.Intent;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    ArrayList<model> datalist;

    public myadapter(ArrayList<model> datalist){this.datalist = datalist; }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent,false);
        return new myviewholder(view);
    }

    public void onBindViewHolder(@NonNull final myviewholder holder, final int position){
        holder.t1.setText(datalist.get(position).getNombre());
        holder.t2.setText(datalist.get(position).getDescripcion());


        holder.t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.t1.getContext(), DetailsActivity.class);
                    intent.putExtra("uNombre", datalist.get(position).getNombre());
                    intent.putExtra("uDescripcion", datalist.get(position).getDescripcion());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.t1.getContext().startActivity(intent);
                }
            });
    }

    public int getItemCount() {return datalist.size();}

    class myviewholder extends RecyclerView.ViewHolder{
        TextView t1, t2;
        Double l1, l2;
        public myviewholder(@NonNull View itemView){
            super(itemView);
            t1 = itemView.findViewById(R.id.txt_nombre);
            t2 = itemView.findViewById(R.id.txt_descripcion);
        }
    }
}
