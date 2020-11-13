package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Register your parse model
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("cTkB6G5YZ3ycLtGCnFcEKArjruzycivFMpPbnIvB")
                .clientKey("SyDVW895CXN2mn0gPSXIQFx1FdVo4dLo43ZlnYdg")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
