import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Carta } from '../models/carta';

@Injectable({
  providedIn: 'root'
})
export class CartasService {

  constructor(
    private apiService: ApiService
  ) { }

  async cogerCartasPorTipo(numero: number) {
    const dato = await this.apiService.get<Carta[]>("Cards/GetCardByIdImage/type="+numero)
    return dato.data
  }

  async cogerCartaPorTipoYId(id: number, tipo: number) {
    const dato = await this.apiService.get<Carta>("Cards/GetCardByIdImage/id=" + id + "type=" + tipo)
    return dato.data
  }
}
