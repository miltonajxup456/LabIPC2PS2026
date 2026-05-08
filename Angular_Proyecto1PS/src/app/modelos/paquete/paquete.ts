export interface Paquete {
  idPaquete?: number,
  nombre: string,
  duracionDias: number,
  precio: number,
  capacidad: number,
  descripcion?: string,
  estadoPaquete?: boolean,
  idDestino: number,
  destino?: string,
  reservacionesHechas?: number
}
