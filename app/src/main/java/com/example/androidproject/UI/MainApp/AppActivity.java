package com.example.androidproject.UI.MainApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.androidproject.R;
import com.google.android.material.snackbar.Snackbar;

//TODO: menu navigation
public class AppActivity extends AppCompatActivity {
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        navController = Navigation.findNavController(this,R.id.NavHost_mainapp);
        NavigationUI.setupActionBarWithNavController(this,navController);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                navController.navigateUp();
                break;
            case R.id.menu_profile:
                Snackbar.make(findViewById(android.R.id.content),"MainAppActivity",2000).show();
                navController.navigate(R.id.action_global_profileFragment);
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
}