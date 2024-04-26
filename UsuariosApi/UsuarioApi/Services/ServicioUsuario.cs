using Usuarios.Modelo;
using Repositorio;
using System.Security.Claims;
namespace Usuarios.Servicio{
    public interface IServicioUsuarios{
        string solicitudCodeActiv(string id);
        void altaUsuario(string id,string nombre,string code,string oauth);
        void bajaUsuario(string id);
        Dictionary<string,object> verificar(string id,string contra);
        Dictionary<string,object> verificarOauth(string oauth);
        List<Usuario> listadoUsuarios();
    }
    public class ServicioUsuarios:IServicioUsuarios{
        private Repositorio<Usuario,string> repositorio;
        public ServicioUsuarios(Repositorio<Usuario,string> repos){
            repositorio=repos;
        }
        public string solicitudCodeActiv(string id){
            const string caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random rnd = new Random();
            string code= new string(Enumerable.Repeat(caracteres, 6)
                .Select(s => s[rnd.Next(s.Length)]).ToArray());
            repositorio.Add(new Usuario(id,code+DateTime.Now.AddDays(7).ToString("MMMM dd")));
            return code+DateTime.Now.AddDays(7).ToString("MMMM dd");
        }
        public void altaUsuario(string id,string nombre,string code,string oauth){
            Usuario user=repositorio.GetById(id);
            if(user.CodigoActivacion==code && DateTime.Parse(code.Substring(6))>DateTime.Now){
                repositorio.Update(new Usuario(id,nombre,oauth,true,code));
            }
        }
        public void bajaUsuario(string id){
            Usuario user=repositorio.GetById(id);
            if(user!=null){
                repositorio.Delete(user);
            }
        }
        public Dictionary<string,object> verificar(string id,string contra){
            Dictionary<string,object> claims =new Dictionary<string, object>();
            if(repositorio.GetById(id).Autenticacion==true) claims=verificarOauth(contra);
            else if(repositorio.GetById(id).Acceso==contra){
                claims.Add("id",id);
                claims.Add("nombre",repositorio.GetById(id).Nombre);
                claims.Add("rol","gestor");
            }
            return claims;
        }
        public Dictionary<string,object> verificarOauth(string oauth){
            Dictionary<string,object> claims =new Dictionary<string, object>();
            List<Usuario> usuarios=new List<Usuario>(repositorio.GetAll());
            foreach(Usuario u in usuarios){
                if(u.Acceso==oauth){
                    claims.Add("id",u.Id);
                    claims.Add("nombre",repositorio.GetById(u.Id).Nombre);
                    claims.Add("rol","gestor");
                }
            }
            return claims;
        }
        public List<Usuario> listadoUsuarios(){
            List<Usuario> usuarios=new List<Usuario>(repositorio.GetAll());
            return usuarios;
        }
    }
}