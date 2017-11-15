package projeto.bionet.example.com.bionet;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import static android.support.design.R.styleable.TextInputLayout;
public class Cadastro_Usuario extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private FirebaseUser user;
    private EditText etEmail, etSenha, etNome, etSnome, etCpf, etCep, etRua, etNum, etComplemento, etBairro, etCidade, etEstado;
    private String email, senha, nome, snome, cpf, cep, rua, num, complemento, bairro, cidade, estado, teste;
    private Address retornoCep;
    DocumentReference profileRef;
    RadioGroup RGrupo;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__usuario);
        android.support.design.widget.TextInputLayout emailV = (TextInputLayout) findViewById(R.id.emailV);
        TextInputLayout senhaV = (TextInputLayout) findViewById(R.id.senhaV);
        TextInputLayout cpfV = (TextInputLayout) findViewById(R.id.cpflayout);
        RGrupo = (RadioGroup) findViewById(R.id.radiogroup);
        Intent intent1 = getIntent();
        if(intent1.getStringExtra("atividade").equalsIgnoreCase("alterar")){
           /* emailV.setVisibility(View.INVISIBLE);
            senhaV.setVisibility(View.INVISIBLE);*/

            ((LinearLayout) senhaV.getParent()).removeView(senhaV);
            ((LinearLayout) emailV.getParent()).removeView(emailV);

        }else{
            emailV.setVisibility(View.VISIBLE);
            senhaV.setVisibility(View.VISIBLE);
        }
        mAuth = FirebaseAuth.getInstance();
        // mDatabase = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        //teste = "";
        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        if (intent.getStringExtra("atividade").equalsIgnoreCase("alterar")){
            etNome = (EditText) findViewById(R.id.nome);
            etSnome = (EditText) findViewById(R.id.sobrenome);
            etCpf = (EditText) findViewById(R.id.cpf);
            etCpf.setEnabled(false);
            etCep = (EditText) findViewById(R.id.cep);
            etRua = (EditText) findViewById(R.id.rua);
            etNum = (EditText) findViewById(R.id.num);
            etComplemento = (EditText) findViewById(R.id.complemento);
            etCidade = (EditText) findViewById(R.id.cidade);
            etBairro = (EditText) findViewById(R.id.bairro);
            etEstado = (EditText) findViewById(R.id.estado);
            Button button = (Button) findViewById(R.id.botao_registro);
            button.setText("Alterar");
            TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.nome1);
            textInputLayout.setTop(10);

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Carregando Dados...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

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
                            etNome.setText(document.getString("nome"));
                            etSnome.setText(document.getString("sobrenome"));
                            etCpf.setText(document.getString("cpf"));
                        } else {
                            Toast.makeText(Cadastro_Usuario.this, "Falha ao carregar dados.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    progressDialog.dismiss();
                }
            });


            etNome = (EditText) findViewById(R.id.nome);
            etSnome = (EditText) findViewById(R.id.sobrenome);
            etCpf = (EditText) findViewById(R.id.cpf);
            etCep = (EditText) findViewById(R.id.cep);
            etRua = (EditText) findViewById(R.id.rua);
            etNum = (EditText) findViewById(R.id.num);
            etComplemento = (EditText) findViewById(R.id.complemento);
            etCidade = (EditText) findViewById(R.id.cidade);
            etBairro = (EditText) findViewById(R.id.bairro);
            etEstado = (EditText) findViewById(R.id.estado);

            ((LinearLayout) RGrupo.getParent()).removeView(RGrupo);
            getSupportActionBar().setTitle("Alteração de Cadastro");



        }else if(intent.getStringExtra("atividade").equalsIgnoreCase("cadastrar")) {
            etEmail = (EditText) findViewById(R.id.email);
            etSenha = (EditText) findViewById(R.id.password);
            etNome = (EditText) findViewById(R.id.nome);
            etSnome = (EditText) findViewById(R.id.sobrenome);
            etCpf = (EditText) findViewById(R.id.cpf);
            etCep = (EditText) findViewById(R.id.cep);
            etRua = (EditText) findViewById(R.id.rua);
            etNum = (EditText) findViewById(R.id.num);
            etComplemento = (EditText) findViewById(R.id.complemento);
            etCidade = (EditText) findViewById(R.id.cidade);
            etBairro = (EditText) findViewById(R.id.bairro);
            etEstado = (EditText) findViewById(R.id.estado);

            RGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.p_juridica) {
                        Intent intent = new Intent(Cadastro_Usuario.this, cadastroPJuridica.class);
                        intent.putExtra("atividade","cadastrar");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                        finish();
                    }
                }
            });
        }


    }
    public void checarCampos(View v) {
        Intent intent = getIntent();
        if (intent.getStringExtra("atividade").equalsIgnoreCase("alterar")) {
            nome = etNome.getText().toString().trim();
            snome = etSnome.getText().toString().trim();
            cep = etCep.getText().toString().trim();
            rua = etRua.getText().toString().trim();
            num = etNum.getText().toString().trim();
            complemento = etComplemento.getText().toString().trim();
            bairro = etBairro.getText().toString().trim();
            cidade = etCidade.getText().toString().trim();
            estado = etEstado.getText().toString().trim();
            if (TextUtils.isEmpty(nome)) {
                etNome.setError("O campo Nome deve ser preenchido!");
                etNome.requestFocus();
                return;
            } else if (TextUtils.isEmpty(snome)) {
                etSnome.setError("O campo Sobrenome deve ser preenchido!");
                etSnome.requestFocus();
                return;
            } else if (TextUtils.isEmpty(cep) || cep.length() < 8) {
                etCep.setError("O campo CEP deve ser preenchido!");
                etCep.requestFocus();
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
            nome = etNome.getText().toString().trim();
            snome = etSnome.getText().toString().trim();
            cpf = etCpf.getText().toString().trim();
            cep = etCep.getText().toString().trim();
            rua = etRua.getText().toString().trim();
            num = etNum.getText().toString().trim();
            complemento = etComplemento.getText().toString().trim();
            bairro = etBairro.getText().toString().trim();
            cidade = etCidade.getText().toString().trim();
            estado = etEstado.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                etEmail.setError("O campo Email deve ser preenchido!");
                etEmail.requestFocus();
                return;
            } else if (TextUtils.isEmpty(senha) || senha.length() < 6) {
                etSenha.setError("O campo Senha deve ser preenchido com no mínimo 6 caracteres!");
                etSenha.requestFocus();
                return;
            } else if (TextUtils.isEmpty(nome)) {
                etNome.setError("O campo Nome deve ser preenchido!");
                etNome.requestFocus();
                return;
            } else if (TextUtils.isEmpty(snome)) {
                etSnome.setError("O campo Sobrenome deve ser preenchido!");
                etSnome.requestFocus();
                return;
            } else if (TextUtils.isEmpty(cpf) || cpf.length() < 11) {
                etCpf.setError("O campo CPF deve ser preenchido corretamente!");
                etCpf.requestFocus();
                return;
            } else if (TextUtils.isEmpty(cep) || cep.length() < 8) {
                etCep.setError("O campo CEP deve ser preenchido!");
                etCep.requestFocus();
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
                Cadastrar(/*email,senha,nome,snome,cpf,cep,rua,num,bairro,cidade,estado*/);
            }
        }
    }
    // Talvez não precisa passar nada por parametro, Já que as variaveis são da activty.
    public void Cadastrar(/*String email, String senha, String nome, String snome, String cpf, String cep,
                          String rua, String num, String bairro, String cidade, String estado */) {
        Intent intent = getIntent();
        if(intent.getStringExtra("atividade").equalsIgnoreCase("cadastrar")) {
            mAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Cadastro_Usuario.this, "Erro ao criar usuário!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Cadastro_Usuario.this, "Usuário Criado com sucesso!",
                                        Toast.LENGTH_LONG).show();
                                salvarPerfil();
                            }
                        }
                    });
        }else if(intent.getStringExtra("atividade").equalsIgnoreCase("alterar")){
            Toast.makeText(Cadastro_Usuario.this, "alteração realizada com sucesso!",
                    Toast.LENGTH_LONG).show();
            salvarPerfil();
        }
    }
    public void salvarPerfil() {
        Intent intent = getIntent();
        if (intent.getStringExtra("atividade").equalsIgnoreCase("cadastrar")) {
            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Cadastro_Usuario.this, "Erro ao salvar informações - LOGAR 2!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String id = user.getUid();
                                Map<String, Object> usuario = new HashMap<>();
                                usuario.put("nome", nome);
                                usuario.put("sobrenome", snome);
                                usuario.put("cpf", cpf);
                                usuario.put("cep", cep);
                                usuario.put("rua", rua);
                                usuario.put("numero", num);
                                usuario.put("complemento", complemento);
                                usuario.put("bairro", bairro);
                                usuario.put("cidade", cidade);
                                usuario.put("estado", estado);
                                usuario.put("tipo", "Pessoa Fisica");
                                db.collection("Profile").document(id).set(usuario);
                                Intent intent = new Intent(Cadastro_Usuario.this, Login.class);
                                startActivity(intent);
                            }
                        }
                    });
        }else if(intent.getStringExtra("atividade").equalsIgnoreCase("alterar")){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String id = user.getUid();
            Map<String, Object> usuario = new HashMap<>();
            usuario.put("nome", nome);
            usuario.put("sobrenome", snome);
            usuario.put("cpf", cpf);
            usuario.put("cep", cep);
            usuario.put("rua", rua);
            usuario.put("numero", num);
            usuario.put("complemento", complemento);
            usuario.put("bairro", bairro);
            usuario.put("cidade", cidade);
            usuario.put("estado", estado);
            usuario.put("tipo", "Pessoa Fisica");
            db.collection("Profile").document(id).set(usuario);
            Intent intent1 = new Intent(Cadastro_Usuario.this, Login.class);
            startActivity(intent1);
        }
    }
    public void requestCep(View v) throws Exception {
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
                    teste = jsonString.toString();
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
