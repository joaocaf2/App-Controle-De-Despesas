package com.example.controlededespesas.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controlededespesas.R;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CadastroDespesaActivity extends AppCompatActivity {

    private EditText edtNomeDespesa;
    private EditText edtDescricaoDespesa;
    private EditText edtValorDespesa;
    private EditText edtDataDespesa;
    private Despesa despesa;
    private DespesaDAO dao;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_despesa_activity);
        inicializaViews();
        despesa = new Despesa();
        dao = new DespesaDAO();
        if (estaEmEdicao()) {
            setaOsDadosEmEdicaoNosEditText();
            setTitle("Edição de despesa");
        } else {
            setTitle("Cadastro de despesa");
        }
    }

    private boolean estaEmEdicao() {
        return getIntent().hasExtra("IdDespesaEditar");
    }

    private void setaOsDadosEmEdicaoNosEditText() {
        Bundle extras = getIntent().getExtras();
        Integer idDespesaEditar = (Integer) extras.get("IdDespesaEditar");
        despesa = dao.findById(idDespesaEditar);
        edtNomeDespesa.setText(despesa.getNome());
        edtDescricaoDespesa.setText(despesa.getDescricao());
        String data = formatter.format(despesa.getData()).replace("-", "/");
        edtDataDespesa.setText(data);
        edtValorDespesa.setText(String.valueOf(despesa.getValor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_de_despesa_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.cadastro_despesa_activity_menu_cadastrar) {
            if (!verificaSeTodosOsCamposForamPreenchidos()) {
                Toast.makeText(getBaseContext(), "Todos os campos são requeridos", Toast.LENGTH_LONG).show();
            } else {
                salvaDespesaEFechaFormulario();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvaDespesaEFechaFormulario() {

        if (!estaEmEdicao()) {
            despesa = new Despesa(edtNomeDespesa.getText().toString(),
                    edtDescricaoDespesa.getText().toString(),
                    new BigDecimal(edtValorDespesa.getText().toString()),
                    LocalDate.parse(edtDataDespesa.getText().toString().
                            replace("/", "-"), formatter));
        } else {
            despesa.setNome(edtNomeDespesa.getText().toString());
            despesa.setValor(new BigDecimal(edtValorDespesa.getText().toString()));
            despesa.setData(LocalDate.parse(edtDataDespesa.getText().toString().
                    replace("/", "-"), formatter));
            despesa.setDescricao(edtDescricaoDespesa.getText().toString());
        }
        if (!estaEmEdicao()) {
            dao.adiciona(despesa);
        } else {
            dao.edita(despesa);
        }

        Toast.makeText(getBaseContext(), "Despesa salva com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }

    private boolean verificaSeTodosOsCamposForamPreenchidos() {
        return !(edtNomeDespesa.getText().toString().isEmpty()
                || edtDescricaoDespesa.getText().toString().isEmpty()
                || edtValorDespesa.getText().toString().isEmpty()
                || edtDataDespesa.getText().toString().isEmpty());
    }

    private void inicializaViews() {
        edtNomeDespesa = findViewById(R.id.cadastro_despesa_activity_edtNomeDespesa);
        edtDescricaoDespesa = findViewById(R.id.cadastro_despesa_activity_edtDescDespesa);
        edtValorDespesa = findViewById(R.id.cadastro_despesa_activity_edtValorDespesa);
        edtDataDespesa = findViewById(R.id.cadastro_despesa_activity_edtDataDespesa);
    }


}
