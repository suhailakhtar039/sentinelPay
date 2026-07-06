package com.paymentservice.dto.analytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MonthlyVolumeResponse {
    String month;
    BigDecimal totalVolume;
}
