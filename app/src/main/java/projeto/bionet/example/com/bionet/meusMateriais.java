package projeto.bionet.example.com.bionet;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
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

public class meusMateriais extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    DocumentReference coletaRef;
    ArrayList<Coleta> coletaArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_materiais);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayList<Coleta> coletaArray = new ArrayList<>();




        db.collection("Coleta")
                .whereEqualTo("proprietario", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = new HashMap<>();
                                doc = document.getData();

                                Gson gson = new Gson();
                                JsonElement jsonElement = gson.toJsonTree(doc);
                                Coleta coleta = gson.fromJson(jsonElement, Coleta.class);
                                coletaArray.add(coleta);
                            }
                            gerarLista(coletaArray);
                        } else {
                            Toast.makeText(meusMateriais.this, "Você não tem nenhum material cadastrado!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }

    private void gerarLista(ArrayList array){

        ListView lista = (ListView) findViewById(R.id.listaColetas);
        AdapterLista adapter = new AdapterLista(array, this);
        lista.setAdapter(adapter);

    }



}
