import { Injectable } from '@angular/core';
import { LoginResponse } from '../../shared/models/login-response';

@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  private readonly TOKEN_KEY = 'sp_token';
  private readonly USER_KEY = 'sp_user';

  saveAuth(response: LoginResponse): void {
    localStorage.setItem(this.TOKEN_KEY, response.token);
    localStorage.setItem(
      this.USER_KEY,
      JSON.stringify({ email: response.email, userId: response.userId, role: response.role }),
    );
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
  }

  getUserId(): string | null {
    const user = this.getUser();
    return user?.userId ?? null;
  }

  getEmail(): string | null {
    const user = this.getUser();
    return user?.email ?? null;
  }

  getRole(): string | null {
    const user = this.getUser();
    return user?.role ?? null;
  }

  private getUser(): {
    userId: string;
    email: string;
    role: string;
  } | null {
    const user = localStorage.getItem(this.USER_KEY);

    if (!user) {
      return null;
    }

    try {
      return JSON.parse(user);
    } catch {
      return null;
    }
  }
}
