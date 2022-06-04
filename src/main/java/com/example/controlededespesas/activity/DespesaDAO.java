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


}
