package com.example.philosophy.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.philosophy.R;
import com.example.philosophy.details.AdminActivity;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String USERNAME = "Admin";
    public static final String PASSWORD = "0709";
    private EditText adminName;
    private EditText adminPwd;
    private Button adminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        init();
    }

    private void init() {
        adminName = findViewById(R.id.edit_adminName);
        adminPwd = findViewById(R.id.edit_adminPwd);
        adminLogin = findViewById(R.id.btn_adminLogin);

        adminLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_adminLogin:
                login();
                break;
        }
    }

    private void login() {
        //获取用户输入的帐号，密码，做校验
        String username = adminName.getText().toString().trim();
        //判断是否输入了账号
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = adminPwd.getText().toString().trim();

        //是否输入了密码
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        //调用调用服务端的登录接口
        if ((USERNAME.equals(username) && PASSWORD.equals(password))) {

            //登录成功，进入首页
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);

            //关闭当前页面
            finish();
        } else {
            //登录失败，进行提示
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}
