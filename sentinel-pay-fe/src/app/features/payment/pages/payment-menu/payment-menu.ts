import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-payment-menu',
  imports: [CommonModule, MatCardModule, MatButtonModule, RouterLink, RouterOutlet],
  templateUrl: './payment-menu.html',
})
export class PaymentMenu {
  showMenu = true;

  constructor(private router: Router) {}

  ngOnInit() {
    this.showMenu = this.router.url === '/payments';

    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe(() => {
      this.showMenu = this.router.url === '/payments';
    });
  }
}
