import { DailyTransaction } from '../../model/daily-transaction.model';
import { MonthlyVolume } from '../../model/monthly-volume.model';
import { PaymentStatus } from '../../model/payment-status.model';
import { TopReceiver } from '../../model/top-receiver.model';
import {
  AreaChartOptions,
  ColumnChartOptions,
  DonutChartOptions,
  HorizontalBarChartOptions,
} from './analytics-charts.type';

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

export function createPaymentStatusChart(data: PaymentStatus[]): Partial<DonutChartOptions> {
  return {
    series: data.map((s) => s.count),

    labels: data.map((s) => s.status),

    chart: {
      type: 'donut',
      height: 320,
    },

    plotOptions: {
      pie: {
        donut: {
          size: '70%',
        },
      },
    },

    legend: {
      position: 'bottom',
      fontSize: '14px',
    },

    dataLabels: {
      enabled: false,
    },

    tooltip: {
      y: {
        formatter: (value) => `${value} Payments`,
      },
    },

    responsive: [
      {
        breakpoint: 768,
        options: {
          chart: {
            height: 280,
          },
          legend: {
            position: 'bottom',
          },
        },
      },
    ],
  };
}

export function createTopReceiversChart(data: TopReceiver[]): Partial<HorizontalBarChartOptions> {
  const sorted = [...data].sort((a, b) => b.totalReceived - a.totalReceived).slice(0, 5);

  return {
    series: [
      {
        name: 'Amount Received',
        data: sorted.map((receiver) => receiver.totalReceived),
      },
    ],

    chart: {
      type: 'bar',
      height: 320,
      toolbar: {
        show: false,
      },
    },

    plotOptions: {
      bar: {
        horizontal: true,
        borderRadius: 6,
        barHeight: '55%',
      },
    },

    dataLabels: {
      enabled: false,
    },

    xaxis: {
      categories: sorted.map((receiver) => `User ${receiver.receiverId}`),

      labels: {
        formatter: (value) => `₹${Number(value).toLocaleString('en-IN')}`,
      },
    },

    yaxis: {
      title: {
        text: undefined,
      },
    },

    tooltip: {
      y: {
        formatter: (value) => `₹${value.toLocaleString('en-IN')}`,
      },
    },

    stroke: {
      width: 1,
    },

    grid: {
      borderColor: '#E5E7EB',
      strokeDashArray: 5,
    },

    legend: {
      show: false,
    },
  };
}

export function createDailyTransactionsChart(
  data: DailyTransaction[],
): Partial<ColumnChartOptions> {
  // Ensure dates are displayed chronologically
  const sorted = [...data].sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());

  return {
    series: [
      {
        name: 'Transactions',
        data: sorted.map((transaction) => transaction.transactionCount),
      },
    ],

    chart: {
      type: 'bar',
      height: 320,
      toolbar: {
        show: false,
      },
      animations: {
        enabled: true,
        speed: 600,
      },
    },

    plotOptions: {
      bar: {
        horizontal: false,
        borderRadius: 6,
        columnWidth: '45%',
      },
    },

    dataLabels: {
      enabled: false,
    },

    xaxis: {
      categories: sorted.map((transaction) =>
        new Date(transaction.date).toLocaleDateString('en-IN', {
          day: '2-digit',
          month: 'short',
        }),
      ),
      title: {
        text: 'Date',
      },
    },

    yaxis: {
      title: {
        text: 'Transactions',
      },
      labels: {
        formatter: (value) => value.toFixed(0),
      },
      decimalsInFloat: 0,
      forceNiceScale: true,
    },

    tooltip: {
      x: {
        show: true,
      },
      y: {
        formatter: (value) => `${value.toLocaleString()} Transactions`,
      },
    },

    grid: {
      borderColor: '#E5E7EB',
      strokeDashArray: 5,
    },

    legend: {
      show: false,
    },
  };
}
