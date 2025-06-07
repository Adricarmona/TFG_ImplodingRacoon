using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Services.GamesService;

namespace implodingRacoon.Services
{
    public class MesaService
    {
        private readonly UnitOfWork _unitOfWork;

        public MesaService(UnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        internal async Task<Mesa> GetMesaInfoByIdAsync(int id)
        {
            Games games = new Games();
            Game mesa = Games.buscarMesa(id);

            if (mesa == null)
                return null;

            List<UserGame> jugadores = mesa.cogerUsuariosMesa();

            Mesa mesaInfo = new Mesa
            {
                IdMesa = mesa.IdSala,
                jugadores = jugadores,
                BarajaMesa = mesa.SalaEmpezada ? mesa.BarajaMesa : null,
                CartasLanzadas = mesa.SalaEmpezada ? mesa.CartasLanzadas : null,
            };

            return mesaInfo;

        }
    }
}
