package projeto.bionet.example.com.bionet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.support.design.R.styleable.TextInputLayout;

public class Cadastro_Usuario extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText etEmail, etSenha, etNome, etSnome, etCpf, etCep, etRua, etBairro, etCidade, etEstado;
    private String email, senha, nome, snome, cpf, cep, rua, bairro, cidade, estado;


    RadioGroup RGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__usuario);
        RGrupo = (RadioGroup) findViewById(R.id.radiogroup);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        etEmail = (EditText) findViewById(R.id.email);
        etSenha = (EditText) findViewById(R.id.password);
        etNome = (EditText) findViewById(R.id.nome);
        etSnome = (EditText) findViewById(R.id.sobrenome);
        etCpf = (EditText) findViewById(R.id.cpf);
        etCep = (EditText) findViewById(R.id.cep);
        etRua = (EditText) findViewById(R.id.rua);
        etCidade = (EditText) findViewById(R.id.cidade);
        etBairro = (EditText) findViewById(R.id.bairro);
        etEstado = (EditText) findViewById(R.id.estado);


        RGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.p_juridica) {
                    Intent intent = new Intent(Cadastro_Usuario.this,cadastroPJuridica.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                    finish();
                }

            }
        });
    }


    public void Cadastrar(View view){

        email = etEmail.getText().toString().trim();
        senha = etSenha.getText().toString().trim();
        nome = etNome.getText().toString().trim();
        snome = etSnome.getText().toString().trim();
        cpf = etCpf.getText().toString().trim();
        cep = etCep.getText().toString().trim();
        rua = etRua.getText().toString().trim();
        bairro = etBairro.getText().toString().trim();
        cidade = etCidade.getText().toString().trim();
        estado = etEstado.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Cadastro_Usuario.this, "Erro ao criar usuário!",
                                    Toast.LENGTH_SHORT).show();
                        }else{

                            Toast.makeText(Cadastro_Usuario.this, "Usuário Criado com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                            salvarPerfil();
                        }
                    }
                });

    }

    public void salvarPerfil(){

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Cadastro_Usuario.this, "Erro ao salvar informações - LOGAR 2!",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String id = user.getUid();

                            Map<String, Object> usuario = new HashMap<>();
                            usuario.put("nome", nome);
                            usuario.put("sobrenome", snome);
                            usuario.put("cpf", cpf);
                            usuario.put("cep", cep);
                            usuario.put("rua", rua);
                            usuario.put("bairro", bairro);
                            usuario.put("cidade", cidade);
                            usuario.put("estado", estado);

                            db.collection("usuarios").document(id).collection("Profile")
                                    .document(id).set(usuario);


                            Intent intent = new Intent(Cadastro_Usuario.this, Login.class);
                            startActivity(intent);
                         }

                    }
                });




    }

}



