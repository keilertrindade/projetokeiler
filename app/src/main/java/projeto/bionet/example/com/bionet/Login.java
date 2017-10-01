package projeto.bionet.example.com.bionet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    private Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        botao = (Button) findViewById(R.id.botao_registro);
    }


    public void onClick (View v) {

        Intent intent = new Intent(Login.this,Cadastro_Usuario.class);
        startActivity(intent);

        //Your Logic
    }


}
