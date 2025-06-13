import { Injectable } from '@angular/core';
import { WebsocketService } from './websocket.service';
import { Subscription } from 'rxjs';
import { JsonWebsoket } from '../models/json-websoket';

@Injectable({
  providedIn: 'root'
})
export class UnirseMesaService {
  
  codigoMesa: string

  messageReceived$: Subscription;     

  constructor( public webSocketService: WebsocketService  ) {

    this.messageReceived$ = this.webSocketService.messageReceived.subscribe(async message => 
      await this.readMessage(message)
    );

  }

  readMessage(mensaje: string) {
    try {
      // Paso del mensaje a objeto
      const parsedMessage: JsonWebsoket = JSON.parse(mensaje);

      this.handleSocketMessage(parsedMessage);
    } catch (error) {
      console.error('Error al parsear el mensaje recibido:', error);
    }
  }

  handleSocketMessage(parsedMessage: JsonWebsoket) {
    if(parsedMessage.type == 3) {
      if (parsedMessage.message == "True") {
        console.log("ha funcionado")
        const url: string = "partida_mano/:"+this.codigoMesa
        window.location.href = url
      } else {
        console.log("error al crear la sala")
      }
    }
  }

  sendMessage(mensaje: string) {
    this.webSocketService.sendRxjs(mensaje)
  }
}
