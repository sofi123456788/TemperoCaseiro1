package com.example.temperocaseiro1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tela_cadastro extends AppCompatActivity {
    private TextView btnVoltar;
    private TextView txtEntrar;
    private EditText editNomeCompleto;
    private EditText editEmailCadastro;
    private EditText editSenhaCadastro;
    private EditText editConfirmarSenha;
    private RadioGroup radioGroupGenero;
    private TextView btnCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro); // mostra o visual da tela de cadastro

        btnVoltar = findViewById (R.id.btnVoltar); // associa a variável criada ao id dentro do xml corresponde, isso permite que o campo do btnVoltar funcione
        txtEntrar = findViewById (R.id.txtEntrar);

        btnVoltar.setOnClickListener(v -> {
            finish(); // fecha a tela atual e volta para a anterior
        });
        txtEntrar.setOnClickListener(v -> {
            finish();
        });

        // conexão com os componentes
        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        editEmailCadastro = findViewById(R.id.editEmailCadastro);
        editSenhaCadastro = findViewById(R.id.editSenhaCadastro);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        radioGroupGenero = findViewById(R.id.radioGroupGenero); // guarda o id do componente marcado
        btnCriarConta = findViewById(R.id.btnCriarConta);

        // após o usuário clicar em criar conta, o sistema pega todas as informações presentes nos campos
        // aqui provavelmente será feita a conexão com o banco de dados + mensagem de confirmação

        btnCriarConta.setOnClickListener(v -> { // "quando o usuário clicar no botão cria conta execute o código abaixo"
            String nome = editNomeCompleto.getText().toString();
            String email = editEmailCadastro.getText().toString();
            String senha = editSenhaCadastro.getText().toString();
            String confsenha = editConfirmarSenha.getText().toString();

            int generoSelcionado = radioGroupGenero.getCheckedRadioButtonId();
            String genero;

            if(generoSelcionado == R.id.radioMasculino) {
                genero = "masculino";
            }
            else if(generoSelcionado == R.id.radioFeminino) {
                genero = "feminino";
            }
            else { // implementar posteriormente uma mensagem de ERRO! nessa porção.
                genero = "";
            }

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
