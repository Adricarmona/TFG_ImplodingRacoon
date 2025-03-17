using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database.Repository;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;

namespace implodingRacoon.Services
{
    public class AuthService
    {
        private readonly UnitOfWork _unitOfWork;
        private readonly TokenValidationParameters _tokenParameters;

        public AuthService(UnitOfWork unitOfWork, IOptionsMonitor<JwtBearerOptions> jwtOptions) 
        {
            _unitOfWork = unitOfWork;
            _tokenParameters = jwtOptions.Get(JwtBearerDefaults.AuthenticationScheme)
                   .TokenValidationParameters;
        }

        public async Task<ICollection<UserSimple>> GetAllUsersAsync()
        {
            var usuarios = await _unitOfWork.UsuarioRepository.GetAllAsync();

            // Convertimos la lista de Usuario a UserSimple (DTO)
            return usuarios.Select(u => new UserSimple
            {
                Id = u.Id,
                NombreUsuario = u.NombreUsuario,
                Correo = u.Correo,
                Foto = u.Foto,
                Conectado = u.Conectado
            }).ToList();
        }

        public async Task<UserSimple> GetUser(LoginRequest loginRequest)
        {
            return await _unitOfWork.UsuarioRepository.GetUserByCredential(loginRequest.EmailOrUser); ;
        }

        public async Task<string> Login(LoginRequest loginRequest)
        {
            UserSimple usuario = await _unitOfWork.UsuarioRepository.GetUserByCredential(loginRequest.EmailOrUser);

            if (usuario == null) return null;

            String token = ObtenerJWT(usuario);

            return token;
        }

        public async Task<string> Register(UserRegister userRegister)
        {
            UserSimple usuarioSimple = await _unitOfWork.UsuarioRepository.AddUser(userRegister);

            if (usuarioSimple == null) return null;

            String token = ObtenerJWT(usuarioSimple);

            await _unitOfWork.SaveAsync();

            return token;
        }

        public string ObtenerJWT(UserSimple usuario)
        {
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                // EL CONTENIDO DEL JWT
                Claims = new Dictionary<string, object>
                {
                    { "id", usuario.Id },
                    { "name", usuario.NombreUsuario },
                    { ClaimTypes.Role, usuario.Admin },
                    { "image", usuario.Foto }
                },
                Expires = DateTime.UtcNow.AddYears(3),
                SigningCredentials = new SigningCredentials(
                        _tokenParameters.IssuerSigningKey,
                        SecurityAlgorithms.HmacSha256Signature
                    )
            };
            JwtSecurityTokenHandler tokenHandler = new JwtSecurityTokenHandler();
            SecurityToken token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);
        }
    }
}
