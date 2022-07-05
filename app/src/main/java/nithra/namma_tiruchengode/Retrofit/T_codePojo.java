package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_codePojo {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("news_content")
    @Expose
    public String newsContent;
    @SerializedName("content_image")
    @Expose
    public String contentImage;
    @SerializedName("cdate")
    @Expose
    public String cdate;
    @SerializedName("status")
    @Expose
    public String status;
}
