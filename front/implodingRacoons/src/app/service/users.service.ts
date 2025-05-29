import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { UsuariosSimple } from '../models/usuarios-simple';
import { UsuarioAmigo } from '../models/usuario-amigo';
import { AuthResponse } from '../models/auth-response';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor( private apiService: ApiService ) { }

  async obtenerUsuarioPorId(id: string) {
    const resultado =  await this.apiService.get<UsuariosSimple>("User/GetUserById/"+id)

    return resultado.data
  }

  async obtenerAmigosPorUsuario(id: string) {
    const resultado =  await this.apiService.get<UsuarioAmigo[]>("User/GetFriendsbyUserId/"+id)

    return resultado.data
  }

  async eliminarAmigo(idUsuario: string, idAmigo: number) {
    const resultado =  await this.apiService.delete<AuthResponse>("User/DeleteFriend/"+idUsuario+"?friendId="+idAmigo)

    return resultado.data
  }

  async actualizarFoto(foto: File, idUsuario: string) {
    const formData = new FormData();
    formData.append('foto', foto, foto.name);

    try {
      const resultado = await this.apiService.post<AuthResponse>("User/ChangeUserPhoto?id=" + idUsuario, formData)
      return resultado.data
    } catch (error) {
      return error
    }

  }

}
