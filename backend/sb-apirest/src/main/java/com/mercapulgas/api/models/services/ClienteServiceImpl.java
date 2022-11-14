package com.mercapulgas.api.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercapulgas.api.models.dao.IClienteDao;
import com.mercapulgas.api.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;

	@Override
	public List<Cliente> listarClientes() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	public Cliente listarClientePorId(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public Cliente guardaractualizarCliente(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	public void eliminarClientePorId(Long id) {
		clienteDao.deleteById(id);
	}
	
}
