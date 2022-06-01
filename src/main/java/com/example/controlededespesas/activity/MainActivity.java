package com.example.controlededespesas.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controlededespesas.R;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private TableLayout tableDespesas;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de despesas");
        setContentView(R.layout.main_activity);
        tableDespesas = findViewById(R.id.activity_main_table_layout_despesas);
        tableDespesas.setClickable(true);
        inicializaTabela();

    }

    private void inicializaTabela() {
        Despesa despesa = new Despesa("Qualquer coisa",
                new BigDecimal("80.40"), LocalDate.parse("03-04-2020", formatter));
        TableRow row = new TableRow(this);

        TextView coluna1 = new TextView(this);
        coluna1.setTextColor(Color.RED);
        coluna1.setText("Descrição");
        row.addView(coluna1);

        TextView coluna2 = new TextView(this);
        coluna2.setText("Valor da despesa");
        coluna2.setTextColor(Color.RED);
        row.addView(coluna2);

        TextView coluna3 = new TextView(this);
        coluna3.setText("Data");
        coluna3.setTextColor(Color.RED);
        row.addView(coluna3);

        tableDespesas.addView(row);
        for (int i = 0; i < 5; i++) {
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
    }
}
