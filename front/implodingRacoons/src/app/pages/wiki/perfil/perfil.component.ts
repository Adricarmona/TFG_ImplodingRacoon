import { Component } from '@angular/core';
import { NavbarComponent } from "../navbar/navbar.component";
import { UsersService } from '../../../service/users.service';
import { AuthService } from '../../../service/auth.service';
import { UsuariosSimple } from '../../../models/usuarios-simple';
import { UsuarioAmigo } from '../../../models/usuario-amigo';
import { PublicacionTarjeta } from '../../../models/publicacion-tarjeta';
import { WikiService } from '../../../service/wiki.service';
import { RouterModule } from '@angular/router';
import { DatePipe } from '@angular/common';
import { AuthResponse } from '../../../models/auth-response';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [NavbarComponent, RouterModule, DatePipe, FormsModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent {

  modalModificacionFoto: boolean = false
  modalMoificacionUsuario: boolean = false

  idUsuario: string = ""

  datosUsuario: UsuariosSimple = {
    id: 0,
    nombreUsuario: "",
    urlFoto: "",
    cantidadAmigos: 0
  }

  amigos: UsuarioAmigo[] = null

  postHechos: PublicacionTarjeta[] = null

  foto: File
  nuevoNombre: string = ""

  antiguaContrasenia: string = ""
  nuevaContrasena: string = ""

  confirmadoUsuarioEliminar: boolean = false

  constructor(
    private usersService: UsersService,
    private authService: AuthService,
    private wikiService: WikiService
  ) {}

  async ngOnInit() {
    this.idUsuario = this.authService.cogerIdJwt()
    
    try {
      this.datosUsuario = await this.usersService.obtenerUsuarioPorId(this.idUsuario)
      this.amigos = await this.usersService.obtenerAmigosPorUsuario(this.idUsuario)
      this.postHechos = await this.wikiService.cogerPostPorUsuarioConcreto(this.idUsuario)
    } catch (error) {
      console.log(error)
    }
  }

  async eliminarAmigo(idAmigo: number) {
    
    try {
      const recibido = await this.usersService.eliminarAmigo(this.idUsuario, idAmigo)
      if (recibido.code == 200) {
        alert("Usuario eliminado")
        this.amigos = await this.usersService.obtenerAmigosPorUsuario(this.idUsuario)
      } else {
        alert("Error al eliminar amigo")
      }

    } catch (error) {
      console.log(error)
    }

  }

  activarModalCambioIcono() {
    this.modalModificacionFoto == true ? this.modalModificacionFoto = false : this.modalModificacionFoto = true
  }

  activarModalCambioDatos() {
    this.modalMoificacionUsuario == true ? this.modalMoificacionUsuario = false : this.modalMoificacionUsuario = true
    this.confirmadoUsuarioEliminar = false
  }

  onFileSelected(event: any) {
    const image = event.target.files[0] as File; 
    this.foto = image
  }

  async actualizarFoto() {

    if (this.foto != null) {
      const result: AuthResponse = await this.usersService.actualizarFoto(this.foto, this.idUsuario)
      if (result != null || result.code != 200) {
        this.datosUsuario = await this.usersService.obtenerUsuarioPorId(this.idUsuario)
        this.activarModalCambioIcono()
      } else {
        console.log("Error")
      }

    } else {
      console.log("lafotoEsNulkl")
    }
  }

  confirmarEliminar() {
    this.confirmadoUsuarioEliminar = true
  }

  async actualizarNombre() {
    const resultado = await this.usersService.cambiarNombre(this.nuevoNombre , this.idUsuario)
      if (resultado != null) {
        this.datosUsuario = await this.usersService.obtenerUsuarioPorId(this.idUsuario)
        this.activarModalCambioDatos()
        this.nuevoNombre = ""
      } else {
        console.log("Error")
      }
  }

  async cambiarContrasenia() {
    const resultado = await this.usersService.cambiarContrasenia(this.antiguaContrasenia,this.nuevaContrasena,this.idUsuario)

    if (resultado != null &&resultado.code == 200) {
      alert("Se ha cambiado de contraseña correctamente")
      this.antiguaContrasenia = ""
      this.nuevaContrasena = ""
      this.activarModalCambioDatos()
    } else {
      alert("Error al cambiar la contraseña")
    }
  }

  async eliminarUsuario() {
    const resultado = await this.usersService.eliminarUsuario(this.idUsuario)

    if (resultado != null &&resultado.code == 200) {
      alert("Se ha eliminado su usuario")
      this.authService.eliminarJwtSessionYLocalStorage()
      window.location.reload()
    } else {
      alert("Error al eliminar el usuario")
    }

  }
}
