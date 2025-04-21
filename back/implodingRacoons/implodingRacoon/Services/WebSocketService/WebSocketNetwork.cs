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

            List<Game> mesas = Games.mesas();

            foreach (WebSocketHandler wsHandler in handlers)
            {

                foreach (Game mesa in mesas)
                {

                    foreach (UserGame userGame in mesa.cogerUsuariosMesa())
                    {
                        if (userGame == wsHandler.Usuario)
                        {
                            if (mesa.quitarUsuarioMesa(userGame) == "funciono")
                            {
                                tasks.Add(wsHandler.SendAsync("Usuario desconectado: " + userGame.Id + " Con id del ws:" + disconnectedHandler.Id));
                            }
                            else
                            {
                                 wsHandler.SendAsync("Usuario no encontrado: " + userGame.Id + " Con id del ws:" + disconnectedHandler.Id);
                            }


                        }
                    }

                }


            }

            // Esperamos a que todas las tareas de envío de mensajes se completen
            await Task.WhenAll(tasks);
        }

        private Task OnMessageReceivedAsync(WebSocketHandler userHandler, string message)
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

                    webSocketService.unirseMesa(receivedUser, userHandler, _handlers, tasks);

                    break;

                case "start":

                    webSocketService.empezarPartida(receivedUser, userHandler, handlers, tasks);

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
