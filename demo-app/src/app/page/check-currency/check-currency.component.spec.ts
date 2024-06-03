import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { FormControlComponent } from '../../component/form-control/form-control.component';
import { CurrencyService } from '../../service/currency.service';
import { CheckCurrencyComponent } from './check-currency.component';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';

describe('CheckCurrencyComponent', () => {
  let fixture: ComponentFixture<CheckCurrencyComponent>;
  let component: CheckCurrencyComponent;
  let currencyService: jasmine.SpyObj<CurrencyService>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        {
          provide: CurrencyService,
          useValue: jasmine.createSpyObj('CurrencyService', [
            'getCurrencyValue$',
            'refreshAllUserAccessRecords',
          ]),
        },
      ],
      imports: [ReactiveFormsModule, FormControlComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CheckCurrencyComponent);
    component = fixture.componentInstance;
    currencyService = TestBed.inject(
      CurrencyService
    ) as jasmine.SpyObj<CurrencyService>;
  });

  it('should create formGroup with {currency, name, surname}', () => {
    fixture.detectChanges();

    expect(component.form.contains('currency')).toBeTruthy();
    expect(component.form.contains('name')).toBeTruthy();
    expect(component.form.contains('surname')).toBeTruthy();
  });

  it('should not be loading initially', () => {
    fixture.detectChanges();

    expect(component.isLoading()).toBeFalsy();
  });

  it('should not have currencyValueRead initially', () => {
    fixture.detectChanges();

    expect(component.currencyValueRead()).toBeFalsy();
  });

  describe('onSubmit()', () => {
    it('should not proceed, when form is invalid', () => {
      fixture.detectChanges();

      component.onSubmit();

      expect(currencyService.getCurrencyValue$).not.toHaveBeenCalled();
    });

    it('should proceed, when form is valid', () => {
      currencyService.getCurrencyValue$.and.returnValue(of(1));

      component.form.setValue({
        currency: 'USD',
        name: 'John',
        surname: 'Doe',
      });

      fixture.detectChanges();
      component.onSubmit();

      expect(currencyService.getCurrencyValue$).toHaveBeenCalledWith(
        'USD',
        'John Doe'
      );
    });
  });

  it('should not render loadingSpinenner when not loading', () => {
    fixture.detectChanges();

    const loadingSpinner = fixture.debugElement.query(
      By.css('.spinner-border')
    );

    expect(loadingSpinner).toBeFalsy();
  });

  it('should render loadingSpinenner when loading', () => {
    component.isLoading.set(true);
    fixture.detectChanges();

    const loadingSpinner = fixture.debugElement.query(
      By.css('.spinner-border')
    );

    expect(loadingSpinner).toBeTruthy();
  });

  it('should not render read value for currency initially', () => {
    fixture.detectChanges();

    const readValue = fixture.debugElement.query(By.css('[role="alert"]'));

    expect(readValue).toBeFalsy();
  });

  it('should render read value for currency when present', () => {
    component.currencyValueRead.set(12.5);
    fixture.detectChanges();

    const readValue = fixture.debugElement.query(By.css('[role="alert"]'));

    expect(readValue).toBeTruthy();
  });

  it('should have button disabled when form is invalid', () => {
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));

    expect(button.attributes['disabled']).toEqual('');
  });

  it('should have button enabled when form is valid', () => {
    component.form.setValue({ currency: 'USD', name: 'John', surname: 'Doe' });
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));

    expect(button.attributes['disabled']).toBeFalsy();
  });
});
