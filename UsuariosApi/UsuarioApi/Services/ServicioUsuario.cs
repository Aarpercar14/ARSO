using Usuarios.Modelo;
using Repositorio;
namespace Usuarios.Servicio{
    public interface IServicioUsuarios{
        string solicitudCodeActiv(string id);
        string altaUsuario(string code,string oauth);
        void bajaUsuario(string id);
        map<string,Usuario> verificar(string user);
        map<string,Usuario> verificarOauth(string id);
        List<Usuario> listadoUsuarios();
    }
    public class ServicioUsuarios:IServicioUsuarios{
        private Repositorio<Usuario,string> repositorio;
        public ServicioUsuarios(Repositorio<Usuario,string> repos){

        }
    }
}