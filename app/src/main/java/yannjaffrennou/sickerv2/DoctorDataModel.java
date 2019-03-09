package yannjaffrennou.sickerv2;

public class DoctorDataModel {
    String name;
    String photo;
    String distance;
    boolean sponsored;
    int price;
    int discount;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {return photo;}
    public void setPhoto(String photo) {this.photo = photo;}

    public boolean getSponsored() {return sponsored; }
    public void setSponsored(Boolean sponsored) {this.sponsored = sponsored;}

    public int getPrice() {return this.price;}
    public void setPrice(int price) {this.price = price;}

    public int getDiscount() {return this.discount;}
    public void setDiscount(int discount) {this.discount = discount;}

    public String getDistance() {return distance;}
    public void setDistance(String distance) {this.distance = distance;}

}