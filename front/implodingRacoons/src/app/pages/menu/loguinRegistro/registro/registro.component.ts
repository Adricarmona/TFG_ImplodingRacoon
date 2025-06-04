import { Component } from '@angular/core';
import { FooterComponent } from "../../componentes/footer/footer.component";
import { AuthService } from '../../../../service/auth.service';
import { LogueadoComponent } from '../../componentes/navbar/logueado/logueado.component';
import { SinLoguearComponent } from '../../componentes/navbar/sin-loguear/sin-loguear.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthRegister } from '../../../../models/auth-register';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule,LogueadoComponent, SinLoguearComponent, FooterComponent],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {

  form: FormGroup;

  logueado: boolean = false;

  remember: boolean = false;

  correctPassword: boolean = true;

    constructor(
      private authService: AuthService,
      public fb: FormBuilder,
      private router: Router
    ) {
      this.form = this.fb.group({
      nombreUsuario: ['', [Validators.required]],
      correo: ['', [Validators.email, Validators.required]],
      password: ['', [Validators.required]],
      password2: ['', [Validators.required]]
    });
    }

    ngOnInit(): void {
    if(this.authService.jwt != "" && this.authService.jwt != null) {
      this.logueado = true
    }
  }

  async submit(){
    if (this.form.get('password')?.value != this.form.get('password2')?.value) {
      this.correctPassword = false;
      return;
    }

    const register: AuthRegister= {
      nombreUsuario: this.form.get('nombreUsuario')?.value,
      correo: this.form.get('correo')?.value,
      password: this.form.get('password')?.value,
      remember: this.remember
    };
    await this.authService.register(register);

    await this.router.navigateByUrl('')
  }

}
