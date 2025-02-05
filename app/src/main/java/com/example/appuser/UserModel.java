package com.example.appuser;

public class UserModel {
    String name,email,image,password;

    public UserModel() {
    }

    public UserModel(String name, String email, String image,String password) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
