package net.lemarechal.corentin.babelbloc;

import android.graphics.Bitmap;

public class Drapeau {

    private String code;
    private Bitmap image;

    public Drapeau(Bitmap image, String code){
        this.image = image;
        this.code = code;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getCode() {
        return code;
    }
}
