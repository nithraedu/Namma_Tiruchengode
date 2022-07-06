package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sub_Category {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("view_count")
    @Expose
    public String view_count;

    @SerializedName("sub_category")
    @Expose
    public String subCategory;

    @SerializedName("sub_category_logo")
    @Expose
    public String subCategoryLogo;

    @SerializedName("status")
    @Expose
    public String status;

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

    public String getSubCategoryLogo() {
        return subCategoryLogo;
    }

    public void setSubCategoryLogo(String subCategoryLogo) {
        this.subCategoryLogo = subCategoryLogo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
