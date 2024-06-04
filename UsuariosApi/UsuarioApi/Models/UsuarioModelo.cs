using MongoDB.Bson.Serialization.Attributes;
namespace Usuarios.Modelo{
    public class Usuario{
        [BsonId]
        public string Id{get;set;}
        public string? Nombre{get;set;}
        public string? Acceso{get;set;}
        public string? Rol{get;set;}
        public Usuario(string id, string nombre, string acceso,string rol){
            Id=id;
            Nombre=nombre;
            Acceso=acceso;
            Rol=rol;
        }
    }
    
}