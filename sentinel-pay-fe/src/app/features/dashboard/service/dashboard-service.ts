import { Injectable } from '@angular/core';
import { forkJoin, map, Observable } from 'rxjs';

import { AnalyticsService } from './analytics-service';
import { PaymentService } from '../../payment/services/payment-service';

import { DashboardSummary } from '../model/dashboard-summary';
import { DashboardAnalytics } from '../model/dashboard-analytics.model';
import { PaymentResponse } from '../../payment/model/payment-response';
import { ApiResponse } from '../../../shared/models/api-response';
import { WalletService } from '../../../core/services/wallet-service';

interface DashboardApiResponse {
  analytics: DashboardAnalytics;

  payments: ApiResponse<PaymentResponse[]>;
}

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  constructor(
    private analyticsService: AnalyticsService,
    private paymentService: PaymentService,
    private walletService: WalletService,
  ) {}

  getDashboardSummary(): Observable<DashboardSummary> {
    return forkJoin({
      analytics: this.analyticsService.getDashboardAnalytics(),
      wallet: this.walletService.getMyWallet(),
      payments: this.paymentService.getMyPayments(),
    }).pipe(
      map((response) => {
        const recentPayments = [...response.payments.data]
          .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
          .slice(0, 5);

        return {
          analytics: response.analytics,
          wallet: response.wallet.data,
          recentPayments,
        };
      }),
    );
  }
}
