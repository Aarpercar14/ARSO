using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
namespace Usuarios.Modelo{
    public class Usuario{
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id{get;set;}
        public string Nombre{get;set;}
        public string Apellidos{get;set;}
        public string Email{get;set;}
        public string Password{get;set;}
        public string Telefono{get;set;}
        public DateTime Nacimiento{get;set;}
    }
}