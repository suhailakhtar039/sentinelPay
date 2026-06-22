import { Route } from '@angular/router';

import { LoginComponent } from './features/auth/login-component/login-component';
import { authGuard } from './core/guards/auth-guard';
import { MainLayout } from './features/main-layout/main-layout';

export const routes: Route[] = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },

  {
    path: 'login',
    component: LoginComponent,
  },

  {
    path: 'register',
    loadComponent: () => import('./features/register/register').then((m) => m.Register),
  },

  {
    path: '',
    component: MainLayout,
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard').then((m) => m.Dashboard),
      },

      {
        path: 'ledger',
        loadComponent: () => import('./features/ledger/ledger').then((m) => m.Ledger),
      },
    ],
  },

  {
    path: '**',
    redirectTo: 'login',
  },
];
