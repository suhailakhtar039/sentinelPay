package com.paymentservice.dashboard;

import com.paymentservice.dto.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboard(Long userId);
}
