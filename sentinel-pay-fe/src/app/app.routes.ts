import { Route } from '@angular/router';
import { LoginComponent } from './features/auth/login-component/login-component';
import { authGuard } from './core/guards/auth-guard';

export const routes: Route[] = [
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () => import('./features/dashboard/dashboard').then((m) => m.Dashboard),
  },
  {
    path: 'register',
    loadComponent: () => import('./features/register/register').then((m) => m.Register),
  },
  {
    path: '**',
    redirectTo: '',
  },
];
