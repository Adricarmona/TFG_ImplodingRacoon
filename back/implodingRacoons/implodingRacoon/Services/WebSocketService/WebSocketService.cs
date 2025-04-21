using System.Net.WebSockets;
using System.Text.Json;
using System.Threading;
using implodingRacoon.Services.GamesService;
using System.Threading.Tasks;
using implodingRacoon.Models.Database.Dto;

namespace implodingRacoon.Services.WebSocketService
{
    public class WebSocketService
    {
       
        /**
         *  Funcion para unirse a una mesa
         */
        public void unirseMesa(RecivedUserWebSocket receivedUser, WebSocketHandler userHandler, List<WebSocketHandler> handlers, List<Task> tasks)
        {
            /// primer identifier es la mesa
            /// segundo identifier es el id del jugador
            if (receivedUser.Identifier == null || receivedUser.Identifier2 == null)
            {
                userHandler.SendAsync("error json");
            }
            else
            {
                Game mesa = Games.buscarMesa(Int32.Parse(receivedUser.Identifier));

                if (mesa == null)
                {
                    userHandler.SendAsync("mesa no existe");
                } 
                else
                {

                    if (mesa.salaEmpezada == true)
                    {
                        userHandler.SendAsync("mesa ya empezada");
                    }
                    else
                    {

                        if (mesa.cogerUsuariosMesa().Count >= 5)
                        {
                            userHandler.SendAsync("mesa llena");
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
                                        userHandler.SendAsync("Usuario ya existente");
                                        repetido = true;
                                        break;
                                    }

                                }

                            }

                            if (repetido == false)
                            {

                                // añadimos el usuario a la mesa
                                UserGame newuser = new UserGame(Int32.Parse(receivedUser.Identifier2));
                                Games.unirMesa(Int32.Parse(receivedUser.Identifier), newuser);
                                userHandler.Usuario = newuser;

                                foreach (UserGame usuarios in mesa.cogerUsuariosMesa())
                                {
                                    foreach (WebSocketHandler handler in handlers)
                                    {
                                        if (userHandler == handler)
                                        {
                                            userHandler.SendAsync("Unido a la mesa: " + mesa.IdSala);
                                        }
                                        else if (handler.Usuario == usuarios)
                                        {
                                            tasks.Add(handler.SendAsync("Usuario unido: " + usuarios.Id));
                                        }

                                    }
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
        public void empezarPartida(RecivedUserWebSocket receivedUser, WebSocketHandler userHandler, WebSocketHandler[] handlers, List<Task> tasks)
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

            Game partida = Games.anadirMesa();
            userHandler.SendAsync("creada sala: " + partida.IdSala + "");

            /*
            while (receivedUser.TypeMessage != "stop")
            {
                System.Threading.Thread.Sleep(300);
                userHandler.SendAsync("si");
            }
            */
        }
    }
}
