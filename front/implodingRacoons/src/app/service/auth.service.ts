import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ApiService } from './api.service';
import { AuthRequest } from '../models/auth-request';
import { AuthResponse } from '../models/auth-response';
import { lastValueFrom, Observable } from 'rxjs';
import { Result } from '../models/result';

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
    
}
