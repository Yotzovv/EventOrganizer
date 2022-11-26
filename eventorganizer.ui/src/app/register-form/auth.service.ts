
import { Router } from "@angular/router";
import { Injectable } from "@angular/core";

@Injectable()
export class AuthService {
  token: string | undefined;
  username: string | undefined;

  constructor(private router: Router) { }
  registerUSer(email: string, password: string, username: string) {
    // firebase.auth().createUserWithEmailAndPassword(email, password)
    //   .catch(
    //   error => console.log(error)
    //   )
  }

  signinUser(email: string, password: string) {
    // firebase.auth().signInWithEmailAndPassword(email, password)
    //   .then(
    //   response => {
    //     this.router.navigate(["/"]);
    //   }
    //   )
    //   .catch(
    //   error => console.log(error)
    //   );
    // firebase.auth().onAuthStateChanged(user => {
    //   if (user) {
    //     this.username = firebase.auth().currentUser.email;
    //     firebase
    //       .auth()
    //       .currentUser.getIdToken()
    //       .then((tkn: string) => (this.token = tkn));
    //   } else {
    //     console.warn("onAuthStatusChnaged => no user");
    //   }
    // });
  }
 logout() {
    // firebase
    //   .auth()
    //   .signOut()
    //   .then(response => this.router.navigate(["/"]));
    // this.token = null;
  }
  getToken() {
    return this.token;
  }
  isAunthenticated() {
    return this.token != null;
  }
  getUserDetails() {
    return this.username;

  }

}