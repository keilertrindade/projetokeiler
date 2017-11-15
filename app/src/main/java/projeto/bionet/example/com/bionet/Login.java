package projeto.bionet.example.com.bionet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    ProgressDialog progressDialog;

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
                    Intent it = new Intent(getApplicationContext(),LobbyActivity.class);
                    startActivity(it);
                    finish();
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

        if(TextUtils.isEmpty(email)) {
            etEmail.setError("O campo Email deve ser preenchido!");
            return;
        }
        else if (TextUtils.isEmpty(senha)){
            etSenha.setError("O campo Senha deve ser preenchido!");
        }
        else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Logando...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, "Usuário ou Senha Inválidos",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(Login.this, LobbyActivity.class);
                                startActivity(intent);
                            }
                            progressDialog.dismiss();
                        }
                    });


        }

    }


    public void Cadastro (View v) {
        Intent intent = new Intent(Login.this,Cadastro_Usuario.class);
        intent.putExtra("atividade","cadastrar");
        startActivity(intent);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
    }

    public void resetarsenha(View v){
        Intent intent = new Intent(Login.this,ResetPasswordActivity.class);
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

