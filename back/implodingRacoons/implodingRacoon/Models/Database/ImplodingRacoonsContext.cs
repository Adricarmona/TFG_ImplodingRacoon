using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
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
            string serverConnection = "Server=db18393.databaseasp.net; Database=db18393; Uid=db18393; Pwd=E-n7jZ5=_3aL;";
            string baseDir = AppDomain.CurrentDomain.BaseDirectory;


            #if DEBUG
                options.UseSqlite($"DataSource={baseDir}{DATABASE_PATH}");
            #else
                options.UseMySql(serverConnection,ServerVersion.AutoDetect(serverConnection));
            #endif
        }

    }
}
