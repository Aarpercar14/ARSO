using System;
using Usuarios.Modelo;
using Usuarios.Repositorio;
using Usuarios.Servicio;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;
using Repositorio;

namespace UsuariosApi{
    public class StartUp{
        public StartUp(IConfiguration configuration){
            Configuration = configuration;
        }
        public IConfiguration Configuration { get; }
        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services){
            services.AddSingleton<Repositorio<Usuario, string>, RepositorioUsuarioMongoDB>();
            services.AddSingleton<IServicioUsuarios, ServicioUsuarios>();
            services.AddControllers(options =>
            {
                options.Filters.Add(typeof(ManejadorGlobalErrores));
            });
            services.AddControllers();
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo { Title = "UsuariosApi", Version = "v1" });
            });
        }
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env){
            if (env.IsDevelopment()){
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "BookleApi v1"));
            }
            // app.UseHttpsRedirection();
            app.UseRouting();
            app.UseAuthorization();
            app.UseEndpoints(endpoints =>{
                endpoints.MapControllers();
            });
        }
    }
}
public class ManejadorGlobalErrores : ExceptionFilterAttribute{
    public override void OnException(ExceptionContext context){
        base.OnException(context);
        if (context.Exception is ArgumentException) {
            context.Result = new BadRequestResult();
        }         
    }
}