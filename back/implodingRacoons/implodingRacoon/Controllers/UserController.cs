using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class UserController : Controller
    {
        private readonly UserService _userService;
        public UserController(UserService userService)
        {
            _userService = userService;
        }

        [HttpGet("GetUserById/{id}")]
        public async Task<ActionResult<UserPerfil>> GetUserByIdAsync(int id)
        {
            var user = await _userService.GetUserByIdAsync(id);

            if (user == null)
                return NotFound("No se encontró el usuario");

            return Ok(user);
        }

    }
}
