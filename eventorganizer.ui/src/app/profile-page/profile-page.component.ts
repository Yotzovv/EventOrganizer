import { RequestService } from 'src/app/request/request.service';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as _ from 'lodash';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent {
  imageError: string;
  isImageSaved: boolean;
  cardImageBase64: string;
  requestService: RequestService;

  constructor(requestService: RequestService) {
    this.requestService = requestService;
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

   // Variable to hold the selected file
   selectedFile: File;

   // Event handler for when the file input value changes
   onFileChange(event) {
     // Update the selected file variable with the selected file
     this.selectedFile = event.target.files[0];
   }
 
   // Function to upload the image
   uploadImage() {
     // Check if a file has been selected
     if (this.selectedFile) {
       // Read the file and convert it to base64
       const fileReader = new FileReader();
       fileReader.readAsDataURL(this.selectedFile);
       fileReader.onload = () => {
         // Get the base64 string
         const base64String = fileReader.result as string;
         // Do something with the base64 string, such as sending it to a server
         this.requestService.addProfilePicture(base64String).subscribe((res) => {});
       };
     }
   }

  onSubmit() {

  }
}
