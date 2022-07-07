package nithra.namma_tiruchengode.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Third_Category {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("sector_name")
    @Expose
    public String sectorName;
    @SerializedName("view_count")
    @Expose
    public String view_count;
    @SerializedName("opening_time")
    @Expose
    public String openingTime;
    @SerializedName("closing_time")
    @Expose
    public String closingTime;
    @SerializedName("leave_day")
    @Expose
    public String leaveDay;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("status")
    @Expose
    public String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(String leaveDay) {
        this.leaveDay = leaveDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
