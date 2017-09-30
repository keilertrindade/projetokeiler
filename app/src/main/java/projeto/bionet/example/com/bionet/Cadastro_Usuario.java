package projeto.bionet.example.com.bionet;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;

import static android.support.design.R.styleable.TextInputLayout;

public class Cadastro_Usuario extends AppCompatActivity {

   // RadioGroup grupo = (RadioGroup) findViewById(R.id.radiogroup);
   // EditText et1 = (EditText) findViewById(R.id.nome);
   // EditText et2 = (EditText) findViewById(R.id.razao);
   RadioGroup RGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_cadastro__usuario);
        RGrupo = (RadioGroup) findViewById(R.id.radiogroup);


        RGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.p_juridica) {
                    //do work when radioButton1 is active
                    Intent intent = new Intent(Cadastro_Usuario.this,cadastroPJuridica.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }


     /*Provavelmente será mais fácil apenas alterar o layout, escondendo os campos tem o problema do restante ficar
     * desarrumado. Vou pensar em uma forma de organizar (talvez dividindo em groupviews). O ruim que alterando o layout
     * talvez dê problema com os radiobutton, teria que modificar tudo abaixo deles apenas.
     *
     * Usando outro layout, posso manter o radiogroup com o mesmo id para reaproveitar o código de troca de tela.
     *
     * Posso apenas deixar os campos desabilitados, seria mais prático mas talvez o layout ficasse uma bosta... Posso tentar utilizar
     * relative layout para suprir o espaço deixado quando um campo se torna invisível.
     *
     * */


    }

