import { LedgerResponse } from '../../ledger/model/ledger-response';
import { WalletResponse } from '../../../shared/models/wallet-response';
import { PaymentResponse } from '../../payment/model/payment-response';

export interface DashboardSummary {
  wallet: WalletResponse;

  payments: PaymentResponse[];

  transactions: LedgerResponse[];

  recentPayments: PaymentResponse[];

  walletBalance: number;

  totalPayments: number;

  successfulPayments: number;

  failedPayments: number;
}
