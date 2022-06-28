package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Helplinepojo {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("helpline_category")
    @Expose
    public String helplineCategory;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("status")
    @Expose
    public String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHelplineCategory() {
        return helplineCategory;
    }

    public void setHelplineCategory(String helplineCategory) {
        this.helplineCategory = helplineCategory;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
