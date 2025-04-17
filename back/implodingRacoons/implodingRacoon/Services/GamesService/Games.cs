namespace implodingRacoon.Services.GamesService
{
    public class Games
    {
        private static List<Game> games;

        public static Game anadirMesa()
        {
            int numeroMasAlto = 1;

            foreach (Game gameEmpezado in games)
            {
                if (gameEmpezado.IdSala >= numeroMasAlto) { numeroMasAlto = gameEmpezado.IdSala++; }
            }

            Game game = new Game();
            game.IdSala = numeroMasAlto;

            games.Add(game);

            return game;
        }

        public static void quitarMesa(Game game)
        {
            if (games.Contains(game)) 
                games.Remove(game);
        }

        public static Game buscarMesa(int idMesa)
        {
            foreach (Game game in games)
            {
                if (game.IdSala == idMesa) return game;
            }

            return null;
        }

        public static List<Game> mesas()
        {
            return games;
        }

        public static void iniciarMesas()
        {
            games = new List<Game>();
        }
    }
}
