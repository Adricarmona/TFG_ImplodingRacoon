using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Repository.Repository;
using Microsoft.EntityFrameworkCore;

namespace implodingRacoon.Models.Database.Repository
{
    public class UsuarioRepository : Repository<UserSimple, int>
    {
        public UsuarioRepository(ImplodingRacoonsContext context) : base(context) { }

        public async Task<UserSimple> GetUserByCredential(string emailOrUser)
        {
            return await GetQueryable()
                .FirstOrDefaultAsync(user => user.Correo == emailOrUser || user.NombreUsuario == emailOrUser);
        }
    }
}
