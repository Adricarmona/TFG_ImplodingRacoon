import { Component } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { LogueadoComponent } from "../componentes/navbar/logueado/logueado.component";
import { SinLoguearComponent } from "../componentes/navbar/sin-loguear/sin-loguear.component";
import { FooterComponent } from "../componentes/footer/footer.component";
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { WebsocketsEnviar } from '../../../models/websockets-enviar';
import { UnirseMesaService } from '../../../service/unirse-mesa.service';

@Component({
  selector: 'app-unirse',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, FooterComponent, RouterModule, FormsModule],
  templateUrl: './unirse.component.html',
  styleUrl: './unirse.component.css'
})
export class UnirseComponent {

  logueado: boolean = false
  idHost: number = 0;

  codigoMesa: string
  contrasenia: boolean = false


  constructor(
    private authService: AuthService,
    private unirseMesa: UnirseMesaService
  ) {}

  ngOnInit(): void {
    if(this.authService.existeUsuario()) {
      this.logueado = true

      this.idHost = this.authService.cogerIdJwt()
    }
  }

  unirseSala() {
    if (this.codigoMesa != null) {
      const enviarJson: WebsocketsEnviar = {
        TypeMessage: "join",
        Identifier: this.codigoMesa,
        Identifier2: this.idHost.toString()
      }

      const enviar: string = JSON.stringify(enviarJson)

      this.unirseMesa.codigoMesa = this.codigoMesa
      this.unirseMesa.sendMessage(enviar)
    } else {
      alert("Error al ingresar el codigo")
    }

  }

}
