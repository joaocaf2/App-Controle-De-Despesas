package com.example.controlededespesas.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controlededespesas.R;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListaDeDespesasActivity extends AppCompatActivity {

    private TableLayout tableDespesas;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DespesaDAO dao = new DespesaDAO();
    private TextView txtTotalDespesa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de despesas");
        setContentView(R.layout.lista_de_despesas_activity);
        tableDespesas = findViewById(R.id.activity_main_table_layout_despesas);
        tableDespesas.setClickable(true);
        inicializaViews();
    }

    private void inicializaViews() {
        txtTotalDespesa = findViewById(R.id.lista_de_despesas_activity_textView_totalCalc);
        inicializaTabela();
    }

    @Override
    protected void onResume() {
        if (dao.findLast() != null) {
            adicionaLinhaATabela(dao.findLast());
        }
        atualizaTextViewTotalDespesas();
        super.onResume();
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

    private void adicionaLinhaATabela(Despesa despesa) {
        TableRow linha = new TableRow(this);
        TextView colunaDescricao = new TextView(this);

        colunaDescricao.setText(despesa.getDescricao());
        colunaDescricao.setGravity(Gravity.CENTER);
        linha.addView(colunaDescricao);

        TextView colunaDespesa = new TextView(this);
        colunaDespesa.setText(String.valueOf(despesa.getValor()));
        colunaDespesa.setGravity(Gravity.CENTER);
        linha.addView(colunaDespesa);

        TextView colunaData = new TextView(this);
        colunaData.setText(String.valueOf(despesa.getData().toString().
                replace("-", "/")));
        colunaData.setGravity(Gravity.CENTER);
        linha.addView(colunaData);

        tableDespesas.addView(linha);

    }

    private void inicializaTabela() {
        Despesa despesa = new Despesa("Qualquer coisa",
                "qualquer descricao", new BigDecimal("80.40"), LocalDate.parse("03-04-2020", formatter));
        TableRow row = new TableRow(this);

        TextView coluna1 = new TextView(this);
        coluna1.setTextColor(Color.RED);
        coluna1.setText("Descrição");
        coluna1.setTextSize(20.0F);
        row.addView(coluna1);

        TextView coluna2 = new TextView(this);
        coluna2.setText("Valor da despesa");
        coluna2.setTextColor(Color.RED);
        coluna2.setTextSize(20.0F);
        row.addView(coluna2);

        TextView coluna3 = new TextView(this);
        coluna3.setText("Data");
        coluna3.setTextColor(Color.RED);
        coluna3.setTextSize(10.0F);
        row.addView(coluna3);

        tableDespesas.addView(row);
    }
}
