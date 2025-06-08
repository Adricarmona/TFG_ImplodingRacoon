import { Injectable } from '@angular/core';
import { WebsocketService } from './websocket.service';
import { JsonWebsoket } from '../models/json-websoket';
import { ApiService } from './api.service';
import { Mesa } from '../models/mesa';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MesaService {
  messageReceived$: Subscription;
  disconnected$: Subscription;

  numeroMesa: string;

  mesa: Mesa;

  constructor(
    public webSocketService: WebsocketService,
    public apiService: ApiService,
  ) 
  {
    this.messageReceived$ = this.webSocketService.messageReceived.subscribe(async message => 
      await this.readMessage(message)
    );

  }


  private async readMessage(message: string): Promise<void> {

    try {
      // Paso del mensaje a objeto
      const parsedMessage: JsonWebsoket = JSON.parse(message);

      this.handleSocketMessage(parsedMessage);
    } catch (error) {
      console.error('Error al parsear el mensaje recibido:', error);
    }
  }

  async handleSocketMessage(parsedMessage: JsonWebsoket) {
    if(parsedMessage.type == 3) {
      if (parsedMessage.message == "new user") {
        this.mesa = await this.cogerDatosMesa(this.numeroMesa)
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
