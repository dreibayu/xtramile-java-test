import { Component } from '@angular/core';
import { PatientListComponent } from './components/patient-list/patient-list';

@Component({
  selector: 'app-root',
  imports: [PatientListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {}