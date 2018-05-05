package com.example.admin.vkmess;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.vkmess.Fragments.FragmentMessage;
import com.example.admin.vkmess.Fragments.FragmentProfile;
import com.example.admin.vkmess.Fragments.FragmentFriends;


public class BodyMess extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    FragmentProfile fProfile;
    FragmentMessage fMessage;
    FragmentFriends fFriends;
    public static String TOKEN;
    public static String ID;
    public static String server;
    public static String key;
    public static int ts;
    public static int pts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        TextView textView = findViewById(R.id.profileName);
//        textView.setText("LehaLOH");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.openDrawer(GravityCompat.START);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TOKEN = getIntent().getExtras().getString("token");
        ID = getIntent().getExtras().getString("id");
        key = getIntent().getExtras().getString("key");
        server = getIntent().getExtras().getString("server");
        ts = getIntent().getExtras().getInt("ts");
        System.out.println(server);
        System.out.println(key);
        System.out.println(ts);

        fMessage = new FragmentMessage();
        fProfile = new FragmentProfile();
        fFriends = new FragmentFriends();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fTrans = getFragmentManager().beginTransaction();

        if (id == R.id.nav_profile) {
            fTrans.replace(R.id.container, fProfile);
        } else if (id == R.id.nav_messages) {
            fTrans.replace(R.id.container, fMessage);
        } else if (id == R.id.nav_friends) {
            fTrans.replace(R.id.container, fFriends);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } fTrans.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
