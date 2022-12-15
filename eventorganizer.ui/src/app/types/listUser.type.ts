export class ListUser {
    username: string;
    email: string;
    role: string;
    isActive: boolean;

    constructor(username: string, email: string, role: string, isActive: boolean) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
    }
}