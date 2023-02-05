import { UserProfile } from './../types/userProfile.type';
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
  
  private username = new FormControl('');
  private email = new FormControl('');
  private name = new FormControl('');
  private location = new FormControl('');

  constructor(requestService: RequestService) {
    this.requestService = requestService;

    this.requestService.getCurrentLoggedInUser().subscribe(res => {
      this.username.setValue(res.username);
      this.email.setValue(res.email);
      this.name.setValue(res.name);
      this.location.setValue(res.location);
    })
  }
  // TODO: fix
  profileForm: FormGroup = new FormGroup({
    username: this.username,
    email: this.email,
    firstName: this.name,
    location: this.location,
    password: new FormControl(null, [
      // Validators.required,
      // this.checkPassword,
    ]),
    confirmPassword: new FormControl(null, [
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
         const imageData = base64String.replace(/^data:image\/[a-z]+;base64,/, '');
         const strippedBase64String = imageData.slice(0, -1);

         // Do something with the base64 string, such as sending it to a server
         this.requestService.addProfilePicture(strippedBase64String).subscribe((res) => {});
       };
     }
   }

  onSubmit() {
    if (this.profileForm.valid) {
      const userDto = this.profileForm.value;
      Object.keys(userDto).forEach(k => {
        if (!userDto[k]) {
          delete userDto[k];
        }
      });
      this.requestService.editAccount(userDto).subscribe(res => {
        console.log('Update user res: ');
        console.log(res);
      });
    } else {
      console.log('Form invalid ');
      console.log(this.profileForm);
    }
  }
}
