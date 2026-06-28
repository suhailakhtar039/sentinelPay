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

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, MatButtonModule, RouterLink, MatCardModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  email!: string | null;
  role!: string | null;

  wallet?: WalletResponse;
  payments: PaymentResponse[] = [];
  transactions: LedgerResponse[] = [];

  // wallet balance variable
  walletBalance?: number;

  // total payments
  totalPayments?: number;

  // total successfull transaction
  successfulTransaction?: number;

  // total failed transaction
  failedTransaction?: number;

  constructor(
    private tokenService: TokenStorageService,
    private router: Router,
    private walletService: WalletService,
    private ledgerService: LedgerService,
    private paymentService: PaymentService,
    private cdf: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.email = this.tokenService.getEmail();
    this.role = this.tokenService.getRole();

    forkJoin({
      wallet: this.walletService.getMyWallet(),
      payments: this.paymentService.getMyPayments(),
      ledger: this.ledgerService.getMyTransactions(),
    }).subscribe({
      next: (result: any) => {
        ((this.wallet = result.wallet.data),
          (this.payments = result.payments.data),
          (this.transactions = result.ledger.data));
        // Total wallet balance
        this.walletBalance = Number(this.wallet?.balance);

        // Total Payments
        this.totalPayments = Number(this.payments.length);

        // succesful payments
        this.successfulTransaction = this.payments.filter(
          (payment) => payment.status === PaymentStatus.COMPLETED,
        ).length;

        // failed payments
        this.failedTransaction = this.payments.filter(
          (payment) =>
            payment.status === PaymentStatus.FAILED ||
            payment.status === PaymentStatus.FRAUD_REJECTED,
        ).length;

        this.cdf.detectChanges();
      },
      error: (err: any) => {
        console.log(err);
      },
    });
  }

  logout() {
    this.tokenService.logout();
    this.router.navigate(['/login']);
  }
}
