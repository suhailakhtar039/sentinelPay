import { Route } from '@angular/router';
import { LoginComponent } from './features/auth/login-component/login-component';
import { Dashboard } from './features/dashboard/dashboard';

export const routes: Route[] = [
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: Dashboard,
  },
  {
    path: '**',
    component: LoginComponent,
  },
];
