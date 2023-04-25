package com.example.linkmemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class LinkFragment extends Fragment {
    View view;
    RadioGroup radioGroup;
    Button centerBtn;
    Button[] linkBtn = new Button[8];
    String curWord = "Hello";
    String[] colors = new String[] {"#800CDD", "#3BA3F2", "#2A83D8"};
    ArrayList<String>[] linkWord = new ArrayList[3];

    private LinkFragment.linkBtnClicked mCallback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (LinkFragment.linkBtnClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement searchClicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null; // => avoid leaking
    }

    public interface linkBtnClicked {
        void sendDataToBook(String word);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_link, container, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        centerBtn = view.findViewById(R.id.centerBtn);
        centerBtn.setText(curWord);
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().switchFragment(3, true);
            }
        });
        linkBtn[0] = view.findViewById(R.id.linkBtn1);
        linkBtn[1] = view.findViewById(R.id.linkBtn2);
        linkBtn[2] = view.findViewById(R.id.linkBtn3);
        linkBtn[3] = view.findViewById(R.id.linkBtn4);
        linkBtn[4] = view.findViewById(R.id.linkBtn5);
        linkBtn[5] = view.findViewById(R.id.linkBtn6);
        linkBtn[6] = view.findViewById(R.id.linkBtn7);
        linkBtn[7] = view.findViewById(R.id.linkBtn8);
        for (Button button : linkBtn) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String data = MainActivity.getInstance().findWord(
                            button.getText().toString().toLowerCase());
                    if (data.contentEquals(""))
                        Toast.makeText(view.getContext(), "暂未收录此单词", Toast.LENGTH_SHORT).show();
                    else {
                        updateWord(data);
                        int id = radioGroup.getCheckedRadioButtonId();
                        if (id == R.id.meanLikeBtn)
                            updateUI(1);
                        else if (id == R.id.lookLikeBtn)
                            updateUI(2);
                        else
                            updateUI(3);
                        mCallback.sendDataToBook(data);
                    }
                }
            });
        }
        if (linkWord[0] != null)
            updateUI(1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.meanLikeBtn)
                    updateUI(1);
                else if (i == R.id.lookLikeBtn)
                    updateUI(2);
                else
                    updateUI(3);
            }
        });
        return view;
    }

    private void setBtnVisible(int cnt)
    {
        for (int i = 0; i < cnt; i++)
            linkBtn[i].setVisibility(View.VISIBLE);
    }
    private void setAllInvisible()
    {
        for (int i = 0; i < linkBtn.length; i++)
            linkBtn[i].setVisibility(View.INVISIBLE);
    }

    private void setBtnColor(int cnt, int color)
    {
        for (int i = 0; i < cnt; i++)
            linkBtn[i].setBackgroundColor(color);
    }
    private void updateUI(int index)
    {
        centerBtn.setText(curWord);
        int siz = linkWord[index - 1].size();
        setAllInvisible();
        setBtnVisible(siz);
        setBtnColor(siz, Color.parseColor(colors[index - 1]));
        for (int i = 0; i < siz; i++)
            linkBtn[i].setText(linkWord[index - 1].get(i));
    }
    public void updateWord(String data)
    {
        String[] parts = data.split("-");
        String[] wordAndMeaning = parts[0].split(";");

        for (int i = 0; i < linkWord.length; i++) {
            linkWord[i] = new ArrayList<>(5);
            String[] words = parts[i + 1].split(";");
            for (String word : words)
                linkWord[i].add(word);
        }
        curWord = wordAndMeaning[0];
    }
}