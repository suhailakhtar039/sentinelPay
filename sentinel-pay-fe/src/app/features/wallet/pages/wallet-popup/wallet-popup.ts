import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroupDirective } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { WalletService } from '../../../../core/services/wallet-service';

@Component({
  selector: 'app-wallet-popup',
  imports: [CommonModule, MatCardModule, MatButtonModule, MatInputModule, ReactiveFormsModule],
  templateUrl: './wallet-popup.html',
  styleUrl: './wallet-popup.css',
})
export class WalletPopup {
  @ViewChild(FormGroupDirective)
  private formDirective!: FormGroupDirective;

  walletService = inject(WalletService);
  fb = inject(FormBuilder);

  topUpForm = this.fb.group({
    amount: ['', [Validators.required, Validators.min(1)]],
  });

  submit() {
    if (this.topUpForm.invalid) {
      return;
    }

    const amount = Number(this.topUpForm.controls.amount.value);
    if (Number.isNaN(amount)) {
      return;
    }

    this.walletService.topupwallet(amount)?.subscribe({
      next: (request) => {
        console.log(request.data);
        this.formDirective.resetForm();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
