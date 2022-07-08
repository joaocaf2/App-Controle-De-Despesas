package com.example.controlededespesas.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.controlededespesas.R;
import com.example.controlededespesas.activity.Despesa;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DespesasAdatper extends BaseAdapter {
    private List<Despesa> despesas = new ArrayList<Despesa>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Context context;

    public DespesasAdatper(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return despesas.size();
    }

    @Override
    public Despesa getItem(int posicao) {
        return despesas.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return despesas.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View viewCriada = LayoutInflater.
                from(context).inflate(R.layout.item_despesa, viewGroup, false);
        setaOsDadosNasTextViews(posicao, viewCriada);
        return viewCriada;
    }

    private void setaOsDadosNasTextViews(int posicao, View viewCriada) {
        TextView txtNomeDespesa = viewCriada.findViewById(R.id.item_nome_despesa);
        TextView txtDataDespesa = viewCriada.findViewById(R.id.item_data_despesa);
        TextView txtValorDespesa = viewCriada.findViewById(R.id.item_valor_despesa);
        Despesa despesa = despesas.get(posicao);
        txtNomeDespesa.setText("Nome: " + despesa.getNome());
        txtDataDespesa.setText("Data: " + "...");
        txtValorDespesa.setText("Valor: " + String.valueOf(despesa.getValor()));
    }

    public void atualizaDados(List<Despesa> despesas) {
        this.despesas.clear();
        this.despesas.addAll(despesas);
        notifyDataSetChanged();
    }
}
