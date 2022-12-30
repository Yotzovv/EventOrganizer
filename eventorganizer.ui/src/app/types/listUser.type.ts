export class ListUser {
    username: string;
    email: string;
    roles: string[];
    isActive: boolean;
    profilePictureBase64: string;

    constructor(username: string, email: string, roles: string[], isActive: boolean) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.isActive = isActive;
    }
}