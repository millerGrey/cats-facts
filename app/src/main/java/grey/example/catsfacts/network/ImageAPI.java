package grey.example.catsfacts.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageAPI {

    @GET("/cat")
    Call<ResponseBody> getImage();
}
