import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { EmployeeService } from '../services/employee.service';
import { CoreService } from '../core/core.service';
import { Employee, EmployeeImage } from '../employee';

@Component({
  selector: 'app-emp-add-edit',
  templateUrl: './emp-add-edit.component.html',
  styleUrls: ['./emp-add-edit.component.scss']
})
export class EmpAddEditComponent implements OnInit {
  empForm: FormGroup;
  editMode: boolean = false;
  imageUrl: SafeUrl | null = null;
  imageFiles: File[] = [];
  selectedFileName: string | null = null;

  constructor(
    private snackBar: MatSnackBar,
    private _fb: FormBuilder,
    private _empService: EmployeeService,
    private _dialogRef: MatDialogRef<EmpAddEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _coreService: CoreService,
    private _sanitizer: DomSanitizer
  ) {
    this.empForm = this._fb.group({
      id: 0,
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      gender: ['', Validators.required],
      image: [''] // Ajoutez cette ligne pour stocker le nom de l'image
    });

    if (data && data.employee) {
      this.editMode = true;
      this.empForm.patchValue(data.employee);
      const images = data.employee.employeeImages;
      if (images && images.length > 0) {
        this.imageUrl = this.getImageUrl(images[0]);
      }
      this.empForm.patchValue({ id: data.employee.id });
    }
  }

  ngOnInit(): void {}

  getImageUrl(image: EmployeeImage): SafeUrl | null {
    if (image && image.picByte) {
      const imageUrl = 'data:' + image.type + ';base64,' + image.picByte;
      return this._sanitizer.bypassSecurityTrustUrl(imageUrl);
    }
    return null;
  }

  onFileSelected(event: any): void {
    this.imageFiles = event.target.files;
    const file = this.imageFiles[0];
    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imageUrl = this._sanitizer.bypassSecurityTrustUrl(reader.result as string);
        //this.empForm.patchValue({ image: file.name }); // Mettez à jour le nom de l'image dans le formulaire
      };
    }
  }

  showSuccessMessage() {
    const config = new MatSnackBarConfig();
    config.duration = 3000;
    config.horizontalPosition = 'center';
    config.verticalPosition = 'top';

    this.snackBar.open('Formation Ajoutée!', 'Close', config);
  }

  addEmployee(employeeData: Employee, imageFiles: File[]): void {
    this._empService.addEmployee(employeeData, imageFiles)
      .subscribe(response => {
        if (response) {
          this.showSuccessMessage();
          this._dialogRef.close();
          location.reload();
        }
      }, error => {
        console.error('Error adding employee:', error);
      });
  }

  editEmployee(employeeData: Employee, imageFiles: File[]): void {
    this._empService.updateEmployee(employeeData.id, employeeData, imageFiles)
      .subscribe(response => {
        if (response) {
          this.showSuccessMessage();
          this._dialogRef.close();
          location.reload();
        }
      }, error => {
        console.error('Error updating employee:', error);
      });
  }

  onFormSubmit(): void {
    if (this.empForm.valid) {
      const employeeData = this.empForm.value;
      if (this.editMode) {
        this.editEmployee(employeeData, this.imageFiles);
      } else {
        this.addEmployee(employeeData, this.imageFiles);
      }
    }
  }
}
