import { Component } from '@angular/core';
import { TokenStorageService } from '../../core/services/token-storage';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { DashboardService } from './service/dashboard-service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { DashboardSummary } from './model/dashboard-summary';
import { MatIconModule } from '@angular/material/icon';
import { AnalyticsChartsComponent } from './components/analytics-charts-component/analytics-charts-component';

@Component({
  selector: 'app-dashboard',
  imports: [
    CommonModule,
    MatButtonModule,
    RouterLink,
    MatCardModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatChipsModule,
    MatIconModule,
    AnalyticsChartsComponent,
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  email!: string | null;
  role!: string | null;

  loading = true;
  errorMessage = '';
  summary?: DashboardSummary;

  displayedColumns: string[] = ['receiver', 'amount', 'status', 'date'];

  constructor(
    private tokenService: TokenStorageService,
    private dashboardService: DashboardService,
  ) {}

  ngOnInit() {
    this.email = this.tokenService.getEmail();
    this.role = this.tokenService.getRole();

    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;

    this.dashboardService.getDashboardSummary().subscribe({
      next: (summary) => {
        this.summary = summary;

        this.loading = false;
      },

      error: () => {
        this.loading = false;

        this.errorMessage = 'Unable to load dashboard. Please try again';
      },
    });
  }
}
