export class UserProfile {
    username: string;
    firstName: string;
    lastName: string;
    location: string;
    password: string;

    constructor(_username: string, _firstname: string, _lastname: string, _location: string, _password: string) {
        this.username = _username;
        this.firstName = _firstname;
        this.lastName = _lastname;
        this.location = _location;
        this.password = _password;
    }

}