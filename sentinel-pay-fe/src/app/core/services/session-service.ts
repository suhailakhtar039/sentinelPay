import { inject, Injectable } from '@angular/core';
import { TokenStorageService } from './token-storage';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private readonly tokenStorage = inject(TokenStorageService);

  private readonly router = inject(Router);

  private readonly snackBar = inject(MatSnackBar);

  private sessionExpiredHandled = false;

  logout() {
    if (this.sessionExpiredHandled) {
      return;
    }

    this.sessionExpiredHandled = true;

    this.tokenStorage.logout();

    this.snackBar.open('Your session has expired. Please login again', 'Close', {
      duration: 4000,
    });

    this.router.navigate(['/login']);
  }
}
