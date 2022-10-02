package com.example.controlededespesas.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlededespesas.R;
import com.example.controlededespesas.activity.Despesa;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DespesasAdatper extends RecyclerView.Adapter<DespesasAdatper.DespesasViewHolder> {

    private List<Despesa> despesas = new ArrayList<Despesa>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Context context;

    public DespesasAdatper(Context context, List<Despesa> despesas) {
        this.context = context;
        this.despesas = despesas;
    }


    public void atualizaDados(List<Despesa> despesas) {
        this.despesas.clear();
        this.despesas.addAll(despesas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DespesasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_despesa, parent, false);
        return new DespesasViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull DespesasAdatper.DespesasViewHolder holder, int position) {
        Despesa despesa = despesas.get(position);
        holder.preencheCampos(despesa);
    }

    @Override
    public int getItemCount() {
        return despesas.size();
    }

    class DespesasViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeDespesa;
        private TextView valorDespesa;

        public DespesasViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeDespesa = itemView.findViewById(R.id.item_nome_despesa);
            valorDespesa = itemView.findViewById(R.id.item_valor_despesa);
        }

        private void preencheCampos(Despesa despesa) {
            nomeDespesa.setText(despesa.getNome());
            valorDespesa.setText(String.valueOf(despesa.getValor()));
        }
    }

}
