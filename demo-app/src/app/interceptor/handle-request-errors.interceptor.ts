import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const handleRequestErrorsInterceptor: HttpInterceptorFn = (
  req,
  next
) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      switch (error.status) {
        case 404:
          alert('Nie znaleziono żądanego zasobu!');
          break;

        default:
          alert('Wystąpił nieoczekiwany błąd!');
          break;
      }

      return throwError(() => error.status);
    })
  );
};
