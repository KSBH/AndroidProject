package com.example.karim.autochecketmat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListUsers extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        populateListView();

    }

    private void populateListView() {
        helper.findUsers();
        ArrayAdapter<String> adapt= new ArrayAdapter<>(this, R.layout.myusers,helper.userss);
        ListView list = (ListView)findViewById(R.id.lv_li_lu);
        list.setAdapter(adapt);
    }
}
