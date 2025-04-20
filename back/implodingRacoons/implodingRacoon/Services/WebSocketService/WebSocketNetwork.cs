using System.Net.WebSockets;
using System.Text.Json;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Services.GamesService;

namespace implodingRacoon.Services.WebSocketService
{
    public class WebSocketNetwork
    {
        // Contador para asignar un id a cada WebSocketHandler
        // para mirar cuantos usuarios tiene concectados
        private static int _idCounter = 0;

        // Lista de WebSocketHandler (clase que gestiona cada WebSocket)
        private readonly List<WebSocketHandler> _handlers = new List<WebSocketHandler>();
        // Semáforo para controlar el acceso a la lista de WebSocketHandler
        private readonly SemaphoreSlim _semaphore = new SemaphoreSlim(1, 1);


        public async Task HandleAsync(WebSocket webSocket)
        {
            // Creamos un nuevo WebSocketHandler a partir del WebSocket recibido y lo añadimos a la lista
            WebSocketHandler handler = await AddWebsocketAsync(webSocket);

            // Notificamos a los usuarios que un nuevo usuario se ha conectado
            //await NotifyUserConnectedAsync(handler);

            // Esperamos a que el WebSocketHandler termine de manejar la conexión
            await handler.HandleAsync();
        }

        private async Task<WebSocketHandler> AddWebsocketAsync(WebSocket webSocket)
        {
            // Esperamos a que haya un hueco disponible
            await _semaphore.WaitAsync();

            // Sección crítica
            // Creamos un nuevo WebSocketHandler, nos suscribimos a sus eventos y lo añadimos a la lista
            WebSocketHandler handler = new WebSocketHandler(_idCounter, webSocket);
            handler.Disconnected += OnDisconnectedAsync;
            handler.MessageReceived += OnMessageReceivedAsync;
            _handlers.Add(handler);

            // Incrementamos el contador para el siguiente
            _idCounter++;

            // Liberamos el semáforo
            _semaphore.Release();

            return handler;
        }

        private Task NotifyUserConnectedAsync(WebSocketHandler newHandler)
        {
            // Lista donde guardar las tareas de envío de mensajes
            List<Task> tasks = new List<Task>();
            // Guardamos una copia de los WebSocketHandler para evitar problemas de concurrencia
            WebSocketHandler[] handlers = _handlers.ToArray();
            int totalHandlers = handlers.Length;

            string messageToNew = $"Hay {totalHandlers} usuarios conectados, tu id es {newHandler.Id}";
            string messageToOthers = $"Se ha conectado usuario con id {newHandler.Id}. En total hay {totalHandlers} usuarios conectados";

            // Enviamos un mensaje personalizado al nuevo usuario y otro al resto
            foreach (WebSocketHandler handler in handlers)
            {
                string message = handler.Id == newHandler.Id ? messageToNew : messageToOthers;

                tasks.Add(handler.SendAsync(message));
            }

            // Devolvemos una tarea que se completará cuando todas las tareas de envío de mensajes se completen
            return Task.WhenAll(tasks);
        }

        private async Task OnDisconnectedAsync(WebSocketHandler disconnectedHandler)
        {
            // Esperamos a que haya un hueco disponible
            await _semaphore.WaitAsync();

            // Sección crítica
            // Nos desuscribimos de los eventos y eliminamos el WebSocketHandler de la lista
            disconnectedHandler.Disconnected -= OnDisconnectedAsync;
            disconnectedHandler.MessageReceived -= OnMessageReceivedAsync;
            _handlers.Remove(disconnectedHandler);

            // Liberamos el semáforo
            _semaphore.Release();

            // Lista donde guardar las tareas de envío de mensajes
            List<Task> tasks = new List<Task>();
            // Guardamos una copia de los WebSocketHandler para evitar problemas de concurrencia
            WebSocketHandler[] handlers = _handlers.ToArray();

            /**
             * 
             * 
             *          CUANDO QUERAMOS NOTIFICAR O ALGO A UN USUARIO QUE SE QUIERE DESCONECTAR VA AQUI
             * 
             * 
             */

            // Esperamos a que todas las tareas de envío de mensajes se completen
            await Task.WhenAll(tasks);
        }

        private Task OnMessageReceivedAsync(WebSocketHandler userHandler, string message)
        {
            // Lista donde guardar las tareas de envío de mensajes
            List<Task> tasks = new List<Task>();
            // Guardamos una copia de los WebSocketHandler para evitar problemas de concurrencia
            WebSocketHandler[] handlers = _handlers.ToArray();

            /**
             * 
             *      Cuando queramos notificar un mensaje iria aqui
             * 
             */

            RecivedUserWebSocket receivedUser;

            try
            {
                receivedUser = JsonSerializer.Deserialize<RecivedUserWebSocket>(message);
            }
            catch (Exception ex)
            {
                receivedUser = new RecivedUserWebSocket();
                receivedUser.TypeMessage = "error";
            }

            switch (receivedUser.TypeMessage)
            {
                case "create":

                    Game partida = Games.anadirMesa();

                    userHandler.SendAsync("creada sala: "+partida.IdSala + "");

                    break;

                case "look":
                    foreach (Game gameLook in Games.mesas())
                    {
                        List<UserGame> lista = gameLook.cogerUsuariosMesa();

                        UsersInLobby usuersInLobby = new UsersInLobby
                        {
                            IdSala = gameLook.IdSala,
                            IdUsuario1 = lista.Count > 0 && lista[0].Id != null ? lista[0].Id : 0,
                            IdUsuario2 = lista.Count > 1 && lista[1].Id != null ? lista[1].Id : 0,
                            IdUsuario3 = lista.Count > 2 && lista[2].Id != null ? lista[2].Id : 0,
                            IdUsuario4 = lista.Count > 3 && lista[3].Id != null ? lista[3].Id : 0,
                            IdUsuario5 = lista.Count > 4 && lista[4].Id != null ? lista[4].Id : 0
                        };

                        userHandler.SendAsync(JsonSerializer.Serialize(usuersInLobby));
                    }
                    break;

                case "join":
                    /// primer identifier es la mesa
                    /// segundo identifier es el id del jugador
                    if (receivedUser.Identifier == null || receivedUser.Identifier2 == null)
                    {
                        userHandler.SendAsync("error json");
                        break;
                    }

                    Game mesa = Games.buscarMesa(Int32.Parse(receivedUser.Identifier));

                    if (mesa == null)
                    {
                        userHandler.SendAsync("mesa no existe");
                        break;
                    }

                    if (mesa.salaEmpezada == true)
                    {
                        userHandler.SendAsync("mesa ya empezada");
                        break;
                    }

                    if (mesa.cogerUsuariosMesa().Count >= 5)
                    {
                        userHandler.SendAsync("mesa llena");
                        break;
                    }

                    userHandler.IdUsuario = Int32.Parse(receivedUser.Identifier2);
                    Games.unirMesa(Int32.Parse(receivedUser.Identifier), Int32.Parse(receivedUser.Identifier2));

                    foreach (UserGame usuarios in mesa.cogerUsuariosMesa())
                    {
                        foreach (WebSocketHandler handler in _handlers)
                        {
                            if (handler.IdUsuario == usuarios.Id)
                            {
                                tasks.Add(handler.SendAsync("Usuario unido: "+userHandler.IdUsuario));
                            }
                        }
                    }

                    userHandler.SendAsync("Unido a la mesa: " + receivedUser.Identifier);

                    break;

                case "start":

                    if (receivedUser.Identifier == null)
                    {
                        userHandler.SendAsync("mesa no indicada");
                        break;
                    }

                    // empieza la partida con el id de la mesa dado
                    Game game = Games.buscarMesa(Int32.Parse(receivedUser.Identifier));

                    if (game == null)
                    {
                        userHandler.SendAsync("mesa no encontrada");
                        break;
                    }


                    game.empezarPartida(true);

                    foreach (UserGame usuarios in game.cogerUsuariosMesa())
                    {
                        foreach (WebSocketHandler handler in _handlers)
                        {
                            if (handler.IdUsuario == usuarios.Id)
                            {
                                tasks.Add(handler.SendAsync("La partida ha comenzado"));
                            }
                        }


                    }

                    break;

                case "test?":
                    userHandler.SendAsync("Funciona");
                    break;

                default:
                    userHandler.SendAsync("error json");
                    break;
            }

            // Devolvemos una tarea que se completará cuando todas las tareas de envío de mensajes se completen
            return Task.WhenAll(tasks);
        }
    }
}
