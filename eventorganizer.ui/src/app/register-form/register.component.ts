import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  FormGroupDirective,
} from '@angular/forms';
import { RequestService } from '../request/request.service';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup | undefined;
  fieldRequired: string = 'This field is required';
  constructor(
    private request: RequestService
    ) {}

  ngOnInit() {
    this.createForm();
  }
  createForm() {
    let emailregex: RegExp =
      /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    this.registerForm = new FormGroup({
      username: new FormControl('pesho_pi4a', [Validators.required]),
      email: new FormControl('pesho_pi4a@abv.bg', [
        Validators.required,
        Validators.pattern(emailregex),
      ]),
      password: new FormControl('Qwerty123', [
        Validators.required,
        this.checkPassword,
      ]),
    });
  }
  emaiErrors() {
    return this.registerForm!.get('email').hasError('required')
      ? 'This field is required'
      : this.registerForm.get('email').hasError('pattern')
      ? 'Not a valid emailaddress'
      : '';
  }
  checkPassword(control) {
    let enteredPassword = control.value;
    let passwordCheck = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{6,})/;
    return !passwordCheck.test(enteredPassword) && enteredPassword
      ? { requirements: true }
      : null;
  }
  getErrorPassword() {
    return this.registerForm.get('password').hasError('required')
      ? 'This field is required (The password must be at least six characters, one uppercase letter and one number)'
      : this.registerForm.get('password').hasError('requirements')
      ? 'Password needs to be at least six characters, one uppercase letter and one number'
      : '';
  }
  checkValidation(input: string) {
    const validation =
      this.registerForm.get(input).invalid &&
      (this.registerForm.get(input).dirty ||
        this.registerForm.get(input).touched);
    return validation;
  }
  onSubmit(
    formData: FormGroup,
    formDirective: FormGroupDirective
  ): void {
    console.log('submit')
    console.log('submit')
    console.log('submit')
    console.log('submit')
    console.log('submit')
    const email = formData.value.email;
    const password = formData.value.password;
    const username = formData.value.username;
    console.log({
      username,
      email,
      password
    })
    this.request.registerUser$({
      username,
      email,
      password
    }).subscribe(res => {
      console.log(res);
    })

    formDirective.resetForm();
    this.registerForm.reset();
  }

}
