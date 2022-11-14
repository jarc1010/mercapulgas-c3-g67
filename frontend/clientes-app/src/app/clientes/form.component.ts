import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {
  public cliente: Cliente = new Cliente();
  public titulo: String = "Crear Cliente";

  constructor(private clienteService: ClienteService,
    private rutas: Router,
    private activarRutas: ActivatedRoute) { }

  ngOnInit(): void {
    this.cargarCliente();
  }

  public cancelarRegistro(): void {
    console.log("clicked...! cancelar registro");
    Swal.fire({
      title: 'Está seguro de cancelar el proceso?',
      text: "No se podrá revertir el mismo!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, Cancelarlo!',
      cancelButtonText: 'NO'
    }).then((result) => {
      if (result.isConfirmed) {
          this.rutas.navigate(['/clientes'])
      }
    })
  }

  public cargarCliente(): void {
    this.activarRutas.params.subscribe(params => {
      let id = params['id']
      if (id) {
        this.clienteService.getCliente(id).subscribe(cliente => this.cliente = cliente)
      }
    })
  }

  public crearCliente(): void {
    console.log("clicked...! crear");
    console.log(this.cliente);
    this.clienteService.crearCliente(this.cliente).subscribe(
      cliente => {
        this.rutas.navigate(['/clientes'])
        //Swal.fire('Nuevo Cliente', `Cliente ${(cliente.nombre).toUpperCase()} creado con éxito!`,'success')
        Swal.fire({ title: 'Nuevo Cliente', text: `Cliente ${(cliente.nombre).toUpperCase()} creado con éxito!`, icon: 'success', timer: 5000 })
      }
    );
  }

  public actualizarCliente(): void {
    console.log("clicked...! actualizar");
    console.log(this.cliente);
    this.clienteService.actualizarCliente(this.cliente).subscribe(
      json => {
        this.rutas.navigate(['/clientes'])
        Swal.fire({ title: 'Cliente Actualizado', text: `${json.mensaje} : ${(json.cliente.nombre).toUpperCase()}`, icon: 'success', timer: 5000 })
      }
    );
  }

}