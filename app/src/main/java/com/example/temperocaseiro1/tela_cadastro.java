package com.example.temperocaseiro1;
import android.os.Bundle;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro); // mostra o visual da tela de cadastro

        btnVoltar = findViewById (R.id.btnVoltar);
        txtEntrar = findViewById (R.id.txtEntrar);

        btnVoltar.setOnClickListener(v -> {
            finish(); // fecha a tela atual e volta para a anterior
        });

        txtEntrar.setOnClickListener(v -> {
            finish();
        });

        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        String nome = editNomeCompleto.getText().toString();

        editEmailCadastro = findViewById(R.id.editEmailCadastro);
        String email = editEmailCadastro.getText().toString();

        editSenhaCadastro = findViewById(R.id.editSenhaCadastro);
        String senha = editSenhaCadastro.getText().toString();

        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        String confsenha = editConfirmarSenha.getText().toString();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
