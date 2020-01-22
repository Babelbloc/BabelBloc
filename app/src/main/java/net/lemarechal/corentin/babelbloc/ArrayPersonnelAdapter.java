package net.lemarechal.corentin.babelbloc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArrayPersonnelAdapter extends ArrayAdapter<Personnel> {

    private ArrayList<Personnel> objets;
    private int item_id;

    public ArrayPersonnelAdapter(Context context, int resource, ArrayList<Personnel> objects) {
        super(context, resource, objects);
        this.objets = objects;
        this.item_id=resource;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            v = inflater.inflate(this.item_id,null);

        }
        Personnel p = objets.get(position);
        if(p != null){
            TextView tv_Nom = v.findViewById(R.id.Nom);
            TextView tv_Prenom = v.findViewById(R.id.Prenom);
            TextView tv_Identifiant = v.findViewById(R.id.Identifiant);
            ImageView icone = v.findViewById(R.id.imgInfirmiere);

            if(tv_Nom != null){
                tv_Nom.setText(p.PersonnelNom);
            }
            if(tv_Prenom != null){
                tv_Prenom.setText(p.PersonnelPrenom);
            }
            if(tv_Identifiant != null){
                tv_Identifiant.setText(p.PersonnelIdentifiant);
            }

            if(icone != null){
               icone.setImageResource(R.drawable.nurse);
            }

        }
        return v;
    }
}
