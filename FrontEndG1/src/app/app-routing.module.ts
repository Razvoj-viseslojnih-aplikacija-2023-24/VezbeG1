import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArtiklComponent } from './components/main/artikl/artikl.component';
import { DobavljacComponent } from './components/main/dobavljac/dobavljac.component';
import { PorudzbinaComponent } from './components/main/porudzbina/porudzbina.component';
import { StavkaPorudzbineComponent } from './components/main/stavka-porudzbine/stavka-porudzbine.component';
import { AboutComponent } from './components/utility/about/about.component';
import { AuthorComponent } from './components/utility/author/author.component';
import { HomeComponent } from './components/utility/home/home.component';

const routes: Routes = [
  {path:'artikl', component:ArtiklComponent},
  {path:'dobavljac', component:DobavljacComponent},
  {path:'porudzbina', component:PorudzbinaComponent},
  {path:'stavka-porudzbine', component:StavkaPorudzbineComponent},
  {path:'home', component:HomeComponent},
  {path:'about', component:AboutComponent},
  {path:'author', component:AuthorComponent},
  {path:'', component:HomeComponent, pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
