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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.support.design.R.styleable.TextInputLayout;

public class Cadastro_Usuario extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etEmail, etSenha, etNome, etSnome, etCpf, etCep, etRua, etBairro, etCidade, etEstado;
    private String email, senha, nome, snome, cpf, cep, rua, bairro, cidade, estado;


    RadioGroup RGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__usuario);
        RGrupo = (RadioGroup) findViewById(R.id.radiogroup);
        mAuth = FirebaseAuth.getInstance();

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

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                           // myRef.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            myRef.child("cpf").setValue(cpf);
                            myRef.child("nome").setValue(nome);
                            myRef.child("sobrenome").setValue(snome);
                            myRef.child("email").setValue(email);

                            myRef.child("cep").setValue(cep);
                            myRef.child("rua").setValue(rua);
                            myRef.child("bairro").setValue(bairro);
                            myRef.child("cidade").setValue(cidade);
                            myRef.child("estado").setValue(estado);
                        }
                    }
                });

    }
}

