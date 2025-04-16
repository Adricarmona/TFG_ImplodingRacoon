using System.Net.WebSockets;
using System.Security.Claims;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace implodingRacoon.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WebSocketController : Controller
    {

        public WebSocketController()
        { }

        [HttpGet]
        public async Task ConnectAsync()
        {

            if (HttpContext.WebSockets.IsWebSocketRequest)
            {
                // Aceptamos la solicitud
                WebSocket webSocket = await HttpContext.WebSockets.AcceptWebSocketAsync();

                Console.WriteLine("HE ENTRADO EN MI WEB SOCKET SUUUUUUUUU");

            }
            else
            {
                Console.WriteLine("Websoket fallado");
                HttpContext.Response.StatusCode = StatusCodes.Status400BadRequest;
            }


        }
    }
}
