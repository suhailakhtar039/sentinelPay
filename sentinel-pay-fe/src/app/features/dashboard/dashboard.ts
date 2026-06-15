import { Component } from '@angular/core';
import { TokenStorageService } from '../../core/services/token-storage';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, MatButtonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  email!: string | null;
  role!: string | null;

  constructor(
    private tokenService: TokenStorageService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.email = this.tokenService.getEmail();
    this.role = this.tokenService.getRole();
  }

  logout() {
    this.tokenService.logout();
    this.router.navigate(['/login']);
  }
}
