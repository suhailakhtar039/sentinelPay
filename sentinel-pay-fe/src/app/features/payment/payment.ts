import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router, RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-payment',
  imports: [CommonModule, MatCardModule, MatButtonModule, RouterLink, RouterOutlet],
  templateUrl: './payment.html',
  styleUrl: './payment.css',
})
export class Payment {
  private readonly router = inject(Router);

  get showMenu(): boolean {
    return this.router.url === '/payments';
  }
}
