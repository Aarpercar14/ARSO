using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using Usuarios.Modelo;
using Usuarios.Servicio;

namespace Usuarios.Controllers{
    [Route("api/usuarios")]
    [ApiController]
    public class UsuarioController : ControllerBase{
        private readonly IServicioUsuarios servicio;
        public UsuarioController(IServicioUsuarios servicio){
            this.servicio = servicio;
        }
        [HttpGet("codigo/{id}")]
        public ActionResult<string> CodigoActivacion(string id){
            return servicio.solicitudCodeActiv(id);
        }
        [HttpPost("alta/{id}")]
        public ActionResult<string> Alta(string id,[FromQuery] string nombre,[FromQuery]string code,[FromQuery]string oauth){
            return servicio.altaUsuario(id,nombre,code,oauth);
            //TODO pasarela
        }
        [HttpPost("baja/{id}")]
        public ActionResult<string> Baja(string id){
            return servicio.bajaUsuario(id);
        }

    }
}