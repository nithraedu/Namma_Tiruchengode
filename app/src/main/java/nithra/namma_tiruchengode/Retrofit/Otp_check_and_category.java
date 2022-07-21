package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Otp_check_and_category {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("category")
    @Expose
    public List<Category> category;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public class Category{
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("sub_category")
        @Expose
        public List<SubCategory> subCategory;

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
    }
    public class SubCategory{
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("sub_category")
        @Expose
        public String subCategory;

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
    }

}
