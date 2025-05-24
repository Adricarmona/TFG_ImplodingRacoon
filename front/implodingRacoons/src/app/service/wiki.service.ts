import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { PublicacionTarjeta } from '../models/publicacion-tarjeta';
import { lastValueFrom, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WikiService {

  constructor( private apiService: ApiService ) { }

  async cogerPostPorNombre(palabra: string): Promise<PublicacionTarjeta[]> {
    const resultado =  await this.apiService.get<PublicacionTarjeta[]>("Publicacion/GetPostByName/"+palabra)

    return resultado.data
  }
}
