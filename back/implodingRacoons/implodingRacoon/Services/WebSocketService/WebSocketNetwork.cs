using System.Net.WebSockets;
using System.Text.Json;
using implodingRacoon.Models.Database.Dto;
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

                    Games.anadirMesa();
                    userHandler.SendAsync("creada");

                    break;

                case "look":

                    foreach (Game game in Games.mesas())
                    {
                        userHandler.SendAsync(game.IdSala+"");
                        List<UserGame> lista = game.cogerUsuariosMesa();
                        foreach (UserGame usuarios in lista)
                        {
                            userHandler.SendAsync(usuarios.Id+"");
                        }
                    }  

                    break;

                case "join":

                    Games.unirMesa(Int32.Parse(receivedUser.Identifier), Int32.Parse(receivedUser.Identifier2));
                    
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
