using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Services.GamesService
{
    public class Game
    {
        public int IdSala { get; set; }
        private List<UserGame> UsuariosMesa { get; set; } = new List<UserGame>();
        private  UserGame HostMesa { get; set; }
        public int OrdenJugadores { get; set; }
        public bool SalaEmpezada { get; set; }
        private List<Carta> BarajaMesa { get; set; }
        private List<Carta> CartasLanzadas { get; set; }
        public string Contrasenia { get; set; }


        public Game() {}

        public string anadirHostMesa(UserGame hostMesa)
        {
            if (hostMesa != null)
            {
                HostMesa = hostMesa;
                return "ingresado";
            }

            return "fallado";
        }

        public UserGame cogerHostMesa()
        {
            return HostMesa;
        }

        public string anadirUsuarioMesa(UserGame userGame)
        {
            if (UsuariosMesa.Contains(userGame)) return "esta";

            UsuariosMesa.Add(userGame);

            return "funciono";
        }

        public string quitarUsuarioMesa(UserGame userGame)
        {
            if (!UsuariosMesa.Contains(userGame)) return "no esta";

            UsuariosMesa.Remove(userGame);

            return "funciono";
        }

        public List<UserGame> cogerUsuariosMesa()
        {
            return UsuariosMesa;
        }

        public void lanzarCarta(Carta carta)
        {
            CartasLanzadas.Add(carta);
        }


        // Cartas mesa
        public List<Carta> mirarTresCartasArriba()
        {
            List<Carta> tresCartas = new List<Carta>();
            for (int i = CartasLanzadas.Count; i < CartasLanzadas.Count - 3 && i > 0 ; i--)
            {
                tresCartas.Add(CartasLanzadas[i]);
            }

            return tresCartas;
        }

        public void anadirMesaBaraja(List<Carta> baraja)
        {
            BarajaMesa.Clear();
            BarajaMesa = baraja;
        }


        // Empezar partida
        public void empezarPartida(bool empezarPartida)
        {
            SalaEmpezada = empezarPartida;
        }
    }
}
