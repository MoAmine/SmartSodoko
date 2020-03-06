package com.example.smartsodoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChoixActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter<Integer> leschoix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);

        listView = (ListView) findViewById(R.id.listeView);
        Integer [] meschoix = {1,2,3,4,5,6,7,8,9};
        leschoix = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meschoix);
        listView.setAdapter(leschoix);
        listView.setOnItemClickListener(this) ;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int choix = position +1;
        Intent intentchoix = new Intent();
        intentchoix.putExtra("result",choix);
        setResult(RESULT_OK,intentchoix);
         Log.i("TAG", " lv----------------" +choix );
        finish();

    }


}

