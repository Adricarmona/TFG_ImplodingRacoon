import { Component } from '@angular/core';

@Component({
  selector: 'app-mesa',
  standalone: true,
  imports: [],
  templateUrl: './mesa.component.html',
  styleUrl: './mesa.component.css'
})
export class MesaComponent {

  cartasMedio = "http://localhost:5097/cards/parteAtras.png"

  acabarPartida() {
    alert("Acabas de cerrar la partida")

    const url: string = ""
    window.location.href = url
  }
}
