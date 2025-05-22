using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database.Repository.Repository;
using Microsoft.EntityFrameworkCore;

namespace implodingRacoon.Models.Database.Repository
{
    public class PublicacionRepository : Repository<Publicacion, int>
    {
        public PublicacionRepository(ImplodingRacoonsContext context) : base(context) { }

        public async Task<List<PublicacionTarjetas>> GetAllPublicacionesAndUserAsync()
        {
            return await GetQueryable()
                .Include(Publicacion => Publicacion.Usuario)
                .Include(Publicacion => Publicacion.Comentarios)
                .Select(p => new PublicacionTarjetas
                {
                    Id = p.Id,
                    Titulo = p.Titulo,
                    Comentarios = p.Comentarios.Count(),
                    Fecha = p.Fecha,
                    nombreUsuario = p.Usuario.NombreUsuario
                })
                .ToListAsync();
        }

        internal async Task<List<PublicacionTarjetas>> GetAllPublicacionesAndUserAsync(int id)
        {
            return await GetQueryable()
                .Include(Publicacion => Publicacion.Usuario)
                .Include(Publicacion => Publicacion.Comentarios)
                .Select(p => new PublicacionTarjetas
                {
                    Id = p.Id,
                    Titulo = p.Titulo,
                    Comentarios = p.Comentarios.Count(),
                    Fecha = p.Fecha,
                    nombreUsuario = p.Usuario.NombreUsuario
                })
                .Where(p => p.Id == id)
                .ToListAsync();
        }

        public async Task<Publicacion> GetPublicacionAndComents(int id)
        {
            return await GetQueryable()
                .Include(Publicacion => Publicacion.Comentarios)
                .Where(p => p.Id == id)
                .FirstOrDefaultAsync();
        }

        public async Task<PublicacionInformacion> GetPublicacionAndUser(int id)
        {
            return await GetQueryable()
                .Include(Publicacion => Publicacion.Usuario)
                .Include(Publicacion => Publicacion.Comentarios)
                .Where(p => p.Id == id) 
                .Select(p => new PublicacionInformacion
                {
                    Id = p.Id,
                    Titulo = p.Titulo,
                    Descripcion = p.Descripcion,
                    Fecha = p.Fecha,
                    nombreUsuario = p.Usuario.NombreUsuario
                })
                .FirstOrDefaultAsync(); 
        }
    }
}
