import { Component } from '@angular/core';
import { LogueadoComponent } from "./navbar/logueado/logueado.component";
import { SinLoguearComponent } from "./navbar/sin-loguear/sin-loguear.component";
import { OpcionesMenuComponent } from "./opciones-menu/opciones-menu.component";
import { FooterComponent } from "./footer/footer.component";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, OpcionesMenuComponent, FooterComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

}
