package com.sayed.deeplinklearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class ActivityDynamicLink extends AppCompatActivity {
    private final String TAG = getClass().getName();
    private FirebaseAnalytics analytics;
    private TextView txtResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_link);
        txtResult = (TextView) findViewById(R.id.txtResult);

        //here we will initialize the Firebase Dynamic Links
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink( getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        if(pendingDynamicLinkData != null) {
                            //init analytics if you want to get analytics from your dynamic links
                            analytics = FirebaseAnalytics.getInstance(ActivityDynamicLink.this);

                            Uri deepLink = pendingDynamicLinkData.getLink();
                            txtResult.append("\nonSuccess called " + deepLink.toString());

                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                txtResult.setText(e.getMessage());
            }
        });
    }
}
