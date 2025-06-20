﻿namespace implodingRacoon.Services.GamesService
{
    public class Games
    {
        private static List<Game> games;

        public static Game anadirMesa(string identifier2)
        {
            Game game = new Game();
            game.IdSala = games.Count + 1;
            game.usuariosMaximos = Int32.Parse(identifier2); 

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

        /**
         * Unir jugador mesa
         */
        public static void unirMesa(int idMesa, UserGame UsuarioGame)
        {
            Game mesa = buscarMesa(idMesa);
            if (mesa != null)
            {
               mesa.anadirUsuarioMesa(UsuarioGame);
            }
        }
    }
}
