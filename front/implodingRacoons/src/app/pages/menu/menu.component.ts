import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogueadoComponent } from "./navbar/logueado/logueado.component";
import { OpcionesMenuComponent } from "./opciones-menu/opciones-menu.component";
import { FooterComponent } from "./footer/footer.component";
import { RegistroComponent } from "./loguinRegistro/registro/registro.component";
import { LoginComponent } from "./loguinRegistro/login/login.component";
import { StateService } from '../../service/state.service';
import { SinLoguearComponent } from './navbar/sin-loguear/sin-loguear.component';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, OpcionesMenuComponent, FooterComponent, RegistroComponent, LoginComponent, CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  estado: 'menu' | 'registro' | 'login' = 'menu';
  logueado: boolean = false;

  constructor(private estadoService: StateService) {}

  ngOnInit(): void {
    this.estadoService.estado$.subscribe(e => this.estado = e);
  }
}
