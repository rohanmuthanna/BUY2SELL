package buy2sell.rohanmuthanna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mForgotPassword;
    private Button mPasswordRecoverButton;
    private TextView mGoBackToLogin;
    private ProgressBar mForgotPasswordProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mForgotPassword = findViewById(R.id.forgotPassword);
        mPasswordRecoverButton = findViewById(R.id.passwordRecoverButton);
        mGoBackToLogin = findViewById(R.id.goBackToLogin);
        mForgotPasswordProgressBar = findViewById(R.id.forgotPasswordProgressBar);

        mAuth=FirebaseAuth.getInstance();

        mGoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

        mPasswordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mForgotPassword.getText().toString().trim();
                if (mail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter Registered Email To Continue", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                loading(true);
                                Toast.makeText(getApplicationContext(), "Password Reset Email Sent Successfully (Check Spam Folder)", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPasswordActivity.this,SignInActivity.class));
                            }
                            else
                            {
                                loading(false);
                                Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });






    }

    private void loading(Boolean isLoading)
    {
        if (isLoading)
        {
            mPasswordRecoverButton.setVisibility(View.INVISIBLE);
            mForgotPasswordProgressBar.setVisibility(View.VISIBLE);
        } else
        {
            mForgotPasswordProgressBar.setVisibility(View.INVISIBLE);
            mPasswordRecoverButton.setVisibility(View.VISIBLE);
        }
    }


}