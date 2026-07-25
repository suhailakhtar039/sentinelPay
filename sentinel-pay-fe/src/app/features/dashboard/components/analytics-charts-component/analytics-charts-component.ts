import { Component, computed, input } from '@angular/core';
import { DashboardAnalytics } from '../../model/dashboard-analytics.model';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { createMonthlyChart } from './analytics-charts.config';
import { NgApexchartsModule } from 'ng-apexcharts';

@Component({
  selector: 'app-analytics-charts-component',
  imports: [CommonModule, MatCardModule, NgApexchartsModule],
  templateUrl: './analytics-charts-component.html',
  styleUrl: './analytics-charts-component.css',
})
export class AnalyticsChartsComponent {
  analytics = input.required<DashboardAnalytics>();

  monthlyChart = computed(() => createMonthlyChart(this.analytics().monthlyVolume));
}
