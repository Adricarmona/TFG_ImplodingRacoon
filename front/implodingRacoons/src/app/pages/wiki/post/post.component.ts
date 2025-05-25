import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from "../navbar/navbar.component";
import { WikiService } from '../../../service/wiki.service';
import { PublicacionTarjeta } from '../../../models/publicacion-tarjeta';
import { DatePipe } from '@angular/common';
import { ComentarioPublicacion } from '../../../models/comentario-publicacion';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PublicarComentario } from '../../../models/publicar-comentario';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [NavbarComponent , DatePipe, FormsModule],
  templateUrl: './post.component.html',
  styleUrl: './post.component.css'
})
export class PostComponent implements OnInit {

  codigoIdentificador: string = "";
  Post: PublicacionTarjeta;
  Comentarios: ComentarioPublicacion[] = []

  comentarioEscrito: string = "";

    constructor(
      private wikiService: WikiService,
      private route: ActivatedRoute, 
      private authService: AuthService
    ){}

  async ngOnInit() {

    this.route.params.subscribe(params => {
      this.codigoIdentificador = params['id'];
    })

    await this.cogerPostEnConcreto()
    await this.cogerComentario()

  }

  async subirComentario() {

    if (this.authService.existeUsuario) {

      const publicarComentario: PublicarComentario = {
        comentario: this.comentarioEscrito,
        fecha: new Date,
        publicacionId: parseInt(this.codigoIdentificador),
        usuarioId: this.authService.cogerIdJwt()
      }

      try {
        this.wikiService.publicarComentario(publicarComentario)
      } catch (error) {
        console.log(error)
      }

    } else {
      
      console.log("usuario no iniciado")

    }

  }

  async cogerComentario(){

    try {
      this.Comentarios = await this.wikiService.cogerComentariosPost(this.codigoIdentificador)
    } catch (error) {
      console.error('Error cargando comentarios:', error);
    }

  }

  async cogerPostEnConcreto() {

    try {
      this.Post = await this.wikiService.cogerPostConcreto(this.codigoIdentificador);
    } catch (error) {
      console.error('Error cargando posts:', error);
    }

  }

}
