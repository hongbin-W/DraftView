package com.hongbin.draftview;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hongbin.library.DraftControllerView;

public class MainActivity extends AppCompatActivity {
    DraftControllerView viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewById = findViewById(R.id.draft);

    }

    public void onclick(View view) {
        viewById.show();
    }
}
