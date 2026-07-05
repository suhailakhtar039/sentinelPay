import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UserProfile } from '../models/user-profile';
import { ProfileService } from '../services/profile-service';
import { finalize } from 'rxjs';
import { UpdateProfileRequest } from '../models/update-profile-request';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-profile',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile {
  profile?: UserProfile;
  profileForm!: FormGroup;

  loading = true;
  saving = false;

  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
  ) {}

  ngOnInit() {
    this.profileForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
    });

    this.loadProfile();
  }

  loadProfile() {
    this.loading = true;
    this.errorMessage = '';

    this.profileService
      .getMyProfile()
      .pipe(
        finalize(() => {
          this.loading = false;
          // this.cdf.detectChanges();
        }),
      )
      .subscribe({
        next: (response) => {
          this.profile = response.data;
          this.profileForm.patchValue({
            name: response.data.name,
          });
        },
        error: () => {
          this.loading = false;
          this.errorMessage = 'Unable to load profile';
        },
      });
  }

  saveProfile(): void {
    if (this.profileForm.invalid) {
      this.profileForm.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.successMessage = '';

    const request: UpdateProfileRequest = {
      name: this.profileForm.value.name,
    };
    this.profileService
      .updateProfile(request)
      .pipe(
        finalize(() => {
          this.saving = false;
          // this.cdf.detectChanges();
        }),
      )
      .subscribe({
        next: () => {
          this.profile!.name = request.name;
          this.successMessage = 'Profile updated successfully';
        },
        error: () => {
          this.errorMessage = 'Unable to update profile';
        },
      });
  }
}
