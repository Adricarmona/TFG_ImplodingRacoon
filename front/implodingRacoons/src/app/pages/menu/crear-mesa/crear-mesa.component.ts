import { Component } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { LogueadoComponent } from "../componentes/navbar/logueado/logueado.component";
import { SinLoguearComponent } from "../componentes/navbar/sin-loguear/sin-loguear.component";
import { FooterComponent } from "../componentes/footer/footer.component";
import { RouterModule } from '@angular/router';
import { CrearMesaService } from '../../../service/crear-mesa.service';
import { FormsModule } from '@angular/forms';
import { WebsocketsEnviar } from '../../../models/websockets-enviar';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-crear-mesa',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, FooterComponent, RouterModule, FormsModule],
  templateUrl: './crear-mesa.component.html',
  styleUrl: './crear-mesa.component.css'
})
export class CrearMesaComponent {

  logueado: boolean = false;

  tamanioSala: number = 2;
  salaConContrasenia: boolean = false;
  contrasenia: string = "";

  constructor(
    private authService: AuthService,
    private crearMesa: CrearMesaService
  ) {}

  ngOnInit(): void {
    if(this.authService.existeUsuario()) {
      this.logueado = true
    }
  }
  
  subirTamanioSala() { this.tamanioSala++ }
  restarTamanioSala() { this.tamanioSala-- }

  crearSala() {
    const enviarJson: WebsocketsEnviar = {
      TypeMessage: "create",
      Identifier: "1",
      Identifier2: "2"
    }

    const enviar: string = JSON.stringify(enviarJson)

    this.crearMesa.sendMessage(enviar)
  }
}
