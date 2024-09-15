package com.hustler.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.icu.number.Scale;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        fab = findViewById(R.id.fabScan);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ScanActivity3.class);
                startActivity(i);
            }
        });



        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.w1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.w2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.w3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.w4, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome!!");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.OpenDrawer,R.string.CloseDrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.wasteEducation){
                    Toast.makeText(MainActivity.this, "Education", Toast.LENGTH_SHORT).show();
//                    loadFragment(new EducationFragment());
                    Intent intent = new Intent(MainActivity.this, EducationActivity.class);
                    startActivity(intent);
                }else if(id == R.id.recyling){
                    Toast.makeText(MainActivity.this, "Recycling", Toast.LENGTH_SHORT).show();
//                    loadFragment(new RecyclingFragment());
                    Intent intent = new Intent(MainActivity.this, RecyclingActivity.class);
                    startActivity(intent);
                }else if(id == R.id.scan){
                    Toast.makeText(MainActivity.this, "Opening Scanner", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ScanActivity3.class);
                    startActivity(intent);

                }else if(id == R.id.homeMain){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


//        if(getSupportActionBar()!=null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }



//    private void loadFragment(Fragment fragment) {
//
//        FragmentManager fm = getSupportFragmentManager();
////        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.container, fragment);
//        ft.commit();
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId==R.id.setting){
            Toast.makeText(this, "GoingToSetting", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(i);
        }else if(itemId == R.id.share){
            Toast.makeText(this, "Sharing", Toast.LENGTH_SHORT).show();
        }else if(itemId == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}