package projeto.bionet.example.com.bionet;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class cadastroPJuridica extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private EditText etEmail, etSenha, etRazao, etTelefone, etCnpj, etCep, etRua, etNum, etComplemento, etBairro, etCidade, etEstado;
    private String email, senha, razao, telefone, cnpj, cep, rua, num, complemento, bairro, cidade, estado;
    private Address retornoCep;
    DocumentReference profileRef;

    RadioGroup RGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_cadastro_pjuridica);
        RGrupo = (RadioGroup) findViewById(R.id.radiogroup);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        TextInputLayout senhaV = (TextInputLayout) findViewById(R.id.senhaV);
        TextInputLayout emailV = (TextInputLayout) findViewById(R.id.emailV);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent1 = getIntent();
        if(intent1.getStringExtra("atividade").equalsIgnoreCase("alterar")){
            emailV.setVisibility(View.INVISIBLE);
            senhaV.setVisibility(View.INVISIBLE);
        }
        Intent intent = getIntent();
        if (intent.getStringExtra("atividade").equalsIgnoreCase("alterar")) {
            etRazao = (EditText) findViewById(R.id.razao);
            etTelefone = (EditText) findViewById(R.id.telefone);
            etCnpj = (EditText) findViewById(R.id.cnpj);
            etCnpj.setEnabled(false);
            etCep = (EditText) findViewById(R.id.cep);
            etRua = (EditText) findViewById(R.id.rua);
            etNum = (EditText) findViewById(R.id.num);
            etComplemento = (EditText) findViewById(R.id.complemento);
            etCidade = (EditText) findViewById(R.id.cidade);
            etBairro = (EditText) findViewById(R.id.bairro);
            etEstado = (EditText) findViewById(R.id.estado);
            Button button = (Button) findViewById(R.id.botao_registro);
            button.setText("alterar");
            profileRef = db.collection("Profile").document(user.getUid());
            profileRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            etRua.setText(document.getString("rua"));
                            etNum.setText(document.getString("numero"));
                            etCep.setText(document.getString("cep"));
                            etBairro.setText(document.getString("bairro"));
                            etCidade.setText(document.getString("cidade"));
                            etEstado.setText(document.getString("estado"));
                            etRazao.setText(document.getString("Razão Social"));
                            etCnpj.setText(document.getString("cnpj"));
                            etTelefone.setText(document.getString("telefone"));
                        } else {
                            Toast.makeText(cadastroPJuridica.this, "Documento Inexistente",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(cadastroPJuridica.this, "" + task.getException(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
            etRazao = (EditText) findViewById(R.id.razao);
            etCnpj = (EditText) findViewById(R.id.cnpj);
            etCep = (EditText) findViewById(R.id.cep);
            etRua = (EditText) findViewById(R.id.rua);
            etNum = (EditText) findViewById(R.id.num);
            etComplemento = (EditText) findViewById(R.id.complemento);
            etCidade = (EditText) findViewById(R.id.cidade);
            etBairro = (EditText) findViewById(R.id.bairro);
            etEstado = (EditText) findViewById(R.id.estado);
            profileRef = db.collection("Profile").document(user.getUid());


            RGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.p_fisica) {
                        Intent intent = new Intent(cadastroPJuridica.this, Cadastro_Usuario.class);
                        intent.putExtra("atividade", "alterar");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                        finish();
                    }
                }
            });
        } else if (intent.getStringExtra("atividade").equalsIgnoreCase("cadastrar")) {
            etEmail = (EditText) findViewById(R.id.email);
            etSenha = (EditText) findViewById(R.id.password);
            etRazao = (EditText) findViewById(R.id.razao);
            etCnpj = (EditText) findViewById(R.id.cnpj);
            etCep = (EditText) findViewById(R.id.cep);
            etRua = (EditText) findViewById(R.id.rua);
            etNum = (EditText) findViewById(R.id.num);
            etComplemento = (EditText) findViewById(R.id.complemento);
            etCidade = (EditText) findViewById(R.id.cidade);
            etBairro = (EditText) findViewById(R.id.bairro);
            etEstado = (EditText) findViewById(R.id.estado);
            etTelefone = (EditText) findViewById(R.id.telefone);

            RGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.p_fisica) {
                        Intent intent = new Intent(cadastroPJuridica.this, Cadastro_Usuario.class);
                        intent.putExtra("atividade", "cadastrar");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                        finish();
                    }
                }
            });
        }
    }


    public void checarCampos(View v){

        Intent intent = getIntent();
        if (intent.getStringExtra("atividade").equalsIgnoreCase("alterar")) {
            razao = etRazao.getText().toString().trim();
            cnpj = etCnpj.getText().toString().trim();
            cep = etCep.getText().toString().trim();
            rua = etRua.getText().toString().trim();
            num = etNum.getText().toString().trim();
            complemento = etComplemento.getText().toString().trim();
            bairro = etBairro.getText().toString().trim();
            cidade = etCidade.getText().toString().trim();
            estado = etEstado.getText().toString().trim();
            telefone = etTelefone.getText().toString().trim();
            if (TextUtils.isEmpty(razao)) {
                etRazao.setError("O campo Nome deve ser preenchido!");
                etRazao.requestFocus();
                return;
            } else if (TextUtils.isEmpty(cep) || cep.length() < 8) {
                etCep.setError("O campo CEP deve ser preenchido!");
                etCep.requestFocus();
                return;
            }else if (TextUtils.isEmpty(telefone) || telefone.length() < 11){
                etTelefone.setError("Preencha o campo Telefone corretamente");
                etTelefone.requestFocus();
                return;
            }
            // Talvez esses próximos não sejam necessários tendo em vista a checagem do CEP
            else if (TextUtils.isEmpty(rua)) {
                etRua.setError("O campo Rua deve ser preenchido!");
                return;
            } else if (TextUtils.isEmpty(num)) {
                etNum.setError("O campo número deve ser preenchido!");
            } else if (TextUtils.isEmpty(bairro)) {
                etBairro.setError("O campo Bairro deve ser preenchido!");
                return;
            } else if (TextUtils.isEmpty(cidade)) {
                etCidade.setError("O campo Cidade deve ser preenchido!");
                return;
            } else if (TextUtils.isEmpty(estado)) {
                etEstado.setError("O campo Estado deve ser preenchido!");
                return;
            } else {
                salvarPerfil();
            }
        } else if (intent.getStringExtra("atividade").equalsIgnoreCase("cadastrar")) {
            email = etEmail.getText().toString().trim();
            senha = etSenha.getText().toString().trim();
            razao = etRazao.getText().toString().trim();
            cnpj = etCnpj.getText().toString().trim();
            cep = etCep.getText().toString().trim();
            rua = etRua.getText().toString().trim();
            num = etNum.getText().toString().trim();
            complemento = etComplemento.getText().toString().trim();
            bairro = etBairro.getText().toString().trim();
            cidade = etCidade.getText().toString().trim();
            estado = etEstado.getText().toString().trim();
            telefone = etTelefone.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                etEmail.setError("O campo Email deve ser preenchido!");
                etEmail.requestFocus();
                return;
            } else if (TextUtils.isEmpty(senha) || senha.length() < 6) {
                etSenha.setError("O campo Senha deve ser preenchido com no mínimo 6 caracteres!");
                etSenha.requestFocus();
                return;
            } else if (TextUtils.isEmpty(razao)) {
                etRazao.setError("O campo razão social deve ser preenchido!");
                etRazao.requestFocus();
                return;
            } else if (TextUtils.isEmpty(cnpj) || cnpj.length() < 11) {
                etCnpj.setError("O campo CNPJ deve ser preenchido corretamente!");
                etCnpj.requestFocus();
                return;
            } else if (TextUtils.isEmpty(cep) || cep.length() < 8) {
                etCep.setError("O campo CEP deve ser preenchido!");
                etCep.requestFocus();
                return;
            }else if (TextUtils.isEmpty(telefone) || telefone.length() < 11){
                etTelefone.setError("Preencha o campo Telefone corretamente");
                etTelefone.requestFocus();
                return;
            }
            // Talvez esses próximos não sejam necessários tendo em vista a checagem do CEP
            else if (TextUtils.isEmpty(rua)) {
                etRua.setError("O campo Rua deve ser preenchido!");
                return;
            } else if (TextUtils.isEmpty(num)) {
                etNum.setError("O campo número deve ser preenchido!");
            } else if (TextUtils.isEmpty(bairro)) {
                etBairro.setError("O campo Bairro deve ser preenchido!");
                return;
            } else if (TextUtils.isEmpty(cidade)) {
                etCidade.setError("O campo Cidade deve ser preenchido!");
                return;
            } else if (TextUtils.isEmpty(estado)) {
                etEstado.setError("O campo Estado deve ser preenchido!");
                return;
            } else {
                Cadastrar();
            }
        }
    }

    public void Cadastrar(){

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(cadastroPJuridica.this, "Erro ao criar usuário!",
                                    Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(cadastroPJuridica.this, "Usuário Criado com sucesso!",
                                    Toast.LENGTH_LONG).show();

                            salvarPerfil();
                        }
                    }
                });
    }

    public void salvarPerfil() {
        Intent intent = getIntent();
        if (intent.getStringExtra("atividade").equalsIgnoreCase("cadastrar")) {
            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(cadastroPJuridica.this, "Erro ao salvar informações - LOGAR 2!",
                                        Toast.LENGTH_LONG).show();
                            } else {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String id = user.getUid();

                                Map<String, Object> usuario = new HashMap<>();
                                usuario.put("Razão Social", razao);
                                usuario.put("cnpj", cnpj);
                                usuario.put("cep", cep);
                                usuario.put("rua", rua);
                                usuario.put("numero", num);
                                usuario.put("complemento", complemento);
                                usuario.put("bairro", bairro);
                                usuario.put("cidade", cidade);
                                usuario.put("estado", estado);
                                usuario.put("tipo", "Pessoa Juridíca");
                                usuario.put("telefone", telefone);

                                db.collection("Profile").document(id).set(usuario);


                                Intent intent = new Intent(cadastroPJuridica.this, Login.class);
                                startActivity(intent);
                                finish();
                                // Talvez dar logout do usuário ativo para que ele possa realizar o login

                            }
                        }
                    });
        }else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String id = user.getUid();

            Map<String, Object> usuario = new HashMap<>();
            usuario.put("Razão Social", razao);
            usuario.put("cnpj", cnpj);
            usuario.put("cep", cep);
            usuario.put("rua", rua);
            usuario.put("numero", num);
            usuario.put("complemento", complemento);
            usuario.put("bairro", bairro);
            usuario.put("cidade", cidade);
            usuario.put("estado", estado);
            usuario.put("tipo", "Pessoa Juridíca");
            usuario.put("telefone", telefone);

            db.collection("Profile").document(id).set(usuario);


            Intent intent1 = new Intent(cadastroPJuridica.this, Login.class);
            startActivity(intent1);
            finish();
        }
    }

    public void requestCep(View v) throws Exception { // Adicionar IF/Else para informar erro no CEP.

        cep = etCep.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));

                    StringBuilder jsonString = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        jsonString.append(line);
                    }

                    urlConnection.disconnect();
                    String teste = jsonString.toString();
                    Gson gson = new Gson();
                    retornoCep = gson.fromJson(teste, Address.class);

                    etRua.post(new Runnable() {
                        @Override
                        public void run() {
                            etRua.setText(retornoCep.getLogradouro());
                        }
                    });


                    etBairro.post(new Runnable() {
                        @Override
                        public void run() {
                            etBairro.setText(retornoCep.getBairro());
                        }
                    });

                    etCidade.post(new Runnable() {
                        @Override
                        public void run() {
                            etCidade.setText(retornoCep.getLocalidade());
                        }
                    });

                    etEstado.post(new Runnable() {
                        @Override
                        public void run() {
                            etEstado.setText(retornoCep.getUf());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}