import { DashboardAnalytics } from './dashboard-analytics.model';
import { PaymentResponse } from '../../payment/model/payment-response';
import { WalletResponse } from '../../../shared/models/wallet-response';

export interface DashboardSummary {
  analytics: DashboardAnalytics;

  recentPayments: PaymentResponse[];

  wallet: WalletResponse;
}
