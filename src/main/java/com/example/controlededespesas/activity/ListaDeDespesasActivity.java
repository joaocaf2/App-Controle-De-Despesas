package com.example.controlededespesas.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controlededespesas.R;
import com.example.controlededespesas.adapter.DespesasAdatper;

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.lista_de_despesas_activity_lv_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        new AlertDialog.Builder(this).
                setTitle("Pagando despesa")
                .setMessage("Tem certeza que deseja pagar esta despesa?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pagaDespesaSelecionada(item);
                    }
                })
                .setNegativeButton("Não", null).show();
        return super.onContextItemSelected(item);
    }

    private void pagaDespesaSelecionada(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Despesa despesa = despesasAdatper.getItem(menuInfo.position);
        despesa.pagar(true);
        despesasAdatper.notifyDataSetChanged();
        atualizaTextViewTotalDespesas();
    }


    private void inicializaViews() {
        lvDespesas = findViewById(R.id.lista_de_despesas_activity_listViewDespesas);
        txtTotalDespesa = findViewById(R.id.lista_de_despesas_activity_textView_totalCalc);
    }

    private void configuraAdapterListView() {
        despesasAdatper = new DespesasAdatper(this);
        lvDespesas.setAdapter(despesasAdatper);
        lvDespesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {
                Despesa despesa = (Despesa) adapterView.getItemAtPosition(posicao);
                Integer idDespesa = despesa.getId();
                Intent intent = new Intent(ListaDeDespesasActivity.this,
                        CadastroDespesaActivity.class);
                intent.putExtra("IdDespesaEditar", idDespesa);
                startActivity(intent);

            }
        });
        registerForContextMenu(lvDespesas);
    }


    @Override
    protected void onResume() {
        super.onResume();
        atualizaTextViewTotalDespesas();
        despesasAdatper.atualizaDados(dao.findAll());
    }

    private void populaDadosIniciaisNaListView() {
        Despesa despesa = new Despesa();
        despesa.setNome("Comprar pao");
        despesa.setDescricao("Comprei um pao do bao sô");
        despesa.setValor(new BigDecimal("40.80"));
        despesa.setData(LocalDate.now());
        despesa.pagar(false);
        dao.adiciona(despesa);
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
