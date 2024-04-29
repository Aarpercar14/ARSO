using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
namespace Usuarios.Modelo{
    public class Usuario{
        [BsonId]
        public string Id{get;set;}
        public string? Nombre{get;set;}
        public string? Acceso{get;set;}
        public bool? Autenticacion{get;set;}
        public string? CodigoActivacion{get;set;}
        public Usuario(string id,string code){
            Id=id;
            CodigoActivacion=code;
        }
        public Usuario(string id, string nombre, string acceso,bool autenticacion,string code){
            Id=id;
            Nombre=nombre;
            Acceso=acceso;
            Autenticacion=autenticacion;
            CodigoActivacion=code;

        }
    }
    
}