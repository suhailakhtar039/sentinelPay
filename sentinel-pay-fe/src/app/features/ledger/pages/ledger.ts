import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { LedgerResponse } from '../model/ledger-response';
import { LedgerService } from '../service/ledger-service';

@Component({
  selector: 'app-ledger',
  imports: [CommonModule, MatCardModule, MatTableModule, MatChipsModule, MatProgressSpinnerModule],
  templateUrl: './ledger.html',
  styleUrl: './ledger.css',
})
export class Ledger {
  transactions: LedgerResponse[] = [];

  loading = true;

  errorMessage = '';

  displayedColumns = ['paymentId', 'type', 'amount', 'status', 'time'];

  constructor(
    private ledgerService: LedgerService,
    private cdf: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.loadTransaction();
  }

  loadTransaction(): void {
    this.loading = false;
    this.ledgerService.getMyTransactions().subscribe({
      next: (response) => {
        this.transactions = response.data;
        this.loading = false;
        this.cdf.detectChanges();
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Unable to load transactions.';
        this.cdf.detectChanges();
      },
    });
  }

  getTransactionType(transaction: LedgerResponse): string {
    // Temporary implementation
    // We'll improve this later when the backend provides transaction type.
    return 'Transfer';
  }
}
