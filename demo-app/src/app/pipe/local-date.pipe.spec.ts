import { TestBed } from '@angular/core/testing';
import { LocalDatePipe } from './local-date.pipe';

describe('LocalDatePipe', () => {
  let pipe: LocalDatePipe;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LocalDatePipe],
    });

    pipe = TestBed.inject(LocalDatePipe);
  });

  it('should transform date', () => {
    const testDate = '2024-05-29 17:46:49.770520';
    const expectedDate = '29.05.2024, 17:46';

    expect(pipe.transform(testDate)).toEqual(expectedDate);
  });
});
