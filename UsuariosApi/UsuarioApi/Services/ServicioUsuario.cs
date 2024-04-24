using Usuarios.Modelo;
using Repositorio;
namespace Usuarios.Servicio{
    public interface IServicioUsuarios{
        string solicitudCodeActiv(string id);
        string altaUsuario(string code,string oauth);
        void bajaUsuario(string id);
        Dictionary<string,Usuario> verificar(string user);
        Dictionary<string,Usuario> verificarOauth(string id);
        List<Usuario> listadoUsuarios();
    }
    public class ServicioUsuarios:IServicioUsuarios{
        private Repositorio<Usuario,string> repositorio;
        public ServicioUsuarios(Repositorio<Usuario,string> repos){
            repositorio=repos;
        }
        public string solicitudCodeActiv(string id){
        //Solicitud código de activación (para el gestor): tiene como parámetro el identificador de 
        //un usuario. La operación genera un código asociado al usuario con un plazo de validez. 
        //Este código será requerido en el proceso de alta. Nota: existirá un proceso administrativo
        //,ajeno a la aplicación, que permite al usuario solicitar el alta en la aplicación. 
        //Si cumple los requisitos, el gestor activa un código para que se proceda con el alta.
            const string caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random rnd = new Random();
            string s= new string(Enumerable.Repeat(caracteres, 6)
                .Select(s => s[rnd.Next(s.Length)]).ToArray());
            return id+s+DateTime.Now.AddDays(7);
             
        }
        public string altaUsuario(string code,string oauth){
        //Operación alta de un usuario. En la información del alta se proporciona un código de 
        //activación que se utiliza para comprobar que el usuario está autorizado a realizar el alta.
        // Esta operación solo da de alta usuarios de la aplicación, ya que los gestores se dan
        // de alta directamente en la base de datos. En relación a la información relativa al 
        //proceso de autenticación, se ofrecen dos alternativas: con usuario/contraseña y mediante 
        //OAuth2. Si se establece una constraseña, se opta por la primera opción. En cambio, 
        //si se elige OAuth2, se añadirá a la petición un campo con el identificador de usuario 
        //OAuth2 de GitHub. Los gestores tendrán autenticación con usuario/contraseña. El resto 
        //de información proporcionada en el proceso de alta se deja a criterio de cada grupo 
        //(nombre completo, correo electrónico, teléfono, dirección postal, etc.).

        
        }
        public void bajaUsuario(string id){

        }
        public Dictionary<string,Usuario> verificar(string user){

        }
        public Dictionary<string,Usuario> verificarOauth(string id){

        }
        public List<Usuario> listadoUsuarios(){

        }
        //Baja de un usuario (ofrecida al gestor).
        //Verificar las credenciales de un usuario. En el proceso de login con usuario/contraseña 
        //este operación comprueba que exista el usuario y que coincida la contraseña.
        //Verificar usuario OAuth2. En el proceso de autenticación OAuth2 esta operación comprueba
        // si el identificador OAuth2 de GitHub corresponde con alguno de los usuarios registrados 
        //en la aplicación, es decir, de un usuario registrado con autenticación OAuth2.
        //En las dos operaciones de verificación se retorna un mapa con los claims (identificador
        // de usuario, nombre completo, rol) que se utilizarán para emitir un token JWT.
        //Listado de todos los usuarios (para el gestor).
    }
}