package com.example.linkmemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SignFragment extends Fragment {
    View view;
    EditText signNameEditText;
    EditText pwdEditText1;
    EditText pwdEditText2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign, container, false);
        signNameEditText = view.findViewById(R.id.signNameEditText);
        pwdEditText1 = view.findViewById(R.id.signPwdEditText1);
        pwdEditText2 = view.findViewById(R.id.signPwdEditText2);
        view.findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1, str2, str3;
                str1 = signNameEditText.getText().toString();
                str2 = pwdEditText1.getText().toString();
                str3 = pwdEditText2.getText().toString();
                if (str1.isEmpty()) {
                    MainActivity.showText(view, "用户名不得为空");
                    return;
                }
                if (str2.isEmpty()) {
                    MainActivity.showText(view, "密码不得为空");
                    return;
                }
                if (str3.isEmpty()) {
                    MainActivity.showText(view, "请确认密码");
                    return;
                }
                if (!str2.equals(str3)) {
                    MainActivity.showText(view, "两次密码不一致");
                    return;
                }
                if (!MainActivity.SignIn(str1, str2)) {
                    MainActivity.showText(view, "注册失败");
                    return;
                }
                MainActivity.showText(view, "注册成功");
                MainActivity.getInstance().switchFragment(0, false);
            }
        });
        view.findViewById(R.id.btnCancelSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().switchFragment(0, false);
            }
        });
        return view;
    }
}