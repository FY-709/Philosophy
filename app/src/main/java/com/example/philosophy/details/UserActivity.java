package com.example.philosophy.details;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.philosophy.MainActivity;
import com.example.philosophy.R;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.UsersEntity;
import com.example.philosophy.utils.ImageUtil;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_PICTURE = 0;

    private ImageView userPic;
    private EditText userName;
    private EditText userProfile;
    private EditText userTel;
    private EditText userPwd;
    private Button resetUser;
    private Button updateUser;

    private int uNum;
    private String uPic;
    private String uName;
    private String uProfile;
    private String uTel;
    private String uPwd;
    private UsersEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        init();
        resetUser();
    }

    private void init() {
        SharedPreferences sp = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        uTel = sp.getString("userTel", "");
        UsersEntity userEntity = AppDatabase.getInstance(this).usersDao().queryByTel(uTel);
        uNum = userEntity.getuNum();
        user = AppDatabase.getInstance(this).usersDao().queryByNum(uNum);
        uPic = user.getuPic();
        uName = user.getuName();
        uTel = user.getuTel();
        uProfile = user.getuProfile();
        uPwd = user.getuPwd();

        userPic = findViewById(R.id.image_userPic);
        userName = findViewById(R.id.edit_userName);
        userProfile = findViewById(R.id.edit_userProfile);

        userTel = findViewById(R.id.edit_userTel);
        userPwd = findViewById(R.id.edit_userPwd);

        resetUser = findViewById(R.id.btn_resetUser);
        updateUser = findViewById(R.id.btn_updateUser);

        userPic.setOnClickListener(this);
        resetUser.setOnClickListener(this);
        updateUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_userPic:
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PICTURE);
                break;
            case R.id.btn_resetUser:
                resetUser();
                break;
            case R.id.btn_updateUser:
                uName = userName.getText().toString();
                uProfile = userProfile.getText().toString();

                if (!userTel.getText().toString().equals(uTel)) {
                    uTel = userTel.getText().toString();

                    SharedPreferences sp = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.remove("userTel");
                    editor.remove("userPwd");
                    editor.apply();
                    Toast.makeText(this, "修改成功，请重新登录账号", Toast.LENGTH_SHORT).show();
                }

                uPwd = userPwd.getText().toString();

                if (updateUser(uNum, uPic, uTel, uPwd, uName, uProfile) == 1) {
                    Toast.makeText(UserActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(UserActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                } else {
                    Toast.makeText(UserActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PICTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = ImageUtil.handleImageOnKitKat(this, data);
                userPic.setImageBitmap(bitmap);
                uPic = ImageUtil.imagePath;
            }
        }
    }

    private void resetUser() {
        userPic.setImageBitmap(ImageUtil.getImage(user.getuPic()));
        userName.setText(user.getuName());
        userProfile.setText(user.getuProfile());

        userTel.setText(user.getuTel());
        userPwd.setText(user.getuPwd());
    }

    private int updateUser(int uNum, String uPic, String uTel, String uPwd, String uName, String uProfile) {
        UsersEntity userEntity = new UsersEntity();
        userEntity.setuNum(uNum);
        userEntity.setuPic(uPic);
        userEntity.setuTel(uTel);
        userEntity.setuPwd(uPwd);
        userEntity.setuName(uName);
        userEntity.setuProfile(uProfile);
        return AppDatabase.getInstance(this).usersDao().update(userEntity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
