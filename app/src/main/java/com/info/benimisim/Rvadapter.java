package com.info.benimisim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Rvadapter extends RecyclerView.Adapter<Rvadapter.RvAdapternesnetutucu> {
    private Context mcontext;
    private List<Kullanıcı> kullanıcıList;

    public Rvadapter() {
    }

    @NonNull
    @Override
    public RvAdapternesnetutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardtasarim,parent,false);

        return new RvAdapternesnetutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapternesnetutucu holder, int position) {
        Kullanıcı kullanıcı=kullanıcıList.get(position);
        holder.textView.setText(kullanıcı.getBaslik());
        holder.textView3.setText(kullanıcı.getid());
        holder.textView3.setVisibility(View.INVISIBLE);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mcontext,DetayActivity.class);
                intent.putExtra("id",holder.textView3.getText().toString());
                mcontext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return kullanıcıList.size();
    }

    public Rvadapter(Context mcontext, List<Kullanıcı> kullanıcıList) {
        this.mcontext = mcontext;
        this.kullanıcıList = kullanıcıList;
    }

    public class RvAdapternesnetutucu extends RecyclerView.ViewHolder{
        public TextView textView,textView3;
        public CardView cardView;

        public RvAdapternesnetutucu(View view){
            super(view);
            textView=view.findViewById(R.id.textView2);
            textView3=view.findViewById(R.id.textView3);
            cardView=view.findViewById(R.id.cardview);


        }
    }
}
