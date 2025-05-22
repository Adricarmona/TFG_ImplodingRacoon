using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Services;
using Microsoft.AspNetCore.Mvc;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class PublicacionController : Controller
    {
        private readonly PublicacionService _publicacionService;

        public PublicacionController(PublicacionService publicacionService)
        {
            _publicacionService = publicacionService;
        }

        [HttpPost("CreatePost")]
        public async Task<ActionResult> CreatePost(publicarPublicacion publicacion)
        {
            if (publicacion == null)
                return BadRequest("Publicación no válida.");

            var resutlado = await _publicacionService.CreatePost(publicacion);

            if (resutlado == false)
            {
                return BadRequest("Error al crear la publicación.");
            } else
            {
                return Ok("Publicación creada con éxito.");
            }
                
        }

        [HttpGet("GetPostsByUserId/{id}")]
        public async Task<ActionResult<List<PublicacionDto>>> GetPostsByUserId(int id)
        {
            var publicaciones = await _publicacionService.GetPostsByUserId(id);

            if (publicaciones == null || !publicaciones.Any())
                return NotFound("No se encontraron publicaciones.");
            
            return Ok(publicaciones);
        }

        [HttpGet("GetAllPosts")]
        public async Task<ActionResult<List<PublicacionTarjetas>>> GetAllPosts()
        {
            var publicaciones = await _publicacionService.GetAllPosts();

            if (publicaciones == null || !publicaciones.Any())
                return NotFound("No se encontraron publicaciones.");
            
            return Ok(publicaciones);
        }

        [HttpGet("GetPostById/{id}")]
        public async Task<ActionResult<List<PublicacionInformacion>>> GetPostById(int id)
        {
            var publicaciones = await _publicacionService.GetPostByUserId(id);

            if (publicaciones == null)
                return NotFound("No se encontraron publicaciones.");
            
            return Ok(publicaciones);
        }

        [HttpGet("GetPostByName/{name}")]
        public async Task<ActionResult<List<PublicacionTarjetas>>> GetPostByName(string name)
        {
            if (string.IsNullOrEmpty(name))
                return BadRequest("Nombre de publicación no válido.");

            var publicaciones = await _publicacionService.GetPostByName(name);

            if (publicaciones == null)
                return NotFound("No se encontraron publicaciones.");
            
            return Ok(publicaciones);
        }
    }
}
