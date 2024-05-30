import { HttpTestingController } from '@angular/common/http/testing';
import { Observable } from 'rxjs';

export function testRequestType(
  url: string,
  requestType: 'GET' | 'POST' | 'DELETE' | 'PUT',
  methodToCall$: () => Observable<any> | void,
  httpController: HttpTestingController
) {
  let observable = methodToCall$();
  if (observable) {
    observable.subscribe();
  }

  const testRequest = httpController.expectOne(url);
  expect(testRequest.request.method).toEqual(requestType);
}

export function testReturnValue(
  url: string,
  mockResponseData: any,
  expectedValue: any,
  methodToCall$: () => Observable<any>,
  httpController: HttpTestingController
) {
  methodToCall$().subscribe((res) => expect(res).toEqual(expectedValue));
  httpController.expectOne(url).flush(mockResponseData);
}
