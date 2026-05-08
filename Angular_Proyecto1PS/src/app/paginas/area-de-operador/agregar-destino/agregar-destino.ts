import { Component, OnInit } from '@angular/core';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DestinoService } from '../../../restapi/destino-service/destino-service';

@Component({
  selector: 'app-agregar-destino',
  imports: [Head, RegresoMenu, ReactiveFormsModule, FormsModule],
  templateUrl: './agregar-destino.html',
  styleUrl: './agregar-destino.css',
})
export class AgregarDestino implements OnInit {

  titulo: string = 'Agregar un Destino';
  linkAnterior: string = '/area-operador';
  paisBuscado: string = '';
  paisSeleccionado: string = '';

  formDestino!: FormGroup;

  constructor(private destinoService: DestinoService) {}

  ngOnInit(): void {
    this.formDestino = new FormGroup({
      nombre: new FormControl<string>('', Validators.required),
      pais: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required),
      climaEpoca: new FormControl<string>('', Validators.required),
      url: new FormControl<string>('')
    })
  }


  guardarDestino() {
    if (this.formDestino.value.nombre.length > 40) {
      alert('El nombre no puede sobrepasar 40 caracteres');
      return;
    }
    if (this.formDestino.value.descripcion.length > 150) {
      alert('La descipcion tiene como maximo 150 caracteres');
      return;
    }
    if (this.formDestino.value.climaEpoca.length > 100) {
      alert('La descripcion del clima no puede sobrepasar los 100 caracteres');
      return;
    }
    if ((this.formDestino.value.url.length > 150)) {
      alert('El url de la imagen es demasiodo larga (max 150 caracteres)');
      return;
    }
    this.destinoService.crearDestino(this.formDestino.value).subscribe({
      next: () => {
        alert('El destino se ha creado con exito');
      },
      error: () => {alert('No se puede repetir el nombre de un destino')}
    })
  }

  setPais(paisElegido: string) {
    this.paisSeleccionado = paisElegido;
    this.formDestino.patchValue({
      pais: paisElegido
    })
  }

  paises: string[] = [
  "Afganistán",
  "Albania",
  "Alemania",
  "Andorra",
  "Angola",
  "Antigua y Barbuda",
  "Arabia Saudita",
  "Argelia",
  "Argentina",
  "Armenia",
  "Australia",
  "Austria",
  "Azerbaiyán",
  "Bahamas",
  "Bangladés",
  "Barbados",
  "Baréin",
  "Bélgica",
  "Belice",
  "Benín",
  "Bielorrusia",
  "Birmania",
  "Bolivia",
  "Bosnia y Herzegovina",
  "Botsuana",
  "Brasil",
  "Brunéi",
  "Bulgaria",
  "Burkina Faso",
  "Burundi",
  "Bután",
  "Cabo Verde",
  "Camboya",
  "Camerún",
  "Canadá",
  "Catar",
  "Chad",
  "Chile",
  "China",
  "Chipre",
  "Colombia",
  "Comoras",
  "Corea del Norte",
  "Corea del Sur",
  "Costa de Marfil",
  "Costa Rica",
  "Croacia",
  "Cuba",
  "Dinamarca",
  "Dominica",
  "Ecuador",
  "Egipto",
  "El Salvador",
  "Emiratos Árabes Unidos",
  "Eritrea",
  "Eslovaquia",
  "Eslovenia",
  "España",
  "Estados Unidos",
  "Estonia",
  "Etiopía",
  "Filipinas",
  "Finlandia",
  "Fiyi",
  "Francia",
  "Gabón",
  "Gambia",
  "Georgia",
  "Ghana",
  "Granada",
  "Grecia",
  "Guatemala",
  "Guyana",
  "Guinea",
  "Guinea-Bisáu",
  "Guinea Ecuatorial",
  "Haití",
  "Honduras",
  "Hungría",
  "India",
  "Indonesia",
  "Irak",
  "Irán",
  "Irlanda",
  "Islandia",
  "Islas Marshall",
  "Islas Salomón",
  "Israel",
  "Italia",
  "Jamaica",
  "Japón",
  "Jordania",
  "Kazajistán",
  "Kenia",
  "Kirguistán",
  "Kiribati",
  "Kuwait",
  "Laos",
  "Lesoto",
  "Letonia",
  "Líbano",
  "Liberia",
  "Libia",
  "Liechtenstein",
  "Lituania",
  "Luxemburgo",
  "Macedonia del Norte",
  "Madagascar",
  "Malasia",
  "Malaui",
  "Maldivas",
  "Malí",
  "Malta",
  "Marruecos",
  "Mauricio",
  "Mauritania",
  "México",
  "Micronesia",
  "Moldavia",
  "Mónaco",
  "Mongolia",
  "Montenegro",
  "Mozambique",
  "Namibia",
  "Nauru",
  "Nepal",
  "Nicaragua",
  "Níger",
  "Nigeria",
  "Noruega",
  "Nueva Zelanda",
  "Omán",
  "Países Bajos",
  "Pakistán",
  "Palaos",
  "Panamá",
  "Papúa Nueva Guinea",
  "Paraguay",
  "Perú",
  "Polonia",
  "Portugal",
  "Reino Unido",
  "República Centroafricana",
  "República Checa",
  "República del Congo",
  "República Democrática del Congo",
  "República Dominicana",
  "Ruanda",
  "Rumanía",
  "Rusia",
  "Samoa",
  "San Cristóbal y Nieves",
  "San Marino",
  "San Vicente y las Granadinas",
  "Santa Lucía",
  "Santo Tomé y Príncipe",
  "Senegal",
  "Serbia",
  "Seychelles",
  "Sierra Leona",
  "Singapur",
  "Siria",
  "Somalia",
  "Sri Lanka",
  "Suazilandia",
  "Sudáfrica",
  "Sudán",
  "Sudán del Sur",
  "Suecia",
  "Suiza",
  "Surinam",
  "Tailandia",
  "Tanzania",
  "Tayikistán",
  "Timor Oriental",
  "Togo",
  "Tonga",
  "Trinidad y Tobago",
  "Túnez",
  "Turkmenistán",
  "Turquía",
  "Tuvalu",
  "Ucrania",
  "Uganda",
  "Uruguay",
  "Uzbekistán",
  "Vanuatu",
  "Vaticano",
  "Venezuela",
  "Vietnam",
  "Yemen",
  "Yibuti",
  "Zambia",
  "Zimbabue"
]

}
