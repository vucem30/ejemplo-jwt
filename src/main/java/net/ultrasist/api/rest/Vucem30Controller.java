package net.ultrasist.api.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import net.ultrasist.api.model.User;
import net.ultrasist.api.model.VucemCredentials;
import net.ultrasist.api.model.exceptions.ServiceException;
import net.ultrasist.api.model.exceptions.V30Error;
import net.ultrasist.api.model.meta.EnumMetaCatAtt;
import net.ultrasist.api.service.Vucem30Service;
import net.ultrasist.api.utils.JwtFunctios;
import net.ultrasist.api.utils.RestHttpResponseHelper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class Vucem30Controller {
    private Vucem30Service demoService;
    JwtFunctios jwtFunctios = JwtFunctios.getInstance();

    public Vucem30Controller(Vucem30Service demoService){
        this.demoService = demoService;
    }

    @Operation(
        summary = "AccessController::login",
        description = "Retorna un JWT si la autenticación fué exitosa. Retorna un 401 (Forbidden) si no lo fue")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully sign in to the system"), 
        @ApiResponse(responseCode = "400", description = "Missing request body"), 
        @ApiResponse(responseCode = "400", description = "Invalid request body"), 
        @ApiResponse(responseCode = "401", description = "UnAuthorized"), 
        @ApiResponse(responseCode = "403", description = "Forbidden"), 
        @ApiResponse(responseCode = "404", description = "Schema not found"), 
        @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PostMapping(
            value = "/login",
            produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> login(
            @Parameter(
                name="cred", 
                description="Representa las credenciales (usuario y clave) de quien intenta ingresar al sistema")
            @RequestBody VucemCredentials cred) {
        try {
            String jwt = demoService.login(cred.getUser(), cred.getPassword());
            jwt = "{'jwt': '"+jwt+"'}"; // no usar el 'replace' aqui, porque sólo cambiará el primer caracter
            return RestHttpResponseHelper.getReturnValue(HttpStatus.OK, jwt.replace('\'', '\"'), null);
        } catch (ServiceException e) {
            return RestHttpResponseHelper.getReturnValue(HttpStatus.UNAUTHORIZED, null, new V30Error(
                "Error de autenticación",
                "El usuario y/o el password son incorrectos. Solución %s",
                HttpStatus.UNAUTHORIZED,"Favor de revisar los datos de acceso %s %s",cred.getUser(),cred.getPassword()));
        } 
    }    

    @GetMapping(
            value = "/prueba/{id}",
            produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> prueba(@PathVariable int id) {
        HttpStatus status = id%2==0? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;

        User u = new User(
            id, 
            "gus@aol.com", 
            "secreto", 
            "uuid-abcd");

        V30Error ce = new V30Error(
            "Error en el servicio", 
            "El id no es par", 
            1000, 
            "ERR-1000", 
            "A", "B");

        return RestHttpResponseHelper.getReturnValue(status, u, ce);
    }

    @PostMapping(
            value = "/solicitud-detalle/{idSolicitud}/{idMetaCatAtt}/{valor}",
            produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> persinsertIntoSolicitudDetalle(
            @RequestHeader("jwt") String jwt,
            @PathVariable int idSolicitud, 
            @PathVariable EnumMetaCatAtt idMetaCatAtt, 
            @PathVariable String valor){
        if(!jwtFunctios.isJwtValid(jwt)){
            return RestHttpResponseHelper.getReturnValue(HttpStatus.UNAUTHORIZED, null, new V30Error(
                "Error de autorización",
                "El token de autorización no es válido o ha expirado. Solución %s",
                HttpStatus.UNAUTHORIZED,"Favor generar un nuevo token"));                
        }
        try {
            Integer k = this.demoService.persistAttributeIntoSolicitudDetalle(idSolicitud, idMetaCatAtt, valor);
            return RestHttpResponseHelper.getReturnValue(HttpStatus.OK, k, null);
        } catch (ServiceException e) {
            return RestHttpResponseHelper.getReturnValue(HttpStatus.INTERNAL_SERVER_ERROR, null, new V30Error(e));
        }
    } 

    @DeleteMapping(
            value = "/solicitud-detalle/{idSolicitud}/{idMetaCatAtt}",
            produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> deleteSolicitudDetalle(
            @RequestHeader("jwt") String jwt,
            @PathVariable int idSolicitud, 
            @PathVariable EnumMetaCatAtt idMetaCatAtt){
        if(!jwtFunctios.isJwtValid(jwt)){
            return RestHttpResponseHelper.getReturnValue(HttpStatus.UNAUTHORIZED, null, new V30Error(
                "Error de autorización",
                "El token de autorización no es válido o ha expirado. Solución %s",
                HttpStatus.UNAUTHORIZED,"Favor generar un nuevo token"));                
        }
        try {
            Integer k = this.demoService.deleteSolicitudDetalle(idSolicitud, idMetaCatAtt);
            return RestHttpResponseHelper.getReturnValue(HttpStatus.OK, k, null);
        } catch (ServiceException e) {
            return RestHttpResponseHelper.getReturnValue(HttpStatus.INTERNAL_SERVER_ERROR, null, new V30Error(e));
        }
    } 

    @Operation(
        summary = "DemoController::getJsonData",
        description = "Retorna el contenido de un archivo JSON almacenado en el directorio `src/main/resources`")
    @GetMapping("/json-data")
    public ResponseEntity<String> getJsonData() {
        try {
            // Carga el archivo JSON desde `src/main/resources`
            Path filePath = new ClassPathResource("jsons/producto.json").getFile().toPath();
            // Lee el contenido del archivo como String y lo retorna "as expected.... jajajaja"
            String jsonContent = Files.readString(filePath);
            return ResponseEntity.ok(jsonContent);
        } catch (IOException e) {
            // Manejo de errores si el archivo no se encuentra o no se puede leer
            return ResponseEntity.status(500).body("Error al leer el archivo JSON");
        }
    }

    @Operation(
        summary = "DemoController::getTramiteDetalle",
        description = "Retorna un mapa con los <label style='color:red;font-size:16px;'>detalles</label> de un trámite dado su ID de solicitud<br/> Como es de sólo lectura y no expone información sensible, no requiere token de autorización")
    @GetMapping("/tramite-detalle/{idSolicitud}")
    public ResponseEntity<Map<String, String>> getTramiteDetalle(@PathVariable int idSolicitud) {
        Map<String, String> data = this.demoService.getTramiteAllAtt(idSolicitud);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
