import { Component } from '@angular/core';
import { CheckCurrencyComponent } from './page/check-currency/check-currency.component';
import { UserAccessRecordsTableComponent } from './page/user-access-records-table/user-access-records-table.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CheckCurrencyComponent, UserAccessRecordsTableComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {}
