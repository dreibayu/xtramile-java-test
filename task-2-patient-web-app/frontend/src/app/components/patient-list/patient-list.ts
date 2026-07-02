import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Patient } from '../../models/patient.model';
import { PatientService } from '../../services/patient.service';
import { PatientFormComponent } from '../patient-form/patient-form';

@Component({
  selector: 'app-patient-list',
  imports: [CommonModule, ReactiveFormsModule, PatientFormComponent],
  templateUrl: './patient-list.html',
  styleUrl: './patient-list.css',
})
export class PatientListComponent implements OnInit {
  readonly searchControl = new FormControl('');
  readonly pageSize = 5;

  patients: Patient[] = [];
  selectedPatient: Patient | null = null;
  showForm = false;
  page = 0;
  totalPages = 0;
  totalElements = 0;
  loading = false;
  errorMessage = '';

  constructor(private readonly patientService: PatientService) {}

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients(page = this.page): void {
    this.loading = true;
    this.errorMessage = '';
    const search = this.normalizeSearch(this.searchControl.value);

    this.patientService.getPatients(page, this.pageSize, search).subscribe({
      next: (response) => {
        this.patients = response.content;
        this.page = response.page;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load patient data.';
        this.loading = false;
      },
    });
  }

  search(event?: Event): void {
    event?.preventDefault();
    this.loadPatients(0);
  }

  clearSearch(): void {
    this.searchControl.setValue('');
    this.loadPatients(0);
  }

  createPatient(): void {
    this.selectedPatient = null;
    this.showForm = true;
  }

  editPatient(patient: Patient): void {
    this.selectedPatient = patient;
    this.showForm = true;
  }

  deletePatient(patient: Patient): void {
    if (!patient.id) {
      return;
    }

    const confirmed = window.confirm(`Delete patient ${patient.fullName || patient.pid}?`);
    if (!confirmed) {
      return;
    }

    this.patientService.deletePatient(patient.id).subscribe({
      next: () => {
        const targetPage = this.patients.length === 1 && this.page > 0 ? this.page - 1 : this.page;
        this.loadPatients(targetPage);
      },
      error: () => {
        this.errorMessage = 'Unable to delete patient.';
      },
    });
  }

  handleSaved(): void {
    this.showForm = false;
    this.selectedPatient = null;
    this.loadPatients(this.page);
  }

  cancelForm(): void {
    this.showForm = false;
    this.selectedPatient = null;
  }

  previousPage(): void {
    if (this.page > 0) {
      this.loadPatients(this.page - 1);
    }
  }

  nextPage(): void {
    if (this.page + 1 < this.totalPages) {
      this.loadPatients(this.page + 1);
    }
  }

  private normalizeSearch(value: string | null): string {
    return (value || '').trim().replace(/\s+/g, ' ');
  }
}
