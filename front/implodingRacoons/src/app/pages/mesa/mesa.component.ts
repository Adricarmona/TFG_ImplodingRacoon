import { Component } from '@angular/core';
import { MesaService } from '../../service/mesa.service';
import { ActivatedRoute } from '@angular/router';
import { WebsocketService } from '../../service/websocket.service';
import { ApiService } from '../../service/api.service';
import { Mesa } from '../../models/mesa';
import { Subscription } from 'rxjs';
import { JsonWebsoket } from '../../models/json-websoket';
import { tick } from '@angular/core/testing';

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
  jugadoresMesa: number = 5

  constructor(
    private route: ActivatedRoute, 
    public webSocketService: WebsocketService,
    public apiService: ApiService,
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
      } else if (parsedMessage.message == "new user") {
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
      return datosMesa.data
    } catch (error) {
      console.log(error)
      return null
    }
  }
}
