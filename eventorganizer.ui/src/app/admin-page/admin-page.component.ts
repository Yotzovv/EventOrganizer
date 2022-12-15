import { ListUser } from './../types/listUser.type';
import { CreateUserDto } from './../types/user.type';
import { RequestService } from './../request/request.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent {
  public users: ListUser[] = [];

  requestService: RequestService;

  constructor(requestService: RequestService) {
    this.requestService = requestService;

    requestService.getAllUsers().subscribe((res) => {
      this.users = res;
    });
  }

  roles = ['user', 'admin'];
  displayedColumns = ['name', 'email', 'roles', 'buttons'];

  onChangeRole(user: ListUser, event: MouseEvent) {
    const newRole = (event.target as HTMLElement).innerText;

    this.requestService.changeUserRole(user.email, newRole).subscribe();
  }

  deactivateUser(user: any) {
    this.requestService.deactivateUser(user.email).subscribe();
  }

  activateUser(user: any) {
    this.requestService.activateUser(user.email).subscribe();
  }
}