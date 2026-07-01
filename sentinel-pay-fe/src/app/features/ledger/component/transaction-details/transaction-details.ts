import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { LedgerResponse } from '../../model/ledger-response';
import { MatAnchor } from '@angular/material/button';

@Component({
  selector: 'app-transaction-details',
  imports: [CommonModule, MatDialogModule, MatAnchor],
  templateUrl: './transaction-details.html',
  styleUrl: './transaction-details.css',
})
export class TransactionDetails {
  constructor(@Inject(MAT_DIALOG_DATA) public transaction: LedgerResponse) {}
}
