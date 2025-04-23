using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Services.WebSocketService
{
    public class WSHelper
    {
        private readonly UnitOfWork _unitOfWork;

        public WSHelper(UnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<ICollection<Carta>> getCartas()
        {
            return await _unitOfWork.CartaRepository.GetAllAsync();
        }
    }
}
