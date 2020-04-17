package com.example.bomb_defuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button btnRules, btnCreatedBy, btnStart, btnSet; //These are the button in the start screen.
    private boolean preferencesChanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this,R.xml.root_preferences,false);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        btnStart = findViewById(R.id.btnStart);
        btnSet = findViewById(R.id.btnSet);
        btnRules = findViewById(R.id.btnRules);
        btnCreatedBy= findViewById(R.id.btnCreatedBy);

        btnStart.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRulesPage();
            }
        });
        btnCreatedBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreditDialog();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferencesChanged){
            updatePref(PreferenceManager.getDefaultSharedPreferences(this));
            preferencesChanged = false;
        }
    }

    public void openGame() {
        Intent intent = new Intent( this, GameActivity.class);
        startActivity(intent);
    }

    public void openSettings() {
        Intent intent = new Intent( this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openRulesPage() {
        Intent intent = new Intent(this, RulesPage.class);
        startActivity(intent);
    }

    public void openCreditDialog() {
        creditDialog creditDialog = new creditDialog();
        creditDialog.show(getSupportFragmentManager(),"credit dialog");
    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    preferencesChanged = true;

                    updatePref(sharedPreferences);
                }
            };

    public void updatePref(SharedPreferences sharedPreferences) {
        String dif = sharedPreferences.getString("pref_difficulty", null);
        String wires = sharedPreferences.getString("pref_wires", null);
        assert dif != null;
        assert wires != null;

        Log.e("updatePref", dif);       //For testing!!
        Log.e("updatePref", wires);

        int temp1 = Integer.parseInt(dif);
        int temp2 = Integer.parseInt(wires);

        switch (temp1) {
            case 1:
                GameEngine.getInstance().setWIDTH(9);
                GameEngine.getInstance().setHEIGHT(13);
                break;
            case 2:
                GameEngine.getInstance().setWIDTH(12);
                GameEngine.getInstance().setHEIGHT(18);
                break;
            case 3:
                GameEngine.getInstance().setWIDTH(14);
                GameEngine.getInstance().setHEIGHT(21);
                break;
        }


        switch (temp2) {
            case 1:
                GameEngine.getInstance().setWIRE_NUMBER(15);
                break;
            case 2:
                GameEngine.getInstance().setWIRE_NUMBER(25);
                break;
            case 3:
                GameEngine.getInstance().setWIRE_NUMBER(35);
                break;
        }
    }


}
