import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../service/auth.service';
import { CartasService } from '../../../service/cartas.service';

@Component({
  selector: 'app-mano',
  standalone: true,
  imports: [],
  templateUrl: './mano.component.html',
  styleUrl: './mano.component.css'
})
export class ManoComponent {

  public idMesa: string = ""
  public nombreJugador: string = ""

  posicionTipoCartas: number;

  cartasMedio = "http://localhost:5097/cards/parteAtras.png"

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private cartaService: CartasService
  ) {
    this.nombreJugador = this.authService.cogerNombreJwt()

    const numero = parseInt(localStorage.getItem('tipoCarta'))
    this.posicionTipoCartas = (numero == 0 || numero == 1) ? numero : 0
  }

  async ngOnInit(){
    this.route.params.subscribe(params => {
      this.idMesa = params['id'].slice(1);
    })

    this.cartasMedio = (await this.cartaService.cogerCartaPorTipoYId(14, this.posicionTipoCartas)).urlImage

  }

}
