using System.Net.WebSockets;
using System.Security.Claims;
using implodingRacoon.Services.WebSocketService;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WebSocketController : Controller
    {

        private readonly WebSocketNetwork _webSocketNetwork;

        public WebSocketController(WebSocketNetwork webSocketNetwork)
        {
            _webSocketNetwork = webSocketNetwork;
        }

        [HttpGet]
        public async Task ConnectAsync()
        {

            if (HttpContext.WebSockets.IsWebSocketRequest)
            {
                // Aceptamos la solicitud
                WebSocket webSocket = await HttpContext.WebSockets.AcceptWebSocketAsync();

                await _webSocketNetwork.HandleAsync(webSocket);

            }
            else
            {
                Console.WriteLine("Websocket fallado");
                HttpContext.Response.StatusCode = StatusCodes.Status400BadRequest;
            }


        }
    }
}
