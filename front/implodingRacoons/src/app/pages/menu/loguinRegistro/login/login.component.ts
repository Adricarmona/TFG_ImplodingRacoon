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
import { FooterComponent } from "../../componentes/footer/footer.component";
import { Router, RouterModule } from '@angular/router';
import { LogueadoComponent } from '../../componentes/navbar/logueado/logueado.component';
import { SinLoguearComponent } from '../../componentes/navbar/sin-loguear/sin-loguear.component';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, LogueadoComponent, SinLoguearComponent, FooterComponent, RouterModule, NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  form: FormGroup;
  remember: boolean = false;
  logueado: boolean = false;
  errorMsg: string | null = null;

  constructor(
    private authService: AuthService,
    public fb: FormBuilder,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required]],
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
      this.errorMsg = null
      await this.authService.login(authData);
      await this.router.navigateByUrl('');
    } catch (error) {
      this.errorMsg = 'Usuario o contraseña incorrecta';
      console.error('Error en login:', error);
    }
  }
}
