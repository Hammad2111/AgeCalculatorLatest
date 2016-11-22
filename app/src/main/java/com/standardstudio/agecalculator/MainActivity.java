package com.standardstudio.agecalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static InterstitialAd interstitialAd;
    int adchooser;
    NativeExpressAdView adView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InterstitialAdmob_Load();
        adView = (NativeExpressAdView)findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adchooser=1;
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else {

                    Intent in = new Intent(getApplicationContext(), ResultCalculationActivity.class);
                    startActivity(in);
                }

            }
        });

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                if(adchooser==1)
                {
                    Intent in=new Intent(getApplicationContext(),ResultCalculationActivity.class);
                    startActivity(in);
                }
                else if(adchooser==2)
                {
                    Intent in=new Intent(getApplicationContext(),ResultCalculationActivity.class);
                    startActivity(in);
                }
                else if(adchooser==3)
                {
                    Intent in=new Intent(getApplicationContext(),DeveloperIntro.class);
                    startActivity(in);
                }
                super.onAdClosed();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            adchooser=2;
            if(interstitialAd.isLoaded())
            {
                interstitialAd.show();
            }
            else {

                Intent in = new Intent(getApplicationContext(), ResultCalculationActivity.class);
                startActivity(in);
            }

        } else if (id == R.id.nav_about) {

            adchooser=3;
            if(interstitialAd.isLoaded())
            {
                interstitialAd.show();
            }
            else {

                Intent in = new Intent(getApplicationContext(), DeveloperIntro.class);
                startActivity(in);
            }


        } else if (id == R.id.nav_Rate) {

        } else if (id == R.id.nav_check) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.standardstudio.agecalculator"));
            startActivity(i);


        } else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "How old you are?\n."+Uri.parse("https://play.google.com/store/apps/details?id=com.standardstudio.agecalculator"));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_Rate) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.standardstudio.agecalculator"));
            startActivity(i);


        }
        else if (id == R.id.nav_more) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Standard+Studio"));
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*Assign and Load Intercialtial*/
    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }
}
