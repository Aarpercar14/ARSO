using Usuarios.Modelo;
using Repositorio;
using System.Security.Claims;
using System.Net.Http.Headers;
using System.Globalization;

namespace Usuarios.Servicio
{
    public interface IServicioUsuarios
    {
        string altaUsuario(string id, string nombre, string oauth,string rol);
        string bajaUsuario(string id);
        string verificarOauth(string oauth);
        List<Usuario> listadoUsuarios();
    }
    public class ServicioUsuarios : IServicioUsuarios
    {
        private Repositorio<Usuario, string> repositorio;
        public ServicioUsuarios(Repositorio<Usuario, string> repos)
        {
            repositorio = repos;
        }
        public string altaUsuario(string id, string nombre, string oauth, string rol)
        {
            Usuario user=new Usuario(id, nombre, oauth, rol);
            repositorio.Add(user);
            return "Usuario "+id+" ha sido dado de alta correctamente";
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
        public string verificarOauth(string oauth)
        {
            List<Usuario> usuarios = new List<Usuario>(repositorio.GetAll());
            foreach (Usuario user in usuarios)
            {
                if (user.Acceso == oauth) return "{\"id\": \"" + user.Id + "\", \"nombre\": \"" + user.Nombre +
                        "\", \"rol\": \"" + user.Rol + "\"}";

            }
            return "";
        }
        public List<Usuario> listadoUsuarios()
        {
            List<Usuario> usuarios = new List<Usuario>(repositorio.GetAll());
            return usuarios;
        }
    }
}