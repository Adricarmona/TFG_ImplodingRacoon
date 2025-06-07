using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class MesaController : Controller
    {
        private MesaService _mesaService;
        public MesaController(MesaService mesaService)
        {
            _mesaService = mesaService;
        }

        [HttpGet("GetMesaById/{id}")]
        public async Task<ActionResult<Mesa>> GetMesaByIdAsync(int id)
        {
            var mesa = await _mesaService.GetMesaInfoByIdAsync(id);

            if (mesa == null)
                return NotFound("No se encontró la mesa");

            return Ok(mesa);
        }
    }
}
