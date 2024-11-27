package com.example.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.example.api.model.User;
import com.example.api.model.VucemCredentials;
import com.example.api.model.VucemRestResponse;
import com.example.api.model.exceptions.ServiceException;
import com.example.api.model.exceptions.V30Error;
import com.example.api.model.meta.EnumMetaCatAtt;
import com.example.api.service.DemoService;
import com.example.api.utils.RestHttpResponseHelper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class DemoController {
    private DemoService demoService;

    public DemoController(DemoService demoService){
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
            return RestHttpResponseHelper.getReturnValue(HttpStatus.OK, jwt, null);
        } catch (ServiceException e) {
            return RestHttpResponseHelper.getReturnValue(HttpStatus.UNAUTHORIZED, null, new V30Error(e));
        } catch (Exception e) {
            return RestHttpResponseHelper.getReturnValue(HttpStatus.INTERNAL_SERVER_ERROR, null, new V30Error(e));
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
            value = "/persinsertIntoSolicitudDetalle/{idSolicitud}/{idMetaCatAtt}/{valor}",
            produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> persinsertIntoSolicitudDetalle(
            @PathVariable int idSolicitud, 
            @PathVariable EnumMetaCatAtt idMetaCatAtt, 
            @PathVariable String valor){
        try {
            Integer k = this.demoService.persistAttributeIntoSolicitudDetalle(idSolicitud, idMetaCatAtt, valor);
            return RestHttpResponseHelper.getReturnValue(HttpStatus.OK, k, null);
        } catch (ServiceException e) {
            return RestHttpResponseHelper.getReturnValue(HttpStatus.INTERNAL_SERVER_ERROR, null, new V30Error(e));
        }
    } 

}
