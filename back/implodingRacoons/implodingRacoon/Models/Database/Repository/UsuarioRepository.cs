using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database.Repository.Repository;
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
    }
}
