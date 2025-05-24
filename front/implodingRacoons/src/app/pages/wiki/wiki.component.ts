import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from "./navbar/navbar.component";
import { WikiService } from '../../service/wiki.service';
import { PublicacionTarjeta } from '../../models/publicacion-tarjeta';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-wiki',
  standalone: true,
  imports: [RouterModule, NavbarComponent, DatePipe],
  templateUrl: './wiki.component.html',
  styleUrl: './wiki.component.css'
})
export class WikiComponent implements OnInit {
  PostBuscados: PublicacionTarjeta[] = [];

  constructor(private wikiService: WikiService){}

  async ngOnInit() {
    try {
      this.PostBuscados = await this.wikiService.cogerPostPorNombre("s");
    } catch (error) {
      console.error('Error cargando posts:', error);
    }
  }
}
