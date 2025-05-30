import { Component } from '@angular/core';
import { FooterComponent } from "../componentes/footer/footer.component";
import { SinLoguearComponent } from "../componentes/navbar/sin-loguear/sin-loguear.component";
import { LogueadoComponent } from "../componentes/navbar/logueado/logueado.component";
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-sobre-nosotros',
  standalone: true,
  imports: [FooterComponent, SinLoguearComponent, LogueadoComponent],
  templateUrl: './sobre-nosotros.component.html',
  styleUrl: './sobre-nosotros.component.css'
})
export class SobreNosotrosComponent {

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
