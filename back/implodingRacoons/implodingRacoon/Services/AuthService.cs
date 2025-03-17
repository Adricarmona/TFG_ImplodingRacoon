using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;
using Microsoft.IdentityModel.Tokens;

namespace implodingRacoon.Services
{
    public class AuthService
    {
        private readonly UnitOfWork _unitOfWork;

        public AuthService(UnitOfWork unitOfWork) 
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<UserSimple> login(LoginRequest loginRequest)
        {
            UserSimple user = await _unitOfWork.UsuarioRepository.GetUserByCredential(loginRequest.EmailOrUser);
            if (user == null)
            {
                return null;
            }

            return user;
        }
    }
}
