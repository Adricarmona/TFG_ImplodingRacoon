import { Injectable } from '@angular/core';
import { WebsocketService } from './websocket.service';
import { NavigationStart, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JsonWebsoket } from '../models/json-websoket';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  connected$: Subscription;
  isConnected: boolean = false;
  messageReceived$: Subscription;
  disconnected$: Subscription;

  usersLogged = 0;
  gamesInProgress = 0;

  private navigationSubscription: Subscription;

  constructor(public webSocketService: WebsocketService ,
    private router: Router) 
  {

    this.connected$ = this.webSocketService.connected.subscribe(() => this.isConnected = true);

    this.messageReceived$ = this.webSocketService.messageReceived.subscribe(async message => 
      await this.readMessage(message)
    );

    this.disconnected$ = this.webSocketService.disconnected.subscribe(() => this.isConnected = false);


    this.navigationSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {}
    });
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

  private handleSocketMessage(parsedMessage: JsonWebsoket): void {

    if(parsedMessage.type == 1){
      this.usersLogged = parseInt(parsedMessage.message)
    }
  }
}
