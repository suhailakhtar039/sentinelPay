import { DashboardAnalytics } from './dashboard-analytics.model';
import { PaymentResponse } from '../../payment/model/payment-response';

export interface DashboardSummary {
  analytics: DashboardAnalytics;

  recentPayments: PaymentResponse[];
}
