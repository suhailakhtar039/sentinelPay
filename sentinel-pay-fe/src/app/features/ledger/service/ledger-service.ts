import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../../shared/models/api-response';
import { LedgerResponse } from '../model/ledger-response';
import { API_CONFIG } from '../../../core/config/api.config';

@Injectable({
  providedIn: 'root',
})
export class LedgerService {
  private readonly http = inject(HttpClient);

  getMyTransactions(): Observable<ApiResponse<LedgerResponse[]>> {
    return this.http.get<ApiResponse<LedgerResponse[]>>(`${API_CONFIG.ledger}/me`);
  }
}
