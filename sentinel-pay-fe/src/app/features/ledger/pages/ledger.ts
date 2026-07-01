import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { LedgerResponse } from '../model/ledger-response';
import { LedgerService } from '../service/ledger-service';
import { MatSort, MatSortModule, Sort } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-ledger',
  imports: [
    CommonModule,
    MatCardModule,
    MatTableModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatSortModule,
    MatPaginatorModule,
  ],
  templateUrl: './ledger.html',
  styleUrl: './ledger.css',
})
export class Ledger {
  // transactions: LedgerResponse[] = [];
  dataSource = new MatTableDataSource<LedgerResponse>([]);

  @ViewChild(MatSort)
  sort!: MatSort;

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

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
    this.loading = true;

    this.errorMessage = '';

    this.ledgerService.getMyTransactions().subscribe({
      next: (response) => {
        this.dataSource.data = response.data;

        this.loading = false;

        this.cdf.detectChanges();

        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
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
