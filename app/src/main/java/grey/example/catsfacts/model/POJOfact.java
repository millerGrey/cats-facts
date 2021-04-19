package grey.example.catsfacts.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POJOfact {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sentCount")
    @Expose
    private Integer sentCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}