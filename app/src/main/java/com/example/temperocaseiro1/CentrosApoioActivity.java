package com.example.temperocaseiro1;

import android.os.Bundle;
import android.widget.Toast;

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

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_centros_apoio);

        setTitle("Centros de apoio");

        // Encontra o RecyclerView da tela
        recyclerCentrosApoio = findViewById(R.id.recyclerCentrosApoio);

        // Define que os cards serão organizados verticalmente
        recyclerCentrosApoio.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Cria o Adapter
        adapter = new CentroApoioAdapter(
                listaCentros,
                centro -> {

                    Toast.makeText(
                            CentrosApoioActivity.this,
                            centro.getNome(),
                            Toast.LENGTH_SHORT
                    ).show();

                }
        );

        // Liga o Adapter ao RecyclerView
        recyclerCentrosApoio.setAdapter(adapter);

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

                    listaCentros.clear();

                    listaCentros.addAll(response.body());

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
                        "Erro ao conectar com a API.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}