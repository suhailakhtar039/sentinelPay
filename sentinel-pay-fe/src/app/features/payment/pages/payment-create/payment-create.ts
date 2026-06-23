import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { PaymentService } from '../../services/payment-service';
import { CreatePaymentRequest } from '../../model/create-payment-request';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-payment-create',
  imports: [
    CommonModule,
    ReactiveFormsModule,

    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
  ],
  templateUrl: './payment-create.html',
  styleUrl: './payment-create.css',
})
export class PaymentCreate {
  private readonly fb = inject(FormBuilder);
  private readonly paymentService = inject(PaymentService);
  private readonly snackBar = inject(MatSnackBar);

  loading = false;

  paymentForm = this.fb.group({
    receiverUserId: ['', [Validators.required, Validators.min(1)]],
    amount: ['', [Validators.required, Validators.min(1)]],
  });

  submit() {
    if (this.paymentForm.invalid) {
      this.paymentForm.markAllAsTouched();
      return;
    }
    this.loading = true;

    const request: CreatePaymentRequest = {
      receiverUserId: Number(this.paymentForm.value.receiverUserId),
      amount: Number(this.paymentForm.value.amount),
    };

    this.paymentService
      .createPayment(request)
      .pipe(
        finalize(() => {
          this.loading = false;
        }),
      )
      .subscribe({
        next: (response) => {
          this.snackBar.open(response.message || 'Payment Initiated Successfully', 'Close', {
            duration: 3000,
          });
          this.paymentForm.reset();
        },
        error: (err: any) => {
          const message = err?.error?.message || 'Unable To Process Payment';
          this.snackBar.open(message, 'Close', { duration: 5000 });
        },
      });
  }
}
