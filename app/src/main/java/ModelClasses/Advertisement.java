package ModelClasses;

import java.util.LinkedList;

import RestfullServices.FindJobService;
import RestfullServices.Client;

public class Advertisement {
    private int id,advertiser_id=1;
    private String title, province , district, education , last_date;
    private LinkedList<String> skills;
    private FindJobService service = Client.getClient().create(FindJobService.class);//ex

    public Advertisement(int id, String title, String province, String district, String education, String last_date, LinkedList<String> skills) {
        this.advertiser_id = id;
        this.title = title;
        this.province = province;
        this.district = district;
        this.education = education;
        this.last_date = last_date;
        this.skills = skills;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public void setSkills(LinkedList<String> skills) {
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getEducation() {
        return education;
    }

    public String getLast_date() {
        return last_date;
    }

    public LinkedList<String> getSkills() {
        return skills;
    }
}
