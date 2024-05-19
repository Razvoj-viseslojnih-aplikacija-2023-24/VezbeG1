import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Artikl } from 'src/app/models/artikl';
import { StavkaPorudzbine } from 'src/app/models/stavka-porudzbine';
import { ArtiklService } from 'src/app/services/artikl.service';
import { StavkaPorudzbineService } from 'src/app/services/stavka-porudzbine.service';

@Component({
  selector: 'app-stavka-porudzbine-dialog',
  templateUrl: './stavka-porudzbine-dialog.component.html',
  styleUrls: ['./stavka-porudzbine-dialog.component.css']
})
export class StavkaPorudzbineDialogComponent {

  flag!: number;
  artikli!: Artikl[];

  constructor(
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<StavkaPorudzbine>,
    @Inject (MAT_DIALOG_DATA) public data: StavkaPorudzbine,
    public service:StavkaPorudzbineService,
    public artiklService:ArtiklService
  ){}

  ngOnInit(): void {
    this.artiklService.getAllArtikls().subscribe(
      (data) => {
        this.artikli = data;
      }
    )
  }

  public compare(a:any, b:any){
    return a.id == b.id;
  }

  public add(){
    this.service.addStavkaPorudzbine(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Uspesno dodata stavka porudzbine sa ID: ${data.id}`,
                            `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno dodavanje`, `Zatvori`, {duration:1000});
    }
  }

  public update(){
    this.service.updateStavkaPorudzbine(this.data).subscribe(
      (data) => {
        this.snackBar.open(`Uspesno azurirana stavka porudzbine sa ID: ${data.id}`,
                            `U redu`, {duration:2500});
      }
    ),
    (error:Error) => {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open(`Neuspesno azuriranje`, `Zatvori`, {duration:1000});
    }
  }

  public delete(){
    this.service.deleteStavkaPorudzbine(this.data.id).subscribe(
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
