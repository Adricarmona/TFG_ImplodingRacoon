import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from "../navbar/navbar.component";
import { WikiService } from '../../../service/wiki.service';
import { PublicacionTarjeta } from '../../../models/publicacion-tarjeta';
import { DatePipe } from '@angular/common';
import { ComentarioPublicacion } from '../../../models/comentario-publicacion';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [NavbarComponent , DatePipe],
  templateUrl: './post.component.html',
  styleUrl: './post.component.css'
})
export class PostComponent implements OnInit {

  codigoIdentificador: string = "";
  Post: PublicacionTarjeta;
  Comentarios: ComentarioPublicacion[] = []

    constructor(
      private wikiService: WikiService,
      private route: ActivatedRoute, 
    ){}

  async ngOnInit() {

    this.route.params.subscribe(params => {
      this.codigoIdentificador = params['id'];
    })

    try {
      this.Post = await this.wikiService.cogerPostConcreto(this.codigoIdentificador);
    } catch (error) {
      console.error('Error cargando posts:', error);
    }

    try {
      this.Comentarios = await this.wikiService.cogerComentariosPost(this.codigoIdentificador)
    } catch (error) {
      console.error('Error cargando comentarios:', error);
    }
  }
  
}
