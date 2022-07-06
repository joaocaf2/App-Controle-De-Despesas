package com.example.controlededespesas.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controlededespesas.R;
import com.example.controlededespesas.adapter.DespesasAdatper;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListaDeDespesasActivity extends AppCompatActivity {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DespesaDAO dao = new DespesaDAO();
    private TextView txtTotalDespesa;

    private DespesasAdatper despesasAdatper;
    private ListView lvDespesas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de despesas");
        setContentView(R.layout.lista_de_despesas_activity);
        populaDadosIniciaisNaListView();
        inicializaViews();
        configuraAdapterListView();
    }

    private void inicializaViews() {
        lvDespesas = findViewById(R.id.lista_de_despesas_activity_listViewDespesas);
        txtTotalDespesa = findViewById(R.id.lista_de_despesas_activity_textView_totalCalc);
    }

    private void configuraAdapterListView() {
        despesasAdatper = new DespesasAdatper(this);
        lvDespesas.setAdapter(despesasAdatper);
    }


    @Override
    protected void onResume() {
        super.onResume();
        atualizaTextViewTotalDespesas();
        despesasAdatper.atualizaDados(dao.findAll());
    }

    private void populaDadosIniciaisNaListView() {
        Log.i("Chamando...", "Chamando populaDadosIniciaisNaListView");
        Despesa despesa = new Despesa();
        despesa.setNome("Comprar pao");
        despesa.setDescricao("Comprei um pao do bao sô");
        despesa.setValor(new BigDecimal("40.80"));
        dao.adiciona(despesa);
        Log.i("Dao? ", dao.findLast().getNome());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_de_despesas_activity_menu_opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.lista_de_despesas_activity_menu_nova_despesa) {
            Intent intent = new Intent(this, CadastroDespesaActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void atualizaTextViewTotalDespesas() {
        BigDecimal totalDespesas = dao.getTotalDespesas();
        txtTotalDespesa.setText("R$ " + String.valueOf(totalDespesas));
    }


}
