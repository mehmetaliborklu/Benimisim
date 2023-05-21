package com.info.benimisim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.util.List;

public class yakinilanadapter extends RecyclerView.Adapter<yakinilanadapter.yakinilannesnetutucu>  {
    private Context mcontext;
    private List<Kullanıcı> yakinilanlist;


    public yakinilanadapter() {
    }

    @NonNull
    @Override
    public yakinilannesnetutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.yakinilanlarcard,parent,false);
        return new yakinilannesnetutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull yakinilannesnetutucu holder, int position) {
        Kullanıcı yakinilan= yakinilanlist.get(position);
        holder.textViewistanimi.setText(yakinilan.getBaslik());
        holder.textViewücret.setText(yakinilan.getücret()+" "+"₺");
        holder.textViewkonum.setText(yakinilan.getMahalle());
        holder.textViewtelefon.setText(yakinilan.getTelefon());
        holder.textViewistanimidetay.setText(yakinilan.getIş_Tanımı());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mcontext,yakinilandetay.class);
                intent.putExtra("istanımı",holder.textViewistanimidetay.getText().toString());
                intent.putExtra("ücret",holder.textViewücret.getText().toString());
                intent.putExtra("konum",holder.textViewkonum.getText().toString());
                intent.putExtra("telefon",holder.textViewtelefon.getText().toString());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return yakinilanlist.size();
    }

    public yakinilanadapter(Context mcontext, List<Kullanıcı> yakinilanlist) {
        this.mcontext = mcontext;
        this.yakinilanlist = yakinilanlist;
    }

     public class yakinilannesnetutucu extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ConstraintLayout constraintLayout;
        public TextView textViewistanimi,textViewücret,textViewkonum,textViewtelefon,textViewistanimidetay;


         public yakinilannesnetutucu(View itemView) {
             super(itemView);
             cardView=itemView.findViewById(R.id.cardviewyakinilan);
             constraintLayout=itemView.findViewById(R.id.csyakinilan);
             textViewistanimi=itemView.findViewById(R.id.yakinilanistanimi);
             textViewkonum=itemView.findViewById(R.id.yakinilankonum);
             textViewücret=itemView.findViewById(R.id.yakinilanücret);
             textViewtelefon=itemView.findViewById(R.id.textViewtelefon);
             textViewistanimidetay=itemView.findViewById(R.id.textViewistanimmidetay);
         }
     }


}
