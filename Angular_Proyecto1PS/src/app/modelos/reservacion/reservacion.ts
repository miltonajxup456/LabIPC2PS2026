export interface Reservacion {
  numReservacion?: number,
  fechaViaje: string,
  fechaCreacion?: string,
  cantPasajeros: number,
  costo: number,
  agenteDeRegistro: number,
  paquete: number,
  estado?: number,
  dineroCancelado?: number,
  nombrePaquete: string,
  nombreCliente?: string,
  dpiPasajeros: string[]
}
