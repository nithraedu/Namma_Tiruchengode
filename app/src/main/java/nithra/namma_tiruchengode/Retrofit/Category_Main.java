package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category_Main {
    @SerializedName("category")
    @Expose
    public ArrayList<Category> category;
    @SerializedName("slider")
    @Expose
    public ArrayList<Slider> slider;
    @SerializedName("banner_slider")
    @Expose
    public ArrayList<BannerSlider> banner_slider;

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    public ArrayList<Slider> getSlider() {
        return slider;
    }

    public void setSlider(ArrayList<Slider> slider) {
        this.slider = slider;
    }

    public ArrayList<BannerSlider> getBanner_slider() {
        return banner_slider;
    }

    public void setBanner_slider(ArrayList<BannerSlider> banner_slider) {
        this.banner_slider = banner_slider;
    }
}
