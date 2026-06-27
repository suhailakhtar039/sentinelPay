import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-payment',
  imports: [CommonModule, RouterOutlet],
  templateUrl: './payment.html',
  styleUrl: './payment.css',
})
export class Payment {}
