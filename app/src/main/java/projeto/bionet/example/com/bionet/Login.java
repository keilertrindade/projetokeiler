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
    private EditText edtEmail, edtSenha;
    String email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mAuth = FirebaseAuth.getInstance();

        edtSenha = (EditText) findViewById(R.id.senha);
        edtEmail = (EditText) findViewById(R.id.email);
        botao = (Button) findViewById(R.id.botao_registro);
        email = edtEmail.getText().toString() ;


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

    public void clickLogin(View v) {

        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Log.w("AUTH", "Falha ao efetuar o Login: ", task.getException());
                }else{
                    Log.d("AUTH", "Login Efetuado com sucesso!!!");
                }
            }
        });
    }


    public void Cadastro (View v) {
        Intent intent = new Intent(Login.this,Cadastro_Usuario.class);
        startActivity(intent);
    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

