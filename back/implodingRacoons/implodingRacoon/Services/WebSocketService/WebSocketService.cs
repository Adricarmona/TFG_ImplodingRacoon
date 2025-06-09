using System.Net.WebSockets;
using System.Text.Json;
using System.Threading;
using implodingRacoon.Services.GamesService;
using System.Threading.Tasks;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database;

namespace implodingRacoon.Services.WebSocketService
{
    public class WebSocketService
    {
        private static List<WebSocketHandler> userHandlerHost = new List<WebSocketHandler>();

        /**
         *  Funcion para unirse a una mesa
         */
        public void unirseMesa(RecivedUserWebSocket receivedUser, WebSocketHandler userHandler, WebSocketHandler[] handlers)
        {
            /// primer identifier es la mesa
            /// segundo identifier es el id del jugador
            if (receivedUser.Identifier == null || receivedUser.Identifier2 == null)
            {
                JsonWebsoket messageToEnviar = new JsonWebsoket
                {
                    type = 3,
                    message = "error json"
                };
                string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                userHandler.SendAsync(mensageEnviar);
            }
            else
            {
                Game mesa = Games.buscarMesa(Int32.Parse(receivedUser.Identifier));

                if (mesa == null)
                {
                    JsonWebsoket messageToEnviar = new JsonWebsoket
                    {
                        type = 3,
                        message = "mesa no existe"
                    };
                    string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                    userHandler.SendAsync(mensageEnviar);
                } 
                else
                {

                    if (mesa.SalaEmpezada == true)
                    {
                        JsonWebsoket messageToEnviar = new JsonWebsoket
                        {
                            type = 3,
                            message = "mesa ya empezada"
                        };
                        string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                        userHandler.SendAsync(mensageEnviar);
                    }
                    else
                    {

                        if (mesa.cogerUsuariosMesa().Count > mesa.usuariosMaximos)
                        {
                            JsonWebsoket messageToEnviar = new JsonWebsoket
                            {
                                type = 3,
                                message = "mesa llena"
                            };
                            string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                            userHandler.SendAsync(mensageEnviar);
                        }
                        else
                        {

                            // Comprobamos si el usuario ya está conectado
                            bool repetido = false;
                            foreach (WebSocketHandler wsHandler in handlers)
                            {
                                if (userHandler.Usuario != null)
                                {
                                    if (wsHandler.Usuario == userHandler.Usuario)
                                    {
                                        JsonWebsoket messageToEnviar = new JsonWebsoket
                                        {
                                            type = 3,
                                            message = "Usuario ya existente"
                                        };
                                        string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                                        userHandler.SendAsync(mensageEnviar);
                                        repetido = true;
                                        break;
                                    }

                                }

                            }

                            if (repetido == false)
                            {

                                // añadimos el usuario a la mesa
                                UserGame newuser = new UserGame(Int32.Parse(receivedUser.Identifier2));
                                userHandler.Usuario = newuser;

                                Games.unirMesa(Int32.Parse(receivedUser.Identifier), newuser);

                                JsonWebsoket messageToEnviar = new JsonWebsoket
                                {
                                    type = 3,
                                    message = true.ToString()
                                };
                                string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                                userHandler.SendAsync(mensageEnviar);

                                /*
                                 *  Esto no va 

                                foreach (UserGame usuarios in mesa.cogerUsuariosMesa())
                                {
                                    foreach (WebSocketHandler handler in userHandlerHost)
                                    {

                                        JsonWebsoket messageToEnviarOtros = new JsonWebsoket
                                        {
                                            type = 3,
                                            message = "new user"
                                        };
                                        string mensageEnviarOtros = JsonSerializer.Serialize(messageToEnviar);

                                        handler.SendAsync(mensageEnviarOtros);

                                    }

                                }
                                */

                                foreach (var item in handlers)
                                {
                                    JsonWebsoket messageToEnviarOtros = new JsonWebsoket
                                    {
                                        type = 3,
                                        message = "new user"
                                    };
                                    string mensageEnviarOtros = JsonSerializer.Serialize(messageToEnviar);

                                    item.SendAsync(mensageEnviarOtros);
                                }

                            }
                        }
                    }
                }
            }
        }
        

        /**
         *  Funcion para empezar una partida en una mesa
         */
        public async Task empezarPartida(RecivedUserWebSocket receivedUser, WebSocketHandler userHandler, WebSocketHandler[] handlers, List<Task> tasks, List<Carta> cartas)
        {
            if (receivedUser.Identifier == null)
            {
                userHandler.SendAsync("mesa no indicada");
            }
            else
            {

                // empieza la partida con el id de la mesa dado
                Game game = Games.buscarMesa(Int32.Parse(receivedUser.Identifier));

                if (game == null)
                {
                    userHandler.SendAsync("mesa no encontrada");
                }
                else
                {
                    game.empezarPartida(true);
                    game.anadirMesaBaraja( await RecogerCartas(cartasBomba: game.cogerUsuariosMesa().Count - 1, cartas: cartas));

                    foreach (UserGame usuarios in game.cogerUsuariosMesa())
                    {
                        foreach (WebSocketHandler handler in handlers)
                        {
                            if (handler.Usuario.Id == usuarios.Id)
                            {
                                tasks.Add(handler.SendAsync("La partida ha comenzado"));
                            }
                        }
                    }
                }
            }
        }

        public void salirPartida(RecivedUserWebSocket receivedUser, WebSocketHandler userHandler)
        {
            if (receivedUser.Identifier == null)
            {
                userHandler.SendAsync("mesa no indicada");
            }
            else
            {
                Game mesaSalir = Games.buscarMesa(Int32.Parse(receivedUser.Identifier));

                if (mesaSalir == null)
                {
                    userHandler.SendAsync("mesa no encontrada");
                }
                else
                {
                    mesaSalir.quitarUsuarioMesa(userHandler.Usuario);
                }
            }
        }

        public void crearSala(WebSocketHandler userHandler, RecivedUserWebSocket receivedUser)
        {
            if (receivedUser.Identifier == null || receivedUser.Identifier == "")
            {
                JsonWebsoket messageToEnviar = new JsonWebsoket
                {
                    type = 2,
                    message = "dato ingresado incorrectamente"
                };
                string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                userHandler.SendAsync(mensageEnviar);
            }
            else if(receivedUser.Identifier2 == null || receivedUser.Identifier2 == "")
            {
                JsonWebsoket messageToEnviar = new JsonWebsoket
                {
                    type = 2,
                    message = "Jugadores maximos no ingresados"
                };
                string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                userHandler.SendAsync(mensageEnviar);
            }
            else
            {
                Game partida = Games.anadirMesa(receivedUser.Identifier2);

                UserGame newuser = new UserGame(Int32.Parse(receivedUser.Identifier));
                userHandler.Usuario = newuser;
                userHandlerHost.Add(userHandler);

                partida.anadirUsuarioMesa(newuser);

                JsonWebsoket messageToEnviar = new JsonWebsoket
                {
                    type = 2,
                    message = partida.IdSala + ""
                };
                string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                userHandler.SendAsync(mensageEnviar);

            }


        }

        public bool buscarUsuario(WebSocketHandler userHandler)
        {

            foreach (Game mesa in Games.mesas())
            {
                /*
                if (userHandler.Usuario == mesa.cogerHostMesa())
                {
                    return true;
                }
                */
                foreach (UserGame usuario in mesa.cogerUsuariosMesa())
                {
                    if (userHandler.Usuario == usuario)
                    {
                        return true;
                    }
                }
            }

            return false;

        }

        public bool buscarUsuarioMesaEmpezado(WebSocketHandler userHandler)
        {

            foreach (Game mesa in Games.mesas())
            {
                /*
                if (userHandler.Usuario == mesa.cogerHostMesa())
                {
                    if (mesa.SalaEmpezada)
                    {
                        return true;
                    }
                }
                */
                foreach (UserGame usuario in mesa.cogerUsuariosMesa())
                {
                    if (userHandler.Usuario == usuario)
                    {
                        if (mesa.SalaEmpezada)
                        {
                            return true;
                        }
                    }
                }
            }

            return false;

        }

        public async Task<List<Carta>> RecogerCartas(List<Carta> cartas,int numeroCartas = 40, int cartasBomba = 0)
        {
            // numero random
            Random random = new Random();

            // cogiendo cartas bbdd
            List<Carta> cartasList = cartas;

            // cartas finales
            List<Carta> cartasDadas = new();

            // cantidad cartasBombas puestas
            int cartasBombaPuestas = 0;

            for (int i = 0; i < cartasBomba; i++)
            {
                cartasDadas.Add(cartasList[0]);
            }


            for (int i = 0; i < numeroCartas - cartasBomba; i++)
            {
                Carta carta = cartasList[random.Next(numeroCartas)];

                Carta cartasComrpobar = new Carta();
                do
                {
                    cartasComrpobar = cartasList[random.Next(cartasList.Count)];
                }
                while (cartasComrpobar.Tipo == "C4");

                cartasDadas.Add(cartasComrpobar);
            }

            List<Carta> cartasBarajadas = barajar(cartasDadas);

            return cartasBarajadas;
        }

        public List<Carta> barajar(List<Carta> listaCartasABarajar)
        {
            // numero random
            Random random = new Random();

            // las nuevas barajas
            List<Carta> barajardas = new List<Carta>();

            // el numero de las cartas
            int numeroCartas = listaCartasABarajar.Count;

            for (int i = 0; i < numeroCartas; i++)
            {
                Carta cartaSeleccionada = listaCartasABarajar[random.Next()];

                barajardas.Add(cartaSeleccionada);
            }

            return barajardas;
        }

    }
}
