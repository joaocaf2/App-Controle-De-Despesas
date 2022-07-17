package com.example.controlededespesas.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {
    private final static List<Despesa> despesas = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void adiciona(Despesa despesa) {
        despesa.setId(contadorDeIds);
        despesas.add(despesa);
        contadorDeIds++;
    }

    public void edita(Despesa despesa) {
        int indice = despesas.indexOf(despesa);
        despesas.set(indice, despesa);
    }

    public Despesa findById(int idBuscado) {
        for (Despesa despesa : despesas) {
            if (despesa.getId() == idBuscado) {
                return despesa;
            }
        }
        return null;
    }

    private void remove(Despesa despesa) {
        Despesa despesaBuscada = findById(despesa.getId());
        despesas.remove(despesaBuscada);
    }


    public BigDecimal getTotalDespesas() {
        BigDecimal total = new BigDecimal("0");
        for (Despesa despesa : despesas) {
            if (despesa.getStatus() == false) {
                BigDecimal valor = despesa.getValor();
                total = total.add(valor);
            }
        }
        return total;
    }


    public Despesa findLast() {
        if (despesas.isEmpty()) return null;
        if (despesas.get(despesas.size() - 1) == null) return null;
        return despesas.get(despesas.size() - 1);
    }

    public ArrayList<Despesa> findAll() {
        return new ArrayList<>(despesas);
    }
}
