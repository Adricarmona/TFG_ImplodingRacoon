import { Component } from '@angular/core';
import { FooterComponent } from "../../componentes/footer/footer.component";
import { AuthService } from '../../../../service/auth.service';
import { LogueadoComponent } from '../../componentes/navbar/logueado/logueado.component';
import { SinLoguearComponent } from '../../componentes/navbar/sin-loguear/sin-loguear.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthRegister } from '../../../../models/auth-register';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, LogueadoComponent, SinLoguearComponent, FooterComponent, NgIf],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {

  form: FormGroup;
  logueado: boolean = false;
  remember: boolean = false;

  constructor(
    private authService: AuthService,
    public fb: FormBuilder,
    private router: Router
  ) {
    this.form = this.fb.group({
      nombreUsuario: ['', [Validators.required, Validators.minLength(4)]],
      correo: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      password2: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  ngOnInit(): void {
    if (this.authService.jwt != "" && this.authService.jwt != null) {
      this.logueado = true;
    }
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const password2 = form.get('password2')?.value;
    return password === password2 ? null : { passwordMismatch: true };
  }

  async submit() {
    if (this.form.invalid) return;

    const register: AuthRegister = {
      nombreUsuario: this.form.get('nombreUsuario')?.value,
      correo: this.form.get('correo')?.value,
      password: this.form.get('password')?.value,
      remember: this.remember
    };

    await this.authService.register(register);
    await this.router.navigateByUrl('');
  }
}
