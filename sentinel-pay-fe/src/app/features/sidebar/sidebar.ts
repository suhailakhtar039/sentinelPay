import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { AuthService } from '../../core/services/auth';
import { SessionService } from '../../core/services/session-service';
import { SidebarService } from '../../core/services/sidebar-service';

@Component({
  selector: 'app-sidebar',
  imports: [
    CommonModule,
    MatSidenavModule,
    MatIconModule,
    MatToolbarModule,
    RouterModule,
    MatListModule,
    RouterOutlet,
  ],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  @ViewChild('drawer')
  drawer!: MatSidenav;

  constructor(
    private authService: AuthService,
    private sessionService: SessionService,
    private router: Router,
    private sidebarService: SidebarService,
  ) {}

  logout(): void {
    this.authService.logout().subscribe({
      next: () => this.sessionService.logout(),
      error: () => this.sessionService.logout(),
    });

    this.router.navigate(['/login']);
  }

  ngAfterViewInit(): void {
    this.sidebarService.toggle$.subscribe(() => {
      this.drawer.toggle();
    });
  }
}
