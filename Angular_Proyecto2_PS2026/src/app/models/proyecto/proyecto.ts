export interface Proyecto {
  idProyecto?: number,
  titulo: string,
  descripcion: string,
  presupuesto: number,
  fechaLimite: string,
  cliente: string,
  categoria: number,
  tipoCategoria?: string,
  estado: number,
  tipoEstado?: string,
  freelancer?: string
  comision?: number
}
