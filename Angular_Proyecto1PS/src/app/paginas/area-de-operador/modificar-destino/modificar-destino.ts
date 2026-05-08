import { Component, signal } from '@angular/core';
import { DestinoService } from '../../../restapi/destino-service/destino-service';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Destino } from '../../../modelos/destino/destino';

@Component({
  selector: 'app-modificar-destino',
  imports: [Head, RegresoMenu, FormsModule, ReactiveFormsModule],
  templateUrl: './modificar-destino.html',
  styleUrl: './modificar-destino.css',
})
export class ModificarDestino {

  titulo: string = 'Modificacion de Destinos';
  linkAnterior: string = '/area-operador';
  paisBuscado: string = '';
  paisSeleccionado: string = '';

  formDestino!: FormGroup;

  allDestinos = signal<Destino[]>([]);
  destinoElegido = signal<Destino | null>(null);

  constructor(private destinoService: DestinoService) {}

  ngOnInit(): void {
    this.formDestino = new FormGroup({
      nombre: new FormControl<string>('', Validators.required),
      pais: new FormControl<string>('', Validators.required),
      descripcion: new FormControl<string>('', Validators.required),
      climaEpoca: new FormControl<string>('', Validators.required),
      url: new FormControl<string>('')
    })

    this.destinoService.getAllDestinos().subscribe({
      next: (destinos: Destino[]) => {
        this.allDestinos.set(destinos);
      }
    })
  }
  guardarDestino() {
    const form = this.formDestino.value;
    if (form.nombre.length > 40) {
      alert('El nombre no puede sobrepasar 40 caracteres');
      return;
    }
    if (form.descripcion.length > 150) {
      alert('La descipcion tiene como maximo 150 caracteres');
      return;
    }
    if (form.climaEpoca.length > 100) {
      alert('La descripcion del clima no puede sobrepasar los 100 caracteres');
      return;
    }
    if ((form.url.length > 150)) {
      alert('El url de la imagen es demasiodo larga (max 150 caracteres)');
      return;
    }
    this.destinoService.actualizarDestino(this.destinoElegido()!.idDestino!, form).subscribe({
      next: () => {
        alert('El destino se ha actualizado con exito'); 
        this.destinoElegido.update(destino => destino ? {...destino, ...form}: destino);
        this.allDestinos.update(todo => todo.filter(destino => destino.idDestino != this.destinoElegido()?.idDestino));
        this.allDestinos.update(todo => todo ? [...todo, this.destinoElegido()!]: todo);
        this.paisSeleccionado = '';
        this.destinoElegido.set(null);
        this.formDestino.reset();
      }
    })
  }

  eliminarDestino() {
    if (!this.destinoElegido()) {
      alert('Aun no se ha seleccionado un destino para eliminarlo');
      return;
    }
    console.log(this.destinoElegido())
    this.destinoService.borrarDestino(this.destinoElegido()!).subscribe({
      next: () => {
        alert('El Destino se ha borrado con exito');

        this.allDestinos.update(dest => dest.filter(todo => todo.idDestino !== this.destinoElegido()?.idDestino));
        this.paisSeleccionado = ''
        this.destinoElegido.set(null);
        this.formDestino.reset();
      }
    })
  }

  setDestino(eleccion: Destino) {
    this.destinoElegido.set(eleccion);
    this.paisSeleccionado = eleccion.pais;
    this.formDestino.patchValue({
      nombre: eleccion.nombre,
      pais: eleccion.pais,
      descripcion: eleccion.descripcion,
      climaEpoca: eleccion.climaEpoca,
      url: eleccion.url
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
