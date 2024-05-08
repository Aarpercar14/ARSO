using MongoDB.Driver;
using Usuarios.Modelo;
using Repositorio;

namespace Usuarios.Repositorio{
    public class RepositorioUsuarioMongoDB:Repositorio<Usuario,string>{
        public readonly IMongoCollection<Usuario> usuarios;
        public RepositorioUsuarioMongoDB(){
            var database=new MongoClient("mongodb://root:example@mongo:27017/usuarios?authSource=admin").GetDatabase("arso");
            usuarios=database.GetCollection<Usuario>("usuarios");
        }
        
        
        public string Add(Usuario user){
            usuarios.InsertOne(user);
            return user.Id;
        }
        public void Update(Usuario user){
            usuarios.ReplaceOne(usuario=>usuario.Id==user.Id,user);
        }
        public void Delete(Usuario user){
            usuarios.DeleteOne(usuario=>usuario.Id==user.Id);
        }
        public Usuario GetById(string id){
            return usuarios.Find(user=>user.Id==id).FirstOrDefault();
        }
        public List<Usuario> GetAll(){
            return usuarios.Find(_=>true).ToList();
        }
        public List<string> GetIds(){
            return usuarios.Find(_=>true).ToList().Select(unidad=>unidad.Id).ToList();
        }

    }
}