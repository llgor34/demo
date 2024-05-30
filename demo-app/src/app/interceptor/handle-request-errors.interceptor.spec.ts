import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
import { handleRequestErrorsInterceptor } from './handle-request-errors.interceptor';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

describe('handleRequestErrorsInterceptor', () => {
  let httpTestingController: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: HTTP_INTERCEPTORS,
          useValue: handleRequestErrorsInterceptor,
          multi: true,
        },
      ],
    });

    httpTestingController = TestBed.inject(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should alert "Nie znaleziono żądanego zasobu!" for 404 error', () => {
    spyOn(window, 'alert');

    httpClient
      .get('/test')
      .pipe(
        catchError((error: HttpErrorResponse) => {
          expect(error.status).toBe(404);
          return throwError(() => error);
        })
      )
      .subscribe();

    const req = httpTestingController.expectOne('/test');
    req.flush('Not found', { status: 404, statusText: 'Not Found' });

    expect(window.alert).toHaveBeenCalledWith(
      'Nie znaleziono żądanego zasobu!'
    );
  });

  it('should alert "Wystąpił nieoczekiwany błąd!" for other errors', () => {
    spyOn(window, 'alert');

    httpClient
      .get('/test')
      .pipe(
        catchError((error: HttpErrorResponse) => {
          expect(error.status).toBe(500);
          return throwError(() => error);
        })
      )
      .subscribe();

    const req = httpTestingController.expectOne('/test');
    req.flush('Internal Server Error', {
      status: 500,
      statusText: 'Internal Server Error',
    });

    expect(window.alert).toHaveBeenCalledWith('Wystąpił nieoczekiwany błąd!');
  });
});
