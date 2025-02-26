using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using static System.Net.Mime.MediaTypeNames;
using System.Collections.Generic;

namespace implodingRacoon.Models.Database
{
    public class ImplodingRacoonsContext : DbContext
    {
        private const string DATABASE_PATH = "images.db";

        private readonly Settings _settings;

        //public DbSet<Image> Images { get; set; }

        public ImplodingRacoonsContext(IOptions<Settings> options)
        {
            _settings = options.Value;
        }

        protected override void OnConfiguring(DbContextOptionsBuilder options)
        {
            //string baseDir = AppDomain.CurrentDomain.BaseDirectory;
            //options.UseSqlite($"DataSource={baseDir}{DATABASE_PATH}");
            options.UseSqlite(_settings.DatabaseConnection);
        }
    }
}
