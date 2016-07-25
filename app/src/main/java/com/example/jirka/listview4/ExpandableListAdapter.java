package com.example.jirka.listview4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Jirka on 22.6.2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, List<String>> skupinyOS;
    private List<String> verzeOS;

    public ExpandableListAdapter(Activity context, List<String> verzeOS, Map<String, List<String>> skupinyOS)
    {
        this.context = context;
        this.skupinyOS = skupinyOS;
        this.verzeOS = verzeOS;

    }

    @Override
    public Object getChild(int poziceSkupiny, int pozicePolozky) {
        Object o = skupinyOS.get(verzeOS.get(poziceSkupiny)).get(pozicePolozky);
        return o;

    }



    @Override
    public long getChildId(int poziceSkupiny, int pozicePolozky) {
        return pozicePolozky;
    }


    //detChildView - parametry - pozice skupiny, pozice položky v rámci skupiny, jeslti je položka psolední, odkaz na vytvořené vieW, odkaz na rodičovské view
    @Override
    public View getChildView(final int poziceSkupiny, final int pozicePolozky, boolean posledniPolozka, View convertView, ViewGroup parent) {

        final String verze = (String) getChild(poziceSkupiny, pozicePolozky); // zíslání položky dle souřednic
        LayoutInflater inflater = context.getLayoutInflater();

        // pokud view ještě neexistuje, je potřeba jej vytvořit
        if(convertView == null)
            convertView = inflater.inflate(R.layout.polozka_item,null);

        // získáme odkazy na objekty ve view
        TextView tvItemText = (TextView) convertView.findViewById(R.id.tvPolozkaText);
        ImageView imDelete = (ImageView) convertView.findViewById(R.id.picVymaz);

        //nastavujeme listener
        imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   // příprava dialogového okna
                AlertDialog.Builder builder = new AlertDialog.Builder(context);  // vytvoření DialogBuilderu
                builder.setMessage("Skutečně smazat?");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.ic_delete);
                //nastavení tlačítka Ano
                builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<String> child = skupinyOS.get(verzeOS.get(poziceSkupiny)); // získání listu s položkou
                        child.remove(pozicePolozky);  // odstranění konkrétního záznamu
                        notifyDataSetChanged();     // notifikace o změně datasetu - zřejmě vyvolá překreslení?
                    }
                });
                // nastavení tlačítka ne (Cancel)
                builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();  //vytvoření dialogu
                alertDialog.show(); // spuštění dialogi


            };
        });


        tvItemText.setText(verze);
        return convertView;
    }

    @Override
    public Object getGroup(int poziceSkupiny)
    {
        return verzeOS.get(poziceSkupiny);
    }

    @Override
    public long getGroupId(int poziceSkupiny) {
        return poziceSkupiny;
    }


    @Override
    public int getGroupCount() {
        return verzeOS.size();
    }  // vrací počet skupin (delka List<String> s názvem skupiny (př Verze 1xx);

    @Override
    public int getChildrenCount(int poziceSkupiny) { // vrací velikost (počet členů) List<String> skupiny.
        return skupinyOS.get(verzeOS.get(poziceSkupiny)).size();
    }

    @Override
    public View getGroupView(int poziceSkupiny, boolean jeRozbalena, View convertView, ViewGroup viewGroup) {
        String nazevSkupiny = (String) getGroup(poziceSkupiny);
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.skupina_item, null);
        }

        TextView tvSkupina = (TextView) convertView.findViewById(R.id.tvSkupina);
        tvSkupina.setTypeface(null, Typeface.BOLD);
        //tvSkupina.setText(nazevSkupiny);
        tvSkupina.setText(nazevSkupiny);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
