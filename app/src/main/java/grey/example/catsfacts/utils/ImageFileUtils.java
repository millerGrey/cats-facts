package grey.example.catsfacts.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class ImageFileUtils {

    static final String APP_DIR_PATH = "/catsfacts";

    public static String saveImage(Context context, Bitmap image) {
        Date date = new Date();
        String savedImagePath = null;
        File storageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                + APP_DIR_PATH);
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, "file" + DateFormat.format("yyMMddhhmmss", date).toString() + ".jpg");
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return savedImagePath;
    }

    public static void deleteImage(Context context, String imagePath) {
        File image = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                + APP_DIR_PATH);
        if (image.exists())
            image.delete();
    }
}