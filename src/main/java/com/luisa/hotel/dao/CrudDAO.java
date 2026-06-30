package com.luisa.hotel.dao;

import java.util.List;

public interface CrudDAO<T> {

    void inserir(T obj);

    void atualizar(T obj);

    void excluir(Integer id);

    List<T> listar();
}