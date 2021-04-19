package grey.example.catsfacts.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import grey.example.catsfacts.model.POJOfact;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Loader {

    private static final String FACT_URL = "https://cat-fact.herokuapp.com";
    private static final String IMAGE_URL = "https://cataas.com";
    private static final Retrofit sFactRetrofit = new Retrofit.Builder()
            .baseUrl(FACT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final Retrofit sImageRetrofit = new Retrofit.Builder()
            .baseUrl(IMAGE_URL)
            .build();
    static FactAPI sFactAPI = sFactRetrofit.create(FactAPI.class);
    static ImageAPI sImageAPI = sImageRetrofit.create(ImageAPI.class);

    public static String getFact() {
        String text = "";
        Call<POJOfact> req = sFactAPI.getFact();
        try {
            POJOfact body = req.execute().body();
            text = body != null ? body.getText() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static Bitmap getImage() {
        Bitmap bitmap = null;
        Call<ResponseBody> req = sImageAPI.getImage();
        try {
            bitmap = BitmapFactory.decodeStream(req.execute().body().byteStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
