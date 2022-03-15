package com.example.philosophy.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.philosophy.MainActivity;
import com.example.philosophy.R;
import com.example.philosophy.db.AppDatabase;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userTel;
    private EditText userPwd;
    private Button userLogin;
    private TextView userRegister;
    private TextView adminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        init();
    }

    private void init() {
        userTel = findViewById(R.id.edit_userTel);
        userPwd = findViewById(R.id.edit_userPwd);
        userLogin = findViewById(R.id.btn_userLogin);
        userRegister = findViewById(R.id.text_userRegister);
        adminLogin = findViewById(R.id.text_adminLogin);

        userTel.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    userTel.clearFocus();
                }
                return false;
            }
        });

        userPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    userPwd.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(userPwd.getWindowToken(), 0);
                }
                return false;
            }
        });

        userLogin.setOnClickListener(this);
        userRegister.setOnClickListener(this);
        adminLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_userLogin:
                if (userTel.getText().toString().trim().equals("") | userPwd.getText().
                        toString().trim().equals("")) {
                    Toast.makeText(this, "请输入账号或者注册账号", Toast.LENGTH_SHORT).show();
                } else {
                    readUserInfo();
                }
                break;
            case R.id.text_userRegister:
                Intent userRegisterIntent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(userRegisterIntent);
                break;
            case R.id.text_adminLogin:
                Intent adminLoginIntent = new Intent(UserLoginActivity.this, AdminLoginActivity.class);
                startActivity(adminLoginIntent);
                break;
            default:
                break;
        }
    }

    protected void readUserInfo() {
        if (login(userTel.getText().toString(), userPwd.getText().toString())) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
            intent.putExtra("userTel", userTel.getText().toString());
            startActivity(intent);

            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("userTel", userTel.getText().toString());
            edit.putString("userPwd", userPwd.getText().toString());
            edit.apply();

            SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
            String uTel = sp.getString("userTel", "");
        } else {
            Toast.makeText(this, "账户或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 验证登录信息
     */
    public boolean login(String uTel, String uPwd) {
        Cursor cursor = AppDatabase.getInstance(this).usersDao().queryByTelPwdCursor(uTel, uPwd);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        //SQLiteStudioService.instance().stop();
        super.onDestroy();
    }
}
