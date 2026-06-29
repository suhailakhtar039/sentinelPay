import { LedgerResponse } from '../../../shared/models/ledger-response';
import { WalletResponse } from '../../../shared/models/wallet-response';
import { PaymentResponse } from '../../payment/model/payment-response';

export interface DashboardSummary {
  walletBalance: number;
  totalPayments: number;
  successfulPayments: number;
  failedPayments: number;
  wallet: WalletResponse;
  payments: PaymentResponse[];
  transactions: LedgerResponse[];
}
