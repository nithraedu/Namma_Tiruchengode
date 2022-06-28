package nithra.namma_tiruchengode.Enquiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnquiryPojo {
    @SerializedName("status")
    @Expose
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

