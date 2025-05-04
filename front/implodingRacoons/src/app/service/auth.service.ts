import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ApiService } from './api.service';
import { AuthRequest } from '../models/auth-request';
import { AuthResponse } from '../models/auth-response';
import { lastValueFrom, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private BASE_URL = `${environment.apiUrl}`;

  constructor(
    private http: HttpClient, 
    private apiService: ApiService
  ) { }

  jwt: string = '';

  async login(data: AuthRequest): Promise<AuthResponse | null> {
    try {
      const request: Observable<AuthResponse> = this.http.post<AuthResponse>(`${this.BASE_URL}Auth/login`, data);
      const result: AuthResponse = await lastValueFrom(request);

      this.apiService.jwt = result.token;

      this.jwt = result.token;
      if (data.remember) {
        localStorage.setItem('token', this.jwt);
      } else {
        sessionStorage.setItem('token', this.jwt)
      }

      return result;
    } catch (error)
    {
      console.error('Error logging in');
      return null;
    }
  } 
}
