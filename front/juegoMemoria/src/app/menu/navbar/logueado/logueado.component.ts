import { Component } from '@angular/core';

@Component({
  selector: 'app-logueado',
  standalone: true,
  imports: [],
  templateUrl: './logueado.component.html',
  styleUrl: './logueado.component.css'
})
export class LogueadoComponent {
  public usuariosConectados: number = 100;
  public usuarioNombre: string = "Usuario";
}
