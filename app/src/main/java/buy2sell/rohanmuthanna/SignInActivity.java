package buy2sell.rohanmuthanna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText mLoginEmail, mLoginPassword;
    private Button mButtonLogin;
    private ProgressBar mLoginProgressBar;
    private TextView mGoToSignUp, mGoToForgotPassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mButtonLogin = findViewById(R.id.buttonLogin);
        mGoToSignUp = findViewById(R.id.goToSignUp);
        mLoginProgressBar = findViewById(R.id.loginProgressBar);
        mGoToForgotPassword = findViewById(R.id.goToForgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            finish();
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }

        mGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        mGoToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mLoginEmail.getText().toString().trim();
                String password = mLoginPassword.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkEmailVerification();
                            } else {
                                Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser.isEmailVerified()) {
            loading(true);
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        } else {
            loading(false);
            Toast.makeText(getApplicationContext(), "Email Verification Pending", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            mButtonLogin.setVisibility(View.INVISIBLE);
            mLoginProgressBar.setVisibility(View.VISIBLE);
        } else {
            mLoginProgressBar.setVisibility(View.INVISIBLE);
            mButtonLogin.setVisibility(View.VISIBLE);
        }
    }
}