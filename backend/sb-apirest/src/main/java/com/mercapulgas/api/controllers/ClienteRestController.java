package com.mercapulgas.api.controllers;

import java.util.HashMap;
//import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercapulgas.api.models.entity.Cliente;
import com.mercapulgas.api.models.services.IClienteService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> clientes(){
		return clienteService.listarClientes();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> clientePorId(@PathVariable Long id){
		
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.listarClientePorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consultar en la base de datos.! :(");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		if (cliente == null) {
			response.put("mensaje", "El Cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
		//cliente.setFecha(new Date()); se comenta ya que en la entity al atributo se crea metodo de prepersist
		Cliente clienteNuevo = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			/*
			 * version antes de jdk 8
			 * List<String> errors = new ArrayList<>();
			
			for(FieldError err: result.getFieldErrors()) {
				errors.add("El campo '"+err.getField()+"' "+err.getDefaultMessage());
			}*/
			
			List<String> errors = result.getFieldErrors().
					stream().
					map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage()).
					collect(Collectors.toList());
						
			response.put("errores", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			clienteNuevo = clienteService.guardaractualizarCliente(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.! :(");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		response.put("mensaje", "El cliente ha sido creado con éxito.! :)");
		response.put("cliente", clienteNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> actualizarCliente(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente clienteActual = clienteService.listarClientePorId(id);
		
		Cliente clienteActualizado = null;
		Map<String, Object> response = new HashMap<>();
		
		if (clienteActual == null) {
			response.put("mensaje", "Error: No se pudo editar, el cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setFecha(cliente.getFecha());
			
			clienteActualizado = clienteService.guardaractualizarCliente(clienteActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en la base de datos.! :(");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		response.put("mensaje", "El cliente ha sido actualizado con éxito.! :)");
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			clienteService.eliminarClientePorId(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos.! :(");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		response.put("mensaje", "El cliente ha sido eliminado con éxito.! :)");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);	
	}
}
