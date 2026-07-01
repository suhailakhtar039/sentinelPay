import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { LedgerResponse } from '../../model/ledger-response';

@Component({
  selector: 'app-transaction-details',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './transaction-details.html',
  styleUrl: './transaction-details.css',
})
export class TransactionDetails {
  constructor(@Inject(MAT_DIALOG_DATA) public transaction: LedgerResponse) {}
}
