import { Component } from '@angular/core';
import { LogueadoComponent } from "../../componentes/navbar/logueado/logueado.component";
import { SinLoguearComponent } from "../../componentes/navbar/sin-loguear/sin-loguear.component";
import { FooterComponent } from "../../componentes/footer/footer.component";
import { AuthService } from '../../../../service/auth.service';
import { RouterModule } from '@angular/router';
import { Carta } from '../../../../models/carta';
import { CartasService } from '../../../../service/cartas.service';

@Component({
  selector: 'app-seleccionar-cartas',
  standalone: true,
  imports: [LogueadoComponent, SinLoguearComponent, FooterComponent, RouterModule],
  templateUrl: './seleccionar-cartas.component.html',
  styleUrl: './seleccionar-cartas.component.css'
})
export class SeleccionarCartasComponent {

  logueado: boolean = false;

  tipo: string[] = ["Original", "otro"]
  posicionTipoCartas: number = 0

  cartas: Carta[];

  constructor(
    private authService: AuthService,
    private cartaService: CartasService
  ) {}

  async ngOnInit(): Promise<void> {
    if(this.authService.existeUsuario()) {
      this.logueado = true
    }

    const numero = parseInt(localStorage.getItem('tipoCarta'))
    this.posicionTipoCartas = (numero == 0 || numero == 1) ? numero : 0

    this.cartas = await this.cartaService.cogerCartasPorTipo(this.posicionTipoCartas)
  }

  async cambiarTipo(sumarOQuitar: string) {
    if(sumarOQuitar == "+") {
      this.posicionTipoCartas == 1? this.posicionTipoCartas = 0: this.posicionTipoCartas++;
    } else if (sumarOQuitar == "-") {
      this.posicionTipoCartas == 0? this.posicionTipoCartas = 1: this.posicionTipoCartas--;
    }
    this.cartas = await this.cartaService.cogerCartasPorTipo(this.posicionTipoCartas)
  }

  guardarCartas(){
    alert("Cartas seleccionadas")
    localStorage.setItem('tipoCarta', this.posicionTipoCartas+"");
  }
  
}
