package com.info.benimisim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class MainActivityGiris extends AppCompatActivity {
    private EditText editTextusername,editTextpassword;
    private Button buttongirisyap;
    private TextView textViewkayıtol,textviewreset;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private ConstraintLayout cs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_giris);



        progressDialog=new ProgressDialog(this);
        cs=findViewById(R.id.cs2);
        editTextpassword=findViewById(R.id.textviewpassword);
        editTextusername=findViewById(R.id.textviewusername);
        buttongirisyap=findViewById(R.id.buttongirisyap);
        textViewkayıtol=findViewById(R.id.textViewkayıtol);
        textviewreset=findViewById(R.id.textViewreset);

        textviewreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityGiris.this,resetactivity.class));
            }
        });

        auth=FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null){
        Toast.makeText(MainActivityGiris.this,"Benim İşim'e Hoşgeldiniz",Toast.LENGTH_SHORT).show();

        }
        else {

            startActivity(new Intent(MainActivityGiris.this,MainActivity.class));


        }


        textViewkayıtol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityGiris.this,MainActivityKayitol.class));
                finish();
            }
        });


        buttongirisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=editTextusername.getText().toString();
                String password=editTextpassword.getText().toString();

                if (!TextUtils.isEmpty(username)&& !TextUtils.isEmpty(password)){
                    progressDialog.setTitle("Oturum Açılıyor...");
                    progressDialog.setTitle("Hesabınıza Giriş Yapılıyor");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    girisyap(username,password);

                }
                else {
                    Toast.makeText(MainActivityGiris.this,"Lütfen Gerekli Alanları Doldurunuz",Toast.LENGTH_SHORT).show();
                }
            }

            private void girisyap(String username, String password) {

                auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivityGiris.this,MainActivity.class));

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivityGiris.this,"Giriş Yapılamadı",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }


        });







    }





}