import { Component } from '@angular/core';
import { StateService } from '../../../../service/state.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-sin-loguear',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './sin-loguear.component.html',
  styleUrl: './sin-loguear.component.css'
})
export class SinLoguearComponent {

  constructor(private estadoService: StateService) {}

  irARegistro() {
    this.estadoService.cambiarEstado('registro');
  }

  irALogin() {
    this.estadoService.cambiarEstado('login');
  }

}