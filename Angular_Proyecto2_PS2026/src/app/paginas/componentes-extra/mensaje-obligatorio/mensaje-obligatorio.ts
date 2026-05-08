import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-mensaje-obligatorio',
  imports: [],
  templateUrl: './mensaje-obligatorio.html',
  styles: `p {color: red; margin-top: 50px;}`
})
export class MensajeObligatorio {

  @Input({required: true}) texto: string | number | null = null;
  @Input({required: true}) accion: string = '';

}
