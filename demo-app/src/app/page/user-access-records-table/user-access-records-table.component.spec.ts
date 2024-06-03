import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserAccessRecordsTableComponent } from './user-access-records-table.component';
import { AsyncPipe } from '@angular/common';
import { LocalDatePipe } from '../../pipe/local-date.pipe';
import { CurrencyService } from '../../service/currency.service';
import { UserAccessRecord } from '../../interface/user-access-record.interface';
import { Observable, of } from 'rxjs';
import { By } from '@angular/platform-browser';

describe('UserAccessRecordsTableComponent', () => {
  let fixture: ComponentFixture<UserAccessRecordsTableComponent>;
  let component: UserAccessRecordsTableComponent;
  let currencyService: jasmine.SpyObj<CurrencyService>;

  beforeEach(async () => {
    currencyService = jasmine.createSpyObj('CurrencyService', [
      'getAllUserAccessRecords$',
    ]);

    await TestBed.configureTestingModule({
      providers: [
        {
          provide: CurrencyService,
          useValue: currencyService,
        },
      ],
      imports: [UserAccessRecordsTableComponent, AsyncPipe, LocalDatePipe],
    }).compileComponents();
    fixture = TestBed.createComponent(UserAccessRecordsTableComponent);
    component = fixture.componentInstance;
  });

  it('should call getAllUserAccessRecords$()', () => {
    fixture.detectChanges();
    expect(currencyService.getAllUserAccessRecords$).toHaveBeenCalled();
  });

  it('should display record for each of UserAccessRecord', async () => {
    const records$: Observable<UserAccessRecord[]> = of([
      { currency: 'usd', date: '', name: 'john doe', value: 3.5 },
      { currency: 'usd', date: '', name: 'john doe', value: 3.5 },
      { currency: 'usd', date: '', name: 'john doe', value: 3.5 },
    ]);

    currencyService.getAllUserAccessRecords$.and.returnValue(records$);
    fixture = TestBed.createComponent(UserAccessRecordsTableComponent);

    fixture.detectChanges();
    await fixture.whenRenderingDone();

    const renderedHTML = [...fixture.debugElement.queryAll(By.css('tbody tr'))];
    expect(renderedHTML.length).toEqual(3);
  });
});
