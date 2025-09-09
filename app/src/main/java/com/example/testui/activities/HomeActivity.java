package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.testui.R;
import com.example.testui.ViewModel.HomeViewModel;
import com.example.testui.fragment.DotDoAnFragment;
import com.example.testui.fragment.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    ImageView ivNavBar;
    NavigationView nav;
    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        drawerLayout = findViewById(R.id.main);
        ivNavBar = findViewById(R.id.iv_nav_bar);
        nav = findViewById(R.id.nav_view); // thêm dòng này

        nav.setNavigationItemSelectedListener(this);

        ivNavBar.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            nav.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (item.getItemId() == R.id.nav_graduate) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new DotDoAnFragment())
                    .addToBackStack(null)
                    .commit();
        }
        else if (item.getItemId() == R.id.nav_my_graduate){
            Intent intent = new Intent(this, ChiTietDoAnActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_person) {
            Intent intent = new Intent(this, CapNhapThongTinActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.END);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
            return;
        }

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (!(currentFragment instanceof HomeFragment)) {
            // Nếu không phải HomeFragment → quay về HomeFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            nav.setCheckedItem(R.id.nav_home);
        } else {
            // Nếu đang ở HomeFragment thì thoát app
            super.onBackPressed();
        }
    }

}