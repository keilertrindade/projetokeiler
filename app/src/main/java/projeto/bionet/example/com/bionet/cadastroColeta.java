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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class cadastroColeta extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;

    private EditText etCep, etRua, etNum, etComplemento, etBairro, etCidade, etEstado;
    private Spinner spMaterial, spMedida, spModalidade, spEntrega;
    private CheckBox cbDinheiro, cbCredito, cbDebito, cbMercadoPago;
    private String material, medida, modalidade, cep, rua, num, complemento, bairro, cidade, estado;
    private Float quantidade;
    private Boolean entrega, dinheiro, debito, credito, mercadoPago;
    DocumentReference profileRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_coleta);

        cbDinheiro = (CheckBox) findViewById(R.id.checkboxDinheiro);
        cbCredito = (CheckBox) findViewById(R.id.checkboxCredito);
        cbDebito = (CheckBox) findViewById(R.id.checkboxDebito);
        cbMercadoPago = (CheckBox) findViewById(R.id.checkboxMercadoPago);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        spMaterial = (Spinner) findViewById(R.id.spinnerMaterial);
        spMedida = (Spinner) findViewById(R.id.spinnerUnidade);
        spModalidade = (Spinner) findViewById(R.id.spinnerModalidade);
        spEntrega = (Spinner) findViewById(R.id.spinnerEntrega);



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
        cep = etCep.getText().toString().trim();
        rua = etRua.getText().toString().trim();
        num = etNum.getText().toString().trim();
        complemento = etComplemento.getText().toString().trim();
        bairro = etBairro.getText().toString().trim();
        cidade = etCidade.getText().toString().trim();
        estado = etEstado.getText().toString().trim();


        if (TextUtils.isEmpty(cep) || cep.length() < 8) {
            etCep.setError("O campo CEP deve ser preenchido!");
            etCep.requestFocus();
            return;
        }
        // Talvez esses próximos não sejam necessários tendo em vista a checagem do CEP
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
        }else if(!cbDebito.isChecked() || !cbCredito.isChecked() || !cbDinheiro.isChecked() || !cbMercadoPago.isChecked()){ // Fazer uma outra função, pois tenho que verificar se é doação ou coleta.
            Toast.makeText(cadastroColeta.this, "Por Favor selecione u",
                    Toast.LENGTH_LONG).show();
        }
        else {
            cadastrarColeta(/*email,senha,nome,snome,cpf,cep,rua,num,bairro,cidade,estado */);
        }
    }

    public void cadastrarColeta() {


        Map<String, Object> coleta = new HashMap<>();
        coleta.put("material", material);
        coleta.put("medida", medida);
        coleta.put("modalidade", modalidade);
        coleta.put("quantidade", quantidade);
        coleta.put("cep", cep);
        coleta.put("rua", rua);
        coleta.put("numero", num);
        coleta.put("complemento", complemento);
        coleta.put("bairro", bairro);
        coleta.put("cidade", cidade);
        coleta.put("estado", estado);
        coleta.put("proprietario", user.getUid());


        db.collection("Coleta").add(coleta);



        Intent intent = new Intent(cadastroColeta.this, LobbyActivity.class);
        startActivity(intent);
    }


}

