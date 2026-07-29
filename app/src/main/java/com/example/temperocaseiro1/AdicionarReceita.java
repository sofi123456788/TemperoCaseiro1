package com.example.temperocaseiro1;

// Importa os recursos necessários do Android
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class AdicionarReceita extends AppCompatActivity {


    // ================================
    // DECLARAÇÃO DOS COMPONENTES DA TELA
    // ================================


    // Campo do título da receita
    EditText editTituloReceita;


    // Campo de tempo de preparo
    EditText editTempoPreparo;


    // Campo de porções
    EditText editPorcoes;


    // Lista suspensa de categorias
    Spinner spinnerCategoria;


    // Local onde serão criados os ingredientes
    LinearLayout layoutIngredientes;


    // Local onde serão criados os passos do preparo
    LinearLayout layoutPassos;


    // Botões da tela
    TextView btnAdicionarIngrediente;
    TextView btnAdicionarPasso;
    TextView btnSalvarPublicar;
    TextView btnVoltarReceita;



    // Listas que vão guardar os textos digitados

    ArrayList<EditText> ingredientes = new ArrayList<>();

    ArrayList<EditText> passos = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Liga essa Activity ao XML
        setContentView(R.layout.activity_adicionar_receita);


        // Nome que aparece na barra superior
        setTitle("Adicionar Receita");


        // Chama o método que encontra os componentes
        inicializarComponentes();


        // Chama o método dos cliques dos botões
        configurarBotoes();

    }



    // ======================================
    // PEGAR OS COMPONENTES DO XML
    // ======================================

    private void inicializarComponentes(){


        // Ligando os campos de texto

        editTituloReceita =
                findViewById(R.id.editTituloReceita);


        editTempoPreparo =
                findViewById(R.id.editTempoPreparo);


        editPorcoes =
                findViewById(R.id.editPorcoes);



        // Pegando o Spinner da categoria

        spinnerCategoria =
                findViewById(R.id.spinnerCategoria);



        // Pegando os espaços criados no XML

        layoutIngredientes =
                findViewById(R.id.layoutIngredientes);


        layoutPassos =
                findViewById(R.id.layoutPassos);



        // Pegando os botões

        btnAdicionarIngrediente =
                findViewById(R.id.btnAdicionarIngrediente);


        btnAdicionarPasso =
                findViewById(R.id.btnAdicionarPasso);


        btnSalvarPublicar =
                findViewById(R.id.btnSalvarPublicar);


        btnVoltarReceita =
                findViewById(R.id.btnVoltarReceita);


    }



    // ======================================
    // CONFIGURAÇÃO DOS CLIQUES
    // ======================================


    private void configurarBotoes(){


        // Quando clicar em adicionar ingrediente

        btnAdicionarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                adicionarIngrediente();


            }
        });



        // Quando clicar em adicionar passo

        btnAdicionarPasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                adicionarPasso();


            }
        });




        // Botão voltar

        btnVoltarReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();


            }
        });




        // Botão salvar

        btnSalvarPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                salvarReceita();


            }
        });



    }
    // ======================================
// ADICIONAR UM NOVO INGREDIENTE
// ======================================

    private void adicionarIngrediente() {


        // Cria um novo campo de texto
        EditText novoIngrediente = new EditText(this);


        // Texto de orientação
        novoIngrediente.setHint("Digite o ingrediente");


        // Permite escrever normalmente
        novoIngrediente.setSingleLine(true);


        // Espaçamento interno
        novoIngrediente.setPadding(
                15,
                5,
                15,
                5
        );


        // Define altura e largura do campo
        LinearLayout.LayoutParams parametros =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        50
                );


        // Espaço entre um ingrediente e outro
        parametros.setMargins(
                0,
                8,
                0,
                8
        );


        novoIngrediente.setLayoutParams(parametros);



        // Adiciona o campo dentro da tela
        layoutIngredientes.addView(novoIngrediente);



        // Guarda na lista
        ingredientes.add(novoIngrediente);


    }





    // ======================================
    // ADICIONAR UM NOVO PASSO DO PREPARO
    // ======================================

    private void adicionarPasso() {


        // Cria um novo campo para o passo
        EditText novoPasso = new EditText(this);


        // Texto de orientação
        novoPasso.setHint("Digite o passo do preparo");


        // Permite escrever textos maiores
        novoPasso.setMinLines(2);


        // Define tamanho do campo
        novoPasso.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        70
                )
        );


        // Espaçamento interno
        novoPasso.setPadding(
                10,
                10,
                10,
                10
        );


        // Adiciona o campo criado na tela
        layoutPassos.addView(novoPasso);



        // Guarda o campo na lista
        passos.add(novoPasso);


    }





    // ======================================
    // VALIDAR OS CAMPOS DA RECEITA
    // ======================================

    private boolean validarCampos() {


        // Verifica se o título está vazio

        if(editTituloReceita.getText().toString().trim().isEmpty()){


            editTituloReceita.setError("Digite o título da receita");


            return false;

        }



        // Verifica se o usuário escolheu uma categoria

        if(spinnerCategoria.getSelectedItemPosition() == 0){


            Toast.makeText(
                    this,
                    "Selecione uma categoria",
                    Toast.LENGTH_SHORT
            ).show();


            return false;

        }



        // Verifica se existe pelo menos um ingrediente

        if(ingredientes.size() == 0){


            Toast.makeText(
                    this,
                    "Adicione pelo menos um ingrediente",
                    Toast.LENGTH_SHORT
            ).show();


            return false;

        }



        // Verifica se existe pelo menos um passo

        if(passos.size() == 0){


            Toast.makeText(
                    this,
                    "Adicione pelo menos um passo do preparo",
                    Toast.LENGTH_SHORT
            ).show();


            return false;

        }



        return true;

    }
    // ======================================
    // SALVAR RECEITA (TEMPORARIAMENTE)
    // ======================================

    private void salvarReceita(){



        // Primeiro verifica se todos os campos estão corretos

        if(validarCampos() == false){

            return;

        }




        // Pegando os dados digitados


        String titulo =
                editTituloReceita.getText().toString();



        String categoria =
                spinnerCategoria.getSelectedItem().toString();



        String tempo =
                editTempoPreparo.getText().toString();



        String porcoes =
                editPorcoes.getText().toString();





        // Criando uma variável para guardar ingredientes

        String listaIngredientes = "";



        for(EditText ingrediente : ingredientes){


            listaIngredientes +=
                    ingrediente.getText().toString()
                            + "\n";


        }





        // Criando uma variável para guardar passos

        String listaPassos = "";



        for(EditText passo : passos){


            listaPassos +=
                    passo.getText().toString()
                            + "\n";


        }




        // Por enquanto apenas mostramos os dados
        // futuramente aqui entra o banco de dados


        Toast.makeText(
                this,
                "Receita salva com sucesso!",
                Toast.LENGTH_LONG
        ).show();



    }



}
