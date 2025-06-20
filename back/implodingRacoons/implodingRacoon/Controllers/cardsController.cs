﻿using implodingRacoon.Models;
using implodingRacoon.Models.Database.Dto;
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
        private ImageMapper _imagenMapper;
        public CardsController(CardsService cardsService, ImageMapper imageMapper)
        {
            _cardsService = cardsService;
            _imagenMapper = imageMapper;
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

        [HttpGet("GetCardById/{id}")]
        public async Task<ActionResult<Carta>> GetCardByIdAsync(int id)
        {
            var card = await _cardsService.GetCardByIdAndDisenioAsync(id);

            if (card == null)
            {
                return NotFound("No se encontró la carta");
            }

            return Ok(card);
        }

        [HttpGet("GetCardByIdImage/id={id}type={type}")]
        public async Task<ActionResult<CardWithDisenio>> GetCardByIdImage(int id,TypeCards type)
        {
            var card = await _cardsService.GetCardByIdAndDisenioTypeImageAsync(id, type);

            if (card == null)
            {
                return NotFound("No se encontró la carta");
            }

            card = _imagenMapper.AddCorrectPathCards(card, Request);

            return Ok(card);
        }

        [HttpGet("GetCardByIdImage/type={type}")]
        public async Task<ActionResult<CardWithDisenio>> GetCardsImage(TypeCards type)
        {
            var card = await _cardsService.GetAllCardAndDisenioTypeImageAsync(type);

            if (card == null)
            {
                return NotFound("No se encontró la cartas");
            }

            card = _imagenMapper.AddCorrectPathCards(card, Request).ToList();

            return Ok(card);
        }
    }
}
