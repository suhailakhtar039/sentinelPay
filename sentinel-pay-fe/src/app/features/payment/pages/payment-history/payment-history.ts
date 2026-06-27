import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { PaymentService } from '../../services/payment-service';
import { PaymentResponse } from '../../model/payment-response';
import { MatCardModule } from '@angular/material/card';
import { PaymentStatus } from '../../model/payment-status';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-payment-history',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatChipsModule, MatProgressSpinnerModule],
  templateUrl: './payment-history.html',
  styleUrl: './payment-history.css',
})
export class PaymentHistory implements OnInit {
  private readonly paymentService = inject(PaymentService);
  private readonly cdf = inject(ChangeDetectorRef);

  dataSource = new MatTableDataSource<PaymentResponse>();

  displayedColumns: string[] = [
    'paymentId',
    'receiverUserId',
    'amount',
    'status',
    'failureReason',
    'createdAt',
  ];

  loading = true;

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments(): void {
    this.loading = true;

    this.paymentService
      .getMyPayments()
      .pipe(
        finalize(() => {
          console.log('Finalize called');
          this.loading = false;
          this.cdf.detectChanges();
        }),
      )
      .subscribe({
        next: (response) => {
          console.log('Success', response);
          this.dataSource.data = response.data ?? [];
          this.loading = false;
        },
        error: (error) => {
          this.loading = false;
          console.error('Error', error);
        },
      });
  }

  getChipColor(status: PaymentStatus): 'primary' | 'accent' | 'warn' {
    switch (status) {
      case PaymentStatus.COMPLETED:
        return 'primary';

      case PaymentStatus.PENDING:
        return 'accent';

      case PaymentStatus.FAILED:
      case PaymentStatus.FRAUD_REJECTED:
        return 'warn';

      default:
        return 'primary';
    }
  }
}
