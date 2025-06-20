﻿using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using implodingRacoon.Models;
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

        public async Task<Usuario> GetUser(LoginRequest loginRequest)
        {
            return await _unitOfWork.UsuarioRepository.GetUserByCredential(loginRequest.EmailOrUser); ;
        }

        public async Task<string> Login(LoginRequest loginRequest)
        {
            loginRequest.Password = PasswordHelper.Hash(loginRequest.Password);

            Usuario usuario = await _unitOfWork.UsuarioRepository.GetUserByCredential(loginRequest.EmailOrUser);

            if (usuario == null) return null;

            if (usuario.Contrasena != loginRequest.Password)
                return null;

            String token = ObtenerJWT(usuario);

            return token;
        }

        /**
         *  Servicio que registra el usuario
         */
        public async Task<string> Register(UserRegister userRegister)
        {
            // Comprobamos que no haya cuentas con ningun usuario ni cuenta con los datos dados
            // Si existe alguna de los dos devuelve null
            Usuario usuario = await _unitOfWork.UsuarioRepository.GetUserByCredential(userRegister.NombreUsuario);
            Usuario correo = await _unitOfWork.UsuarioRepository.GetUserByCredential(userRegister.Correo);

            if (usuario != null || correo != null) return null;

            userRegister.Password = PasswordHelper.Hash(userRegister.Password);

            await _unitOfWork.UsuarioRepository.AddUser(userRegister);

            await _unitOfWork.SaveAsync();

            Usuario usuarioDevolver = await _unitOfWork.UsuarioRepository.GetUserByCredential(userRegister.NombreUsuario);

            if (usuarioDevolver == null) return null;

            String token = ObtenerJWT(usuarioDevolver);

            await _unitOfWork.SaveAsync();

            return token;
        }

        public string ObtenerJWT(Usuario usuario)
        {
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                // EL CONTENIDO DEL JWT
                Claims = new Dictionary<string, object>
                {
                    { "id", usuario.Id },
                    { "name", usuario.NombreUsuario },
                    { ClaimTypes.Role, usuario.Admin },
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

        public string ObtenerJWT(UserSimple usuario)
        {
            return ObtenerJWT(new Usuario
            {
                Id = usuario.Id,
                NombreUsuario = usuario.NombreUsuario,
                Correo = usuario.Correo,
                Foto = usuario.Foto,
                Conectado = usuario.Conectado,
                Admin = usuario.Admin
            });
        }
    }
}
