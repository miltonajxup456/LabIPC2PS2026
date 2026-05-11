import { Component, signal } from '@angular/core';
import { Usuario } from '../../../models/usuario/usuario';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { GuardadoUsuario } from '../../login/guardado-usuario/guardado-usuario';
import { UsuarioService } from '../../../restApi/usuario-service/usuario-service';
import { Header } from "../../../componentes/header/header";
import { MenuSecundario } from "../../menu-secundario/menu-secundario";
import { MensajeObligatorio } from "../../componentes-extra/mensaje-obligatorio/mensaje-obligatorio";
import { HabilidadService } from '../../../restApi/habilidad-service/habilidad-service';
import { Habilidad } from '../../../models/habilidad/habilidad';
import { ListaHabilidades } from '../../../models/extras/habilidades/habilidades';

@Component({
  selector: 'app-editar-informacion-cliente',
  imports: [Header, MenuSecundario, ReactiveFormsModule, MensajeObligatorio, FormsModule],
  templateUrl: './editar-informacion-cliente.html',
  styleUrl: './editar-informacion-cliente.css',
})
export class EditarInformacionCliente {

  titulo: string = 'Editar Perfil';
  linkAnterior!: string;
  accionCliente: string = 'Publicar un Proyecto';
  accionFreelancer: string = 'Aplicar para Proyectos';
  usuarioLogeado = signal<Usuario | null>(null);
  habilidades = signal<Habilidad[]>([]);
  idHabilidades = signal<number[]>([]);
  contrasenia: string | null = null;

  formUsuario!: FormGroup;

  constructor(
    private guardado: GuardadoUsuario, 
    private usuarioService: UsuarioService, 
    private habilidadService: HabilidadService) {}

  ngOnInit(): void {
    this.usuarioLogeado.set(this.guardado.getUsuarioLogeado());
    this.formUsuario = new FormGroup ({
      nombre: new FormControl<string>(this.usuarioLogeado()?.nombre || '' ,Validators.required),
      passwordUser: new FormControl<string | null>(null),
      correoElectronico: new FormControl<string>(this.usuarioLogeado()?.correoElectronico || '' ,Validators.required),
      telefono: new FormControl<string>(this.usuarioLogeado()?.telefono || '' ,Validators.required),
      direccion: new FormControl<string>(this.usuarioLogeado()?.direccion || '' ,Validators.required),
      cui: new FormControl<string>(this.usuarioLogeado()?.cui || '' ,Validators.required),
      fechaNac: new FormControl<string>(this.usuarioLogeado()?.fechaNac || '' ,Validators.required),
      descripcionEmpresa: new FormControl<string>(this.usuarioLogeado()?.descripcionEmpresa || ''),
      industriaPerteneciente: new FormControl<string>(this.usuarioLogeado()?.industriaPerteneciente || ''),
      sitioWeb: new FormControl<string>(this.usuarioLogeado()?.sitioWeb || ''),
      biografia: new FormControl<string>(this.usuarioLogeado()?.biografia || ''),
      tarifaReferencial: new FormControl<number>(this.usuarioLogeado()?.tarifaReferencial || 0),
      nivelExperiencia: new FormControl<number>(this.usuarioLogeado()?.nivelExperiencia || 0)
    });

    this.linkAnterior = '/acceso-cliente';
    if (this.usuarioLogeado()?.rol === 3) {
      this.linkAnterior = '/acceso-freelancer'
      this.formUsuario.patchValue({
        nivelExperiencia: this.usuarioLogeado()?.nivelExperiencia!
      });
      this.habilidadService.getHabilidades().subscribe({
        next: (habilidades: Habilidad[]) => this.habilidades.set(habilidades)
      });

      this.usuarioService.getHabilidadesFreelancer(this.usuarioLogeado()?.nombreUsuario!).subscribe({
        next: (habilidadesFree: number[]) => this.idHabilidades.set(habilidadesFree)
      });
    }
  }

  elegirNivel(nivel: number): void {
    this.formUsuario.patchValue({
      nivelExperiencia: nivel
    });
  }

  guardarHabilidad(idHabilidad: number): void {
    if (this.habilidadGuardada(idHabilidad)) {
      this.idHabilidades.update(todos => todos.filter(id => id !== idHabilidad));
    } else {
      this.idHabilidades.update(todos => [...todos, idHabilidad]);
    }
  }

  habilidadGuardada(idHabilidad: number): boolean {
    for (let i = 0; i < this.idHabilidades().length; i++) {
      if (this.idHabilidades()[i] === idHabilidad) {
        return true;
      }
    }
    return false;
  }

  guardarCambios(): void {
    const rol = this.usuarioLogeado()?.rol;
    if (rol === 2) {
      this.guardarCambiosCliente();
    }
    if (rol === 3) {
      this.guardarCambiosFreelancer();
    }
  }

  guardarCambiosCliente(): void {

    this.revisarContraseña();

    const miForm = this.formUsuario.value;
    if (!miForm.descripcionEmpresa) {
      alert('Es necesario agregar la descripcion de la Empresa');
      return;
    }
    if (!miForm.industriaPerteneciente) {
      alert('Es necesario agregar el sector o industria donde trabaja');
      return;
    }
    if (!miForm.sitioWeb) {
      alert('Es necesario agregar el sitio web');
      return;
    }
    this.usuarioService.actualizarCliente(this.usuarioLogeado()?.nombreUsuario!, miForm).subscribe({
      next: () => {
        alert('Se han guardado los cambios con exito');
        this.contrasenia = null;
        this.usuarioService.getUsuario(this.usuarioLogeado()?.nombreUsuario!).subscribe({
          next: (actual: Usuario) => {
            this.usuarioLogeado.set(actual);
            this.guardado.setUsuarioLogeado(actual);
          }
        })
      }
    })
  }

  guardarCambiosFreelancer(): void  {

    this.revisarContraseña();

    const miForm = this.formUsuario.value;
    if (!miForm.biografia) {
      alert('Es necesario agregar una descripcion en Biografia');
      return;
    }
    if (miForm.tarifaReferencial <= 0) {
      alert('La tarifa referencial debe ser mayor a 0');
      return;
    }
    if (miForm.nivelExperiencia < 1 || miForm.nivelExperiencia > 3) {
      alert('Aun no se ha escogido un nivel de experiencia');
      return;
    }
    if (this.idHabilidades().length <= 0) {
      alert('Aun no se han seleccionado habilidades');
      return;
    }
    this.usuarioService.actualizarFreelancer(this.usuarioLogeado()?.nombreUsuario!, miForm).subscribe({
      next: () => {
        alert('Los cambios se han guardado con exito');
        this.contrasenia = null;
        this.usuarioService.getUsuario(this.usuarioLogeado()?.nombreUsuario!).subscribe({
          next: (actual: Usuario) => {
            this.usuarioLogeado.set(actual);
            this.guardado.setUsuarioLogeado(actual);
          }
        });
      }
    });
    const lista: ListaHabilidades = {
      listaIds: this.idHabilidades()
    }
    this.usuarioService.agregarHabilidadesFreelancer(this.usuarioLogeado()?.nombreUsuario!, lista).subscribe({
      next: () => {
        alert('Las habilidades se han guardado con exito');
      }
    })
  }

  private revisarContraseña(): void {
    if (this.contrasenia != null && this.contrasenia.trim()) {
      this.formUsuario.patchValue({
        passwordUser: this.contrasenia
      });
    }
  }

}
