import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthRequest } from '../../../models/auth-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  form: FormGroup;

  async submit(){
    const authData: AuthRequest = { 
      email: this.form.get('email')?.value, 
      password: this.form.get('password')?.value,
      remember: this.remember
    };
  }

}
