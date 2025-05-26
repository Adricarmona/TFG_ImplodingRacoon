import { Component } from '@angular/core';
import { LogueadoComponent } from "../../navbar/logueado/logueado.component";
import { SinLoguearComponent } from "../../navbar/sin-loguear/sin-loguear.component";
import { FooterComponent } from "../../footer/footer.component";
import { AuthService } from '../../../../service/auth.service';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, FooterComponent],
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
