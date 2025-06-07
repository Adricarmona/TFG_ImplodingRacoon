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
  imagenPerfil = "http://localhost:5097/iconos/47db4364-eb50-496a-ac7a-4481e5db1b60_cueto.png"

  jugadoresMesa: number = 5

  acabarPartida() {
    alert("Acabas de cerrar la partida")

    const url: string = ""
    window.location.href = url
  }
}
