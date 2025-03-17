using System.Collections;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Services;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class AuthController : Controller
    {
        private AuthService _authSevice;
        public AuthController(AuthService authSevice)
        {
            _authSevice = authSevice;
        }

        [HttpGet("Todos los usuarios")]
        public async Task<ICollection<UserSimple>> getAllUsersAsync()
        {
            return await _authSevice.GetAllUsersAsync();
        }

        [HttpPost("usuarioConcreto")]
        public async Task<ActionResult<UserSimple>> LoginUsuarioIndicadoAsync([FromBody] LoginRequest user)
        {
            if (user == null) return NotFound("No ingresado");

            if (string.IsNullOrEmpty(user.EmailOrUser)) return NotFound("Usuario vacio");

            if (string.IsNullOrEmpty(user.Password)) return NotFound("Contraseña vacia");


            UserSimple usuario = await _authSevice.GetUser(user);

            if (usuario == null) return NotFound("Usuario no encontrado");

            return Ok(usuario);
        }


        [HttpPost("Login")]
        public async Task<ActionResult<string>> LoginAsync([FromBody] LoginRequest user)
        {
            if (user == null) return NotFound("No ingresado");

            if (string.IsNullOrEmpty(user.EmailOrUser)) return NotFound("Usuario vacio");

            if (string.IsNullOrEmpty(user.Password)) return NotFound("Contraseña vacia");


            String token = await _authSevice.Login(user);

            if (token == null) return NotFound("Usuario no encontrado");

            return Ok(token);
        }

        [HttpPost("Register")]
        public async Task<ActionResult<string>> RegisterAsync([FromBody] UserRegister user)
        {
            if (user == null) return NotFound("No ingresado");

            if (string.IsNullOrEmpty(user.NombreUsuario)) return NotFound("Usuario vacio");

            if (string.IsNullOrEmpty(user.Correo)) return NotFound("Correo vacio");

            if (string.IsNullOrEmpty(user.Password)) return NotFound("Contraseña vacia");

            String resultado = await _authSevice.Register(user);

            if (string.IsNullOrEmpty(resultado)) return NotFound(resultado);

            return Ok(resultado);
        }
    }
}