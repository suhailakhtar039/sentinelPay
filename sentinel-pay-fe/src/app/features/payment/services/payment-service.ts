import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../../shared/models/api-response';
import { PaymentResponse } from '../model/payment-response';
import { API_CONFIG } from '../../../core/config/api.config';
import { CreatePaymentRequest } from '../model/create-payment-request';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  private readonly http = inject(HttpClient);

  private readonly baseUrl = API_CONFIG.payment;

  createPayment(request: CreatePaymentRequest): Observable<ApiResponse<PaymentResponse>> {
    return this.http.post<ApiResponse<PaymentResponse>>(this.baseUrl, request);
  }

  getPaymentById(paymentId: number): Observable<ApiResponse<PaymentResponse>> {
    return this.http.get<ApiResponse<PaymentResponse>>(`${this.baseUrl}/${paymentId}`);
  }

  getMyPayments(): Observable<ApiResponse<PaymentResponse[]>> {
    return this.http.get<ApiResponse<PaymentResponse[]>>(`${API_CONFIG.payment}/me`);
  }
}
