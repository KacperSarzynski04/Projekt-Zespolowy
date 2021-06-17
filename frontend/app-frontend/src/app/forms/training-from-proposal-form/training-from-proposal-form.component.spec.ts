import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingFromProposalFormComponent } from './training-from-proposal-form.component';

describe('EditTrainingFormComponent', () => {
  let component: TrainingFromProposalFormComponent;
  let fixture: ComponentFixture<TrainingFromProposalFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrainingFromProposalFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingFromProposalFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
