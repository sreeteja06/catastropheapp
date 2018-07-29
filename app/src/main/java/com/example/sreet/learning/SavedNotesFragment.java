package com.example.sreet.learning;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SavedNotesFragment extends Fragment {
    private List<String> fileList;
    //EditText searchFile;
    SavedNotesAdapter adapter;
    RecyclerView recyclerView;
    EditText esearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main3, container, false);
        fileList = new ArrayList<String>();


        recyclerView = rootView.findViewById(R.id.review);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SavedNotesAdapter(getActivity(), fileList);
        SavedNotesFragment.WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new SavedNotesFragment.WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        wrapContentLinearLayoutManager.setReverseLayout(true);
        wrapContentLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
        registerForContextMenu(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        esearch = getActivity().findViewById(R.id.savedsearch);
        esearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().toLowerCase());

            }
        });


        File root = new File(Environment
                .getExternalStorageDirectory() + "/Ifhe/Documents");
        ListDir(root);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    void ListDir(File f) {
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files) {
            String s = file.getPath();
            String res[] = s.split("/");
            int h = res.length;
            fileList.add(res[h - 1]);
            adapter.notifyDataSetChanged();

        }


    }
    public  void filter(String text) {
        // Log.i("test",text);
        List<String> temp = new ArrayList<>();
        for (String d : fileList) {
            if (d.toLowerCase().contains(text)) {
                temp.add(d);
            }
        }
        adapter.updateList(temp);
    }
    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context, int horizontal, boolean b) {
            super(context);
        }

        //... constructor
        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probe", "meet a IOOBE in RecyclerView");
            }
        }

    }
}

