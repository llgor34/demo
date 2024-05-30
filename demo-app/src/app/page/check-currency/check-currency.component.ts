import { Component, inject, signal } from '@angular/core';
import { FormControlComponent } from '../../component/form-control/form-control.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CurrencyService } from '../../service/currency.service';

@Component({
  selector: 'app-check-currency',
  standalone: true,
  imports: [FormControlComponent, ReactiveFormsModule],
  templateUrl: './check-currency.component.html',
  styleUrl: './check-currency.component.css',
})
export class CheckCurrencyComponent {
  fb: FormBuilder = inject(FormBuilder);
  currencyService: CurrencyService = inject(CurrencyService);

  form = this.fb.group({
    currency: ['', Validators.required],
    name: ['', Validators.required],
    surname: ['', Validators.required],
  });

  isLoading = signal(false);
  currencyValueRead = signal<number | null>(null);

  onSubmit(): void {
    if (this.form.invalid) return;

    const request$ = this.currencyService.getCurrencyValue$(
      this.getCurrencyCode(),
      this.getUsername()
    );
    this.resetForm();
    this.startLoading();

    request$.subscribe({
      next: (currencyValue) => {
        this.updateCurrencyValueRead(currencyValue);
        this.refreshUserAccessRecords();
        this.finishLoading();
      },
      error: () => this.finishLoading(),
    });
  }

  private refreshUserAccessRecords() {
    this.currencyService.refreshAllUserAccessRecords();
  }

  private updateCurrencyValueRead(value: number) {
    this.currencyValueRead.set(value);
  }

  private getCurrencyCode(): string {
    return this.form.value.currency!;
  }

  private getUsername(): string {
    return `${this.form.value.name} ${this.form.value.surname}`;
  }

  private startLoading(): void {
    this.isLoading.set(true);
  }

  private finishLoading(): void {
    this.isLoading.set(false);
  }

  private resetForm(): void {
    this.form.reset();
  }
}
