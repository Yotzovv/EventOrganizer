import { Component, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'login-form',
  templateUrl: `./login-form.component.html`,
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {

  constructor(
    private router: Router,
  ) {

  }

  loginForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  submit() {
    if (this.loginForm.valid) {
      // this.submitEM.emit(this.loginForm.value);
      // // TODO: set real token

      // TODO: Connect to API
      window.localStorage.setItem('user-token', 'asdasdasdasdas');
      this.router.navigate(['home'])
    }
  }

  @Output() submitEM = new EventEmitter();
}
