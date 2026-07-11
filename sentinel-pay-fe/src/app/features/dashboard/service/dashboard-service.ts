import { ChangeDetectorRef, Injectable } from '@angular/core';
import { WalletService } from '../../../core/services/wallet-service';
import { LedgerService } from '../../ledger/service/ledger-service';
import { PaymentService } from '../../payment/services/payment-service';
import { forkJoin, map, Observable } from 'rxjs';
import { WalletResponse } from '../../../shared/models/wallet-response';
import { LedgerResponse } from '../../ledger/model/ledger-response';
import { PaymentResponse } from '../../payment/model/payment-response';
import { PaymentStatus } from '../../payment/model/payment-status';
import { ApiResponse } from '../../../shared/models/api-response';
import { DashboardSummary } from '../model/dashboard-summary';

interface DashboardApiResponse {
  wallet: ApiResponse<WalletResponse>;
  payments: ApiResponse<PaymentResponse[]>;
  ledger: ApiResponse<LedgerResponse[]>;
}

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  constructor(
    private walletService: WalletService,
    private ledgerService: LedgerService,
    private paymentService: PaymentService,
  ) {}

  getDashboardSummary(): Observable<DashboardSummary> {
    return forkJoin({
      wallet: this.walletService.getMyWallet(),
      payments: this.paymentService.getMyPayments(),
      ledger: this.ledgerService.getMyTransactions(),
    }).pipe(
      map((result: DashboardApiResponse) => {
        const wallet = result.wallet.data;
        const payments = result.payments.data;
        const transactions = result.ledger.data;

        const recentPayments = [...payments]
          .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
          .slice(0, 5);

        return {
          wallet,
          payments,
          transactions,
          recentPayments,

          walletBalance: Number(wallet.balance),

          totalPayments: payments.length,

          successfulPayments: payments.filter(
            (payment) => payment.status === PaymentStatus.COMPLETED,
          ).length,

          failedPayments: payments.filter(
            (payment) =>
              payment.status === PaymentStatus.FAILED ||
              payment.status === PaymentStatus.FRAUD_REJECTED,
          ).length,
        };
      }),
    );
  }
}
