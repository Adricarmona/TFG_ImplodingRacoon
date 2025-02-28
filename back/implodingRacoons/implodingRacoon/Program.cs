using implodingRacoon.Models.Database;
using Microsoft.Extensions.FileProviders;

namespace implodingRacoon
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddScoped<ImplodingRacoonsContext>();

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();





            ///
            ///     APP
            ///
            var app = builder.Build();

            // para la base de datos
            using (IServiceScope scope = app.Services.CreateScope())
            {
                ImplodingRacoonsContext implodingRacoonsContext = scope.ServiceProvider.GetService<ImplodingRacoonsContext>();
                implodingRacoonsContext.Database.EnsureCreated();
            }


            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            // Para las fotos
            app.UseStaticFiles(new StaticFileOptions
            {
                FileProvider = new PhysicalFileProvider(Path.Combine(Directory.GetCurrentDirectory(), "wwwroot"))
            });

            app.UseWebSockets();

            app.UseHttpsRedirection();

            app.UseAuthorization();


            app.MapControllers();

            app.Run();
        }
    }
}
