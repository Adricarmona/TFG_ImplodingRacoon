import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthRequest } from '../../../models/auth-request';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  form: FormGroup;

  constructor(
    private authService: AuthService, 
    public fb: FormBuilder
  )
  {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  remember: boolean = false;

  async submit(){
    const authData: AuthRequest = { 
      email: this.form.get('email')?.value, 
      password: this.form.get('password')?.value,
      remember: this.remember
    };

    this.authService.login(authData);
  }

}
