using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Services
{
    public class CardsService
    {
        private readonly UnitOfWork _unitOfWork;

        public CardsService(UnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<ICollection<Carta>> GetAllCardsAsync()
        {
            var cards = await _unitOfWork.CartaRepository.GetAllAsync();
            return cards;
        }

        public async Task<Carta> GetCardByIdAsync(int id)
        {
            var card = await _unitOfWork.CartaRepository.GetByIdAsync(id);
            return card;
        }

        public async Task<ICollection<Carta>> GetAllCardsAndDisenioAsync()
        {
            var cards = await _unitOfWork.CartaRepository.GetCartaWhithDisenio();
            return cards;
        }
    }
}
