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

public class SignUpActivity extends AppCompatActivity {

    private EditText mSignupEmail,mSignupPassword;
    private Button mButtonSignUp;
    private ProgressBar mSignupProgressBar;
    private TextView mBackToLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mSignupEmail = findViewById(R.id.signupEmail);
        mSignupPassword = findViewById(R.id.signupPassword);
        mButtonSignUp = findViewById(R.id.buttonSignUp);
        mSignupProgressBar = findViewById(R.id.signupProgressBar);
        mBackToLogin = findViewById(R.id.backToLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        mBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = mSignupEmail.getText().toString().trim();
                String password = mSignupPassword.getText().toString().trim();

                if (mail.isEmpty() ||  password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }

                else if (password.length()<8)
                {
                    Toast.makeText(getApplicationContext(), "Password Should Contain More Than 8 Characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                loading(true);
                                Toast.makeText(getApplicationContext(), "Account Creation Successful, Check Email For Verification (Check Spam Folder)", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else
                            {
                                loading(false);
                                Toast.makeText(getApplicationContext(), "Failed! Email Already Exists", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }

    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Unknown  Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private void loading(Boolean isLoading)
    {
        if (isLoading)
        {
            mButtonSignUp.setVisibility(View.INVISIBLE);
            mSignupProgressBar.setVisibility(View.VISIBLE);
        } else
        {
            mSignupProgressBar.setVisibility(View.INVISIBLE);
            mButtonSignUp.setVisibility(View.VISIBLE);
        }
    }
}