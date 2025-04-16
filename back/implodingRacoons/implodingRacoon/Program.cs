using System.Net.WebSockets;
using System.Text;
using implodingRacoon.Controllers;
using implodingRacoon.Models.Database;
using implodingRacoon.Services;
using implodingRacoon.Services.WebSocketService;
using Microsoft.Extensions.FileProviders;
using Microsoft.IdentityModel.Tokens;

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

            // El Jwt
            builder.Services.AddAuthentication()
                .AddJwtBearer(options =>
                {
                    String key = Environment.GetEnvironmentVariable("JWT_KEY");

                    options.TokenValidationParameters = new TokenValidationParameters()
                    {
                        ValidateIssuer = false,
                        ValidateAudience = false,
                        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(key))
                    };

                });

            // El unit of work
            builder.Services.AddScoped<ImplodingRacoonsContext>();
            builder.Services.AddScoped<UnitOfWork>();

            // servicios
            builder.Services.AddScoped<AuthService>();

            // el singleton del websocket
            builder.Services.AddSingleton<WebSocketNetwork>();


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

            // El Websoket
            app.UseWebSockets();

            // Lo de https que si esta comentado se puede por http
            app.UseHttpsRedirection();

            // Configuramos Cors para que acepte cualquier petición de cualquier origen (no es seguro)
            app.UseCors(options =>
                options.AllowAnyHeader()
                    .AllowAnyMethod()
                    .AllowAnyOrigin());

            // Habilitamos la autenticacion y la autorizacion
            app.UseAuthentication();
            app.UseAuthorization();


            app.MapControllers();


            app.Run();
        }
    }
}
