import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../core/services/token-storage';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../core/services/auth';
import { SessionService } from '../../core/services/session-service';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, MatToolbarModule, MatIconModule, MatMenuModule, MatButtonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  email!: string | null;
  constructor(
    private tokenService: TokenStorageService,
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService,
  ) {}
  ngOnInit() {
    this.email = this.tokenService.getEmail();
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => {
        this.sessionService.logout();
      },
      error: () => {
        // Even if logout fails (e.g. token already expired),
        // clear local session so the user is logged out locally.
        this.sessionService.logout();
      },
    });
    this.router.navigate(['/login']);
  }
}
