package ru.klops.klops.gcm;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class KlopsInstanceIDListenerService  extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
