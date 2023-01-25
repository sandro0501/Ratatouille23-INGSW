package com.example.ratatouille23.Views;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class RatatouilleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        configuraS3();
    }

    public void configuraS3() {
        try {
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {

            Log.i("AA", "AAAAA");
        }
    }

}
