package com.example.whatsappclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
         Parse.initialize(new Parse.Configuration.Builder(this)
            .applicationId("TGCU1SaXTK1dBYJgLlZ6Zd9BwvD3cvyod60346Mk")
            .clientKey("jKeVUmpxfFlr93lEHaWTw2CsyWQc4FMAtsmXWDE0")
            .server("https://parseapi.back4app.com/")
            .build()
    );
    }
}
