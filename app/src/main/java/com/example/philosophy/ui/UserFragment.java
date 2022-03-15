package com.example.philosophy.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.philosophy.MainActivity;
import com.example.philosophy.R;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.UsersEntity;
import com.example.philosophy.details.UserActivity;
import com.example.philosophy.login.UserLoginActivity;
import com.example.philosophy.user.FollowCharactersActivity;
import com.example.philosophy.user.FollowGenresActivity;
import com.example.philosophy.utils.ImageUtil;

import java.util.Objects;

public class UserFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        final ImageView userPic = view.findViewById(R.id.image_userPic);
        final TextView userName = view.findViewById(R.id.text_userName);
        final TextView userProfile = view.findViewById(R.id.text_userProfile);

        final TextView userInfo = view.findViewById(R.id.text_userInfo);
        final TextView userCharacters = view.findViewById(R.id.text_userCharacters);
        final TextView userGenres = view.findViewById(R.id.text_userGenres);

        final TextView userLogin = view.findViewById(R.id.text_userLogin);

        final SharedPreferences sp = getContext().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        final String uTel = sp.getString("userTel", "");

        if (uTel != null && !uTel.equals("")) {
            UsersEntity userEntity = AppDatabase.getInstance(getActivity()).usersDao().queryByTel(uTel);
            int uNum = userEntity.getuNum();
            UsersEntity user = AppDatabase.getInstance(getActivity()).usersDao().queryByNum(uNum);
            if (user.getuPic() != null && !user.getuPic().equals("")) {
                userPic.setImageBitmap(ImageUtil.getImage(user.getuPic()));
            }
            if (user.getuName() != null && !user.getuName().equals("")) {
                userName.setText(user.getuName());
            }
            if (!Objects.equals(user.getuProfile(), null) && !Objects.equals(user.getuProfile(), "")) {
                userProfile.setText(user.getuProfile());
            }
            userLogin.setText("退出登录");
        }

        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uTel != null && uTel != "") {
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "您尚未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userCharacters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uTel != null && !Objects.equals(uTel, "")) {
                    Intent intent = new Intent(getActivity(), FollowCharactersActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "您尚未登录", Toast.LENGTH_SHORT).show();
                }

            }
        });

        userGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uTel != null && !Objects.equals(uTel, "")) {
                    Intent intent = new Intent(getActivity(), FollowGenresActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "您尚未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uTel != null && !Objects.equals(uTel, "")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.remove("userTel");
                    editor.remove("userPwd");
                    editor.apply();
                    Toast.makeText(getActivity(), "已退出登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
