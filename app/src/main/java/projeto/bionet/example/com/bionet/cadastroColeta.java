package projeto.bionet.example.com.bionet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class cadastroColeta extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    private EditText etCep, etRua, etNum, etComplemento, etBairro, etCidade, etEstado;
    private Spinner spMaterial, spMedida, spModalidade, spEntrega;
    private String material, medida, modalidade, cep, rua, num, complemento, bairro, cidade, estado;
    private Float quantidade;
    private Boolean entrega, dinheiro, debito, credito, mercadoPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_coleta);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

    }
}
