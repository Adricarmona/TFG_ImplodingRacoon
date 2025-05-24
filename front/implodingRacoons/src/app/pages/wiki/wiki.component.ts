import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from "./navbar/navbar.component";
import { WikiService } from '../../service/wiki.service';
import { PublicacionTarjeta } from '../../models/publicacion-tarjeta';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-wiki',
  standalone: true,
  imports: [RouterModule, NavbarComponent, DatePipe, FormsModule],
  templateUrl: './wiki.component.html',
  styleUrl: './wiki.component.css'
})
export class WikiComponent implements OnInit {
  PostBuscados: PublicacionTarjeta[] = [];

  buscador: string = "";

  constructor(
    private wikiService: WikiService
  ){}

  async ngOnInit() {
    try {
      this.PostBuscados = await this.wikiService.cogerPosts();
    } catch (error) {
      console.error('Error cargando posts:', error);
    }
  }

  async busqueda() {
    try {
      this.PostBuscados = []
      if (this.buscador.trim().length < 1) {
        this.PostBuscados = await this.wikiService.cogerPosts();
      } else {
        this.PostBuscados = await this.wikiService.cogerPostPorNombre(this.buscador);
      }


    } catch (error) {
      console.error('Error cargando posts:', error);
    }
  }

}
