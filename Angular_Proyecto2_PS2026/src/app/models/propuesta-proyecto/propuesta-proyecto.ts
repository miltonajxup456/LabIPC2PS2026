export interface PropuestaProyecto {
  idPropuesta?: number,
  presentacion: string,
  montoOfertado: number,
  plazoEntrega: number,
  fecha?: string
  freelancer: string,
  proyecto: number
  estado?: number
  tipoEstado?: string
  calificacionPromedio?: number
}
