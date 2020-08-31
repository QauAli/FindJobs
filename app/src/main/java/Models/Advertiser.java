package Models;

public class Advertiser {
    private int id;
    private String name, cnic, birth_date,
            email, password,
            province, district,
            address, gender,
            company, mobile, image_name = "";

    public String getImagename() {
        return image_name;
    }

    public void setImagename(String image_name) {
        this.image_name = image_name;
    }

    public int getId() {
        return id;
    }

    public Advertiser(String email, String _password) {
        this.email = email;
        this.password = _password;
    }

    public Advertiser(int id ,String name, String _cnic, String _dob, String email, String _password, String _province, String _district, String _address, String gender, String _company, String mobile) {
        this.id = id;
        this.name = name;
        this.cnic = _cnic;
        this.birth_date = _dob;
        this.email = email;
        this.password = _password;
        this.province = _province;
        this.district = _district;
        this.address = _address;
        this.gender = gender;
        this.company = _company;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getCnic() {
        return cnic;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getCompany() {
        return company;
    }

    public String getMobile() {
        return mobile;
    }

}