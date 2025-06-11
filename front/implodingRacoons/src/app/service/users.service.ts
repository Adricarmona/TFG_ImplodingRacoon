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

  async cambiarNombre(nombre: string, idUsuario: string) {
    const resultado = await this.apiService.post("User/ChangeUserName?newName=" + nombre + "&id=" + idUsuario)
    return resultado.data
  }

  async cambiarContrasenia(oldPassword: string, newPassword: string ,idUsuario: string) {
    const resultado = await this.apiService.post<AuthResponse>("User/ChangeUserPassword?oldPassword=" + oldPassword + "&newPassword=" + newPassword + "&id=" + idUsuario)
    return resultado.data
  }

  async eliminarUsuario(idUsuario: string) {
    const resultado = await this.apiService.delete<AuthResponse>("User/DeleteUser/" + idUsuario)
    return resultado.data
  }

  async obtenerSolicitudes(idUsuario: string) {
    const resultado = await this.apiService.get<UsuarioAmigo[]>("User/GetFriendRequests/" + idUsuario)
    return resultado.data
  }

  async obtenerUsuariosPorAmigos(idUsuario: string, nombre: string) {
    const resultado = await this.apiService.get<UsuarioAmigo[]>("User/GetUsersByIdAndNameForFriends/"+idUsuario+"?name="+nombre)
    return resultado.data
  }

  async obtenerUsuariosPorAmigosSoloId(idUsuario: string) {
    const resultado = await this.apiService.get<UsuarioAmigo[]>("User/GetUsersByIdAndNameForFriends/"+idUsuario)
    return resultado.data
  }

  async eliminarFriendRequestAmigo(idUsuario: string, idAmigoEliminar: string) {
    const resultado = await this.apiService.delete("User/DeleteFriendRequest/"+ idUsuario +"?idFriend="+ idAmigoEliminar)
    return resultado.data
  }

  async agregarFriendRequest(idUsuario: string, idAmigoEliminar: string) {
    const resultado = await this.apiService.put("User/AcceptFriendRequest?id="+ idUsuario +"&friendId="+ idAmigoEliminar)
    return resultado.data
  }

  async enviarSolcitudAmistad(idUsuario: string, idAmigoEliminar: string) {
    const resultado = await this.apiService.put("User/SetFriendRequest?id="+ idUsuario +"&friendId="+ idAmigoEliminar)
    return resultado.data
  }

}
