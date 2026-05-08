import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-head',
  imports: [],
  templateUrl: './head.html',
  styleUrl: './head.css',
})
export class Head {

  @Input({required: true}) titulo: string = '';

}
