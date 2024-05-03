using Usuarios.Modelo;
using Repositorio;
using System.Security.Claims;
using System.Net.Http.Headers;

namespace Usuarios.Servicio
{
    public interface IServicioUsuarios
    {
        string solicitudCodeActiv(string id);
        string altaUsuario(string id, string nombre, string code, string oauth,string rol);
        string bajaUsuario(string id);
        Dictionary<string, object> verificarOauth(string oauth);
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
        public string altaUsuario(string id, string nombre, string code, string oauth,string rol)
        {
            Usuario user = repositorio.GetById(id);
            Console.Write(code);
            Console.Write(user.CodigoActivacion);
            Console.Write(DateTime.Parse(code.Substring(6)) > DateTime.Now);
            if (user.CodigoActivacion == code && DateTime.Parse(code.Substring(6)) > DateTime.Now)
            {
                repositorio.Update(new Usuario(id, nombre, oauth, code,rol));
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

        public Dictionary<string, object> verificarOauth(string oauth){
            Dictionary<string, object> claims = new Dictionary<string, object>();
            List<Usuario> usuarios = new List<Usuario>(repositorio.GetAll());
            foreach (Usuario user in usuarios){
                if (user.Acceso == oauth){
                    claims.Add("id",user.Id);
                    claims.Add("nombre",user.Nombre);
                    claims.Add("rol",user.Rol);
                    return claims;
                }
            }
            return claims;
        }
        public List<Usuario> listadoUsuarios(){
                    List<Usuario> usuarios = new List<Usuario>(repositorio.GetAll());
                    return usuarios;
                }
            }
        }