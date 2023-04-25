package com.example.linkmemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BookFragment extends Fragment {
    View view;
    ListView translationList;
    TextView wordTxt;
    ArrayAdapter<String> adapter;
    String curWord = "Hello";
    String[] wordMeaning = {"Hello", "World"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book, container, false);
        translationList = (ListView) view.findViewById(R.id.translate_List);
        wordTxt = (TextView) view.findViewById(R.id.word_Txt);

        wordTxt.setText(curWord);
        adapter = new ArrayAdapter<>(view.getContext(), R.layout.trans_listview, R.id.transTextView, wordMeaning);
        translationList.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    public void updateWord(String data){
        String[] parts = data.split("-");
        String[] wordAndMeaning = parts[0].split(";");
        curWord = wordAndMeaning[0];
        wordMeaning = new String[wordAndMeaning.length - 1];
        for (int i = 0; i < wordMeaning.length; i++)
            wordMeaning[i] = wordAndMeaning[i + 1];
    }
}