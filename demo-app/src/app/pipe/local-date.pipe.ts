import { Pipe, PipeTransform } from '@angular/core';
import moment from 'moment';

@Pipe({
  name: 'localDate',
  standalone: true,
})
export class LocalDatePipe implements PipeTransform {
  transform(date: string): string {
    return moment(date).format('DD.MM.YYYY, HH:mm');
  }
}
