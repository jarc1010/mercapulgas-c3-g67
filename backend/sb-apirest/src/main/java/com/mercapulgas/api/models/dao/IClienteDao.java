package com.mercapulgas.api.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mercapulgas.api.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
