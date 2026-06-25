import { Component } from '@angular/core';
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

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, MatButtonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  email!: string | null;
  role!: string | null;

  wallet?: WalletResponse;
  payments: PaymentResponse[] = [];
  transactions: LedgerResponse[] = [];

  constructor(
    private tokenService: TokenStorageService,
    private router: Router,
    private walletService: WalletService,
    private ledgerService: LedgerService,
    private paymentService: PaymentService,
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
