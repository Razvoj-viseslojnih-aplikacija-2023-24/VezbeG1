import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Artikl } from 'src/app/models/artikl';
import { StavkaPorudzbine } from 'src/app/models/stavka-porudzbine';
import { StavkaPorudzbineService } from 'src/app/services/stavka-porudzbine.service';
import { StavkaPorudzbineDialogComponent } from '../../dialogs/stavka-porudzbine-dialog/stavka-porudzbine-dialog.component';

@Component({
  selector: 'app-stavka-porudzbine',
  templateUrl: './stavka-porudzbine.component.html',
  styleUrls: ['./stavka-porudzbine.component.css']
})
export class StavkaPorudzbineComponent {

  dataSource!: MatTableDataSource<StavkaPorudzbine>;
  displayedColumns = ['id','redniBroj','kolicina','jedinicaMere','cena','artikl','actions'];
  subscription!:Subscription;

  constructor(private stavkaPorudzbineService: StavkaPorudzbineService,
              public dialog: MatDialog){

  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(){
    this.subscription = this.stavkaPorudzbineService.getAllStavkaPorudzbines().subscribe(
      data => {this.dataSource = new MatTableDataSource(data);
              console.log(data)}),
      (error:Error) => {console.log(error.name + ' ' + error.message);}
  }

  public openDialog(flag:number, id?:number, redniBroj?:number, kolicina?:number, jedinicaMere?:string, cena?:number, artikl?:Artikl ):void{
    const dialogRef = this.dialog.open(StavkaPorudzbineDialogComponent, {data:{id,redniBroj,kolicina,jedinicaMere,cena,artikl}});
    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(
      result =>{
        if(result == 1){
          this.loadData();
        }
      }
    )
  }
}
