package com.example.jirka.listview4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    List<String> skupinyList;
    List<String> polozkyList;

    Map<String, List<String>> skupinyOS;
    ExpandableListView expandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vytvorSkupiny();
        vytvorPolozky();


/*    /// následuje blok pokusů pro práci s mapami
        String _key;  // klíč - zde skupina OS
        List<String> list; // list
        String list_0; // nultá položka v listu
        boolean _has_next;

        for (String key : skupinyOS.keySet())
        {
            _key = key;
        }

        for (List<String> verze : skupinyOS.values())
        {
            list = verze;
            list_0 = list.get(0);
        }

        for (Map.Entry <String, List<String>> entry : skupinyOS.entrySet()){
            _key = entry.getKey();
            list = entry.getValue();
            list_0 = entry.getValue().get(0);


        }

            list = skupinyOS.get("Verze 2.x");

            list = skupinyOS.get(skupinyList.get(1));

            String key = skupinyList.get(1);
*/


        expandableListView = (ExpandableListView) findViewById(R.id.lvListView);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, skupinyList, skupinyOS);
        expandableListView.setAdapter(expListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                final String strSelectedItem = (String) expListAdapter.getChild(groupPosition, childPosition);
                Toast.makeText(getBaseContext(), strSelectedItem, Toast.LENGTH_SHORT ).show();
                return true;




            }
        });


    }

    private void vytvorSkupiny() {
        skupinyList = new ArrayList<String>();
        skupinyList.add("Verze 1.x");
        skupinyList.add("Verze 2.x");
        skupinyList.add("Verze 3.x");
        skupinyList.add("Verze 4.x");
    }

    private void vytvorPolozky() {
        String [] v1 = {"1.5 Cupcake", "1.6 Donut"};
        String [] v2 = {"2.0 Eclair", "2.2 Foryo", "2.3 Gingerbread"};
        String [] v3 = {"3.0 Honeycomb"};
        String [] v4 = {"4.0 Ice Cream Sandwitch", "4.3 Jelly Bean", "4.4 KitKat"};

        skupinyOS = new LinkedHashMap<String, List<String>>();
        for (String laptop: skupinyList)
        {
            if (laptop.equals("Verze 1.x")) naplnPolozky (v1);
            else if (laptop.equals("Verze 2.x")) naplnPolozky(v2);
            else if (laptop.equals("Verze 3.x")) naplnPolozky(v3);
            else if (laptop.equals("Verze 4.x")) naplnPolozky(v4);

            skupinyOS.put(laptop, polozkyList);

        }

    }

    private void naplnPolozky(String[] laptopModels) {

        polozkyList = new ArrayList<String>();
        for (String model: laptopModels)
            polozkyList.add(model);

    }

    private void gitHubTest () {
        int i = 1+1;
        i= i + 4;
        i = i + 5;
        i = i +8;
        // Master Branch - první revize
    }


}
