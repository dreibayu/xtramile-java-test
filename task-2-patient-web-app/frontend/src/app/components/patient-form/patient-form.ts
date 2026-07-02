import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Gender, Patient } from '../../models/patient.model';
import { PatientService } from '../../services/patient.service';

@Component({
  selector: 'app-patient-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './patient-form.html',
  styleUrl: './patient-form.css',
})
export class PatientFormComponent implements OnChanges {
  @Input() patient: Patient | null = null;
  @Output() saved = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  readonly genderOptions: Gender[] = ['MALE', 'FEMALE', 'OTHER'];
  readonly stateOptions = ['ACT', 'NSW', 'NT', 'QLD', 'SA', 'TAS', 'VIC', 'WA'];

  saving = false;
  errorMessage = '';

  private readonly formBuilder = inject(FormBuilder);
  private readonly patientService = inject(PatientService);

  readonly form = this.formBuilder.group({
    pid: ['', [Validators.required, Validators.maxLength(50)]],
    firstName: ['', [Validators.required, Validators.maxLength(100)]],
    lastName: ['', [Validators.required, Validators.maxLength(100)]],
    dateOfBirth: ['', [Validators.required]],
    gender: ['', [Validators.required]],
    phoneNo: ['', [Validators.required, Validators.pattern(/^\+?[0-9\s()-]{8,20}$/)]],
    address: ['', [Validators.required, Validators.maxLength(255)]],
    suburb: ['', [Validators.required, Validators.maxLength(100)]],
    state: ['', [Validators.required]],
    postcode: ['', [Validators.required, Validators.pattern(/^\d{4}$/)]],
  });


  ngOnChanges(changes: SimpleChanges): void {
    if (changes['patient']) {
      this.errorMessage = '';
      if (this.patient) {
        this.form.reset({
          pid: this.patient.pid,
          firstName: this.patient.firstName,
          lastName: this.patient.lastName,
          dateOfBirth: this.patient.dateOfBirth,
          gender: this.patient.gender,
          phoneNo: this.patient.phoneNo,
          address: this.patient.address,
          suburb: this.patient.suburb,
          state: this.patient.state,
          postcode: this.patient.postcode,
        });
      } else {
        this.form.reset();
      }
    }
  }

  get isEditMode(): boolean {
    return !!this.patient?.id;
  }

  submit(): void {
    this.errorMessage = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = this.form.getRawValue() as Patient;
    this.saving = true;

    const request$ = this.isEditMode && this.patient?.id
      ? this.patientService.updatePatient(this.patient.id, payload)
      : this.patientService.createPatient(payload);

    request$.subscribe({
      next: () => {
        this.saving = false;
        this.saved.emit();
      },
      error: (error) => {
        this.saving = false;
        this.errorMessage = error?.error?.message || 'Unable to save patient data.';
      },
    });
  }

  cancel(): void {
    this.cancelled.emit();
  }

  hasError(controlName: string, errorName: string): boolean {
    const control = this.form.get(controlName);
    return !!control && control.touched && control.hasError(errorName);
  }
}