import { tap } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpInterceptor } from '@angular/common/http';
import { HttpRequest, HttpHandler } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  router: Router;
  location: Location;

  constructor(
    router: Router,
    location: Location
  ) {
    this.router = router;
    this.location = location;
  }

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    const authToken = window.localStorage.getItem('user-token') || '';
    let authRequest = request;

    authRequest = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + authToken),
    });

    return next.handle(authRequest).pipe(
      tap({
        error: (error: HttpErrorResponse) => {
          if (error.status === 401 || error.status === 403) {
            window.localStorage.removeItem('user-token');
            this.router.navigate(['auth/login']);
          }
        },
      })
    );
  }
}
