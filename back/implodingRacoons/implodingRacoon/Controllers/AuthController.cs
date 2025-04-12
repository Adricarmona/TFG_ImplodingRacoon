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
        public async Task<ActionResult<ResponseToken>> LoginAsync([FromBody] LoginRequest user)
        {
            ResponseToken responseToken = new();

            if (user == null)
            {
                responseToken.message = "not entered data";
                responseToken.code = 404;

                return NotFound(responseToken);
            }

            if (string.IsNullOrEmpty(user.EmailOrUser))
            {
                responseToken.message = "empty user";
                responseToken.code = 404;

                return NotFound(responseToken);
            }

            if (string.IsNullOrEmpty(user.Password)) 
            {
                responseToken.message = "Empty password";
                responseToken.code = 404;

                return NotFound(responseToken); 
            }


            String token = await _authSevice.Login(user);

            if (token == null)
            {
                responseToken.message = "Incorrect username or password";
                responseToken.code = 404;

                return Ok(responseToken);
            }


            responseToken.message = token;
            responseToken.code = 200;

            return Ok(responseToken);
        }

        [HttpPost("Register")]
        public async Task<ActionResult<ResponseToken>> RegisterAsync([FromBody] UserRegister user)
        {
            ResponseToken responseToken = new();

            if (user == null)
            {
                responseToken.message = "Not entered data";
                responseToken.code = 404;

                return NotFound(responseToken);
            }

            if (string.IsNullOrEmpty(user.NombreUsuario))
            {
                responseToken.message = "empty user";
                responseToken.code = 404;

                return NotFound(responseToken);
            }

            if (string.IsNullOrEmpty(user.Correo))
            {
                responseToken.message = "Empty mail";
                responseToken.code = 404;

                return NotFound(responseToken);
            }

            if (string.IsNullOrEmpty(user.Password))
            {
                responseToken.message = "Empty password";
                responseToken.code = 404;

                return NotFound(responseToken);
            }


            String resultado = await _authSevice.Register(user);

            if (string.IsNullOrEmpty(resultado))
            {
                responseToken.message = "Error generating user";
                responseToken.code = 404;

                return Ok(responseToken);
            }

            responseToken.message = resultado;
            responseToken.code = 201;

            return Ok(responseToken);
        }
    }
}