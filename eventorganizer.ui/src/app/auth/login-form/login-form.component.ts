import { Component, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RequestService } from 'src/app/request/request.service';

@Component({
  selector: 'login-form',
  templateUrl: `./login-form.component.html`,
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {
  @Output() submitEM = new EventEmitter();
  loginErrored: boolean = false;

  constructor(
    private router: Router,
    private request: RequestService,
    private _snackBar: MatSnackBar,
  ) {

  }

  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [
      Validators.required
    ]),
    password: new FormControl('', [
      Validators.required
    ]),
  });

  submit() {
    if (this.loginForm.valid) {
      this.loginErrored = false;
      // this.submitEM.emit(this.loginForm.value);
      // // TODO: set real token
      // TODO: Connect to API
      this.request.loginUser$({
        username: this.loginForm.get('email').value,
        password: this.loginForm.get('password').value
      }).subscribe({
        next: this.onLoginSuccess.bind(this),
        error: this.onLoginError.bind(this),
      });
    }
  }

  onLoginError(err) {
    this.loginErrored = true;
    // this._snackBar.open('Incorrect username or password.', 'close');
  }

  onLoginSuccess({
    access_token,
    refresh_token
  }) {
      window.localStorage.setItem('user-token', access_token);
      this.router.navigate(['home']);
  }


}
