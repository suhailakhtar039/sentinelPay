import { Component, Input } from '@angular/core';
import { DashboardAnalytics } from '../../model/dashboard-analytics.model';

@Component({
  selector: 'app-analytics-charts-component',
  imports: [],
  templateUrl: './analytics-charts-component.html',
  styleUrl: './analytics-charts-component.css',
})
export class AnalyticsChartsComponent {
  @Input({ required: true })
  analytics!: DashboardAnalytics;
}
