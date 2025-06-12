using System.Net.WebSockets;
using System.Text.Json;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Services.GamesService;
using Microsoft.EntityFrameworkCore.Metadata;

namespace implodingRacoon.Services.WebSocketService
{
    public class WebSocketNetwork
    {
        private readonly IServiceProvider _serviceProvider;

        public WebSocketNetwork(IServiceProvider serviceProvider)
        {
            _serviceProvider = serviceProvider;
        }

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
            await NotifyUserConnectedAsync(handler);

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

            JsonWebsoket messageToNotify = new JsonWebsoket
            {
                type = 1,
                message = totalHandlers.ToString(),
            };
            
            string mensageToNotify = JsonSerializer.Serialize(messageToNotify);

            newHandler.SendAsync(mensageToNotify);

            // Enviamos un mensaje personalizado al nuevo usuario y otro al resto
            foreach (WebSocketHandler handler in handlers)
            {
                //string message = handler.Id == newHandler.Id ? messageToNew : messageToOthers;

                tasks.Add(handler.SendAsync(mensageToNotify));
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

            // para ver los usuarios desconectandose en la mesa
            int totalHandlers = handlers.Length;

            JsonWebsoket messageToNotify = new JsonWebsoket
            {
                type = 1,
                message = totalHandlers.ToString(),
            };
            string mensageToNotify = JsonSerializer.Serialize(messageToNotify);

            // Enviamos un mensaje personalizado al nuevo usuario y otro al resto
            foreach (WebSocketHandler handler in handlers)
            {
                //string message = handler.Id == newHandler.Id ? messageToNew : messageToOthers;

                tasks.Add(handler.SendAsync(mensageToNotify));
            }


            /*
             *  Mesas de partidas
             */

            
            List<Game> mesas = Games.mesas();

            foreach (WebSocketHandler wsHandler in handlers)
            {

                foreach (Game mesa in mesas)
                {

                    foreach (UserGame userGame in mesa.cogerUsuariosMesa().ToList())
                    {
                        if (userGame == wsHandler.Usuario)
                        {
                            mesa.quitarUsuarioMesa(userGame);

                        }
                    }

                }

                JsonWebsoket messageToEnviar = new JsonWebsoket
                {
                    type = 3,
                    message = true.ToString()
                };
                string mensageEnviar = JsonSerializer.Serialize(messageToEnviar);

                wsHandler.SendAsync(mensageEnviar);

            }
           

            // Esperamos a que todas las tareas de envío de mensajes se completen
            await Task.WhenAll(tasks);
        }

        private async Task<Task> OnMessageReceivedAsync(WebSocketHandler userHandler, string message)
        {
            // Lista donde guardar las tareas de envío de mensajes
            List<Task> tasks = new List<Task>();
            // Guardamos una copia de los WebSocketHandler para evitar problemas de concurrencia
            WebSocketHandler[] handlers = _handlers.ToArray();

            WebSocketService webSocketService = new WebSocketService();
            /**
             * 
             *      Cuando queramos notificar un mensaje iria aqui
             * 
             */

            ICollection<Carta> cartas;

            using (var scope = _serviceProvider.CreateScope())
            {
                var _wsHelper = scope.ServiceProvider.GetRequiredService<WSHelper>();

                cartas = await _wsHelper.getCartas();
            }

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

                    webSocketService.crearSala(userHandler, receivedUser);

                    break;

                case "look":

                    foreach (Game gameLook in Games.mesas())
                    {
                        List<UserGame> lista = gameLook.cogerUsuariosMesa();

                        UsersInLobby usuersInLobby = new UsersInLobby
                        {
                            IdSala = gameLook.IdSala,
                            IdHost = lista != null && lista.Count > 0 && lista[0] != null ? lista[0].Id : 0,
                            IdUsuario1 = lista != null && lista.Count > 1 && lista[1] != null ? lista[1].Id : 0,
                            IdUsuario2 = lista != null && lista.Count > 2 && lista[2] != null ? lista[2].Id : 0,
                            IdUsuario3 = lista != null && lista.Count > 3 && lista[3] != null ? lista[3].Id : 0,
                            IdUsuario4 = lista != null && lista.Count > 4 && lista[4] != null ? lista[4].Id : 0,
                            IdUsuario5 = lista != null && lista.Count > 5 && lista[5] != null ? lista[5].Id : 0
                        };

                        userHandler.SendAsync(JsonSerializer.Serialize(usuersInLobby));
                    }
                    break;

                case "join":

                    webSocketService.unirseMesa(receivedUser, userHandler, handlers);

                    break;

                case "start":

                    webSocketService.empezarPartida(receivedUser, userHandler, handlers, tasks, cartas.ToList());

                    break;


                case "leave":

                    webSocketService.salirPartida(receivedUser, userHandler);

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
