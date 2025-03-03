import { Component } from '@angular/core';
import { LogueadoComponent } from "./navbar/logueado/logueado.component";
import { SinLoguearComponent } from "./navbar/sin-loguear/sin-loguear.component";
import { OpcionesMenuComponent } from "./opciones-menu/opciones-menu.component";
import { FooterComponent } from "./footer/footer.component";
import { RegistroComponent } from "./loguinRegistro/registro/registro.component";
import { LoginComponent } from "./loguinRegistro/login/login.component";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, OpcionesMenuComponent, FooterComponent, RegistroComponent, LoginComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

}
