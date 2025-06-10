import { Component } from '@angular/core';
import { MesaService } from '../../service/mesa.service';
import { ActivatedRoute } from '@angular/router';
import { WebsocketService } from '../../service/websocket.service';
import { ApiService } from '../../service/api.service';
import { Mesa } from '../../models/mesa';
import { Subscription } from 'rxjs';
import { JsonWebsoket } from '../../models/json-websoket';
import { tick } from '@angular/core/testing';
import { UsuariosSimple } from '../../models/usuarios-simple';
import { UserGame } from '../../models/user-game';
import { UsersService } from '../../service/users.service';

@Component({
  selector: 'app-mesa',
  standalone: true,
  imports: [],
  templateUrl: './mesa.component.html',
  styleUrl: './mesa.component.css'
})
export class MesaComponent {

  messageReceived$: Subscription;
  disconnected$: Subscription;

  mesa: Mesa;

  cartasMedio = "http://localhost:5097/cards/parteAtras.png"
  imagenPerfil = "http://localhost:5097/iconos/47db4364-eb50-496a-ac7a-4481e5db1b60_cueto.png"

  public idMesa: string = ""

  jugador1: UsuariosSimple
  jugador2: UsuariosSimple
  jugador3: UsuariosSimple
  jugador4: UsuariosSimple
  jugador5: UsuariosSimple

  constructor(
    private route: ActivatedRoute, 
    public webSocketService: WebsocketService,
    public apiService: ApiService,
    public userService: UsersService
  ) {
    this.messageReceived$ = this.webSocketService.messageReceived.subscribe(async message => 
      await this.readMessage(message)
    );

  }

  async ngOnInit() {
    this.route.params.subscribe(params => {
      this.idMesa = params['id'].slice(1);
    })

    this.mesa = await this.cogerDatosMesa(this.idMesa)
    console.log(this.mesa)
  }

  acabarPartida() {
    alert("Acabas de cerrar la partida")

    const url: string = ""
    window.location.href = url
  }

  private async readMessage(message: string): Promise<void> {

    try {
      // Paso del mensaje a objeto
      const parsedMessage: JsonWebsoket = JSON.parse(message);
      console.log(parsedMessage)
      this.handleSocketMessage(parsedMessage);
    } catch (error) {
      console.error('Error al parsear el mensaje recibido:', error);
    }
  }

  async handleSocketMessage(parsedMessage: JsonWebsoket) {
    if(parsedMessage.type == 3) {
      if (parsedMessage.message == "True") {
        this.mesa = await this.cogerDatosMesa(this.idMesa)
        console.log(this.mesa)
      } else {
        console.log("error al iniciar sesion")
      }
    }
  }

  sendMessage(mensaje: string) {
    this.webSocketService.sendRxjs(mensaje)
  }

  async cogerDatosMesa(numero: string):Promise<Mesa> {
    try {
      const datosMesa = await this.apiService.get<Mesa>("Mesa/GetMesaById/"+numero)

      const jugadoresMesa: UserGame[] = datosMesa.data.jugadores
      if (jugadoresMesa[1] != null)
        this.jugador1 = await this.userService.obtenerUsuarioPorId(jugadoresMesa[1].id.toString())

      if (jugadoresMesa[2] != null)
        this.jugador2 = await this.userService.obtenerUsuarioPorId(jugadoresMesa[2].id.toString())

      if (jugadoresMesa[3] != null)
        this.jugador3 = await this.userService.obtenerUsuarioPorId(jugadoresMesa[3].id.toString())

      if (jugadoresMesa[4] != null)
        this.jugador4 = await this.userService.obtenerUsuarioPorId(jugadoresMesa[4].id.toString())

      if (jugadoresMesa[5] != null)
        this.jugador5 = await this.userService.obtenerUsuarioPorId(jugadoresMesa[5].id.toString())


      return datosMesa.data
    } catch (error) {
      console.log(error)
      return null
    }
  }
}
