package com.example.temperocaseiro1;

import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temperocaseiro1.api.ApiService;
import com.example.temperocaseiro1.api.RetrofitClient;
import com.example.temperocaseiro1.model.CentroApoio;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CentrosApoioActivity extends AppCompatActivity {

    private RecyclerView recyclerCentrosApoio;

    private CentroApoioAdapter adapter;

    private List<CentroApoio> listaCentros = new ArrayList<>();

    private List<CentroApoio> todosOsCentros = new ArrayList<>();

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_centros_apoio);

        setTitle("Centros de apoio");

        // Encontra o RecyclerView da tela
        recyclerCentrosApoio = findViewById(R.id.recyclerCentrosApoio);

        TextView filtroTodos = findViewById(R.id.filtroTodos);
        TextView filtroDelegacias = findViewById(R.id.filtroDelegacias);
        TextView filtroApoio = findViewById(R.id.filtroApoio);
        TextView filtroSaude = findViewById(R.id.filtroSaude);

        // Define que os cards serão organizados verticalmente
        recyclerCentrosApoio.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Cria o Adapter
        adapter = new CentroApoioAdapter(
                listaCentros,
                centro -> {

                    Intent intent = new Intent(
                            CentrosApoioActivity.this,
                            ActivityDetalhesLocal.class
                    );

                    intent.putExtra("nome", centro.getNome());
                    intent.putExtra("telefone", centro.getTelefone());
                    intent.putExtra("rua", centro.getRua());
                    intent.putExtra("logradouro", centro.getLogradouro());
                    intent.putExtra("bairro", centro.getBairro());
                    intent.putExtra("cidade", centro.getCidade());
                    intent.putExtra("estado", centro.getEstado());
                    intent.putExtra("horarioAbertura", centro.getHorarioAbertura());
                    intent.putExtra("horarioFechamento", centro.getHorarioFechamento());
                    intent.putExtra("tipo", centro.getTipo());

                    startActivity(intent);
                }
        );

        // Liga o Adapter ao RecyclerView
        recyclerCentrosApoio.setAdapter(adapter);

        filtroTodos.setOnClickListener(v -> {
            filtrarCentros("Todos");
        });

        filtroDelegacias.setOnClickListener(v -> {
            filtrarCentros("Delegacia");
        });

        filtroApoio.setOnClickListener(v -> {
            filtrarCentros("Apoio");
        });

        filtroSaude.setOnClickListener(v -> {
            filtrarCentros("Saúde");
        });

        // Cria o serviço da API
        apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        // Busca os centros no banco através da API
        carregarCentros();
    }

    private void carregarCentros() {

        Call<List<CentroApoio>> chamada =
                apiService.listarCentrosApoio();

        chamada.enqueue(new Callback<List<CentroApoio>>() {

            @Override
            public void onResponse(
                    Call<List<CentroApoio>> call,
                    Response<List<CentroApoio>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    todosOsCentros.clear();
                    todosOsCentros.addAll(response.body());

                    listaCentros.clear();
                    listaCentros.addAll(todosOsCentros);

                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(
                            CentrosApoioActivity.this,
                            "Não foi possível carregar os centros.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<CentroApoio>> call,
                    Throwable t) {

                Toast.makeText(
                        CentrosApoioActivity.this,
                        "Erro: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
    private void filtrarCentros(String tipo) {

        listaCentros.clear();

        if (tipo.equals("Todos")) {

            listaCentros.addAll(todosOsCentros);

        } else {

            for (CentroApoio centro : todosOsCentros) {

                if (centro.getTipo() != null &&
                        centro.getTipo().equalsIgnoreCase(tipo)) {

                    listaCentros.add(centro);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}