import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../../service/auth.service';
import { UsersService } from '../../../../../service/users.service';
import { UsuariosSimple } from '../../../../../models/usuarios-simple';
import { MenuService } from '../../../../../service/menu.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-logueado',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './logueado.component.html',
  styleUrl: './logueado.component.css'
})
export class LogueadoComponent implements OnInit {
  public idUsuario: string = ""
  public usuariosConectados: number = 0

  public clicado: boolean = false

  public usuarioSimple: UsuariosSimple = {
    cantidadAmigos: 0,
    id: 0,
    nombreUsuario: "",
    urlFoto: ""
  }

  constructor(
    private authService: AuthService,
    private userService: UsersService,
    public menusoket: MenuService
  ) {}

  
  async ngOnInit() {
    this.idUsuario = this.authService.cogerIdJwt()
    this.usuarioSimple = await this.userService.obtenerUsuarioPorId(this.idUsuario)
  }

  desLoguearse() {
    this.authService.eliminarJwtSessionYLocalStorage()
    window.location.reload()
  }

  clicadoMenu() {
    this.clicado = !this.clicado
  }
}
