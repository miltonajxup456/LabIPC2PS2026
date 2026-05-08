import { Component, Input } from '@angular/core';
import { required } from '@angular/forms/signals';

@Component({
  selector: 'app-header',
  imports: [],
  template: '<h1>{{titulo}}</h1>',
  styleUrl: './header.css',
})
export class Header {

  @Input({required: true}) titulo!: string;

}
