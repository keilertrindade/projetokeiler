package projeto.bionet.example.com.bionet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Pesquisa extends AppCompatActivity {

    /*private EditText palavra;
    private ListView lvPesquisa;

    FirebaseDatabase db;
    DatabaseReference dbReference;

    private List<Coleta> listcoleta = new  ArrayList<Coleta>();
    private ArrayAdapter<Coleta> arrayAdapterColeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        db = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(Pesquisa.this);
        dbReference = db.getReference();

        inicializarComponentes();
        eventoEdit();
    }

    private void eventoEdit() {
        palavra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String texto = palavra.getText().toString().trim();
                pesquisarPalavra(texto);
            }
        });
    }

    private void pesquisarPalavra(String texto) {
        com.google.firebase.database.Query query;

        if(palavra.equals("")){
            query  = dbReference.child("Coleta").orderByChild("material");
        }
        else{
            query = dbReference.child("Coleta")
                    .orderByChild("material").startAt(texto)
                    .endAt(palavra+"\uf8ff");
        }
        listcoleta.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapShot:dataSnapshot.getChildren()){
                    Coleta c = objSnapShot.getValue(Coleta.class);
                    listcoleta.add(c);
                }
                arrayAdapterColeta = new ArrayAdapter<Coleta>(Pesquisa
                        .this, android.R.layout.simple_list_item_1, listcoleta);

                lvPesquisa.setAdapter(arrayAdapterColeta);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes() {
        palavra = (EditText) findViewById(R.id.palavra);
        lvPesquisa = (ListView) findViewById(R.id.lvPesquisa);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    */
}
