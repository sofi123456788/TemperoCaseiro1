package com.example.temperocaseiro1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.temperocaseiro1.api.ApiService;
import com.example.temperocaseiro1.api.RetrofitClient;
import com.example.temperocaseiro1.model.Receita;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReceitasBD extends AppCompatActivity {


    // Layout onde os cards das receitas serão adicionados.
    private LinearLayout layoutReceitas;


    // Botão para voltar para a tela anterior.
    private TextView btnVoltarReceitasBD;


    // Objeto responsável pelas chamadas da API.
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Conecta esta Activity ao arquivo XML.
        setContentView(R.layout.activity_receitas_bd);

        // Define o título da Activity.
        setTitle("Todas as Receitas");


        // Localiza os componentes da tela.
        layoutReceitas = findViewById(R.id.layoutReceitas);

        btnVoltarReceitasBD =
                findViewById(R.id.btnVoltarReceitasBD);


        // Cria a conexão com a API.
        apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);


        // Configura o botão voltar.
        btnVoltarReceitasBD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });


        // Busca as receitas assim que a tela abre.
        carregarReceitas();

    }


    // =====================================================
    // BUSCAR RECEITAS NA API
    // =====================================================

    private void carregarReceitas() {


        // Faz uma requisição GET para /receitas.
        apiService.listarReceitas()
                .enqueue(new Callback<List<Receita>>() {


                    // Caso a API responda.
                    @Override
                    public void onResponse(
                            Call<List<Receita>> call,
                            Response<List<Receita>> response) {


                        // Verifica se a resposta foi bem-sucedida.
                        if (response.isSuccessful() && response.body() != null) {


                            // Pega a lista de receitas enviada pela API.
                            List<Receita> receitas =
                                    response.body();


                            // Limpa a tela antes de adicionar
                            // os cards novamente.
                            layoutReceitas.removeAllViews();


                            // Verifica se não existem receitas.
                            if (receitas.isEmpty()) {

                                TextView mensagem =
                                        new TextView(ReceitasBD.this);

                                mensagem.setText(
                                        "Nenhuma receita cadastrada."
                                );

                                mensagem.setTextSize(18);

                                mensagem.setTextColor(
                                        android.graphics.Color.DKGRAY
                                );

                                mensagem.setPadding(
                                        10,
                                        30,
                                        10,
                                        30
                                );

                                layoutReceitas.addView(mensagem);

                                return;
                            }


                            // Percorre todas as receitas recebidas.
                            for (Receita receita : receitas) {

                                criarCardReceita(receita);

                            }


                        } else {

                            // Caso a API retorne algum erro.
                            Toast.makeText(
                                    ReceitasBD.this,
                                    "Erro ao buscar receitas. Código: "
                                            + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();

                        }

                    }


                    // Caso não consiga conectar com a API.
                    @Override
                    public void onFailure(
                            Call<List<Receita>> call,
                            Throwable t) {


                        Toast.makeText(
                                ReceitasBD.this,
                                "Falha na conexão com a API: "
                                        + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }


    // =====================================================
    // CRIAR UM CARD PARA CADA RECEITA
    // =====================================================

    private void criarCardReceita(Receita receita) {


        // Inflater transforma o XML do card em uma View.
        View card = LayoutInflater
                .from(this)
                .inflate(
                        R.layout.item_receita_bd,
                        layoutReceitas,
                        false
                );


        // Encontra os textos dentro do card.
        TextView titulo =
                card.findViewById(R.id.txtTituloCard);

        TextView categoria =
                card.findViewById(R.id.txtCategoriaCard);

        TextView tempo =
                card.findViewById(R.id.txtTempoCard);

        TextView rendimento =
                card.findViewById(R.id.txtRendimentoCard);


        // Coloca os dados da receita no card.
        titulo.setText(receita.getTitulo());

        categoria.setText(
                "Categoria: " + receita.getCategoria()
        );

        tempo.setText(
                "Tempo: " + receita.getTempoPreparo() + " min"
        );

        rendimento.setText(
                "Rendimento: " + receita.getRendimento()
        );


        // Adiciona o card na tela.
        layoutReceitas.addView(card);

    }

}