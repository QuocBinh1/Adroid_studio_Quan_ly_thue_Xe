package com.example.quan_ly_thue_xe_lamlai;

public class Car {
    private String name;
    private String price;
    private String description;
    private String imgPath;

    public Car(String name, String price, String imgPath, String description) {
        this.name = name;
        this.price = price;
        this.imgPath = imgPath;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImgPath() {
        return imgPath;
    }
    public String getDescription() {
        return description;
    }
}
