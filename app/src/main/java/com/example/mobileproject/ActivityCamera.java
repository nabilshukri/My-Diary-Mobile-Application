package com.example.mobileproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mobileproject.databinding.ActivityCameraBinding;
import com.google.android.material.navigation.NavigationView;

public class ActivityCamera extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    ActivityCameraBinding binding;
    ActivityResultLauncher<Intent> launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTakePic.setOnClickListener(view->launcher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)));
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::fnAfterCam);


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.syncState();

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_main_activity:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_camera_activity:
                        intent = new Intent(getApplicationContext(), ActivityCamera.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_today_activity:
                        intent = new Intent(getApplicationContext(), ActivityToday.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_calendar_activity:
                        intent = new Intent(getApplicationContext(), ActivityCalendar.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_maps_activity:
                        intent = new Intent(getApplicationContext(), ActivityMaps.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        signOut();
                        return true;

                    default:
                        return false;
                }

            }

        });
    }

    private void fnAfterCam(ActivityResult result){
        Bitmap bp = (Bitmap) result.getData().getExtras().get("data");
        binding.imgVwPic.setImageBitmap(bp);

    }

    public void fnTakePic(View vw)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(intent);
    }


    private void signOut() {

        Toast.makeText(getApplicationContext(), "Successfully log out!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}