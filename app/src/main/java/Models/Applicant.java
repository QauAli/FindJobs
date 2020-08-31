package Models;

import java.util.ArrayList;
import java.util.LinkedList;

public class Applicant {
    String first_name,
            last_name,
            cnic,
            birth_date,
            father_name,
            email,
            password,
            province,
            district,
            postal_address,
            permanent_address,
            mobile,
            phone,
            job_status,
            experianceDetails,
            image_path;
    LinkedList<String> skills = new LinkedList<>();
    ArrayList<Education> educations = new ArrayList<>();
    int id;

    public String getImage_path() {
        return image_path;
    }

    public Applicant(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getExperianceDetails() {
        return experianceDetails;
    }

    public void setExperianceDetails(String experianceDetails) {
        this.experianceDetails = experianceDetails;
    }

    public Applicant(String first_name, String last_name,
                     String cnic, String birth_date,
                     String father_name, String email,
                     String password, String province,
                     String district, String postal_address,
                     String permanent_address, String mobile,
                     String phone, String job_status,
                     ArrayList<Education> educations,
                     LinkedList<String> skills,
                     String experianceDetails) {
        this.experianceDetails = experianceDetails;
        this.skills = skills;
        this.first_name = first_name;
        this.last_name = last_name;
        this.cnic = cnic;
        this.birth_date = birth_date;
        this.father_name = father_name;
        this.email = email;
        this.password = password;
        this.province = province;
        this.district = district;
        this.postal_address = postal_address;
        this.permanent_address = permanent_address;
        this.mobile = mobile;
        this.phone = phone;
        this.job_status = job_status;
        this.educations = educations;
    }


    public LinkedList<String> getSkills() {
        return skills;
    }

    public void setSkills(LinkedList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<Education> getEducations() {
        return educations;
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
