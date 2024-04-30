using Usuarios.Modelo;
using Repositorio;
using System.Security.Claims;
using System.Net.Http.Headers;

namespace Usuarios.Servicio
{
    public interface IServicioUsuarios
    {
        string solicitudCodeActiv(string id);
        string altaUsuario(string id, string nombre, string code, string oauth);
        string bajaUsuario(string id);
        Dictionary<string, object> verificar(string id, string contra);

        Task<Dictionary<string, object>> verificarAsync(string id, string contra);

        Task<Dictionary<string, object>> verificarOauthAsync(string oauth);
        List<Usuario> listadoUsuarios();
    }
    public class ServicioUsuarios : IServicioUsuarios
    {
        private Repositorio<Usuario, string> repositorio;
        public ServicioUsuarios(Repositorio<Usuario, string> repos)
        {
            repositorio = repos;
        }
        public string solicitudCodeActiv(string id)
        {
            const string caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random rnd = new Random();
            string code = new string(Enumerable.Repeat(caracteres, 6)
                .Select(s => s[rnd.Next(s.Length)]).ToArray());
            repositorio.Add(new Usuario(id, code + DateTime.Now.AddDays(7).ToString("MM/dd/yyyy")));
            return code + DateTime.Now.AddDays(7).ToString("MM/dd/yyyy");
        }
        public string altaUsuario(string id, string nombre, string code, string oauth)
        {
            Usuario user = repositorio.GetById(id);
            if (user.CodigoActivacion == code && DateTime.Parse(code.Substring(6)) > DateTime.Now)
            {
                repositorio.Update(new Usuario(id, nombre, oauth, true, code));
                return "Alta realizada con exito";
            }
            return "Codigo de alta caducado o incorrecto";
        }
        public string bajaUsuario(string id)
        {
            Usuario user = repositorio.GetById(id);
            if (user != null)
            {
                repositorio.Delete(user);
                return "Usuario dado de baja";
            }
            return "Usuario nulo, no se puede dar de baja";
        }
        public Dictionary<string, object> verificar(string id, string contra)
        {
            Dictionary<string, object> claims = new Dictionary<string, object>();
            if (repositorio.GetById(id).Acceso == contra)
            {
                claims.Add("id", id);
                claims.Add("nombre", repositorio.GetById(id).Nombre);
                claims.Add("rol", "gestor");
            }
            return claims;
        }

        public Task<Dictionary<string, object>> verificarAsync(string id, string contra)
        {
            return Task.FromResult(verificar(id, contra));
        }

        public async Task<Dictionary<string, object>> verificarOauthAsync(string oauth)
        {
            Dictionary<string, object> claims = new Dictionary<string, object>();
            List<Usuario> usuarios = new List<Usuario>(repositorio.GetAll());
            string url = "http://localhost:8090/estaciones";
            string tokenJWT = oauth;
            using (HttpClient cliente = new HttpClient()){
                try{
                    cliente.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", tokenJWT);
                    var respuesta = await cliente.GetAsync(url);
                    string contenidoRespuesta = await respuesta.Content.ReadAsStringAsync();
                    Console.Write(contenidoRespuesta);
                    return claims;
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                }
            }
            return claims;
        }
        public List<Usuario> listadoUsuarios()
                {
                    List<Usuario> usuarios = new List<Usuario>(repositorio.GetAll());
                    return usuarios;
                }
            }
        }