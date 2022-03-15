package com.example.philosophy.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.philosophy.R;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.UsersEntity;

import java.util.regex.Pattern;

import static java.sql.Types.NULL;

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String DATABASE_NAME = "Philosophy.db";
    private EditText userTel;
    private EditText userPwd;
    private EditText repeatPwd;
    private Button userRegister;

    private UsersEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        init();
    }

    protected void init() {
        userTel = findViewById(R.id.edit_userTel);
        userPwd = findViewById(R.id.edit_userPwd);
        repeatPwd = findViewById(R.id.edit_repeatPwd);
        userRegister = findViewById(R.id.btn_userRegister);

        userTel.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if (!Character.isLetterOrDigit(source.charAt(i)) &&
                                    !Character.toString(source.charAt(i)).equals("_")) {
                                Toast.makeText(UserRegisterActivity.this, "只能使用'_'、字母、数字、汉字注册", Toast.LENGTH_SHORT).show();
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });


        userTel.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    userTel.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(userTel.getWindowToken(), 0);
                }
                return false;
            }
        });

        userPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = v.getText().toString();
                    //设置密码长度有问题，判断editText的输入长度需要重新理解
                    System.out.println(" v: ****** v :" + s.length());
                    if (s.length() >= 6) {
                        System.out.println(" ****** s :" + s.length());
                        userPwd.clearFocus();
                        InputMethodManager imm =
                                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userPwd.getWindowToken(), 0);
                    } else {
                        Toast.makeText(UserRegisterActivity.this, "密码设置最少为6位", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        repeatPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    repeatPwd.clearFocus();
                    InputMethodManager im =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(repeatPwd.getWindowToken(), 0);
                }
                return false;
            }
        });

        userRegister.setOnClickListener(this);
        //btn_cancel = (Button) findViewById(R.id.btn_cancle);
        //btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_userRegister:
                if (CheckIsDataAlreadyInDBorNot(userTel.getText().toString())) {
                    Toast.makeText(this, "该手机号码已被注册", Toast.LENGTH_SHORT).show();
                } else {
                    String uTel = userTel.getText().toString();
                    String uPwd = userPwd.getText().toString();
                    if (!Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$").matcher(uTel).matches()) {
                        Toast.makeText(UserRegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }
                    if (!Pattern.compile("^\\w{5,17}$").matcher(uPwd).matches()) {
                        Toast.makeText(UserRegisterActivity.this, "密码只允许使用字母、数字和下划线，且长度在6-18位之间", Toast.LENGTH_SHORT).show();
                    } else {
                        if (userPwd.getText().toString().trim().
                                equals(repeatPwd.getText().toString())) {
                            registerUserInfo(userTel.getText().toString(),
                                    userPwd.getText().toString());
                            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                            Intent register_intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                            startActivity(register_intent);
                        } else {
                            Toast.makeText(this, "两次输入密码不同，请重新输入",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 利用SharedPreferences进行默认登陆设置
     */
    private void saveUserInfo() {
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uId", userTel.getText().toString());
        //判断注册时的两次密码是否相同
        if (userPwd.getText().toString().equals(repeatPwd.getText().toString())) {
            editor.putString("uPass", userPwd.getText().toString());
        }
        editor.apply();
    }

    /**
     * 利用sql创建嵌入式数据库进行注册访问
     */

    private void registerUserInfo(final String uTel, final String uPwd) {
        user = new UsersEntity();
        user.setuNum(NULL);
        user.setuTel(uTel);
        user.setuPwd(uPwd);

        AppDatabase.getInstance(this).usersDao().insert(user);
    }

    /**
     * 检验用户名是否已经注册
     */
    public boolean CheckIsDataAlreadyInDBorNot(String value) {
        Cursor cursor = AppDatabase.getInstance(this).usersDao().queryByTelCursor(value);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
