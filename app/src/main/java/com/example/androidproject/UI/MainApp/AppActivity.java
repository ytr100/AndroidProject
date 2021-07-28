package com.example.androidproject.UI.MainApp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.androidproject.MainappNavigationDirections;
import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.R;

public class AppActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        navController = Navigation.findNavController(this, R.id.NavHost_mainapp);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navController.navigateUp();
                break;
            case R.id.menu_profile:
                MainappNavigationDirections.ActionGlobalProfileFragment action = MainappNavigationDirections.actionGlobalProfileFragment(MyModel.CURRENT_USER.getUsername());
                navController.navigate(action);
                return true;
            case R.id.menu_add:
                break; //delegate to fragments
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyModel.instance.signOutUser();
    }
}