import { Component, Input } from '@angular/core';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-menu-secundario',
  imports: [RouterLink],
  templateUrl: './menu-secundario.html',
  styles: '',
})
export class MenuSecundario {

  @Input({required: true}) linkAnterior: string = '';

}
