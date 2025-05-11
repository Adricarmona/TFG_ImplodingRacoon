using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database.Repository.Repository;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace implodingRacoon.Models.Database.Repository
{
    public class UsuarioRepository : Repository<Usuario, int>
    {
        public UsuarioRepository(ImplodingRacoonsContext context) : base(context) { }

        public async Task<UserSimple> GetUserByCredential(string emailOrUser)
        {
            Usuario usuario = await GetQueryable().FirstOrDefaultAsync(user => user.Correo == emailOrUser || user.NombreUsuario == emailOrUser);

            if (usuario == null) return null;

            return new UserSimple
            {
                Id = usuario.Id,
                NombreUsuario = usuario.NombreUsuario,
                Correo = usuario.Correo,
                Foto = usuario.Foto,
                Conectado = usuario.Conectado
            };

        }

        public async Task<UserSimple> AddUser(UserRegister user)
        {
            Usuario usuario = new Usuario
            {
                NombreUsuario = user.NombreUsuario,
                Correo = user.Correo,
                Contrasena = user.Password,
                Foto = "fotoGenerica.png",
                Conectado = true,
                Admin = false
            };

            Usuario usuarioIngresado = await InsertAsync(usuario);

            return new UserSimple
            {
                Id = usuarioIngresado.Id,
                NombreUsuario = usuarioIngresado.NombreUsuario,
                Correo = usuarioIngresado.Correo,
                Foto = usuarioIngresado.Foto,
                Conectado = usuarioIngresado.Conectado,
                Admin = usuarioIngresado.Admin,
            };
        }

        public async Task<Usuario> GetUserByIdAndSolicitudes(int id)
        {
            Usuario usuario = await GetQueryable()
                .Include(user => user.SolicitudesEnviadas)
                .Include(user => user.SolicitudesRecibidas)
                .Include(user => user.Amigos)
                .AsTracking()
                .FirstOrDefaultAsync(user => user.Id == id);
            if (usuario == null) return null;
            return new Usuario
            {
                Id = usuario.Id,
                NombreUsuario = usuario.NombreUsuario,
                Correo = usuario.Correo,
                Foto = usuario.Foto,
                Conectado = usuario.Conectado,
                Amigos = usuario.Amigos,
                SolicitudesEnviadas = usuario.SolicitudesEnviadas,
                SolicitudesRecibidas = usuario.SolicitudesRecibidas
            };
        }

        public async Task<Usuario> GetUserByIdAndFriends(int id)
        {
            Usuario usuario = await GetQueryable()
                .Include(user => user.Amigos)
                .Include(user => user.SolicitudesRecibidas)
                .Include(user => user.SolicitudesEnviadas)
                .FirstOrDefaultAsync(user => user.Id == id);
            if (usuario == null) return null;
            return new Usuario
            {
                Id = usuario.Id,
                NombreUsuario = usuario.NombreUsuario,
                Correo = usuario.Correo,
                Foto = usuario.Foto,
                Conectado = usuario.Conectado,
                Amigos = usuario.Amigos,
                SolicitudesRecibidas = usuario.SolicitudesRecibidas,
                SolicitudesEnviadas = usuario.SolicitudesEnviadas
            };
        }
    }
}
