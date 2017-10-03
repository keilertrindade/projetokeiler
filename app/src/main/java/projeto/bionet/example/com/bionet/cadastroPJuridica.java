package projeto.bionet.example.com.bionet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class cadastroPJuridica extends AppCompatActivity {

    RadioGroup RGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_cadastro_pjuridica);
        RGrupo = (RadioGroup) findViewById(R.id.radiogroup);

        RGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.p_fisica) {
                    Intent intent = new Intent(cadastroPJuridica.this,Cadastro_Usuario.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
}
