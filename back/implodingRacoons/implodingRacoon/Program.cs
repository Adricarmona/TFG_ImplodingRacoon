using System.Net.WebSockets;
using System.Text;
using System.Threading.Tasks;
using implodingRacoon.Controllers;
using implodingRacoon.Models;
using implodingRacoon.Models.Database;
using implodingRacoon.Services;
using implodingRacoon.Services.GamesService;
using implodingRacoon.Services.WebSocketService;
using Microsoft.Extensions.FileProviders;
using Microsoft.IdentityModel.Tokens;

namespace implodingRacoon
{
    public class Program
    {
        public static async Task Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);



            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            // El Jwt
            builder.Services.AddAuthentication()
                .AddJwtBearer(options =>
                {
                    Settings settings = builder.Configuration.GetSection(Settings.SECTION_NAME).Get<Settings>();
                    string key = settings.JwtKey;

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
            builder.Services.AddScoped<CardsService>();
            builder.Services.AddScoped<UserService>();
            builder.Services.AddScoped<WSHelper>();
            builder.Services.AddScoped<ImageMapper>();

            // el singleton del websocket
            builder.Services.AddSingleton<WebSocketNetwork>();

            builder.Services.AddCors(options =>
            {
                options.AddDefaultPolicy(builder =>
                {
                    builder.AllowAnyOrigin()
                        .AllowAnyHeader()
                        .AllowAnyMethod();
                });
            });

            ///
            ///     APP
            ///
            var app = builder.Build();

            // Configure the HTTP request pipeline.
            /*
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }*/

            app.UseSwagger();
            app.UseSwaggerUI();

            // Para las fotos
            app.UseStaticFiles(new StaticFileOptions
            {
                FileProvider = new PhysicalFileProvider(Path.Combine(Directory.GetCurrentDirectory(), "wwwroot"))
            });

            // El Websoket
            app.UseWebSockets();

            // Lo de https que si esta comentado se puede por http
            app.UseHttpsRedirection();

            /*
            // Configuramos Cors para que acepte cualquier petición de cualquier origen (no es seguro)
            app.UseCors(options =>
                options.AllowAnyHeader()
                    .AllowAnyMethod()
                    .AllowAnyOrigin()); 
            */

            // Habilitamos la autenticacion y la autorizacion
            app.UseAuthentication();
            app.UseAuthorization();


            app.MapControllers();

            // La base de datos
            await SeedDataBase(app.Services);

            // iniciamos las mesas
            Games.iniciarMesas();

            app.Run();
        }

        static async Task SeedDataBase(IServiceProvider serviceProvider)
        {
            using IServiceScope scope = serviceProvider.CreateScope();
            using ImplodingRacoonsContext irContext = scope.ServiceProvider.GetRequiredService<ImplodingRacoonsContext>();

            if (irContext.Database.EnsureCreated())
            {
                Seeder seeder = new Seeder(irContext);
                await seeder.SeedAsync();
            }
        }
    }
}
