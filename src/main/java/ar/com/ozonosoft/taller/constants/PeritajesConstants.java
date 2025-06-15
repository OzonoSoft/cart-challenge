package ar.com.ozonosoft.taller.constants;

public interface PeritajesConstants {

    public static final Integer PERITAJE_STATUS_PENDING = 1;
    public static final Integer PERITAJE_STATUS_CONFIRMED = 2;
    public static final Integer PERITAJE_STATUS_CANCELLED = 3;
    public static final Integer PERITAJE_STATUS_REVISION = 4;
    public static final String ERROR_EMPTY_FIELDS = "Faltan completar campos obligatorios";
    public static final String ERROR_CLIENT_NOT_FOUND = "No se encontro el cliente";
    public static final String ERROR_COMPANY_NOT_FOUND = "No se encontro la empresa";
    public static final String ERROR_VEHICLE_NOT_FOUND = "No se encontro el vehiculo";
    public static final String ERROR_TALLER_NOT_FOUND = "No existe el taller";
    public static final String ERROR_INTERNAL = "Error inesperado al guardar el registro";
    public static final String SUCCESS = "El peritaje fue cargado correctamente";
}
