package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.info.benimisim.databinding.ActivityMapsActivityisaraBinding;

public class MapsActivityisara extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private ActivityMapsActivityisaraBinding binding;
    private DatabaseReference databaseReference;
    private Kullanıcı kullanıcı;
    private String istanımı;
    String ücret;
    String telefon;
    AlertDialog.Builder alertDialog;
    private Button buttongetir;
    private EditText editTextkm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsActivityisaraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        editTextkm=findViewById(R.id.editTextkm);
        buttongetir=findViewById(R.id.buttongetir);

        buttongetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextkm.getText().toString().equals("")){
                    Toast.makeText(MapsActivityisara.this,"Lütfen Km Bilgisi Giriniz",Toast.LENGTH_SHORT).show();

                }
                else {
                    Intent intent=new Intent(MapsActivityisara.this,yakinilan.class);
                    intent.putExtra("ilan",editTextkm.getText().toString());
                    startActivity(intent);
                }


            }
        });




        databaseReference=FirebaseDatabase.getInstance().getReference("Kullanıcılar");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot d:snapshot.getChildren()){
                    kullanıcı=d.getValue(Kullanıcı.class);
                    String konumm=kullanıcı.getKonum().toString();
                    String [] konumbölünmüs =konumm.split(",");
                    Double lat=Double.parseDouble(konumbölünmüs[0]);
                    Double lng=Double.parseDouble(konumbölünmüs[1]);
                    LatLng latlng = new LatLng(lat, lng);
                    ücret=kullanıcı.getücret();
                    telefon=kullanıcı.getTelefon();
                    istanımı=kullanıcı.getIş_Tanımı();
                    String baslik=kullanıcı.getBaslik();

                    Marker marker=mMap.addMarker(new MarkerOptions().position(latlng).title(ücret+" "+"₺").snippet(baslik));
                    marker.setIcon(bitmapDescriptorFromVector(MapsActivityisara.this,R.drawable.marker2));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            ;
            // Add a marker in Sydney and move the camera



        });


    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context,int VectorResId){
        Drawable vectordrawable = ContextCompat.getDrawable(context,VectorResId);
        vectordrawable.setBounds(0,0,vectordrawable.getIntrinsicWidth(),vectordrawable.getIntrinsicHeight());
        Bitmap bitmap=Bitmap.createBitmap(vectordrawable.getIntrinsicWidth(),vectordrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas= new Canvas(bitmap);
        vectordrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MapsActivityisara.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    }
