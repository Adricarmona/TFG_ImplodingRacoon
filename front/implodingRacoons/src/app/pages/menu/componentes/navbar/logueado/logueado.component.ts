import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../../service/auth.service';
import { UsersService } from '../../../../../service/users.service';
import { UsuarioAmigo } from '../../../../../models/usuario-amigo';
import { UsuariosSimple } from '../../../../../models/usuarios-simple';

@Component({
  selector: 'app-logueado',
  standalone: true,
  imports: [],
  templateUrl: './logueado.component.html',
  styleUrl: './logueado.component.css'
})
export class LogueadoComponent implements OnInit {
  private idUsuario: string = ""
  public usuariosConectados: number = 0

  public usuarioSimple: UsuariosSimple = {
    cantidadAmigos: 0,
    id: 0,
    nombreUsuario: "",
    urlFoto: ""
  }

  constructor(
    private authService: AuthService,
    private userService: UsersService
  ) {}

  
  async ngOnInit() {
    this.idUsuario = this.authService.cogerIdJwt()
    this.usuarioSimple = await this.userService.obtenerUsuarioPorId(this.idUsuario)
  }
}
