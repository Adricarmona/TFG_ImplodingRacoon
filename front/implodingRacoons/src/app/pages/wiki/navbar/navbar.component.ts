import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  
  idUsuario: string = ""

  constructor(
    private authService: AuthService,
  ) {}

  ngOnInit() {
    this.idUsuario = this.authService.cogerIdJwt()
  }
}
