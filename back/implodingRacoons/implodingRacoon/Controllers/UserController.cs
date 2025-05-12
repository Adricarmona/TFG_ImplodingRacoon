using implodingRacoon.Models;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class UserController : Controller
    {
        private readonly UserService _userService;
        private ImageMapper _imagenMapper;

        public UserController(UserService userService, ImageMapper imageMapper)
        {
            _userService = userService;
            _imagenMapper = imageMapper;
        }

        [HttpGet("GetUserById/{id}")]
        public async Task<ActionResult<UserPerfil>> GetUserByIdAsync(int id)
        {
            var user = await _userService.GetUserByIdAsync(id);

            if (user == null)
                return NotFound("No se encontró el usuario");

            return Ok(user);
        }

        [HttpGet("GetFriendsbyUserId/{id}")]
        public async Task<ActionResult<List<UserAmigos>>> GetFriendsByUserIdAsync(int id)
        {
            var friends = await _userService.GetFriendsByUserIdAsync(id);

            if (friends == null)
                return NotFound("No se encontraron amigos");


            friends = _imagenMapper.AddCorrectPathUserAmigo(friends, Request).ToList();

            return Ok(friends);
        }

        [HttpPut("SetFriendRequest")]
        public async Task<ActionResult> SetFriendRequest(int id, int friendId)
        {
            var result = await _userService.SetFriendRequest(id, friendId);

            if (result == null)
                return NotFound("No se encontró el usuario");

            return Ok(result);
        }

        [HttpPut("AcceptFriendRequest")]
        public async Task<ActionResult> AcceptFriendRequest(int id, int friendId)
        {
            var result = await _userService.AcceptFriendRequest(id, friendId);

            if (result == null)
                return NotFound("No se encontró el usuario");

            return Ok(result);
        }
    }
}
