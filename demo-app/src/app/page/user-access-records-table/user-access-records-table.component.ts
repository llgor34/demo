import { Component, inject } from '@angular/core';
import { CurrencyService } from '../../service/currency.service';
import { UserAccessRecord } from '../../interface/user-access-record.interface';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { LocalDatePipe } from '../../pipe/local-date.pipe';

@Component({
  selector: 'app-user-access-records-table',
  standalone: true,
  imports: [AsyncPipe, LocalDatePipe],
  templateUrl: './user-access-records-table.component.html',
  styleUrl: './user-access-records-table.component.css',
})
export class UserAccessRecordsTableComponent {
  currencyService: CurrencyService = inject(CurrencyService);
  userAccessRecords$: Observable<UserAccessRecord[]> =
    this.currencyService.getAllUserAccessRecords$();
}
