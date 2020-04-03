package com.example.bomb_defuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnRules, btnCreatedBy, btnStart; //These are the button in the start screen.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRules = findViewById(R.id.btnRules);
        btnCreatedBy= findViewById(R.id.btnCreatedBy);
        btnStart = findViewById(R.id.btnStart);

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
        btnStart.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
    }

    public void openRulesPage() {
        Intent intent = new Intent(this, RulesPage.class);
        startActivity(intent);
    }

    public void openCreditDialog() {
        creditDialog creditDialog = new creditDialog();
        creditDialog.show(getSupportFragmentManager(),"credit dialog");
    }

    public void openGame() {
        Intent intent = new Intent( this, GameActivity.class);
        startActivity(intent);
    }
}
