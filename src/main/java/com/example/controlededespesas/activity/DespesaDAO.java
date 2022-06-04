package com.example.controlededespesas.activity;

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

    public Despesa findById(int idBuscado) {
        for (Despesa despesa : despesas) {
            if (despesa.getId() == idBuscado) {
                return despesa;
            }
        }
        return null;
    }

    private void remove(Despesa despesa) {

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
