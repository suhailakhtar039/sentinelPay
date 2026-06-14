import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule, MatCardSubtitle, MatCardTitle } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { RouterLinkActive, RouterModule, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth';
import { LoginRequest } from '../../../shared/models/login-request';

@Component({
  selector: 'app-login-component',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatCardModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatCardTitle,
    MatCardSubtitle,
    RouterModule,
  ],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent implements OnInit {
  hidePassword = true;
  loginForm!: FormGroup;
  errorField = '';
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const request: LoginRequest = {
        email: this.loginForm.get('email')?.value,
        password: this.loginForm.get('password')?.value,
      };
      this.authService.login(request).subscribe({
        next: (response: any) => {
          console.log('Login successful', response);
          this.router.navigate(['/dashboard']).catch((err) => console.error(err));
        },
        error: (err: any) => {
          console.error('Login failed', err);
          this.loginForm.markAllAsDirty();
          this.errorField = 'Email or Password is wrong';
        },
      });
    } else {
      this.loginForm.markAllAsDirty();
    }
  }
}
