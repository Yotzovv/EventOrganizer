import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent {

  constructor(
  ) {
  }

  // TODO: fix
  profileForm: FormGroup = new FormGroup({
    username: new FormControl('pesho_pi4a', [
      // Validators.required
    ]),
    firstName: new FormControl('Pesho', [
      // Validators.required
    ]),
    lastName: new FormControl('Petrov', [
      // Validators.required
    ]),
    email: new FormControl('pesho@abv.bg', [
      // Validators.required,
      // Validators.pattern(emailregex),
    ]),
    password: new FormControl('qwerty123', [
      // Validators.required,
      // this.checkPassword,
    ]),
    confirmPassword: new FormControl('qwerty123', [
      // Validators.required,
      // this.checkPassword,
    ]),
  });

  onSubmit() {
    
  }
}
