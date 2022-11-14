import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[];

  constructor(private clienteService: ClienteService,
    private rutas: Router) { }

  ngOnInit(): void {
    this.clienteService.getClientes().subscribe(
      clientes => this.clientes = clientes
    );
  }

  public eliminarRegistro(cliente: Cliente): void {
    console.log("clicked...! eliminar registro");
    console.log(cliente);
    Swal.fire({
      //title: 'Está seguro de eliminar el registro?',
      title: `Está seguro de eliminar el registro de ${cliente.nombre} ${cliente.apellido}?`,
      text: "No se podrá revertir el mismo!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'SI, Eliminarlo!',
      cancelButtonText: 'NO'
    }).then((result) => {
      if (result.isConfirmed) {
        this.clienteService.eliminarCliente(cliente.id).subscribe(
          response => {
            this.clientes = this.clientes.filter(cli => cli !== cliente)
            //this.rutas.navigate(['/clientes'])
            Swal.fire({ title: 'Cliente Eliminado', text: `Cliente ${(cliente.nombre).toUpperCase()} eliminado con éxito!`, icon: 'success', timer: 5000 })
          }
        )
      }
    })
  }

}
