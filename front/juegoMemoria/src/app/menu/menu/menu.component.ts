import { Component } from '@angular/core';
import { LogueadoComponent } from "../navbar/logueado/logueado.component";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [LogueadoComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

}
