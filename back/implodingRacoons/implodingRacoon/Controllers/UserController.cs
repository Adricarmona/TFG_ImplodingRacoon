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

            return Ok(new UserPerfil
            {
                Id = user.Id,
                NombreUsuario = user.NombreUsuario,
                cantidadAmigos = user.cantidadAmigos,
                urlFoto = _imagenMapper.AddCorrectPathImage(user.urlFoto, Request)
            });
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

        [HttpGet("GetFriendRequests/{id}")]
        public async Task<ActionResult<List<UserAmigos>>> GetFriendRequests(int id)
        {
            var friendRequests = await _userService.GetFriendRequests(id);

            if (friendRequests == null)
                return NotFound("No se encontraron solicitudes de amistad");

            friendRequests = _imagenMapper.AddCorrectPathUserAmigo(friendRequests, Request).ToList();

            return Ok(friendRequests);
        }

        [HttpDelete("DeleteFriendRequest/{id}")]
        public async Task<ActionResult> DeleteFriendRequest(int id, int idFriend)
        {
            string result = await _userService.DeleteFriendRequest(id, idFriend);

            if (result == "Usuario no encontrado")
                return NotFound("No se encontró el usuario");

            if (result == "Solicitud de amistad no encontrada")
                return NotFound("Solicitud de amistad no encontrada");

            return Ok(result);
        }

        [HttpGet("GetUsersByIdForFriends/{id}")]
        public async Task<ActionResult<List<UserAmigos>>> GetUsersByUsernameForFriends(int id)
        {

            var users = await _userService.GetUsersByUsernameForFriends(id);

            if (users == null) return NotFound("No se encontraron usuarios");

            users = _imagenMapper.AddCorrectPathUserAmigo(users, Request).ToList();

            return Ok(users);

        }

        [HttpGet("GetUsersByIdAndNameForFriends/{id}")]
        public async Task<ActionResult<List<UserAmigos>>> GetUsersByIdAndNameForFriends(int id, string name)
        {

            var users = await _userService.GetUsersByIdAndNameForFriends(id, name);

            if (users == null) return NotFound("No se encontraron usuarios");

            users = _imagenMapper.AddCorrectPathUserAmigo(users, Request).ToList();

            return Ok(users);

        }

        [HttpDelete("DeleteFriend/{id}")]
        public async Task<ActionResult> DeleteFriend(int id, int friendId)
        {
            var result = await _userService.DeleteFriend(id, friendId);

            if (result == "Usuario no encontrado" ||
                result == "Amigo no encontrado" ||
                result == "No es amigo")
                return NotFound(new ResponseToken
                {
                    message = result,
                    code = 404
                });

            return Ok(new ResponseToken
            {
                message = result,
                code = 200
            });
        }

        [HttpPost("ChangeUserPhoto")]
        public async Task<ActionResult> ChangeUserPhoto(IFormFile foto, int id)
        {
            if (foto == null || foto.Length == 0)
                return BadRequest("No se ha proporcionado una imagen válida.");

            var result = await _userService.ChangeUserPhoto(foto, id);

            if (result == null)
                return NotFound("No se encontró el usuario");

            return Ok(new ResponseToken
            {
                message = "Foto de perfil actualizada correctamente.",
                code = 200
            });
        }

        [HttpPost("ChangeUserName")]
        public async Task<ActionResult> ChangeUserName(string newName, int id)
        {
            if (string.IsNullOrEmpty(newName))
                return BadRequest("El nombre de usuario no puede estar vacío.");

            var result = await _userService.ChangeUserName(newName, id);

            if (result == false)
                return NotFound("No se encontró el usuario");

            return Ok(new ResponseToken
            {
                message = "Nombre de usuario actualizado correctamente.",
                code = 200
            });
        }

        [HttpPost("ChangeUserPassword")]
        public async Task<ActionResult> ChangeUserPassword(string oldPassword, string newPassword, int id)
        {
            if (string.IsNullOrEmpty(oldPassword))
                return BadRequest("La contraseña antigua no puede estar vacía.");

            if (string.IsNullOrEmpty(newPassword))
                return BadRequest("La contraseña nueva no puede estar vacía.");

            var contraseñaComparada = await _userService.ComparePassword(oldPassword, id);

            if (contraseñaComparada == "Contraseña incorrecta")
                return BadRequest("La contraseña antigua no coincide.");

            if (contraseñaComparada == "Usuario no encontrado")
                return NotFound("No se encontró el usuario");

            var result = await _userService.ChangeUserPassword(newPassword, id);

            return Ok(new ResponseToken
            {
                message = "Contraseña actualizada correctamente.",
                code = 200
            });
        }

        [HttpDelete("DeleteUser/{id}")]
        public async Task<ActionResult> DeleteUser(int id)
        {
            var result = await _userService.DeleteUser(id);

            if (result == "Usuario no encontrado")
                return NotFound("No se encontró el usuario");

            return Ok(new ResponseToken
            {
                message = result,
                code = 200
            });
        }
    }
}
