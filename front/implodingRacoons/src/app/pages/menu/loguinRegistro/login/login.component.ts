import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../../../service/auth.service';
import { AuthRequest } from '../../../../models/auth-request';
<<<<<<< Updated upstream
import { FooterComponent } from "../../componentes/footer/footer.component";
import { Router, RouterModule } from '@angular/router';
=======
import { FooterComponent } from '../../componentes/footer/footer.component';
import { Router } from '@angular/router';
>>>>>>> Stashed changes
import { LogueadoComponent } from '../../componentes/navbar/logueado/logueado.component';
import { SinLoguearComponent } from '../../componentes/navbar/sin-loguear/sin-loguear.component';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
<<<<<<< Updated upstream
  imports: [FormsModule, ReactiveFormsModule, LogueadoComponent, SinLoguearComponent, FooterComponent, RouterModule],
=======
  imports: [
    FormsModule,
    ReactiveFormsModule,
    LogueadoComponent,
    SinLoguearComponent,
    FooterComponent,
    NgIf
  ],
>>>>>>> Stashed changes
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  form: FormGroup;
  remember: boolean = false;
  logueado: boolean = false;

  constructor(
    private authService: AuthService,
    public fb: FormBuilder,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    if (this.authService.jwt != '' && this.authService.jwt != null) {
      this.logueado = true;
    }
  }

  async submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const authData: AuthRequest = {
      emailOrUser: this.form.get('email')?.value,
      password: this.form.get('password')?.value,
      remember: this.remember,
    };

    try {
      await this.authService.login(authData);
      await this.router.navigateByUrl('');
    } catch (error) {
      console.error('Error en login:', error);
    }
  }
}
