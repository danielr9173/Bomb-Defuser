package com.example.bomb_defuser;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ((TextView)findViewById(R.id.textView)).setText("ASDASDASDASD");

        Log.e("GameActivity", "onCreate");
        GameEngine.getInstance().createGrid(this);

    }
}
