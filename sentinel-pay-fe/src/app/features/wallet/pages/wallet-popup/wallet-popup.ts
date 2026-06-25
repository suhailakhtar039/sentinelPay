import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { WalletService } from '../../../../core/services/wallet-service';

@Component({
  selector: 'app-wallet-popup',
  imports: [CommonModule, FormsModule, MatCardModule, MatButtonModule, MatInputModule],
  templateUrl: './wallet-popup.html',
  styleUrl: './wallet-popup.css',
})
export class WalletPopup {
  amount!: number;

  http = inject(HttpClient);
  walletService = inject(WalletService);

  submit() {
    this.walletService.topupwallet(this.amount)?.subscribe({
      next: (request) => {
        console.log(request.data);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
