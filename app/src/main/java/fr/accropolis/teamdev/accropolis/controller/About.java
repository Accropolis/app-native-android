package fr.accropolis.teamdev.accropolis.controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import fr.accropolis.teamdev.accropolis.R;

/**
 * Created by Nicolas Padiou on 25/02/17.
 */

public class About extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llURI = null;
    LinearLayout llYoutube = null;
    LinearLayout llFacebook = null;
    LinearLayout llTwitter = null;
    LinearLayout llTwitch = null;

    LinearLayout llSnapchat = null;
    LinearLayout llInstagram = null;
    LinearLayout llDiscord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //display arrow
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //display the logo instead of the name
        getSupportActionBar().setLogo(R.drawable.accropolis_ban_mini);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        this.llURI = (LinearLayout)findViewById(R.id.ll_URI);
        this.llURI.setOnClickListener(this);

        this.llYoutube = (LinearLayout)findViewById(R.id.ll_youtube);
        this.llYoutube.setOnClickListener(this);

        this.llTwitch = (LinearLayout)findViewById(R.id.ll_twitch);
        this.llTwitch.setOnClickListener(this);

        this.llFacebook = (LinearLayout)findViewById(R.id.ll_facebook);
        this.llFacebook.setOnClickListener(this);

        this.llTwitter = (LinearLayout)findViewById(R.id.ll_twitter);
        this.llTwitter.setOnClickListener(this);

        this.llSnapchat = (LinearLayout) findViewById(R.id.ll_snapchat);
        this.llSnapchat.setOnClickListener(this);

        this.llInstagram = (LinearLayout) findViewById(R.id.ll_instagram);
        this.llInstagram.setOnClickListener(this);

        this.llDiscord = (LinearLayout) findViewById(R.id.ll_discord);
        this.llDiscord.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_URI:
                Intent wwwIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://accropolis.fr"));
                startActivity(wwwIntent);
                break;
            case R.id.ll_youtube:
                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCqv_wXmLSFtTDA39HQaLssQ"));
                startActivity(youtubeIntent);
                break;
            case R.id.ll_twitch:
                Intent twitchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitch.tv/accropolis"));
                startActivity(twitchIntent);
                break;
            case R.id.ll_facebook:
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/AccropolisPage/"));
                startActivity(facebookIntent);
                break;
            case R.id.ll_twitter:
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Accropolis"));
                startActivity(twitterIntent);
                break;
            case R.id.ll_snapchat:
                Intent snapchatIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.snapchat.com/add/accropolis/"));
                startActivity(snapchatIntent);
                break;
            case R.id.ll_instagram:
                Intent instagramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/accropolis/"));
                startActivity(instagramIntent);
                break;
            case R.id.ll_discord:
                Intent discordIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discordapp.com/invite/4729uxc"));
                startActivity(discordIntent);
                break;
        }
    }
}
