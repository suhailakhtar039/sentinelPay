import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { ProfileService } from '../../services/profile-service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-change-password',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
  ],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css',
})
export class ChangePassword {
  hidePassword = true;
  hideNewPassword = true;
  passwordForm!: FormGroup;
  changingPassword = false;

  errorMessage = '';

  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
    private dialogRef: MatDialogRef<ChangePassword>,
  ) {}

  ngOnInit() {
    this.passwordForm = this.fb.group({
      currentPassword: ['', [Validators.required, Validators.minLength(6)]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit(): void {
    if (this.passwordForm.invalid) {
      this.passwordForm.markAllAsTouched();
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';
    this.changingPassword = true;

    this.profileService
      .changePassword({
        currentPassword: this.passwordForm.value.currentPassword,
        newPassword: this.passwordForm.value.newPassword,
      })
      .pipe(
        finalize(() => {
          this.changingPassword = false;
        }),
      )
      .subscribe({
        next: (response) => {
          this.successMessage = response.message ?? 'Password changed successfully.';

          setTimeout(() => {
            this.dialogRef.close(true);
          }, 800);
        },

        error: (err) => {
          this.errorMessage = err.error?.message ?? 'Unable to change password. Please try again.';
        },
      });
  }

  close() {
    this.dialogRef.close();
  }
}
