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
        public ActionResult<string> codigoActivacion(string id){
            return servicio.solicitudCodeActiv(id);
        }
    }
}