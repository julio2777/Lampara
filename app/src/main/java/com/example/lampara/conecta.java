package com.example.lampara;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class conecta extends Toolbarclass {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conecta);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setupToolbar(myToolbar);
    }
}
