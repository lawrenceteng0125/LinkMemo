package com.example.linkmemo;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginFragment extends Fragment {
    View view;

    TextView welcomeTxt;
    EditText usernameEditText;
    EditText passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        welcomeTxt = view.findViewById(R.id.welcomeTxt);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        MainActivity.getInstance().bottomNavigationView.setVisibility(View.GONE);
        view.findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEditText.getText().toString().isEmpty()) {
                    MainActivity.showText(view, "用户名不得为空");
                    return;
                }
                if (passwordEditText.getText().toString().isEmpty()) {
                    MainActivity.showText(view, "密码不得为空");
                    return;
                }
                if (!MainActivity.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString())) {
                    MainActivity.showText(view, "用户名或密码不正确");
                    return;
                }
                MainActivity.getInstance().bottomNavigationView.setVisibility(View.VISIBLE);
                MainActivity.getInstance().switchFragment(1, false);
            }
        });

        view.findViewById(R.id.btnToSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().switchFragment(4, false);
            }
        });
        //设置logo渐变
        int startColor = Color.parseColor("#800CDD");
        int endColor = Color.parseColor("#3BA3F2");
        welcomeTxt.setTextColor(startColor);
        Shader textShader = new LinearGradient(0, 0,
                welcomeTxt.getPaint().measureText(welcomeTxt.getText().toString()), welcomeTxt.getTextSize(),
                new int[]{startColor, endColor},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        welcomeTxt.getPaint().setShader(textShader);
        return view;
    }
}