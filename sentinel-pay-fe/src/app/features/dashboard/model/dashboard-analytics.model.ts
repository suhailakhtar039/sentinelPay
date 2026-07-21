import { AverageTransaction } from './average-amount.model';
import { DailyTransaction } from './daily-transaction.model';
import { MonthlyVolume } from './monthly-volume.model';
import { OverviewAnalytics } from './overview-analytics.model';
import { PaymentStatus } from './payment-status.model';
import { TopReceiver } from './top-receiver.model';

export interface DashboardAnalytics {
  overview: OverviewAnalytics;

  averageTransaction: AverageTransaction;

  monthlyVolume: MonthlyVolume[];

  dailyTransactions: DailyTransaction[];

  paymentStatusDistribution: PaymentStatus[];

  topReceivers: TopReceiver[];
}
