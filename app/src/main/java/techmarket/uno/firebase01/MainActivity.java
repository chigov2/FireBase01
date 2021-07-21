package techmarket.uno.firebase01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText etName, etLastName, etEmail;
    private DatabaseReference myDatabase;
    private String USER_KEY = "user";
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void onClickSave(View view) {
        String id = myDatabase.getKey();
        String name = etName.getText().toString();
        String sec_name = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        newUser = new User(id,name,sec_name,email);

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sec_name) && !TextUtils.isEmpty(email))
        {
            myDatabase.push().setValue(newUser);
            Toast.makeText(this,"Info added successfully",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Empty field",Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickRead(View view) {
        Intent i = new Intent(MainActivity.this,ReadActivity.class);
        startActivity(i);
    }

    private void init()
    {
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        myDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
}