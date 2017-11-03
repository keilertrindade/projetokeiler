package projeto.bionet.example.com.bionet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class cadastroColeta extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    Address retornoCep;

    private EditText etQuantidade, etValor, etCep, etRua, etNum, etComplemento, etBairro, etCidade, etEstado;
    private Spinner spMaterial, spMedida, spModalidade, spEntrega;
    private CheckBox cbDinheiro, cbCredito, cbDebito, cbMercadoPago;
    private String material, medida, modalidade, quantidade, entrega, valor, cep, rua, num, complemento, bairro, cidade, estado, teste;
    private Boolean dinheiro, debito, credito, mercadoPago;
    DocumentReference profileRef;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_coleta);

        cbDinheiro = (CheckBox) findViewById(R.id.checkboxDinheiro);
        cbCredito = (CheckBox) findViewById(R.id.checkboxCredito);
        cbDebito = (CheckBox) findViewById(R.id.checkboxDebito);
        cbMercadoPago = (CheckBox) findViewById(R.id.checkboxMercadoPago);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        spMaterial = (Spinner) findViewById(R.id.spinnerMaterial);
        spMedida = (Spinner) findViewById(R.id.spinnerUnidade);
        spModalidade = (Spinner) findViewById(R.id.spinnerModalidade);
        spEntrega = (Spinner) findViewById(R.id.spinnerEntrega);
        etValor = (EditText) findViewById(R.id.etValor);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);

        etCep = (EditText) findViewById(R.id.cep);
        etRua = (EditText) findViewById(R.id.rua);
        etNum = (EditText) findViewById(R.id.num);
        etComplemento = (EditText) findViewById(R.id.complemento);
        etCidade = (EditText) findViewById(R.id.cidade);
        etBairro = (EditText) findViewById(R.id.bairro);
        etEstado = (EditText) findViewById(R.id.estado);

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
                    } else {
                        Toast.makeText(cadastroColeta.this, "Documento Inexistente",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(cadastroColeta.this, "" + task.getException(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void checarCampos(View v) {

        material = spMaterial.getSelectedItem().toString();
        medida = spMedida.getSelectedItem().toString();
        modalidade = spModalidade.getSelectedItem().toString();
        quantidade = etQuantidade.getText().toString();
        entrega = spEntrega.getSelectedItem().toString();
        valor = etValor.getText().toString();

        cep = etCep.getText().toString().trim();
        rua = etRua.getText().toString().trim();
        num = etNum.getText().toString().trim();
        complemento = etComplemento.getText().toString().trim();
        bairro = etBairro.getText().toString().trim();
        cidade = etCidade.getText().toString().trim();
        estado = etEstado.getText().toString().trim();

        if (TextUtils.isEmpty(quantidade)) {
            etQuantidade.setError("Preencha a quantidade do Material");
            etQuantidade.requestFocus();
            return;
        }
        else{
            checarPagamento();
        }

    }

    public void cadastrarColeta() {

        Map<String, Object> coleta = new HashMap<>();
        coleta.put("material", material);
        coleta.put("medida", medida);
        coleta.put("modalidade", modalidade);
        coleta.put("quantidade", quantidade);
        coleta.put("entrega", entrega);
        coleta.put("cep", cep);
        coleta.put("rua", rua);
        coleta.put("numero", num);
        coleta.put("complemento", complemento);
        coleta.put("bairro", bairro);
        coleta.put("cidade", cidade);
        coleta.put("estado", estado);
        coleta.put("proprietario", user.getUid());

        if (modalidade.equalsIgnoreCase("Venda")){
            coleta.put("valor", valor);
            coleta.put("dinheiro", dinheiro);
            coleta.put("debito", debito);
            coleta.put("credito", credito);
            coleta.put("mercado pago", mercadoPago);
        }

        db.collection("Coleta").add(coleta);

        Toast.makeText(cadastroColeta.this, "Material Cadastrado com Sucesso!",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(cadastroColeta.this, LobbyActivity.class);
        startActivity(intent);
        finish();
    }


    public void checarEndereco(){

        if (TextUtils.isEmpty(cep) || cep.length() < 8) {
            etCep.setError("O campo CEP deve ser preenchido!");
            etCep.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(rua)) {
            etRua.setError("O campo Rua deve ser preenchido!");
            return;
        }
        else if (TextUtils.isEmpty(num)){
            etNum.setError("O campo número deve ser preenchido!");
        }
        else if (TextUtils.isEmpty(bairro)) {
            etBairro.setError("O campo Bairro deve ser preenchido!");
            return;
        }
        else if (TextUtils.isEmpty(cidade)) {
            etCidade.setError("O campo Cidade deve ser preenchido!");
            return;
        }
        else if (TextUtils.isEmpty(estado)) {
            etEstado.setError("O campo Estado deve ser preenchido!");
            return;
        }
        else {
            cadastrarColeta();
        }
    }

    public void checarPagamento(){

        if (modalidade.equalsIgnoreCase("Venda")) {

            if (!cbDebito.isChecked() && !cbCredito.isChecked() && !cbDinheiro.isChecked() && !cbMercadoPago.isChecked()) { // Fazer uma outra função, pois tenho que verificar se é doação ou coleta.*/
                Toast.makeText(cadastroColeta.this, "Por Favor selecione uma forma de pagamento",
                        Toast.LENGTH_LONG).show();
            }
            else if (TextUtils.isEmpty(valor)){
                etValor.setError("Preencha o Valor!");
                etValor.requestFocus();
                return;
            }
            else {
                formaPagamento();
            }
        }
        else{
            checarEndereco();
        }
    }

    public void formaPagamento(){

        if(cbDinheiro.isChecked()){
            dinheiro = true;
        }
        else{
            dinheiro = false;
        }

        if(cbDebito.isChecked()){
            debito = true;
        }
        else{
            debito = false;
        }

        if(cbCredito.isChecked()){
            credito = true;
        }
        else{
            credito = false;
        }

        if(cbMercadoPago.isChecked()){
            mercadoPago = true;
        }
        else{
            mercadoPago = false;
        }

        checarEndereco();
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

