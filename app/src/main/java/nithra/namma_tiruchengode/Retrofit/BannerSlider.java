package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerSlider {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("banner_slider_image")
    @Expose
    public String banner_slider_image;
    @SerializedName("load_url")
    @Expose
    public String load_url;

    @SerializedName("status")
    @Expose
    public String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner_slider_image() {
        return banner_slider_image;
    }

    public void setBanner_slider_image(String banner_slider_image) {
        this.banner_slider_image = banner_slider_image;
    }

    public String getLoad_url() {
        return load_url;
    }

    public void setLoad_url(String load_url) {
        this.load_url = load_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
