import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})

export class Apiurl {

  private readonly API_URL: string = 'http://localhost:8080/Proyecto2_IPC2_PS2026/';

  public getUrl(): string {
    return this.API_URL;
  }

}
