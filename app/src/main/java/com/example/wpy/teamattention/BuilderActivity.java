package com.example.wpy.teamattention;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BuilderActivity extends AppCompatActivity {
    private String token;
    ImageView image;
    TextView message;
    private int group_id;
    private int user_id;
    int rank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);
        image = findViewById(R.id.builder_image);
        message = findViewById(R.id.builder_message);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        user_id = intentfront.getIntExtra("user_id", 0);
        group_id = intentfront.getIntExtra("group_id", 0);
        rank = intentfront.getIntExtra("rank", 3);
        image.setImageResource(R.drawable.people);
        int id = intentfront.getIntExtra("id", 0);
        String email = intentfront.getStringExtra("email");
        String name = intentfront.getStringExtra("name");
        final int privilege = intentfront.getIntExtra("privilege", 0);
        message.setText("账户: " + name + "\nemail: " + email);
    }
}
