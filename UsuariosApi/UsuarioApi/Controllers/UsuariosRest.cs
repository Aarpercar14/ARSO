using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using Usuarios.Modelo;
using Usuarios.Servicio;

namespace Usuarios.Controllers
{
    [Route("api/usuarios")]
    [ApiController]
    public class UsuarioController : ControllerBase

    {
        private readonly IServicioUsuarios servicio;

        public UsuarioController(IServicioUsuarios servicio)
        {
            this.servicio = servicio;
        }

        [HttpPost("alta/{id}")]
        public ActionResult<string> Alta(string id, [FromQuery] string password, [FromQuery] string nombre, [FromQuery] string rol)
        {
            return servicio.altaUsuario(id, password, nombre, rol);
        }

        [HttpPost("baja/{id}")]
        public ActionResult<string> Baja(string id)
        {

            return servicio.bajaUsuario(id);
        }

        [HttpGet("listadoUsuario")]
        public ActionResult<List<Usuario>> Listado()
        {
            return servicio.listadoUsuarios();
        }

        // [HttpGet("verificarOauth/{oauth}")]
        // public ActionResult<string> verificarOAuth(string oauth)
        // {
        //     var result = servicio.verificarOauth(oauth);
        //     return result;
        // }

        [HttpGet("login")]
        public ActionResult<string> verificarLogin([FromQuery] string id, [FromQuery] string password)
        {
            return servicio.verficarLogin(id, password);
        }
    }
}