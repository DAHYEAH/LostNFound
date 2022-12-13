package com.example.lostnfound.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lostnfound.R;
import com.example.lostnfound.databinding.ActivityMainBinding;
import com.example.lostnfound.ui.home.HomeFragment;
import com.example.lostnfound.ui.lost.list.LostFragment;
import com.example.lostnfound.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private ActivityMainBinding binding;
    HomeFragment homeFragment;
    Fragment fragment = new LostFragment();
    LostFragment lostFragment;
    NotificationsFragment no;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_home, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.navigation_lost || navDestination.getId() == R.id.navigation_lost_write
                        || navDestination.getId() == R.id.navigation_find || navDestination.getId() ==R.id.navigation_find_write){
                    navView.setVisibility(View.GONE);
                }
                else{
                    navView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    public void moveToLost(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_home_to_navigation_lost);
    }

    public void moveToLostWrite(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_lost_to_navigation_lost_write);
    }

    public void moveToLostDetail(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_lost_to_navigation_lost_detail);
    }

    public void backToLost(){
        navController = Navigation.findNavController(this,R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_lost_write_to_navigation_lost);
    }

    public void moveToFind(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_home_to_navigation_find);
    }

    public void moveToFindWrite(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_find_to_navigation_find_write);
    }

    public void moveToFindDetail(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_find_to_navigation_find_detail);
    }

    public void backToFind(){
        navController = Navigation.findNavController(this,R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_find_write_to_navigation_find);
    }

    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
