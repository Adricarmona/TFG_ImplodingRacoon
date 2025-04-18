using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Services;
using Microsoft.AspNetCore.Mvc;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class CardsController : Controller
    {
        private CardsService _cardsService;
        public CardsController(CardsService cardsService)
        {
            _cardsService = cardsService;
        }

        [HttpGet("GetAllCards")]
        public async Task<ActionResult<ICollection<Carta>>> GetAllCardsAsync()
        {
            var cards = await _cardsService.GetAllCardsAndDisenioAsync();
            if (cards == null || cards.Count == 0)
            {
                return NotFound("No hay cartas");
            }
            return Ok(cards);
        }
    }
}
