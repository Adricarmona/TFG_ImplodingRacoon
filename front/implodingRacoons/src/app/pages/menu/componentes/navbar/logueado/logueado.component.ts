import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../../service/auth.service';
import { UsersService } from '../../../../../service/users.service';
import { UsuariosSimple } from '../../../../../models/usuarios-simple';
import { MenuService } from '../../../../../service/menu.service';
import { RouterModule } from '@angular/router';
import { UsuarioAmigo } from '../../../../../models/usuario-amigo';
import { tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-logueado',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './logueado.component.html',
  styleUrl: './logueado.component.css'
})
export class LogueadoComponent implements OnInit {
  public idUsuario: string = ""
  public usuariosConectados: number = 0

  public clicado: boolean = false

  public modalMoificacionUsuario: boolean = false

  public usuarioSimple: UsuariosSimple = {
    cantidadAmigos: 0,
    id: 0,
    nombreUsuario: "",
    urlFoto: ""
  }

  public nombreUsuarioBuscar: string = ""

  public amigos:UsuarioAmigo[];
  public amigosSolicitudes:UsuarioAmigo[];
  public amigosEnviarSolicitudes:UsuarioAmigo[];

  constructor(
    private authService: AuthService,
    private userService: UsersService,
    public menusoket: MenuService,
    private usersService: UsersService
  ) {}

  
  async ngOnInit() {
    this.idUsuario = this.authService.cogerIdJwt()
    this.usuarioSimple = await this.userService.obtenerUsuarioPorId(this.idUsuario)
    this.amigos = await this.userService.obtenerAmigosPorUsuario(this.idUsuario)
    this.amigosSolicitudes = await this.userService.obtenerSolicitudes(this.idUsuario)
  }

  desLoguearse() {
    this.authService.eliminarJwtSessionYLocalStorage()
    window.location.reload()
  }

  clicadoMenu() {
    this.clicado = !this.clicado
  }

  modal() {
    this.modalMoificacionUsuario = !this.modalMoificacionUsuario
  }

  async BuscarUsuario() {
    if (this.nombreUsuarioBuscar.trim() != "" ) {
      this.amigosEnviarSolicitudes = await this.userService.obtenerUsuariosPorAmigos(this.idUsuario, this.nombreUsuarioBuscar)
    } else{
      alert("Ingresa un dato para buscar el usuario")
    }
  }

  async recargarAmigos() {
    this.amigos = await this.userService.obtenerAmigosPorUsuario(this.idUsuario)
  }

  async recargarSolicitudesAmigos() {
    this.amigosSolicitudes = await this.userService.obtenerSolicitudes(this.idUsuario)
  }

  async eliminarFriendRequest(amigo: number) {
    await this.userService.eliminarFriendRequestAmigo(this.idUsuario, amigo+"")
    this.amigos = await this.userService.obtenerAmigosPorUsuario(this.idUsuario)
    this.amigosSolicitudes = await this.userService.obtenerSolicitudes(this.idUsuario)
  }

  async agreagarFirendRequest(amigo: number) {
    await this.userService.agregarFriendRequest(this.idUsuario, amigo+"")
    this.amigos = await this.userService.obtenerAmigosPorUsuario(this.idUsuario)
    this.amigosSolicitudes = await this.userService.obtenerSolicitudes(this.idUsuario)
  }

  async eliminarAmigo(amigo: number) {
    await this.userService.eliminarAmigo(this.idUsuario, amigo)
    this.amigos = await this.userService.obtenerAmigosPorUsuario(this.idUsuario)
    alert("Amigo eliminado")
  }

  async enviarSolicitudAmistad(amigo: number) {
    await this.userService.enviarSolcitudAmistad(this.idUsuario, amigo+"")
    this.amigosSolicitudes = await this.userService.obtenerSolicitudes(this.idUsuario)
    alert("Solicitud enviada")
  }


}
