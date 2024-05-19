import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Dobavljac } from 'src/app/models/dobavljac';
import { DobavljacService } from 'src/app/services/dobavljac.service';

@Component({
  selector: 'app-dobavljac-dialog',
  templateUrl: './dobavljac-dialog.component.html',
  styleUrls: ['./dobavljac-dialog.component.css']
})
export class DobavljacDialogComponent {

  flag!: number;

  constructor(
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<Dobavljac>,
    @Inject (MAT_DIALOG_DATA) public data: Dobavljac,
    public service:DobavljacService
  ){}

  public add(){
    this.service.addDobavljac(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Uspesno dodat dobavljac sa nazivom: ${this.data.naziv}`,
                            `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno dodavanje`, `Zatvori`, {duration:1000});
    }
  }

  public update(){
    this.service.updateDobavljac(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Uspesno azuriran dobavljac sa nazivom: ${this.data.naziv}`,
                            `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno azuriranje`, `Zatvori`, {duration:1000});
    }
  }

  public delete(){
    this.service.deleteDobavljac(this.data.id).subscribe(
      (data) => {
        this.snackBar.open(`${data}`,
                            `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno brisanje`, `Zatvori`, {duration:1000});
    }
  }

  public cancel(){
    this.dialogRef.close();
    this.snackBar.open(`Odustali ste od izmena`, `Zatvori`, {duration:2500});
  }
}
