import { Injectable } from '@angular/core';
import { WebsocketService } from './websocket.service';
import { Subscription } from 'rxjs';
import { JsonWebsoket } from '../models/json-websoket';

@Injectable({
  providedIn: 'root'
})
export class MesaService {
  messageReceived$: Subscription;
  disconnected$: Subscription;

  usersLogged = 0;

  constructor(public webSocketService: WebsocketService) 
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
