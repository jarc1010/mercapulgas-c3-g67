package com.mercapulgas.api.models.services;

import java.util.List;

import com.mercapulgas.api.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> listarClientes();
	
	public Cliente listarClientePorId(Long id);
	
	public Cliente guardaractualizarCliente(Cliente cliente);
	
	public void eliminarClientePorId(Long id);
	
}
