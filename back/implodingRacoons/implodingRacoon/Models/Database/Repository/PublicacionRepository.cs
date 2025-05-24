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

        public async Task<PublicacionComentarios> GetPublicacionAndComents(int id)
        {
            return await GetQueryable()
                .Include(p => p.Comentarios)
                .ThenInclude(c => c.Usuario)
                .Where(p => p.Id == id)
                .Select(p => new PublicacionComentarios
                {
                    Id = p.Id,
                    Titulo = p.Titulo,
                    Descripcion = p.Descripcion,
                    Fecha = p.Fecha,
                    Usuario = new UserAmigos
                    {
                        Id = p.UsuarioId,
                        NombreUsuario = p.Usuario.NombreUsuario,
                        Foto = p.Usuario.Foto
                    },
                    Comentarios = p.Comentarios.Select(c => new ComentarioSimple
                    {
                        Id = c.Id,
                        Comentario = c.Descripcion,
                        Fecha = c.Fecha,
                        nombreUsuario = c.Usuario.NombreUsuario
                    }).ToList()
                })
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
