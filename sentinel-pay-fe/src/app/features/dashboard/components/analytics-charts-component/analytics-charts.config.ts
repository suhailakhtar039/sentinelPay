import { MonthlyVolume } from '../../model/monthly-volume.model';
import { AreaChartOptions } from './analytics-charts.type';

const MONTHS = [
  '',
  'Jan',
  'Feb',
  'Mar',
  'Apr',
  'May',
  'Jun',
  'Jul',
  'Aug',
  'Sep',
  'Oct',
  'Nov',
  'Dec',
];

export function createMonthlyChart(data: MonthlyVolume[]): Partial<AreaChartOptions> {
  return {
    series: [
      {
        name: 'Money Sent',
        data: data.map((m) => m.totalVolume),
      },
    ],

    chart: {
      type: 'area',
      height: 350,
      toolbar: {
        show: false,
      },
      zoom: {
        enabled: false,
      },
    },

    title: {
      text: '',
    },

    stroke: {
      curve: 'smooth',
      width: 3,
    },

    markers: {
      size: 4,
    },

    dataLabels: {
      enabled: false,
    },

    fill: {
      type: 'gradient',
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.35,
        opacityTo: 0.05,
      },
    },

    xaxis: {
      categories: data.map((m) => MONTHS[m.month]),
    },

    yaxis: {
      labels: {
        formatter: (value) => `₹${value.toLocaleString('en-IN')}`,
      },
    },

    tooltip: {
      y: {
        formatter: (value) => `₹${value.toLocaleString('en-IN')}`,
      },
    },

    grid: {
      borderColor: '#E5E7EB',
      strokeDashArray: 5,
    },
  };
}
