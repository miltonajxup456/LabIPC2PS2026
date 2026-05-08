export interface Usuario {
  nombreUsuario: string,
  nombre?: string,
  passwordUser: string,
  correoElectronico?: string,
  telefono?: string,
  direccion?: string,
  cui?: string,
  fechaNac?: string,
  //informacionUsuario?: string,
  baneo?: boolean,
  saldo?: number,
  rol?: number,
  tipoRol?: string,
  descripcionEmpresa?: string
  industriaPerteneciente?: string
  sitioWeb?: string
  biografia?: string
  tarifaReferencial?: number
  nivelExperiencia?: number
  tipoNivelExperiencia?: string
}
