import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuComponent } from './pages/menu/menu.component';
import { WebsocketService } from './service/websocket.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'imploding kittens';

  constructor(
        private websocket: WebsocketService,
  ) {}

  async ngOnInit() {
    await this.websocket.connectRxjs()
  }
}
