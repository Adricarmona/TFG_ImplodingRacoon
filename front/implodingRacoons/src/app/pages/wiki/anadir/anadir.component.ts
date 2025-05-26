import { Component } from '@angular/core';
import { NavbarComponent } from "../navbar/navbar.component";
import { WikiService } from '../../../service/wiki.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/auth.service';
import { PublicarPublicacion } from '../../../models/publicar-publicacion';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-anadir',
  standalone: true,
  imports: [NavbarComponent, FormsModule],
  templateUrl: './anadir.component.html',
  styleUrl: './anadir.component.css'
})
export class AnadirComponent {

  titulo: string = ""
  descripcion: string = ""

  errorEnviar: boolean = false

  constructor(
    private wikiService: WikiService,
    private router: Router, 
    private authService: AuthService
  ){}

  async subirPost() {
    if (this.titulo != "" && this.descripcion != "") {

      const publicarPublicacion: PublicarPublicacion = {
        titulo: this.titulo,
        descripcion: this.descripcion,
        fecha: new Date,
        usuarioId: parseInt(this.authService.cogerIdJwt())
      }

      try {
        await this.wikiService.puiblicarPost(publicarPublicacion)
        
        await this.router.navigateByUrl('/wiki')

      } catch (error) {
        console.log(error)
        this.errorEnviar = true
      }
    }

    this.errorEnviar = true
  }
}
