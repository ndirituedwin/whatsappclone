package com.example.kampuskonekt;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserModel {

    private String userId,username,usermobile,userprofilestatus,userprofilephoto;

    public UserModel() {
    }

    public UserModel(String userId, String username, String usermobile, String userprofilestatus, String userprofilephoto) {
        this.userId = userId;
        this.username = username;
        this.usermobile = usermobile;
        this.userprofilestatus = userprofilestatus;
        this.userprofilephoto = userprofilephoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUserprofilestatus() {
        return userprofilestatus;
    }

    public void setUserprofilestatus(String userprofilestatus) {
        this.userprofilestatus = userprofilestatus;
    }

    public String getUserprofilephoto() {
        return userprofilephoto;
    }

    public void setUserprofilephoto(String userprofilephoto) {
        this.userprofilephoto = userprofilephoto;
    }


    @BindingAdapter("imageUrl")
    public static void loadImage(CircleImageView view, String image) {
        Glide.with(view.getContext()).load(image).into(view);
    }

    @BindingAdapter("imageChat")
    public static void loadImage(ImageView view, String image) {

        Glide.with(view.getContext()).load(image).into(view);

    }
}
