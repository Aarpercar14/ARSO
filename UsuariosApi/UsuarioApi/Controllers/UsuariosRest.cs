using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Text.Json;
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
        [HttpGet("codigo/{id}")]
        public ActionResult<string> codigoActivacion(string id)
        {
            return servicio.solicitudCodeActiv(id);
        }
        [HttpPost("altaUsuario/{id}")]
        public async Task<IActionResult> AltaUsuario(string id, [FromForm] string nombre, [FromForm] string code, [FromForm] string oauth){
            string url = "http://localhost:8080/tu_endpoint_springboot";

            // Datos que quieres enviar en el cuerpo de la solicitud (puedes cambiarlos según tus necesidades)
            var datos = new { id =id ,  nombre= nombre,code=code,oauth=oauth }; // Esto puede ser un objeto o una estructura según tu caso

            // Serializa los datos a JSON
            string jsonData = JsonSerializer.Serialize(datos);
            using (HttpClient cliente = new HttpClient()){
                try{
                    var respuesta = await cliente.PostAsync(url, new StringContent(jsonData, System.Text.Encoding.UTF8, "application/json"));
                return NoContent();
                }catch (Exception ex){
                // Manejo de errores
                Console.WriteLine($"Error al enviar la solicitud: {ex.Message}");
                }
            }
            return NoContent();
        }
    }
}