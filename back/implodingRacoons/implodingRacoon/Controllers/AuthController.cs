using implodingRacoon.Models.Database.Dto;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class AuthController : Controller
    {
        public AuthController()
        {
        }

        [HttpPost("Login")]
        public ActionResult<string> Login([FromBody] LoginRequest user)
        {
            if (user != null)
            {
                return NotFound("No ingresado");
            }

            if (string.IsNullOrEmpty(user.EmailOrUser))
            {
                return NotFound("Usuario vacio");
            }

            if (string.IsNullOrEmpty(user.Password))
            {
                return NotFound("Contraseña vacia");
            }


            return Ok("Yes");
        }
    }
}