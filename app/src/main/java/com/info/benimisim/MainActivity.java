package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.lang.ref.ReferenceQueue;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Toolbar toolbar;
    private FirebaseAuth mauth;
    private Button buttonisara,buttonisver,buttonilanlarim;
    private DatabaseReference databaseReference;
    private ConstraintLayout cs1,csilan;
    private Animation animation;
    private ImageView imageView;
    private AdView banner;
    private InterstitialAd minterstitialAd;
    private int REQUEST_LOCATİON=1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbarmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout){
            mauth.signOut();
            startActivity(new Intent(MainActivity.this,MainActivityGiris.class));
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cs1=findViewById(R.id.cs1);
        mauth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);
        buttonilanlarim=findViewById(R.id.buttonilanlarim);
        buttonisara=findViewById(R.id.buttonisara);
        buttonisver=findViewById(R.id.buttonisver);
        csilan=findViewById(R.id.csilan);
        imageView=findViewById(R.id.imageView6);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animationlogo);
        imageView.setAnimation(animation);
        banner=findViewById(R.id.banner);

        getlocation();
        Location();

        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
          
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},14);
        }

        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest=new AdRequest.Builder().build();



        InterstitialAd.load(this,"ca-app-pub-4479240638574774/8057154739", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        minterstitialAd = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        minterstitialAd = null;
                    }
                });



        setSupportActionBar(toolbar);





        banner.loadAd(adRequest);


        AnimationDrawable animationDrawable= (AnimationDrawable) cs1.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        AnimationDrawable animationDrawable2= (AnimationDrawable) toolbar.getBackground();
        animationDrawable2.setEnterFadeDuration(4000);
        animationDrawable2.setExitFadeDuration(2000);
        animationDrawable2.start();

        buttonilanlarim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (minterstitialAd != null) {
                    minterstitialAd.show(MainActivity.this);
                    minterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(MainActivity.this,ilanlarimactivity.class));
                        }
                    });
                } else {
                    startActivity(new Intent(MainActivity.this,ilanlarimactivity.class));
                }

            }
        });



        buttonisver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Activityisver.class);
                startActivity(intent);
            }
        });

        buttonisara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (minterstitialAd != null) {
                    minterstitialAd.show(MainActivity.this);
                    minterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(MainActivity.this,MapsActivityisara.class));
                        }
                    });
                } else {
                    startActivity(new Intent(MainActivity.this,MapsActivityisara.class));
                }


            }
        });



    }

    private void getlocation(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }


        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

    private void Location(){
        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(2000);
        LocationSettingsRequest.Builder builder= new LocationSettingsRequest.Builder().addAllLocationRequests(Collections.singleton(locationRequest));
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result= LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response=task.getResult(ApiException.class);
                }
                catch (ApiException e){
                    switch (e.getStatusCode()){
                        case LocationSettingsStatusCodes
                                .RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException= (ResolvableApiException)e;
                                resolvableApiException.startResolutionForResult(MainActivity.this,REQUEST_LOCATİON);

                        } catch (IntentSender.SendIntentException sendIntentException) {
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });
    }



}