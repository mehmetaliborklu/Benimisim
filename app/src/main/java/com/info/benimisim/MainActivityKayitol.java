package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityKayitol extends AppCompatActivity {
    private EditText editTextisim,editTextsoyisim,editTexttelefon,editTextemail,editTextpassword;
    private Button buttonkayıtol;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private AdView banner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kayitol);


        editTextisim=findViewById(R.id.textviewisimm);
        editTextsoyisim=findViewById(R.id.texttviewemaill);
        editTexttelefon=findViewById(R.id.texttviewtelefonn);
        editTextemail=findViewById(R.id.texttviewemaill);
        editTextpassword=findViewById(R.id.textviewpassword);
        buttonkayıtol=findViewById(R.id.buttonkayıtoll);
        progressDialog=new ProgressDialog(MainActivityKayitol.this);
        firebaseAuth=FirebaseAuth.getInstance();
        banner=findViewById(R.id.bannerkayit);
        MobileAds.initialize(MainActivityKayitol.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest=new AdRequest.Builder().build();
        banner.loadAd(adRequest);


        buttonkayıtol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isim=editTextisim.getText().toString();
                String Soyisim=editTextsoyisim.getText().toString();
                String email=editTextemail.getText().toString();
                String telefon=editTexttelefon.getText().toString();
                String parola=editTextpassword.getText().toString();

                if (!TextUtils.isEmpty(isim) && !TextUtils.isEmpty(Soyisim) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(telefon) && !TextUtils.isEmpty(parola) ){
                    progressDialog.setTitle("Kaydediliyor...");
                    progressDialog.setMessage("Hesabınız Oluşturuluyor");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    kullanıcıkaydet(isim,parola,email);
                }
                else {
                    Toast.makeText(MainActivityKayitol.this,"Lütfen Gerekli Alanları Doldurunuz",Toast.LENGTH_SHORT).show();

                }

            }

            private void kullanıcıkaydet(String isim, String parola, String email) {
                firebaseAuth.createUserWithEmailAndPassword(email,parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivityKayitol.this,MainActivityGiris.class));
                        }
                        else {
                            progressDialog.dismiss();
                            String mesaj=task.getException().getMessage().toString();
                            Toast.makeText(MainActivityKayitol.this,mesaj,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });







    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MainActivityKayitol.this,MainActivityGiris.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}