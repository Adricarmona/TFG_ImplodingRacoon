import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OpcionesMenuComponent } from "./opciones-menu/opciones-menu.component";
import { FooterComponent } from "./componentes/footer/footer.component";
import { RegistroComponent } from "./loguinRegistro/registro/registro.component";
import { LoginComponent } from "./loguinRegistro/login/login.component";
import { StateService } from '../../service/state.service';
import { AuthService } from '../../service/auth.service';
import { LogueadoComponent } from './componentes/navbar/logueado/logueado.component';
import { SinLoguearComponent } from './componentes/navbar/sin-loguear/sin-loguear.component';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, OpcionesMenuComponent, FooterComponent, RegistroComponent, LoginComponent, CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  logueado: boolean = false;

  constructor(
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    if(this.authService.existeUsuario()) {
      this.logueado = true
    }
  }
}
