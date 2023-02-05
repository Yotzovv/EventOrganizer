export class CreateUserDto {
    username: string;
    email: string;
    location: string;
    password: string;
}

export class LoginUserDto {
    username: string;
    password: string;
}