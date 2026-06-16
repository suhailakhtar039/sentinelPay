import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterLink } from '@angular/router';
import { TokenStorageService } from '../../core/services/token-storage';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-navbar',
  imports: [
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    RouterLink,
    MatButtonModule,
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  email!: string | null;
  constructor(
    private tokenService: TokenStorageService,
    private authService: AuthService,
    private router: Router,
  ) {}
  ngOnInit() {
    this.email = this.tokenService.getEmail();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
