import { ChangeDetectorRef, Component } from '@angular/core';
import { TokenStorageService } from '../../core/services/token-storage';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterLink } from '@angular/router';
import { WalletResponse } from '../../shared/models/wallet-response';
import { PaymentResponse } from '../payment/model/payment-response';
import { LedgerResponse } from '../../shared/models/ledger-response';
import { forkJoin } from 'rxjs';
import { WalletService } from '../../core/services/wallet-service';
import { LedgerService } from '../../core/services/ledger-service';
import { PaymentService } from '../payment/services/payment-service';
import { PaymentStatus } from '../payment/model/payment-status';
import { MatCardModule } from '@angular/material/card';
import { DashboardService } from './service/DashboardService';
import { DashboardSummary } from './model/dashboard-summary';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, MatButtonModule, RouterLink, MatCardModule, MatProgressSpinnerModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  email!: string | null;
  role!: string | null;

  loading = true;
  errorMessage = '';
  summary?: DashboardSummary;

  constructor(
    private tokenService: TokenStorageService,
    private router: Router,
    private dashboardService: DashboardService,
    private cdf: ChangeDetectorRef,
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
        this.cdf.detectChanges();
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Unable to load dashboard. Please try again';
        this.cdf.detectChanges();
      },
    });
  }

  logout() {
    this.tokenService.logout();
    this.router.navigate(['/login']);
  }
}
