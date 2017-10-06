package com.example.karim.autochecketmat;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by V on 06-10-17.
 */

public class AdapterView extends ArrayAdapter<String> {

    int groupid;
    String[] item_list;
    ArrayList<String> desc;
    Context context;
    public AdapterView(Context context, int vg, int id, String[] item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list=item_list;

    }
}
