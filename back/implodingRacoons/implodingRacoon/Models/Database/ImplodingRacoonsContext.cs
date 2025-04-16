using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using static System.Net.Mime.MediaTypeNames;
using System.Collections.Generic;
using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Models.Database
{
    public class ImplodingRacoonsContext : DbContext
    {
        private const string DATABASE_PATH = "ImplodingRacoon.db";

        private readonly Settings _settings;

        public DbSet<Carta> Carta { get; set; }
        public DbSet<Comentario> Comentario { get; set; }
        public DbSet<Diseno> Diseno { get; set; }
        public DbSet<HistorialPartidas> HistorialPartidas { get; set; }
        public DbSet<Publicacion> Publicacion { get; set; }
        public DbSet<Usuario> Usuario { get; set; }
        public DbSet<UsuarioHistorial> UsuarioHistorial { get; set; } 


        public ImplodingRacoonsContext(IOptions<Settings> options)
        {
            _settings = options.Value;
        }

        protected override void OnConfiguring(DbContextOptionsBuilder options)
        {
            /// los datos de la url
            /// son los de electrospeed
            string serverConnection = "Server=db10826.databaseasp.net; Database=db10826; Uid=db10826; Pwd=L!e2rX6%?4yF";
            string baseDir = AppDomain.CurrentDomain.BaseDirectory;


            #if DEBUG
                options.UseSqlite($"DataSource={baseDir}{DATABASE_PATH}");
            #else
                options.UseMySql(serverConnection,ServerVersion.AutoDetect(serverConnection));
            #endif
        }

        // Sin esto no funciona al crear la base de datos
        // genera las relaciones de la tabla solicitud de amistad
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            // De cuando envias las las solicitudes
            modelBuilder.Entity<SolicitudAmistad>()
                .HasOne(s => s.UsuarioEnvia) 
                .WithMany(u => u.SolicitudesEnviadas)
                .HasForeignKey(s => s.UsuarioEnviaId)
                .OnDelete(DeleteBehavior.Restrict);


            // De cuando recibes las solicitudes
            modelBuilder.Entity<SolicitudAmistad>()
                .HasOne(s => s.UsuarioRecibe)
                .WithMany(u => u.SolicitudesRecibidas)
                .HasForeignKey(s => s.UsuarioRecibeId)
                .OnDelete(DeleteBehavior.Restrict);

            base.OnModelCreating(modelBuilder);
        }

    }
}
