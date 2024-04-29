import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Artikl } from 'src/app/models/artikl';
import { ArtiklService } from 'src/app/services/artikl.service';

@Component({
  selector: 'app-artikl-dialog',
  templateUrl: './artikl-dialog.component.html',
  styleUrls: ['./artikl-dialog.component.css']
})
export class ArtiklDialogComponent {

  flag!:number;

  constructor(
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<Artikl>,
    @Inject (MAT_DIALOG_DATA) public data: Artikl,
    public service: ArtiklService
  ){}

  public add(){
    this.service.createArtikl(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Artikl sa nazivom ${data.naziv} je uspesno dodat`, 
        `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno dodavanje`, `Zatvori`, {duration:1500});
    }
  }

  public update(){
    this.service.updateArtikl(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Artikl sa nazivom ${data.naziv} je uspesno azuriran`, 
        `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno azuriranje`, `Zatvori`, {duration:1500});
    }
  }

  public delete(){
    this.service.deleteArtikl(this.data.id).subscribe(
      (data) => {
        this.snackBar.open(`${data}`, 
        `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno brisanje`, `Zatvori`, {duration:1500});
    }
  }

  public cancel(){
    this.dialogRef.close();
    this.snackBar.open(`Odustali ste od izmena`, `Zatvori`, {duration:2500});
  }
}
