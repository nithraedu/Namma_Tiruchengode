package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("slider_image")
    @Expose
    public String slider_image;
    @SerializedName("load_url")
    @Expose
    public String load_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(String slider_image) {
        this.slider_image = slider_image;
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

    @SerializedName("status")
    @Expose
    public String status;


}
