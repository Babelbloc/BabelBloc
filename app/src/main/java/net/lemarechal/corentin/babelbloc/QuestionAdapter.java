package net.lemarechal.corentin.babelbloc;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class QuestionAdapter extends ArrayAdapter<Question> {

    private List<Question> list = new ArrayList<>();

    public QuestionAdapter(@NonNull Context context, List<Question> list) {
        super(context, 0, list);
        try {
            this.list = list;
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        try {
            Question current = list.get(position);
            if (current.getType() == Question.QCU) {
                view = generateQCU(current.getQuestion(), current.getChoix());
            }
            else if (current.getType() == Question.QCM) {
                view = generateQCM(current.getQuestion(), current.getChoix());
            }
            else if (current.getType() == Question.TEXT) {
                view = generateTEXT(current.getQuestion());
            }
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
        return view;
    }

    private View generateQCU(String Question, List<String> choix){
        ConstraintLayout QCU = new ConstraintLayout(getContext());
        try {
            ConstraintLayout.LayoutParams qculayout = new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT));
            qculayout.height = 100;
            QCU.setLayoutParams(qculayout);
            TextView question = new TextView(getContext());
            question.setId(R.id.Question);
            question.setTextSize(18);
            question.setTextColor(getContext().getResources().getColor(R.color.noir));
            question.setText(Question);
            QCU.addView(question);
            RadioGroup group = new RadioGroup(getContext());
            ConstraintLayout.LayoutParams groupparams = new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT,WRAP_CONTENT));
            groupparams.topToBottom = R.id.Question;
            group.setLayoutParams(groupparams);
            group.setOrientation(LinearLayout.HORIZONTAL);
            for (int i = 0; i < choix.size(); i++) {
                RadioButton b = new RadioButton(getContext());
                b.setText(choix.get(i));
                b.setTextColor(getContext().getResources().getColor(R.color.noir));
                group.addView(b);
            }
            QCU.addView(group);
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
        return QCU;
    }

    private View generateQCM(String Question, List<String> choix){
        ConstraintLayout QCM = new ConstraintLayout(getContext());
        try {
            ConstraintLayout.LayoutParams qcmlayout = new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT));
            qcmlayout.height = 100;
            QCM.setLayoutParams(qcmlayout);
            TextView question = new TextView(getContext());
            question.setId(R.id.Question);
            question.setTextSize(18);
            question.setTextColor(getContext().getResources().getColor(R.color.noir));
            question.setText(Question);
            QCM.addView(question);
            LinearLayout linearLayout = new LinearLayout(getContext());
            ConstraintLayout.LayoutParams linearparams = new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT,WRAP_CONTENT));
            linearparams.topToBottom = R.id.Question;
            linearLayout.setLayoutParams(linearparams);
            for (int i = 0; i < choix.size(); i++) {
                CheckBox check = new CheckBox(getContext());
                check.setText(choix.get(i));
                check.setTextColor(getContext().getResources().getColor(R.color.noir));
                linearLayout.addView(check);
            }
            QCM.addView(linearLayout);
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
        return QCM;
    }

    private View generateTEXT(String Question){
        ConstraintLayout TEXT = new ConstraintLayout(getContext());
        try {
            ConstraintLayout.LayoutParams textlayout = new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT));
            textlayout.height = 100;
            TEXT.setLayoutParams(textlayout);
            TextView question = new TextView(getContext());
            question.setId(R.id.Question);
            question.setTextSize(18);
            question.setTextColor(getContext().getResources().getColor(R.color.noir));
            question.setText(Question);
            TEXT.addView(question);
            EditText edit = new EditText(getContext());
            edit.setLines(1);
            edit.setTextColor(getContext().getResources().getColor(R.color.noir));
            ConstraintLayout.LayoutParams editparams = new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT));
            editparams.topToBottom = R.id.Question;
            editparams.width = 400;
            edit.setLayoutParams(editparams);
            TEXT.addView(edit);
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(getContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
        return TEXT;
    }

}
