import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, Subject, map, switchMap } from 'rxjs';
import { environment } from '../../environments/environment';
import { ValueResponse } from '../interface/value-response.interface';
import { UserAccess } from '../interface/user-access.interface';
import { UserAccessRecord } from '../interface/user-access-record.interface';

@Injectable({ providedIn: 'root' })
export class CurrencyService {
  private http: HttpClient = inject(HttpClient);
  private refreshAllUserAccessRecords$ = new BehaviorSubject(null);

  getCurrencyValue$(
    currencyCode: string,
    username: string
  ): Observable<number> {
    const userAccess: UserAccess = { currency: currencyCode, name: username };

    return this.http
      .post<ValueResponse>(
        `${environment.webserverUrl}/currencies/get-current-currency-value-command`,
        userAccess
      )
      .pipe(map((res) => res.value));
  }

  getAllUserAccessRecords$(): Observable<UserAccessRecord[]> {
    return this.refreshAllUserAccessRecords$.pipe(
      switchMap(() =>
        this.http.get<UserAccessRecord[]>(
          `${environment.webserverUrl}/currencies/requests`
        )
      )
    );
  }

  refreshAllUserAccessRecords(): void {
    this.refreshAllUserAccessRecords$.next(null);
  }
}
