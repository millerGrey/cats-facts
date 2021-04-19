package grey.example.catsfacts.network;

import grey.example.catsfacts.model.POJOfact;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FactAPI {

    @GET("/facts/random")
    Call<POJOfact> getFact();
}

