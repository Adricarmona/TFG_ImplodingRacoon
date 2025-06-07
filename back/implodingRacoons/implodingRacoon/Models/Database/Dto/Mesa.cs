using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Services.GamesService;

namespace implodingRacoon.Models.Database.Dto
{
    public class Mesa
    {
        public int IdMesa { get; set; }
        public List<UserGame> jugadores { get; set; }
        public List<Carta> BarajaMesa { get; set; }
        public List<Carta> CartasLanzadas { get; set; }

    }
}
