using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;

namespace implodingRacoon.Services
{
    public class UserService
    {
        private readonly UnitOfWork _unitOfWork;

        public UserService(UnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<UserPerfil> GetUserByIdAsync(int id)
        {
            var user = await _unitOfWork.UsuarioRepository.GetByIdAsync(id);
            
            if (user == null)
                return null;

            return new UserPerfil
            {
                Id = user.Id,
                NombreUsuario = user.NombreUsuario,
                urlFoto = user.Foto,
                cantidadAmigos = user.idAmigos.Count(),
            };
        }

    }
}
