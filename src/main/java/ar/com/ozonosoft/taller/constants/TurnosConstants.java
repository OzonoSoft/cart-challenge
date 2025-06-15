package ar.com.ozonosoft.taller.constants;

public interface TurnosConstants {

    public static final Integer TURNO_STATUS_NUEVO = 1;
    public static final Integer TURNO_STATUS_AGENDADO = 2;
    public static final Integer TURNO_STATUS_REPROGRAMADO = 3;
    public static final Integer TURNO_STATUS_FINALIZADO = 4;
    public static final Integer TURNO_STATUS_CANCELADO = 5;
    public static final String ERROR_EMPTY_FIELDS = "Faltan completar campos obligatorios";
    public static final String ERROR_CLIENT_NOT_FOUND = "No se encontro el cliente";
    public static final String ERROR_VEHICLE_NOT_FOUND = "No se encontro el vehiculo";
    public static final String ERROR_TALLER_NOT_FOUND = "No existe el taller";
    public static final String ERROR_INTERNAL = "Error inesperado al guardar el registro";
    public static final String SUCCESS = "El turno fue cargado correctamente";
}
