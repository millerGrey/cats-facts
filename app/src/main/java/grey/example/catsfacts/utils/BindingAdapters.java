package grey.example.catsfacts.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class BindingAdapters {

    static final float HEIGHT_K = 1 / 3f;
    static int mSize = 0;

    @BindingAdapter({"app:items"})
    public static void setItems(RecyclerView listView, int size) {
        FactAdapter a = (FactAdapter) (listView.getAdapter());
        if (a != null) {
            if (size > mSize) {
                a.notifyItemInserted(0);
                listView.smoothScrollToPosition(0);
            }
            a.notifyDataSetChanged();
        }
        mSize = size;
    }

    @BindingAdapter("app:image")
    public static void setImage(ImageView imageView, String path) {

        Bitmap image = BitmapFactory.decodeFile(path);
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth;
        int newHeight;
        int displayWidth;
        int displayHeight;
        float scaleFactor;
        WindowManager wm = (WindowManager) imageView.getContext().getSystemService(Context.WINDOW_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            displayWidth = wm.getCurrentWindowMetrics().getBounds().width();
            displayHeight = wm.getCurrentWindowMetrics().getBounds().height();
        } else {
            displayWidth = wm.getDefaultDisplay().getWidth();
            displayHeight = wm.getDefaultDisplay().getHeight();
        }

        if(displayHeight < displayWidth){
            imageView.setImageBitmap(image);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            scaleFactor = (float) displayWidth / width;
            newWidth = ((int) (scaleFactor * width));
            newHeight = ((int) (scaleFactor * height));
            imageView.setImageBitmap(Bitmap.createScaledBitmap(image, newWidth, newHeight, false));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
        }
    }
}
