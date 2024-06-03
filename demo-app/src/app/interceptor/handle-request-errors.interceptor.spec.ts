import { TestBed } from '@angular/core/testing';
import {
  HttpClient,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { handleRequestErrorsInterceptor } from './handle-request-errors.interceptor';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

describe('handleRequestErrorsInterceptor', () => {
  let httpController: HttpTestingController;
  let httpClient: HttpClient;

  const url = '/test';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([handleRequestErrorsInterceptor])),
        provideHttpClientTesting(),
      ],
    });
    httpController = TestBed.inject(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
  });

  afterEach(() => {
    httpController.verify();
  });

  it('should alert "Nie znaleziono żądanego zasobu!" for 404 error', () => {
    spyOn(window, 'alert');

    httpClient.get(url).subscribe();
    httpController
      .expectOne(url)
      .flush({}, { status: 404, statusText: 'Not Found' });

    expect(window.alert).toHaveBeenCalledWith(
      'Nie znaleziono żądanego zasobu!'
    );
  });

  it('should alert "Wystąpił nieoczekiwany błąd!" for other errors', () => {
    spyOn(window, 'alert');

    httpClient.get(url).subscribe();
    httpController
      .expectOne(url)
      .flush({}, { status: 500, statusText: 'Internal Server Error' });

    expect(window.alert).toHaveBeenCalledWith('Wystąpił nieoczekiwany błąd!');
  });
});
