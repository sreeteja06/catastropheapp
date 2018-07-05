package com.example.sreet.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserData extends AppCompatActivity {
    private Button button;
    private EditText enroll_ed, block_ed, batch_ed, dob_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        if(!isFirstTimeStartApp()) {
            startMainActivity();
            finish();
        }
        else{
            enroll_ed = (EditText)findViewById(R.id.enroll);
            block_ed = (EditText)findViewById(R.id.Block);
            batch_ed = (EditText)findViewById(R.id.Batch);
            dob_ed = (EditText)findViewById(R.id.DOB);
            button = (Button) findViewById(R.id.submit);
            SharedPreferences shPfUser;
            shPfUser = this.getSharedPreferences("com.example.sreet.learning", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = shPfUser.edit();
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personId = acct.getId();
                editor.putString("userId",personId);
                editor.putString("userName",personName);
                editor.apply();
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((enroll_ed.getText().toString().trim().length()>0)||(block_ed.getText().toString().trim().length()>0)||(batch_ed.getText().toString().trim().length()>0)){
                        editor.putString("enroll",enroll_ed.getText().toString().trim());
                        editor.putString("bolck",block_ed.getText().toString().trim());
                        editor.putString("batch",batch_ed.getText().toString().trim());
                        editor.putString("DOB",dob_ed.getText().toString().trim());
                        editor.apply();
                        setFirstTimeStartStatus(false);
                        startActivity(new Intent(new Intent(UserData.this, MainActivity.class)));
                        finish();
                    }
                    else{
                        Toast.makeText(UserData.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean isFirstTimeStartApp() {
        SharedPreferences ref = getApplicationContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return ref.getBoolean("UserDataEntered", true);
    }

    private void setFirstTimeStartStatus(boolean stt) {
        SharedPreferences ref = getApplicationContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("UserDataEntered", stt);
        editor.commit();
    }

    private void startMainActivity(){
        setFirstTimeStartStatus(false);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct == null) {
            startActivity(new Intent(UserData.this, SignIn.class));
            finish();
        }

        else{
            startActivity(new Intent(UserData.this, MainActivity.class));
            finish();
        }

    }
}
