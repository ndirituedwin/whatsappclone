package com.example.kampuskonekt.Groups;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.kampuskonekt.R;

public class GroupCreateActivity extends AppCompatActivity {

//    private ActionBar toolbar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);
        initializefields();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initializefields() {
               toolbar=findViewById(R.id.groupstoolbar);
              setSupportActionBar(toolbar);
              getSupportActionBar().setDisplayHomeAsUpEnabled(true);
              getSupportActionBar().setDisplayShowHomeEnabled(true);
              getSupportActionBar().setTitle("Create Group");

    }
}