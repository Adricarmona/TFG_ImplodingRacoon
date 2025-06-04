import { Component } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { LogueadoComponent } from "../componentes/navbar/logueado/logueado.component";
import { SinLoguearComponent } from "../componentes/navbar/sin-loguear/sin-loguear.component";
import { FooterComponent } from "../componentes/footer/footer.component";
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-crear-mesa',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, FooterComponent, RouterModule],
  templateUrl: './crear-mesa.component.html',
  styleUrl: './crear-mesa.component.css'
})
export class CrearMesaComponent {

  logueado: boolean = false;

  codigoSala: number = 0;
  tamanioSala: number = 2;

  constructor(
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    if(this.authService.existeUsuario()) {
      this.logueado = true
    }
  }
  
  subirTamanioSala() {
    this.tamanioSala++
  }

  restarTamanioSala() {
    this.tamanioSala--
  }
}
