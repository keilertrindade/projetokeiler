package projeto.bionet.example.com.bionet;

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
   AutoCompleteTextView nome;
   AutoCompleteTextView razao;
   RadioGroup RGrupo;
   android.support.design.widget.TextInputLayout nome1;
   android.support.design.widget.TextInputLayout razao1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__usuario);
        nome = (AutoCompleteTextView) findViewById(R.id.nome);
        razao = (AutoCompleteTextView) findViewById(R.id.razao);
        RGrupo = (RadioGroup) findViewById(R.id.radiogroup);
        nome1 = (android.support.design.widget.TextInputLayout) findViewById(R.id.nome1);
        razao1 = (android.support.design.widget.TextInputLayout) findViewById(R.id.razao1);

        RGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.p_fisica) {
                    //do work when radioButton1 is active
                    nome1.setVisibility(View.VISIBLE);
                    razao1.setVisibility(View.INVISIBLE);

                } else  if (checkedId == R.id.p_juridica) {
                    //do work when radioButton2 is active
                    nome1.setVisibility(View.INVISIBLE);
                    razao1.setVisibility(View.VISIBLE);
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

