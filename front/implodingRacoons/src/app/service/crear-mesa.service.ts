import { Injectable } from '@angular/core';
import { WebsocketService } from './websocket.service';
import { Subscription } from 'rxjs';
import { JsonWebsoket } from '../models/json-websoket';

@Injectable({
  providedIn: 'root'
})
export class CrearMesaService {

  messageReceived$: Subscription;  

  constructor( public webSocketService: WebsocketService  ) {

    this.messageReceived$ = this.webSocketService.messageReceived.subscribe(async message => 
      await this.readMessage(message)
    );

    this.webSocketService.sendRxjs

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
    if(parsedMessage.type == 2) {
      if (parsedMessage.message == "dato ingresado incorrectamente") {
        console.log("error al crear la sala")
      } else {
        console.log("ha funcionado")
        const url: string = "partida/:"+parsedMessage.message
        window.location.href = url
      }
    }
  }

  sendMessage(mensaje: string) {
    this.webSocketService.sendRxjs(mensaje)
  }
}
