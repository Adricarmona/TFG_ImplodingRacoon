import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { AuthRequest } from '../models/auth-request';
import { AuthResponse } from '../models/auth-response';
import { Result } from '../models/result';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private apiService: ApiService
  ) { }

  jwt: string = '';

  async login(data: AuthRequest): Promise<AuthResponse> {
    try {
      const result: Result<AuthResponse> = await this.apiService.post<AuthResponse>(`Auth/Login`, data);
      const request: AuthResponse = result.data;


      this.apiService.jwt = request.message;
      this.jwt = this.apiService.jwt;

      if (data.remember) {
        localStorage.setItem('token', this.jwt);
      } else {
        sessionStorage.setItem('token', this.jwt)
      }

      return request;
    } catch (error)
    {
      console.error('Error logging in');
      return null;
    }
  } 

  cogerSessionStorageYLocalStorage() {
    const local = localStorage.getItem('token')
    const session = sessionStorage.getItem('token')

    if (local != "" && local != null || session != "" && session != null) {
      
      if (local != "" && local != null) {
        return local
      }
      else if(session != "" && session != null) {
        return session
      }
      else {
        return ""
      }

    } else {
      return ""
    }
  }
    
  cogerIdJwt() {
    this.jwt = this.cogerSessionStorageYLocalStorage()
    if (this.jwt == "") {
      return 1
    }
    const decodeadoJwt: any = jwtDecode(this.jwt)
    return decodeadoJwt.id
  }

  existeUsuario() {
    this.jwt = this.cogerSessionStorageYLocalStorage()
    const decodeadoJwt: any = jwtDecode(this.jwt)

    if (decodeadoJwt == "" || decodeadoJwt == null) {
      return false
    } else {
      return true
    }

  }
}
