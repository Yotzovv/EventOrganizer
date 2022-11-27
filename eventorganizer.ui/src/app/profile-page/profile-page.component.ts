import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent {
  profileForm: FormGroup = new FormGroup({
    username: new FormControl('pesho', [
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
  });

  onSubmit() {
    
  }
}
