import { Injectable } from "@angular/core";

@Injectable ({
  providedIn: 'root'
})

export class RestUrl {
  private readonly API_URL = 'http://localhost:8080/Proyecto1_IPC2_PS/';

  public getApiURL(): string {
    return this.API_URL;
  }
}
 