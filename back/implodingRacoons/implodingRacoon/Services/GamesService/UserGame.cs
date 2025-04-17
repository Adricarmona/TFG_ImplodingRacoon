using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Services.GamesService
{
    public class UserGame
    {
        public int Id { get; set; }
        public bool vivo { get; set; }
        public List<Carta> baraja { get; set; }

        public string Implosionar()
        {
            foreach (var carta in baraja)
            {
                if (carta.Tipo == "defuser") return "defusado";
            }

            return "explotado";
        }

        public Carta CogerUnaCartaAleatoria()
        {
            int cantidad = baraja.Count;

            Random random = new Random();
            int numeroAcoger = random.Next(0,cantidad);

            Carta cartaAcoger = baraja[numeroAcoger];
            baraja.Remove(cartaAcoger);

            return cartaAcoger;
        }
    }
}
