package com.example.temperocaseiro1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.temperocaseiro1.api.ApiClient;
import com.example.temperocaseiro1.api.AuthApi;
import com.example.temperocaseiro1.model.CadastroRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        // 1. encontra os campos do xml pelo id
        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        editEmailCadastro = findViewById(R.id.editEmailCadastro);
        editSenhaCadastro = findViewById(R.id.editSenhaCadastro);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        radioGroupGenero = findViewById(R.id.radioGroupGenero); // guarda o id do componente marcado
        btnCriarConta = findViewById(R.id.btnCriarConta);

        btnCriarConta.setOnClickListener(v -> { // "quando o usuário clicar no botão cria conta execute o código abaixo"
            // 2. Captura os dados digitados nos campos
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

            // 3. validação dos dados
            if (nome.trim().isEmpty()) { //conversar com o kdu sobre maneiras mais inteligentes de validar os dados
                editNomeCompleto.setError("Digite seu nome completo");
            }
            else if (email.trim().isEmpty()){
                editEmailCadastro.setError("Digite seu email");
            }
            else if (senha.trim().isEmpty()) {
                editSenhaCadastro.setError("Digite sua senha");
            }
            else if (confsenha.trim().isEmpty()) {
                editConfirmarSenha.setError("Confirme sua senha");
            }
            else if (!senha.equals(confsenha)) {
                editConfirmarSenha.setError("As senhas não correspondem");
            }
            else if (genero.isEmpty()) {
                Toast.makeText(this, "Selecione uma opção de gênero", Toast.LENGTH_SHORT).show();
            }
            else {
                enviarCadastroParaApi(nome, email, senha, genero); //
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void enviarCadastroParaApi(String nome, String email, String senha, String genero) { // esse metodo recebe os dados validados

        CadastroRequest cadastroRequest = new CadastroRequest( // cria um objeto CadastroRequest com eles
                nome,
                email,
                senha,
                genero
        );

        AuthApi authApi = ApiClient.getRetrofit().create(AuthApi.class); // cria uma conexão com a API

        Call<String> call = authApi.cadastrar(cadastroRequest); // prepara uma chamada HTTP para o cadastro do usuário

        call.enqueue(new Callback<String>() { // executa de forma assíncrona para nn travar a tela do app
            @Override
            public void onResponse(Call<String> call, Response<String> response) { //API respondeu
                if (response.isSuccessful()) {
                    Toast.makeText(tela_cadastro.this, response.body(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(tela_cadastro.this, "Erro no cadastro", Toast.LENGTH_SHORT).show(); // deu erro em alguma parte do cadastro
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) { // API nn respondeu
                Toast.makeText(tela_cadastro.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
