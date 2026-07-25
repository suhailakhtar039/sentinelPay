import { Component, Input } from '@angular/core';
import { DashboardAnalytics } from '../../model/dashboard-analytics.model';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-analytics-charts-component',
  imports: [CommonModule, MatCardModule],
  templateUrl: './analytics-charts-component.html',
  styleUrl: './analytics-charts-component.css',
})
export class AnalyticsChartsComponent {
  @Input({ required: true })
  analytics!: DashboardAnalytics;
}
