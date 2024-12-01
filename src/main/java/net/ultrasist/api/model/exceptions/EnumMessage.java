package net.ultrasist.api.model.exceptions;

import org.springframework.http.HttpStatus;

public enum EnumMessage {
    
    /**
     * Constante para credenciales inválidas.<br/>
     * Parámetros extras: NINGUNO.
     */
    BAD_CREDENTIALS(
            1001,
            "Credenciales erróneas",
            "Su clave o su usuario (o ambos) no corresponden a un usuario válido",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante para errores en una transacción de bitcoins.<br/>
     * Parámetros extras: NINGUNO.
     */
    BITCOIN_TRANSACTION(
            1002,
            "Transaccion de BTC fallida",
            "La transacción no pudo ser llevada a cabo",
            HttpStatus.BAD_REQUEST),
    
    /**
     * Constante para error en usuario bloqueados.<br/>
     * Parámetros extras: 2 (minutos y segundos).
     */
    BLOCKED_USER(
            1003,
            "Usuario bloqueado",
            "Bloqueo de seguridad por %d minutos y %d segundos",
            HttpStatus.BAD_REQUEST),
    
    /**
     * Constante para error en el máximo número de intentos fallidos al sistema.<br/>
     * Parámetros extras: 1 (intentos fallidos).
     */
    MAX_FAILED_LOGIN_EXCEPTION(
            1004,
            "User bloqueado",
            "El usuario ha sdo bloqueado por exceder el máximo número (%d) de intentos fallidos al sistema",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante de error enmascarado para bases de datos.<br/>
     * Parámetros extras: 1 (ID para el administrador).
     */
    DATABASE(
            901,
            "Error en la base de datos",
            "Consulte a su adimistrador. ID: %s",
            HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * Constante de error para usuario des habilitado.<br/>
     * Parámetros extras: NINGUNO.
     */
    DISABLED_USER(
            1005,
            "El usuario está deshabilitado",
            "Consulte a su administrador",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante de error para carga de un archivo.<br/>
     * Parámetros extras: 1 (nombre del archivo a cargar).
     */
    FILE_UPLOAD(
            902,
            "Error en la carga de archivos",
            "El archivo %S no pudo ser cargado",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante de error para Google captcha erróneo.<br/>
     * Parámetros extras: NINGUNO.
     */
    GOOGLE_CAPTCHA(
            1006,
            "Captcha Incorrecto",
            "El captcha proporcionado no corresponde con el presentado",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante para errores internos enmascarados.<br/>
     * Parámetros extras: 1 (cadena enmascarada).
     */
    INTERNAL_SERVER(
            903,
            "Error Interno",
            "Info: $s",
            HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * Constante para errores de envio de correo.<br/>
     * Parámetros extras: NINGUNO.
     */
    SEND_MAIL(
            1007,
            "Envío de correo fallido",
            "Ha ocurrido un error al enviar un correo",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante para errores (enmascarados) en la capa de mappers.<br/>
     * Parámetros extras: 1 (detalle enmascarado).
     */
    MAPPER_CALL(
            904,
            "Consulte con su administrador",
            "Detalle: %s",
            HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * Constante para error de recurso no encontrado.<br/>
     * Parámetros extras: 1 (nombre del recurso no encontrado).
     */
    NOT_FOUND(
            1008,
            "Recurso no encontrado",
            "El recurso %s no pudo ser encontrado",
            HttpStatus.NOT_FOUND),

    /**
     * Constante para error en la generación de un PDF.<br/>
     * Parámetros extras: 1 (nombre del PDF que no pudo ser procesado).
     */
    PROCESS_PDF(
            905,
            "Error al procesar el pdf",
            "El pdf %s no pudo ser procesado",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante para errores de validación.<br/>
     * Parámetros extras: 1 (cadena con errores de validación).
     */
    STRENGTH_PASSWORD_VALIDATOR(
            1009,
            "Error al validar la fortalez del password",
            "El password no cumplió con las características requeridas: %s",
            HttpStatus.NOT_ACCEPTABLE),

    /**
     * Constante para error en token expirado.<br/>
     * Parámetros extras: NINGUNO.
     */
    TOKEN_EXPIRED(
            1010,
            "Token Expirado",
            "El token proporcionado ya no es válido y ha expirado. Favor de solicitar uno nuevo",
            HttpStatus.FORBIDDEN),

    /**
     * Constante para error en token inválido.<br/>
     * Parámetros extras: NINGUNO.
     */
    TOKEN_INVALID(
            1110,
            "Token Inválido",
            "El token proporcionado no es válido",
            HttpStatus.FORBIDDEN),

    /**
     * Constante para error en la firma del token.<br/>
     * Parámetros extras: NINGUNO.
     */
    TOKEN_INVALID_SIGNATURE(
            1111,
            "Firma del token inválida",
            "El token proporcionado no posee una firma válida",
            HttpStatus.FORBIDDEN),

    /**
     * Constante para error en la estructura del token.<br/>
     * Parámetros extras: NINGUNO.
     */
    TOKEN_INVALID_STRUCTURE(
            801,
            "Estructura inválida del Token",
            "El token proporcionado no posee una estructura válida: %s",
            HttpStatus.FORBIDDEN),

    /**
     * Constante error cuando el token no existe.<br/>
     * Parámetros extras: NINGUNO.
     */
    TOKEN_NOT_EXIST(
            1011,
            "Token inexistente",
            "El token referido no existe",
            HttpStatus.UNAUTHORIZED),

    /**
     * Constante para registro de usuarios que ya están registrados.<br/>
     * Parámetros extras: 1 (nombre del usuario a registrar).
     */
    USER_ALREADY_EXISTS(
            1013,
            "User existente",
            "El usuario %s ya existe en el sistema",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante para error al buscar un usuario que no existe.<br/>
     * Parámetros extras: 1 (nombre del usuario no existente).
     */
    USER_NOT_EXIST(
            1014,
            "User inexistente",
            "No ha sido posible encontrar al usuario %s en la base de datos",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante para error que indica que se debe esperar antes del siguiente intento de ingreso.<br/>
     * Parámetros extras: 2 (minutos y segundos).
     */
    WAIT_LOGIN(
            1015,
            "User bloqueado",
            "Tiempo de espera para eliminar el bloqueo %d minutos %d segundos",
            HttpStatus.UNAUTHORIZED),

    /**
     * Constante para indicar que el generador del token no se pudo verificar.<br/>
     * Parámetros extras: NINGUNO.
     */
    ISSUER_NOT_VERIFIED(
            1016,
            "Issuer no verificado",
            "No ha sido posible verificar al issuer del presente token",
            HttpStatus.UNAUTHORIZED),

    /**
     * Constante para indicar que el token proporcionado es erróneo.<br/>
     * Parámetros extras: NINGUNO.
     */
    WRONG_TOKEN(
            1017,
            "Token erróneo",
            "El token proporcionado es erróneo",
            HttpStatus.UNAUTHORIZED),

    /**
     * Constante para error al generar un PDF.<br/>
     * Parámetros extras: 1 (mensaje general).
     */
    PDF_GENERATION(
            907,
            "Error al generar el documento PDF",
            "%s",
            HttpStatus.BAD_REQUEST
    ),

    /**
     * Constante para indicar que el tamaño máximo de un archivo a subir ha sido superado.<br/>
     * Parámetros extras: 1 (tamaño del archivo a subir).
     */
    FILE_MAX_UPLOAD(
            908,
            "Error en la carga de archivos al sistema",
            "El archivo tiene un tamaño de %d, la talla máxima es de %d",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante para indicar que una imagen es inválida.<br/>
     * Parámetros extras: NINGUNO.
     */
    NOT_VALID_IMAGE(
            909,
            "Error en la carga de archivos al sistema",
            "Formato de imagen no valido. Solo se aceptan: jpg, jpeg, png, mp4, avi",
            HttpStatus.BAD_REQUEST),

    /**
     * Constante para indicar que la edad mínima no se ha alcanzado.<br/>
     * Parámetros extras: 1 (años de la edad mínima).
     */
    TOO_YOUNG(
            1018,
            "El usuario no puede crear una cuenta",
            "La edad mínima para pertenecer a este sitio es de %d años cumplidos.",
            HttpStatus.BAD_REQUEST
    ),

    /**
     * Constante para indicar que una fecha es inválida.<br/>
     * Parámetros extras: 1 (mensaje general).
     */
    INCORRECT_DATE(
            1019,
            "La fecha ingresada es incorrecta",
            "%s",
            HttpStatus.BAD_REQUEST
    ),

    /**
     * Constante para indicar que hubo un error al cargar un archivo.<br/>
     * Parámetros extras: 1 (mensaje general).
     */
    UPLOAD_SERVICE(
            1020,
            "Error al cargar el archivo",
            "%s",
            HttpStatus.BAD_REQUEST
    ),

    /**
     * Constante para indicar que un usuario no está autorizado para ejecutar una acción.<br/>
     * Parámetros extras: NINGUNO.
     */
    NOT_AUTHORIZED(
            1021,
            "Privilegios insuficientes",
            "El usuario actual no tiene los privilegios suficientes para ejecutar esta acción",
            HttpStatus.UNAUTHORIZED
    ),
    
    /**
     * Constante para indicar que el nick de un usuario ya existe.<br/>
     * Parámetros extras: 1 (el nick existente).
     */
    NICK_ALREADY_EXISTS(
            1022,
            "Nick existente",
            "El nick %s ya existe en el sistema",
            HttpStatus.BAD_REQUEST),
    
    /**
     * Constante para indicar que hay un error en transmisión de contenido.<br/>
     * Parámetros extras: 1 (nombre del archivo multimedia).
     */
    MEDIA_STREAMING_ERROR(
            1023,
            "Error en transmisión de contenido",
            "Ocurrió un error durente la solicitud o transmisión del archivo multimedia: %s",
            HttpStatus.BAD_REQUEST),
    
    /**
     * Constante para indicar que no se pudo cargar un archivoo.<br/>
     * Parámetros extras: NINGUNO.
     */
    UPLOAD_SERVICE_LOG(
            910,
            "Error en la carga de archivos",
            "Fatal Error",
            HttpStatus.BAD_REQUEST
    ),

    /**
     * Constante para indicar que una cadena no psee el formato requerido.<br/>
     * Parámetros extras: 1 la cadena con formato incorrecto.
     */
    INCORRECT_STRING_FORMAT(
            2000,
            "La cadena proporcionada no posee el formato correcto",
            "Formato incorrecto %s",
            HttpStatus.BAD_REQUEST
    );
/** ************************************************************ **/

    private final int exceptionNumber;
    private final String shortMessage;
    private final String detailedMessage;
    private final HttpStatus httpStatus;

    EnumMessage(
            int exceptionNumber,
            String shortMessage,
            String detailedMessage,
            HttpStatus httpStatus) {
        this.exceptionNumber = exceptionNumber;
        this.shortMessage = shortMessage;
        this.detailedMessage = detailedMessage;
        this.httpStatus = httpStatus;
    }

    public int getExceptionNumber() {
        return this.exceptionNumber;
    }
    public String getShortMessage() {
        return this.shortMessage;
    }
    public String getDetailedMessage() {
        return this.detailedMessage;
    }
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
