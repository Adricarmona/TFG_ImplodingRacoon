import { Component } from '@angular/core';
import { SinLoguearComponent } from "../componentes/navbar/sin-loguear/sin-loguear.component";
import { LogueadoComponent } from "../componentes/navbar/logueado/logueado.component";
import { FooterComponent } from "../componentes/footer/footer.component";
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-ajustes',
  standalone: true,
  imports: [SinLoguearComponent, LogueadoComponent, FooterComponent],
  templateUrl: './ajustes.component.html',
  styleUrl: './ajustes.component.css'
})
export class AjustesComponent {
  
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
