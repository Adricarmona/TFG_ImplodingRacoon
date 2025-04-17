using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Services.GamesService
{
    public class Game
    {
        public int IdSala { get; set; }
        private List<UserGame> usuariosMesa { get; set; } = new List<UserGame>();
        public bool salaEmpezada { get; set; }
        private List<Carta> cartasLanzadas { get; set; }
        public string contrasenia { get; set; }

        public Game() {}

        public string anadirUsuarioMesa(UserGame userGame)
        {
            if (usuariosMesa.Contains(userGame)) return "esta";

            usuariosMesa.Add(userGame);

            return "funciono";
        }

        public string quitarUsuarioMesa(UserGame userGame)
        {
            if (!usuariosMesa.Contains(userGame)) return "no esta";

            usuariosMesa.Remove(userGame);

            return "funciono";
        }

        public List<UserGame> cogerUsuariosMesa()
        {
            return usuariosMesa;
        }

        public void lanzarCarta(Carta carta)
        {
            cartasLanzadas.Add(carta);
        }

        public List<Carta> mirarTresCartasArriba()
        {
            List<Carta> tresCartas = new List<Carta>();
            for (int i = cartasLanzadas.Count; i < cartasLanzadas.Count - 3 && i > 0 ; i--)
            {
                tresCartas.Add(cartasLanzadas[i]);
            }

            return tresCartas;
        }

        public void empezarPartida(bool empezarPartida)
        {
            salaEmpezada = empezarPartida;
        }
    }
}
