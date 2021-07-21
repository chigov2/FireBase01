package techmarket.uno.firebase01;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText etLogin, etPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();

    }
    private void init()
    {
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickReg(View view)
    {
        //23. проверяем не пустые ли поля логина пароля
        if (!TextUtils.isEmpty(etLogin.getText().toString()) &&!TextUtils.isEmpty(etPassword.getText().toString()))
        {
            mAuth.createUserWithEmailAndPassword(etLogin.getText().toString(), etPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "User is registerd", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "User is not registered", Toast.LENGTH_SHORT).show();

                                //updateUI(null);
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickLogin(View view)
    {
        if (!TextUtils.isEmpty(etLogin.getText().toString()) &&!TextUtils.isEmpty(etPassword.getText().toString()))
        {
            mAuth.signInWithEmailAndPassword(etLogin.getText().toString(), etPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Authentication is OK.",
                                        Toast.LENGTH_SHORT).show();


                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    @Override//для проверки залогинился/существует ли пользователь
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null)
        {
            Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"User is null",Toast.LENGTH_SHORT).show();
        }

    }
}
