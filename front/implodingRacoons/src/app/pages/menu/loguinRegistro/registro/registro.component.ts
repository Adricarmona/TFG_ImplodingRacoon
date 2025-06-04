import { Component } from '@angular/core';
import { FooterComponent } from "../../componentes/footer/footer.component";
import { AuthService } from '../../../../service/auth.service';
import { LogueadoComponent } from '../../componentes/navbar/logueado/logueado.component';
import { SinLoguearComponent } from '../../componentes/navbar/sin-loguear/sin-loguear.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, FooterComponent, RouterModule],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {

  logueado: boolean = false;

    constructor(
      private authService: AuthService, 
    ) {

    }

    ngOnInit(): void {
    if(this.authService.jwt != "" && this.authService.jwt != null) {
      this.logueado = true
    }
  }

}
