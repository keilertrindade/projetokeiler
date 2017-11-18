package projeto.bionet.example.com.bionet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Calendar;

public class cadastroColeta extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int SELECT_PHOTO = 100;
    private Uri selectedImage;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef,imageRef;
    private ProgressDialog progressDialog;
    private UploadTask uploadTask;
    private FirebaseUser user;
    private Address retornoCep;
    private Coleta coleta, coletaAlt;

    private EditText etQuantidade, etValor, etTelefone, etCep, etRua, etNum, etComplemento, etBairro, etCidade, etEstado;
    private Spinner spMaterial, spMedida, spModalidade, spEntrega;
    private CheckBox cbDinheiro, cbCredito, cbDebito, cbMercadoPago;
    private String material, medida, modalidade, quantidade, entrega, telefone, valor, cep, rua, num, complemento, bairro, cidade, estado, teste;
    private Boolean dinheiro, debito, credito, mercadoPago;
    DocumentReference profileRef, coletaRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_coleta);
        selectedImage = Uri.parse("android.resource://projeto.bionet.example.com.bionet/drawable/bionet");
        cbDinheiro = (CheckBox) findViewById(R.id.checkboxDinheiro);
        cbCredito = (CheckBox) findViewById(R.id.checkboxCredito);
        cbDebito = (CheckBox) findViewById(R.id.checkboxDebito);
        cbMercadoPago = (CheckBox) findViewById(R.id.checkboxMercadoPago);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        spMaterial = (Spinner) findViewById(R.id.spinnerMaterial);
        spMedida = (Spinner) findViewById(R.id.spinnerUnidade);
        spModalidade = (Spinner) findViewById(R.id.spinnerModalidade);
        spEntrega = (Spinner) findViewById(R.id.spinnerEntrega);
        etValor = (EditText) findViewById(R.id.etValor);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);

        etTelefone = (EditText) findViewById(R.id.telefone);
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
                        etTelefone.setText(document.getString("telefone"));
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

    @Override
    protected void onStart()
    {
        super.onStart();

        Intent intent = getIntent();

        if (intent.getStringExtra("atividade").equalsIgnoreCase("alterar")){

            coletaAlt = (Coleta) intent.getSerializableExtra("coleta");
            ArrayAdapter myAdap = (ArrayAdapter) spMaterial.getAdapter();

            spMaterial.setSelection(myAdap.getPosition(coletaAlt.getMaterial()));
            spMaterial.setEnabled(false);

            myAdap = (ArrayAdapter) spMedida.getAdapter();
            spMedida.setSelection(myAdap.getPosition(coletaAlt.getMedida()));

            myAdap = (ArrayAdapter) spModalidade.getAdapter();
            spModalidade.setSelection(myAdap.getPosition(coletaAlt.getModalidade()));

            myAdap = (ArrayAdapter) spEntrega.getAdapter();
            spEntrega.setSelection(myAdap.getPosition(coletaAlt.getEntrega()));

            etQuantidade.setText(coletaAlt.getQuantidade().toString());

            if(coletaAlt.getModalidade().equalsIgnoreCase("Venda")){
                etValor.setText(coletaAlt.getValor().toString());

                if (coletaAlt.getDinheiro() == true){
                    cbDinheiro.setChecked(true);
                }
                if (coletaAlt.getDebito() == true){
                    cbDebito.setChecked(true);
                }
                if (coletaAlt.getCredito() == true){
                    cbCredito.setChecked(true);
                }
                if (coletaAlt.getMercadopago() == true){
                    cbMercadoPago.setChecked(true);
                }

            }
        }
    }

    public void checarCampos(View v) {

        material = spMaterial.getSelectedItem().toString();
        medida = spMedida.getSelectedItem().toString();
        modalidade = spModalidade.getSelectedItem().toString();
        quantidade = etQuantidade.getText().toString();
        entrega = spEntrega.getSelectedItem().toString();
        valor = etValor.getText().toString();
        telefone = etTelefone.getText().toString().trim();

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
        }else if (TextUtils.isEmpty(telefone) || telefone.length() < 11){
            etTelefone.setError("Preencha o campo Telefone corretamente");
            etTelefone.requestFocus();
            return;
        }
        else{
            checarPagamento();
        }
    }

    public void cadastrarColeta() {
        Float qtd = Float.valueOf(quantidade);

        Intent intent = getIntent();

        if (intent.getStringExtra("atividade").equalsIgnoreCase("cadastrar")) {

            String status = "Ativo";

            final String randId = getSaltString();

            if (modalidade.equalsIgnoreCase("Venda")) {

                Float vlr = Float.valueOf(valor);

                coleta = new Coleta(randId, material, medida, modalidade, qtd, entrega,
                        cep, rua, num, complemento, bairro, cidade, estado, user.getUid(), telefone, Calendar.getInstance().getTime(), status,
                        vlr, dinheiro, debito, credito, mercadoPago);
            } else {
                coleta = new Coleta(randId, material, medida, modalidade, qtd, entrega,
                        cep, rua, num, complemento, bairro, cidade, estado, user.getUid(), telefone, Calendar.getInstance().getTime(), status
                );
            }


            db.collection("Coleta").document(randId).set(coleta).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    uploadImage(randId);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(cadastroColeta.this, "Erro ao realizar cadastro!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

        }else{

            if (modalidade.equalsIgnoreCase("Venda")) {

                Float vlr = Float.valueOf(valor);

                coleta = new Coleta(coletaAlt.getId(), material, medida, modalidade, qtd, entrega,
                        cep, rua, num, complemento, bairro, cidade, estado, user.getUid(), telefone,Calendar.getInstance().getTime(), coletaAlt.getStatus(),
                        vlr, dinheiro, debito, credito, mercadoPago);
            } else {
                coleta = new Coleta(coletaAlt.getId(), material, medida, modalidade, qtd, entrega,
                        cep, rua, num, complemento, bairro, cidade, estado, user.getUid(),telefone, Calendar.getInstance().getTime(), coletaAlt.getStatus()
                );
            }


            if (selectedImage.toString().equalsIgnoreCase("android.resource://projeto.bionet.example.com.bionet/drawable/bionet")){
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Alterando...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                db.collection("Coleta").document(coletaAlt.getId()).set(coleta).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(cadastroColeta.this, "Alteração realizada com sucesso!",
                                Toast.LENGTH_LONG).show();
                        Intent intentNew = new Intent(cadastroColeta.this, LobbyActivity.class);
                        startActivity(intentNew);
                        finish();

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(cadastroColeta.this, "Erro ao realizar alteração!",
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });



            }else{
                db.collection("Coleta").document(coletaAlt.getId()).set(coleta).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadImage(coletaAlt.getId());
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(cadastroColeta.this, "Erro ao realizar alteração!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

            }
        }

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Pesquisando Endereço...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

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

                    if (retornoCep.getBairro() == null){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(cadastroColeta.this, "CEP Inválido!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }else {
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

    }

    public void selectImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(cadastroColeta.this,"Imagem selecionada!",Toast.LENGTH_LONG).show();
                    selectedImage = imageReturnedIntent.getData();
                }
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 20) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();

        return saltStr;
    }

    public void uploadImage(String randId) {
        //create reference to images folder and assing a name to the file that will be uploaded

        imageRef = storageRef.child("coleta/"+randId);

        //creating and showing progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cadastrando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        //starting upload
        uploadTask = imageRef.putFile(selectedImage);
        // Observe state change events such as progress, pause, and resume
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //sets and increments value of progressbar
                progressDialog.incrementProgressBy((int) progress);
            }
        });
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(cadastroColeta.this,"Erro no cadastro!",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog.dismiss();

                Toast.makeText(cadastroColeta.this,"Material Cadastrado com Sucesso!",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(cadastroColeta.this, LobbyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

