import { Artikl } from "./artikl";
import { Porudzbina } from "./porudzbina";

export class StavkaPorudzbine{
    id!:number;
    redniBroj!:number;
    kolicina!:number;
    cena!:number;
    jedinicaMere!:string;
    artikl!:Artikl;
    porudzbina!:Porudzbina;
}