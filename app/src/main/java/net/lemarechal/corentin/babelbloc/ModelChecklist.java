package net.lemarechal.corentin.babelbloc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class ModelChecklist extends AppCompatActivity {

    private GestureDetector gDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.modele3dface);
            Button validface = findViewById(R.id.ValidModel);
            validface.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        finish();
                        Intent selectlangue = new Intent(getApplicationContext(), SelectLangueActivity.class);
                        startActivity(selectlangue);
                    } catch (Exception e) {
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                        e.printStackTrace();
                    }
                }
            });
            final ImageView image = findViewById(R.id.imageView);
            final ImageView rotate = findViewById(R.id.rotate);
            rotate.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (rotate.getRotation()!=0){
                            rotate.setRotation(0);
                            image.setImageResource(R.drawable.modeleface);
                        }
                        else {
                            rotate.setRotation(90);
                            image.setImageResource(R.drawable.modeledos);
                        }
                    } catch (Exception e) {
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                        e.printStackTrace();
                    }
                }
            });
            final ImageView point = findViewById(R.id.pointModel);
            ViewGroup.LayoutParams param =  point.getLayoutParams();
            param.width = 20;
            param.height = 20;
            point.setLayoutParams(param);
            gDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    try {
                        float x = e.getX()-10;
                        float y = e.getY()-40;
                        if (x > image.getX()&&y > image.getY()&&x < image.getX()+image.getWidth()&&y < image.getY()+image.getHeight()
                        ) {
                            point.setX(x);
                            point.setY(y);
                        }
                    } catch (Exception ex) {
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create();
                        ex.printStackTrace();
                    }
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {

                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    try {
                        float x = e2.getX()-10;
                        float y = e2.getY()-40;
                        if (x > image.getX()&&y > image.getY()&&x < image.getX()+image.getWidth()&&y < image.getY()+image.getHeight()
                        ) {
                            point.setX(x);
                            point.setY(y);
                        }
                    } catch (Exception e) {
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                        e.printStackTrace();
                    }
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return false;
                }
            });
            final SeekBar taillepoint = findViewById(R.id.taillePoint);
            taillepoint.setRotation(-90);
            taillepoint.setProgress(50);
            taillepoint.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    try {
                        if (seekBar == taillepoint) {
                            ViewGroup.LayoutParams param =  point.getLayoutParams();
                            param.width = (progress/4)+12;
                            param.height = (progress/4)+12;
                            point.setLayoutParams(param);
                        }
                    } catch (Exception e) {
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            ImageView back = findViewById(R.id.Back);
            back.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), CheckListActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        try {
            if (this.gDetector != null)
                this.gDetector.onTouchEvent(event);
        } catch (Exception e) {
            new AlertDialog.Builder(getApplicationContext()).setMessage("Une erreur inattendu s'est produite").create().show();
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }

}
