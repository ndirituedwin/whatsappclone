package com.example.kampuskonekt.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserObject {


    private String username,usermobile;
    private CircleImageView userprofilephoto;

    public UserObject() {
    }

    public UserObject(String username, String usermobile, CircleImageView userprofilephoto) {
        this.username = username;
        this.usermobile = usermobile;
        this.userprofilephoto = userprofilephoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public CircleImageView getUserprofilephoto() {
        return userprofilephoto;
    }

    public void setUserprofilephoto(CircleImageView userprofilephoto) {
        this.userprofilephoto = userprofilephoto;
    }
}
