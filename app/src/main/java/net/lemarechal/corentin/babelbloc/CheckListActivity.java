package net.lemarechal.corentin.babelbloc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CheckListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.checklist);
            Button validcheck = findViewById(R.id.ValidChecklist);
            validcheck.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), ModelChecklist.class);
                        startActivity(intent);
                    } catch (Exception e){
                        e.printStackTrace();
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                    }
                }
            });
            ImageView back = findViewById(R.id.Back);
            back.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), SelectLangueActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
            ListView list = findViewById(R.id.listItem);
            List<String> choix = new ArrayList<>();
            choix.add("choix 1");
            choix.add("choix 2");
            choix.add("choix 3");
            choix.add("choix 4");
            List<Question> listQuestion = new ArrayList<>();
            listQuestion.add(new Question("Question", null, Question.TEXT));
            listQuestion.add(new Question("Question", choix, Question.QCU));
            listQuestion.add(new Question("Question", choix, Question.QCM));
            QuestionAdapter dataAdapter = new QuestionAdapter(getApplicationContext(), listQuestion);
            list.setAdapter(dataAdapter);
        }
        catch (Exception e) {
            e.printStackTrace();
            new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
    }

}
