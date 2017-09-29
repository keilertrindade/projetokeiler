package projeto.bionet.example.com.bionet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Cadastro_Usuario extends AppCompatActivity {

    RadioGroup radioGroup =(RadioGroup)findViewById(R.id.radiogroup);
    final EditText et1 = (EditText) findViewById(R.id.nome);
    final EditText et2 = (EditText) findViewById(R.id.razao);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__usuario);
    }

     //Posso dar loading com todos os campos invisiveis, carregando-os apenas após escolher o rbuuton

    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if(checkedId == R.id.p_fisica){
                et1.setVisibility(View.VISIBLE);
                et2.setVisibility(View.INVISIBLE);
            } else {
                et1.setVisibility(View.INVISIBLE);
                et2.setVisibility(View.VISIBLE);
            }
        }
    }
