import { Injectable } from '@angular/core';
import { forkJoin, map, Observable } from 'rxjs';

import { AnalyticsService } from './analytics-service';
import { PaymentService } from '../../payment/services/payment-service';

import { DashboardSummary } from '../model/dashboard-summary';
import { DashboardAnalytics } from '../model/dashboard-analytics.model';
import { PaymentResponse } from '../../payment/model/payment-response';

interface DashboardApiResponse {
  analytics: DashboardAnalytics;

  payments: PaymentResponse[];
}

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  constructor(
    private analyticsService: AnalyticsService,
    private paymentService: PaymentService,
  ) {}

  getDashboardSummary(): Observable<DashboardSummary> {
    return forkJoin({
      analytics: this.analyticsService.getDashboardAnalytics(),

      payments: this.paymentService.getMyPayments(),
    }).pipe(
      map((response) => {
        const recentPayments = [...response.payments.data]
          .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
          .slice(0, 5);

        return {
          analytics: response.analytics,

          recentPayments,
        };
      }),
    );
  }
}
