package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;



public class LoginActivity extends AppCompatActivity {

    private EditText etUserId, etPassword1, etPassword2;
    private CheckBox cbRemUser, cbRemLogin;
    private boolean isUserExist = false;

    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences sharedPreferences;
    private String existUserid = "";
    private boolean wasOpened = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        isUserExist = sharedPreferences.contains("RM_LOGIN");

        boolean isUserIdRemembered = false;

        if (isUserExist) {
            if (sharedPreferences.getBoolean("RM_LOGIN", false)) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                return;
            }
            isUserIdRemembered = sharedPreferences.getBoolean("RM_USER_ID", false);
            if (isUserIdRemembered) {
                existUserid = sharedPreferences.getString("USER_ID", "");

            }
        }


        setContentView(R.layout.activity_login);


        etUserId = findViewById(R.id.login_email);
        etUserId.setText(existUserid);
        etPassword1 = findViewById(R.id.login_password);
        etPassword2 = findViewById(R.id.login_re_password);
        if (isUserExist) {
            etPassword2.setVisibility(View.GONE);
        }
        cbRemUser = findViewById(R.id.login_remember_pass);
        cbRemLogin = findViewById(R.id.login_remember_login);
        cbRemUser.setChecked(isUserIdRemembered);

        findViewById(R.id.login_Exit_btn).setOnClickListener(r -> finish());
        findViewById(R.id.login_btn).setOnClickListener(r -> {
            String userId = etUserId.getText().toString();
            String password1 = etPassword1.getText().toString();
            String password2 = etPassword2.getText().toString();
            boolean isUserRemembered = cbRemUser.isChecked();
            boolean isLoginRemembered = cbRemLogin.isChecked();

            StringBuilder str = new StringBuilder();
            if (TextUtils.isEmpty(userId) || userId.length() < 4 || userId.length() > 8) {
                str.append("Invalid user id, ");
            }

            if (TextUtils.isEmpty(password1) || password1.length() < 4 || password1.length() > 6) {
                str.append("Invalid Password, ");
            }
            if (!isUserExist && (TextUtils.isEmpty(password2) || password2.length() < 4 || password2.length() > 6)) {
                str.append("Invalid password re-entered, ");
            }


            if (isUserExist) {
                str.append("User exist , ");
                if (!userId.equals(existUserid)) {
                    str.append("User ID didn't match, where " + userId + "!=" + existUserid);
                }
                String existingPassword = sharedPreferences.getString("PASSWORD", "");
                if (!password1.equals(existingPassword)) {
                    str.append(" Password didn't match, where " + password1 + "!=" + existingPassword);
                }
                if (str.toString().length() != 0) {
                    ((TextView) findViewById(R.id.login_error_view)).setText(str.toString());
                    return;
                }
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            } else {
                if (!password1.equals(password2)) {
                    str.append("Password didn't match, ");
                }
                if (str.toString().length() != 0) {
                    ((TextView) findViewById(R.id.login_error_view)).setText(str.toString());
                    return;
                }
                prefsEditor = sharedPreferences.edit();
                prefsEditor.putString("USER_ID", userId);
                prefsEditor.putString("PASSWORD", password1);
                prefsEditor.putBoolean("RM_USER_ID", isUserRemembered);
                prefsEditor.putBoolean("RM_LOGIN", isLoginRemembered);
                prefsEditor.apply();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        wasOpened = true;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (wasOpened) {
            finish();
        }

    }

}