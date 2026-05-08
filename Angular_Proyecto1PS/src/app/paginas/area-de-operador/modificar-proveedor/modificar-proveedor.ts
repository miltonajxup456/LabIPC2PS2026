import { Component, OnInit, signal } from '@angular/core';
import { Head } from "../../../head/head";
import { RegresoMenu } from "../../regreso-menu/regreso-menu";
import { FormsModule } from '@angular/forms';
import { ProveedorService } from '../../../restapi/proveedor-service/proveedor-service';
import { Proveedor } from '../../../modelos/proveedor/proveedor';

@Component({
  selector: 'app-modificar-proveedor',
  imports: [Head, RegresoMenu, FormsModule],
  templateUrl: './modificar-proveedor.html',
  styleUrl: './modificar-proveedor.css',
})
export class ModificarProveedor implements OnInit {

  titulo: string = 'Modificar los Proveedores';
  linkAnterior: string = '/area-operador';
  paisBuscado: string = '';
  pais: string = '';
  nombre: string = '';
  tipoServicio: number = 0;

  todosProveedores = signal<Proveedor[]>([]);
  proveedorElegido = signal<Proveedor | null>(null);

  constructor(private proveedorService: ProveedorService) {}

  ngOnInit(): void {
    this.proveedorService.getProveedores().subscribe({
      next: (proveedores: Proveedor[]) => {
        this.todosProveedores.set(proveedores);
      }
    })
  }

  setProveedor(eleccion: Proveedor) {
    this.proveedorElegido.set(eleccion);
    this.pais = eleccion.pais;
    this.nombre = eleccion.nombre;
    this.tipoServicio = eleccion.tipoServicio;
  }

  setPais(pais: string) {
    this.pais = pais;
  }

  setTipoServicio(tipo: number) {
    this.tipoServicio = tipo;
  }

  guardarProveedor() {
    if(!this.pais) {
      alert('No se ha seleccionado un pais');
      return;
    }
    if  (!this.proveedorElegido()) {
      alert('Aun no se ha seleccionado un Proveedor');
      return;
    }
    if(!this.nombre) {
      alert('No se ha asignado un nombre');
      return;
    }
    if (this.nombre.length > 40) {
      alert('El nombre sobrepasa los 40 caracteres');
      return;
    }
    if (!this.tipoServicio) {
      alert('Aun no se ha seleccionado un tipo de Servicio')
    }
    const nuevo: Proveedor = {
      nombre: this.nombre,
      pais: this.pais,
      tipoServicio: this.tipoServicio
    }
    this.proveedorService.modificarProveedor(this.proveedorElegido()!.idProveedor!, nuevo).subscribe({
      next: () => {
        alert('Se ha guardado el proveedor con exito');
        this.pais = '';
        this.nombre = '';
        this.tipoServicio = 0;
      },
      error: () => {alert('No se puede repetir el nombre de un proveedor')}
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
