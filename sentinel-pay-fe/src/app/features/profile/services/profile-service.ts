import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../../shared/models/api-response';
import { UserProfile } from '../models/user-profile';
import { HttpClient } from '@angular/common/http';
import { API_CONFIG } from '../../../core/config/api.config';
import { UpdateProfileRequest } from '../models/update-profile-request';
import { ChangePasswordRequest } from '../models/change-password-request';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = API_CONFIG.user;

  getMyProfile(): Observable<ApiResponse<UserProfile>> {
    return this.http.get<ApiResponse<UserProfile>>(`${this.baseUrl}/me`);
  }

  updateProfile(request: UpdateProfileRequest) {
    return this.http.put<ApiResponse<string>>(`${this.baseUrl}/me`, request);
  }

  changePassword(request: ChangePasswordRequest) {
    return this.http.put<ApiResponse<string>>(`${this.baseUrl}/change-password`, request);
  }
}
