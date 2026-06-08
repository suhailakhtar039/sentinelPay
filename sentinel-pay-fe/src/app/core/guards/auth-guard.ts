import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenStorageService } from '../services/token-storage';

export const authGuard: CanActivateFn = (route, state) => {
  const tokenStorageService = inject(TokenStorageService);
  const router = inject(Router);

  if (tokenStorageService.isLoggedIn()) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
