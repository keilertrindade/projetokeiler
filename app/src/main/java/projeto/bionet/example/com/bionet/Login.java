package projeto.bionet.example.com.bionet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class Login extends AppCompatActivity {

    private Button botao;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etEmail, etSenha;
    String email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mAuth = FirebaseAuth.getInstance();

        etSenha = (EditText) findViewById(R.id.senha);
        etEmail = (EditText) findViewById(R.id.email);
        botao = (Button) findViewById(R.id.botao_registro);



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(getApplicationContext(), "Bem Vindo " + user.getDisplayName() + "!",
                            Toast.LENGTH_LONG).show();
                    Intent it = new Intent(getApplicationContext(),LobbyActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Sem Usuários Conectados!",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void clickLogin(View v) {

        email = etEmail.getText().toString();
        senha = etSenha.getText().toString();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //String exp = task.getException().toString();
                            Toast.makeText(Login.this, "Não logou",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Login.this, "Bem Vindo!",
                                    Toast.LENGTH_LONG).show();
                            }

                        // ...
                    }
                });
    }


    public void Cadastro (View v) {
        Intent intent = new Intent(Login.this,Cadastro_Usuario.class);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

