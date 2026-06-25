import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../shared/models/api-response';
import { WalletResponse } from '../../shared/models/wallet-response';
import { API_CONFIG } from '../config/api.config';

@Injectable({
  providedIn: 'root',
})
export class WalletService {
  private readonly http = inject(HttpClient);

  getMyWallet(): Observable<ApiResponse<WalletResponse>> {
    return this.http.get<ApiResponse<WalletResponse>>(`${API_CONFIG.wallet}/me`);
  }

  topupwallet(amount: number): Observable<ApiResponse<WalletResponse>> | null {
    return this.http.post<ApiResponse<WalletResponse>>(`${API_CONFIG.wallet}/topup`, { amount });
  }
}
