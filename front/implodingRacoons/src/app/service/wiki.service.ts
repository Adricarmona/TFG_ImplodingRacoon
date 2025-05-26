import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { PublicacionTarjeta } from '../models/publicacion-tarjeta';
import { ComentarioPublicacion } from '../models/comentario-publicacion';
import { PublicarComentario } from '../models/publicar-comentario';
import { PublicarPublicacion } from '../models/publicar-publicacion';

@Injectable({
  providedIn: 'root'
})
export class WikiService {

  constructor( private apiService: ApiService ) { }

  async cogerPostPorNombre(palabra: string): Promise<PublicacionTarjeta[]> {
    const resultado =  await this.apiService.get<PublicacionTarjeta[]>("Publicacion/GetPostByName/"+palabra)

    return resultado.data
  }

  async cogerPosts(): Promise<PublicacionTarjeta[]> {
    const resultado =  await this.apiService.get<PublicacionTarjeta[]>("Publicacion/GetAllPosts/")

    return resultado.data
  }

  async cogerPostConcreto(id: string): Promise<PublicacionTarjeta> {
    const resultado = await this.apiService.get<PublicacionTarjeta>("Publicacion/GetPostById/"+id)

    return resultado.data
  }

  async cogerComentariosPost(id: string): Promise<ComentarioPublicacion[]> {
    const resultado = await this.apiService.get<ComentarioPublicacion[]>("Publicacion/GetComentsByPostId/"+id)

    return resultado.data
  }

  async publicarComentario(publicarComentario : PublicarComentario) {
    const resultado = await this.apiService.post("Publicacion/CreateComment", publicarComentario)

    return resultado.data
  }

  async puiblicarPost(publicarComentario: PublicarPublicacion) {
    const resultado = await this.apiService.post("Publicacion/CreatePost", publicarComentario)

    return resultado.data
  }

    async cogerPostPorUsuarioConcreto(id: string): Promise<PublicacionTarjeta[]> {
    const resultado = await this.apiService.get<PublicacionTarjeta[]>("Publicacion/GetPostsByUserId/"+id)

    return resultado.data
  }
}
