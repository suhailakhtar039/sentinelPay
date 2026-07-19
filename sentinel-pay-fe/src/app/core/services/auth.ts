import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { TokenStorageService } from './token-storage';
import { LoginRequest } from '../../shared/models/login-request';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../../shared/models/login-response';
import { API_CONFIG } from '../config/api.config';
import { RegisterRequest } from '../../shared/models/register-request';
import { RegisterResponse } from '../../shared/models/register-response';
import { ApiResponse } from '../../shared/models/api-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly tokenStorage = inject(TokenStorageService);

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${API_CONFIG.auth}/login`, request).pipe(
      tap((response) => {
        this.tokenStorage.saveAuth(response);
      }),
    );
  }

  register(request: RegisterRequest): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${API_CONFIG.auth}/register`, request);
  }

  logout(): Observable<ApiResponse<string>> {
    return this.http.post<ApiResponse<string>>(`${API_CONFIG.auth}/logout`, {});
  }

  isLoggedIn(): boolean {
    return this.tokenStorage.isLoggedIn();
  }
}
