using MongoDB.Bson.Serialization.Attributes;
namespace Usuarios.Modelo
{
    public class Usuario
    {
        [BsonId]
        public string Id { get; set; }

        public string Password { get; set; }
        public string? Nombre { get; set; }
        public string? Acceso { get; set; }
        public string? Rol { get; set; }
        public Usuario(string id, string password, string nombre, string acceso, string rol)
        {
            Id = id;
            Password = password;
            Nombre = nombre;
            Acceso = acceso;
            Rol = rol;
        }
    }

}