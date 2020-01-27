package net.lemarechal.corentin.babelbloc;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LangueAdapter extends ArrayAdapter<Langue> {

    private List<Langue> langueList = new ArrayList<>();

    public LangueAdapter(@NonNull Context context, ArrayList<Langue> list) {
        super(context, 0, list);
        try {
            this.langueList = list;
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = getImageForPosition(position);
        try {
            final Langue currentLangue = langueList.get(position);
            final int pos = position;
            view.setOnLongClickListener(new AdapterView.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getContext(), currentLangue.getNom(), Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            view.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectLangueActivity.langue.setSelection(pos);
                    try {
                        Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                        method.setAccessible(true);
                        method.invoke(SelectLangueActivity.langue);
                    } catch (Exception e){
                        e.printStackTrace();
                        new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
        return view;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getImageForPosition(position);
    }

    private View getImageForPosition(int position) {
        ImageView imageView = new ImageView(getContext());
        try {
            Langue currentLangue = langueList.get(position);
            if (currentLangue.getDrapeau() == null)
                return imageView;
            imageView.setImageBitmap(currentLangue.getDrapeau().getImage());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300,190));
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
        return imageView;
    }
}
