import { TestBed } from '@angular/core/testing';
import { CurrencyService } from './currency.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { environment } from '../../environments/environment';
import { testRequestType, testReturnValue } from '../testing/generic';
import { UserAccessRecord } from '../interface/user-access-record.interface';
import { ValueResponse } from '../interface/value-response.interface';

describe('CurrencyService', () => {
  let service: CurrencyService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CurrencyService],
      imports: [HttpClientTestingModule],
    });

    service = TestBed.inject(CurrencyService);
    httpController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpController.verify();
  });

  describe('getCurrencyValue$()', () => {
    let url = `${environment.webserverUrl}/currencies/get-current-currency-value-command`;

    it(`should send POST request to ${url}`, () => {
      testRequestType(
        url,
        'POST',
        () => service.getCurrencyValue$('', ''),
        httpController
      );
    });

    it('should return number', () => {
      const mockData: ValueResponse = { value: 5 };
      testReturnValue(
        url,
        mockData,
        5,
        () => service.getCurrencyValue$('', ''),
        httpController
      );
    });
  });

  describe('getAllUserAccessRecords$()', () => {
    let url = `${environment.webserverUrl}/currencies/requests`;

    it(`should send GET request to ${url}`, () => {
      testRequestType(
        url,
        'GET',
        () => service.getAllUserAccessRecords$(),
        httpController
      );
    });

    it('should return UserAccessRecord[]', () => {
      const mockData: UserAccessRecord[] = [
        { currency: 'a', date: '10.01.2020', name: 'John Smith', value: 12 },
      ];
      testReturnValue(
        url,
        mockData,
        mockData,
        () => service.getAllUserAccessRecords$(),
        httpController
      );
    });
  });

  describe('refreshAllUserAccessRecords()', () => {
    let url = `${environment.webserverUrl}/currencies/requests`;

    it(`should send GET request to ${url}`, () => {
      const observable = service.getAllUserAccessRecords$();

      observable.subscribe();
      httpController.expectOne(url);

      service.refreshAllUserAccessRecords();
      httpController.expectOne(url);
    });
  });
});
