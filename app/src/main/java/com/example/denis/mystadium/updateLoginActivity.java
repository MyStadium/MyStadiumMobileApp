package com.example.denis.mystadium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updateLoginActivity extends AppCompatActivity {

    private EditText txtPrenom;
    private EditText txtNom;
    private EditText txtMail;
    private Button btnValidateUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtPrenom = (EditText)findViewById(R.id.editPrenom);
        txtNom = (EditText)findViewById(R.id.editNom);
        txtMail = (EditText)findViewById(R.id.editMail);
        btnValidateUpdate = (Button) findViewById(R.id.btnValidateUpdate);
        btnValidateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnValidateClicked();
            }
        });

        setContentView(R.layout.activity_update_login);
    }

    public void btnValidateClicked(){

    }
}
