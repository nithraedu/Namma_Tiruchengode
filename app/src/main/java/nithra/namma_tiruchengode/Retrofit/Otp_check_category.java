package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Otp_check_category {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("category")
    @Expose
    public String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    @SerializedName("sub_category")
    @Expose
    public List<SubCategory> subCategory;

    public class SubCategory{

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("sub_category")
        @Expose
        public String subCategory;
    }

}
