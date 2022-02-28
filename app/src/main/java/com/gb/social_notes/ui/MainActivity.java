package com.gb.social_notes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gb.social_notes.publisher.Publisher;
import com.gb.social_notes.ui.main.SocialNetworkFragment;
import com.gb.social_notes.R;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher;
    private Navigation navigation;

    public Publisher getPublisher() {
        return publisher;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publisher = new Publisher();
        navigation = new Navigation(getSupportFragmentManager());
        if (savedInstanceState == null) {
            navigation.replaceFragment(SocialNetworkFragment.newInstance(), false);
        }
    }
}